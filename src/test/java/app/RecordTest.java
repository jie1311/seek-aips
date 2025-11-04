package app;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class RecordTest {

  @Test
  public void testNewRecord() {
    LocalDateTime timestamp = LocalDateTime.of(2025, 11, 1, 5, 0);
    Record record = new Record(timestamp, 10);

    assertEquals(timestamp, record.getTimestamp());
    assertEquals(10, record.getCount());
  }
}
