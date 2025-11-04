package app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RecordReaderTest {

  @Test
  public void testReadRecords() throws IOException {
    String input =
        "2025-11-01T05:00:00 5\n" + "2025-11-01T05:30:00 11\n" + "2025-11-01T06:00:00 14\n";

    List<Record> records = RecordReader.readRecords(new StringReader(input));

    assertEquals(3, records.size());
    assertEquals(LocalDateTime.of(2025, 11, 1, 5, 0), records.get(0).getTimestamp());
    assertEquals(5, records.get(0).getCount());
  }
}
