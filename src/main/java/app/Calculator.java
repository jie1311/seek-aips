package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculator {
  public static int totalCars(List<Record> records) {
    return records.stream().mapToInt(Record::getCount).sum();
  }

  public static Map<LocalDate, Integer> carsPerDay(List<Record> records) {
    Map<LocalDate, Integer> dailyMap = new HashMap<>();

    for (Record record : records) {
      LocalDate date = record.getTimestamp().toLocalDate();
      dailyMap.merge(date, record.getCount(), Integer::sum);
    }

    return dailyMap;
  }

  public static List<Record> busiestRecords(List<Record> records, int limit) {
    if (records.size() < limit) {
      throw new IllegalArgumentException("At least " + limit + " records are required.");
    }

    return records.stream()
        .sorted((r1, r2) -> Integer.compare(r2.getCount(), r1.getCount()))
        .limit(limit).collect(Collectors.toList());
  }

  public static List<Record> leastBusyPeriod(List<Record> records, int window) {
    if (records.size() < window) {
      throw new IllegalArgumentException("At least " + window + " records are required.");
    }

    // first window sum
    int currentSum = 0;
    for (int i = 0; i < window; i++) {
      currentSum += records.get(i).getCount();
    }

    // sliding window
    int minSum = currentSum;
    int minIndex = 0;

    for (int i = 1; i <= records.size() - window; i++) {
      currentSum = currentSum 
        - records.get(i - 1).getCount() 
        + records.get(i + window - 1).getCount();
      if (currentSum < minSum) {
        minSum = currentSum;
        minIndex = i;
      }
    }

    return new ArrayList<>(records.subList(minIndex, minIndex + window));
  }
}
