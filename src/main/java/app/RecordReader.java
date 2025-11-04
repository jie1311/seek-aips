package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecordReader {
  public static List<Record> readRecords(Reader reader) throws IOException {
    List<Record> records = new ArrayList<>();

    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        try {
          records.add(parseLine(line));
        } catch (Exception e) {
          throw new IllegalArgumentException("Error parsing line: " + line, e);
        }
      }
    }

    return records;
  }

  private static Record parseLine(String line) {
    String[] parts = line.split(" ");
    LocalDateTime timestamp = LocalDateTime.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    int count = Integer.parseInt(parts[1]);

    return new Record(timestamp, count);
  }
}
