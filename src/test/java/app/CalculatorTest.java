package app;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

  @Test
  public void testTotalCars() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 1, 5, 0), 5),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 11),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 0), 14));
    assertEquals(30, Calculator.totalCars(records));
  }

  @Test
  public void testCarsPerDay() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 3, 5, 0), 10),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 20),
            new Record(LocalDateTime.of(2025, 11, 2, 6, 0), 15),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 0), 5));

    Map<LocalDate, Integer> result = Calculator.carsPerDay(records);

    assertEquals(3, result.size());
    assertEquals(25, result.get(LocalDate.of(2025, 11, 1)));
    assertEquals(15, result.get(LocalDate.of(2025, 11, 2)));
    assertEquals(10, result.get(LocalDate.of(2025, 11, 3)));
  }

  @Test
  public void testBusiestRecords() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 1, 5, 0), 5),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 11),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 0), 20),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 30), 8));

    List<Record> result = Calculator.busiestRecords(records, 2);

    assertEquals(2, result.size());
    assertEquals(20, result.get(0).getCount());
    assertEquals(11, result.get(1).getCount());
  }

  @Test
  public void testBusiestRecordsWithInsufficientRecords() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 1, 5, 0), 10),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 20));

    assertThrows(IllegalArgumentException.class, () -> Calculator.busiestRecords(records, 3));
  }

  @Test
  public void testLeastBusyPeriod() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 1, 5, 0), 10),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 20),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 0), 30),
            new Record(LocalDateTime.of(2025, 11, 1, 6, 30), 5),
            new Record(LocalDateTime.of(2025, 11, 1, 7, 0), 8),
            new Record(LocalDateTime.of(2025, 11, 1, 7, 30), 2));

    List<Record> result = Calculator.leastBusyPeriod(records, 3);

    assertEquals(3, result.size());
    assertEquals(LocalDateTime.of(2025, 11, 1, 6, 30), result.get(0).getTimestamp());
  }

  @Test
  public void testLeastBusyPeriodWithInsufficientRecords() {
    List<Record> records =
        Arrays.asList(
            new Record(LocalDateTime.of(2025, 11, 1, 5, 0), 10),
            new Record(LocalDateTime.of(2025, 11, 1, 5, 30), 20));

    assertThrows(IllegalArgumentException.class, () -> Calculator.leastBusyPeriod(records, 3));
  }
}
