package app;

import java.time.LocalDateTime;

public class Record {
  private final LocalDateTime timestamp;
  private final int count;

  public Record(LocalDateTime timestamp, int count) {
    this.timestamp = timestamp;
    this.count = count;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getCount() {
    return count;
  }
}
