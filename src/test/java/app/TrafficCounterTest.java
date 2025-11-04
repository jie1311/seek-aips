package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TrafficCounterTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @TempDir File tempDir;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testProcessFile() throws IOException {
    // Create a file with records spanning multiple days
    File testFile = new File(tempDir, "test_data.txt");
    try (FileWriter writer = new FileWriter(testFile)) {
      writer.write("2025-11-01T05:00:00 5\n");
      writer.write("2025-11-01T05:30:00 12\n");
      writer.write("2025-11-01T06:00:00 14\n");
      writer.write("2025-11-01T06:30:00 15\n");
      writer.write("2025-11-01T07:00:00 25\n");
      writer.write("2025-11-02T06:00:00 20\n");
      writer.write("2025-11-02T06:30:00 10\n");
      writer.write("2025-11-03T07:00:00 30\n");
    }

    TrafficCounter.processFile(testFile.getAbsolutePath());

    String output = outContent.toString();

    // Verify total cars
    assertTrue(output.contains("Total cars: 131"));

    // Verify cars per day
    assertTrue(output.contains("Cars per day:"));
    assertTrue(output.contains("2025-11-01 71"));
    assertTrue(output.contains("2025-11-02 30"));
    assertTrue(output.contains("2025-11-03 30"));

    // Verify top 3 busiest records
    assertTrue(output.contains("Top 3 half hours with most cars:"));
    assertTrue(output.contains("2025-11-03T07:00:00 30"));
    assertTrue(output.contains("2025-11-01T07:00:00 25"));
    assertTrue(output.contains("2025-11-02T06:00:00 20"));

    // Verify least busy period and first record
    assertTrue(output.contains("1.5 hour period with least cars:"));
    assertTrue(output.contains("2025-11-01T05:00:00 5"));
    assertTrue(output.contains("2025-11-01T05:30:00 12"));
    assertTrue(output.contains("2025-11-01T06:00:00 14"));
  }

  @Test
  public void testProcessFileWithEmptyFile() throws IOException {
    // Create an empty file
    File testFile = new File(tempDir, "empty.txt");
    testFile.createNewFile();

    TrafficCounter.processFile(testFile.getAbsolutePath());

    String output = outContent.toString();
    assertTrue(output.contains("No records found in the file."));
  }

  @Test
  public void testProcessFileWithNonExistentFile() {
    // Test with a file that doesn't exist
    assertThrows(
        IOException.class,
        () -> {
          TrafficCounter.processFile("/nonexistent/path/file.txt");
        });
  }
}
