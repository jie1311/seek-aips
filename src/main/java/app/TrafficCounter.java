package app;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class TrafficCounter {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Eactly one argument expected: <input_file_path>");
      System.exit(1);
    }

    try {
      processFile(args[0]);
    } catch (Exception e) {
      System.err.println("Error processing file: " + args[0] + ": " + e.getMessage());
      System.exit(1);
    }
  }

  public static void processFile(String filename) throws IOException {
    List<Record> records = RecordReader.readRecords(new FileReader(filename));

    if (records.isEmpty()) {
      System.out.println("No records found in the file.");
      return;
    }

    // Calculate and output total cars
    int totalCars = Calculator.totalCars(records);
    System.out.println("Total cars: " + totalCars);
    System.out.println();

    // Calculate and output cars per day
    Map<LocalDate, Integer> carsPerDay = Calculator.carsPerDay(records);
    System.out.println("Cars per day:");
    carsPerDay.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    System.out.println();

    // Calculate and output top 3 busiest records
    if (records.size() >= 3) {
      List<Record> top3BusiestRecords = Calculator.busiestRecords(records, 3);
      System.out.println("Top 3 half hours with most cars:");
      for (Record record : top3BusiestRecords) {
        System.out.println(
            record.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " + record.getCount());
      }
    } else {
      System.out.println("Insufficient records to calculate top 3 half hours.");
    }
    System.out.println();

    // Calculate and output least busy 1.5 hour period
    if (records.size() >= 3) {
      List<Record> leastBusy90Mins = Calculator.leastBusyPeriod(records, 3);
      System.out.println("1.5 hour period with least cars:");
      for (Record record : leastBusy90Mins) {
        System.out.println(
            record.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " " + record.getCount());
      }
    } else {
      System.out.println("Insufficient records to calculate 1.5 hour period.");
    }
  }
}
