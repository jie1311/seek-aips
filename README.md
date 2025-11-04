# Traffic Counter

A Java application that analyzes vehicle traffic data from timestamped records.

## Features

- Calculate total number of cars
- Calculate cars per day
- Calculate top 3 busiest half-hour periods
- Calculate least busy 1.5-hour period 

## Requirements

- Java 11 or higher
- Maven 3.6 or higher (optional, for Maven-based workflow)

## Build and Run

### With Maven

Run the project:
```bash
mvn clean package
java -cp target/classes app.TrafficCounter sample_data.txt
```

Run tests:
```bash
mvn test
```

### With Java directly

Run the project:
```bash
javac -d bin $(find src/main/java -name "*.java")
java -cp bin app.TrafficCounter sample_data.txt
```

## Input Format

The input file should contain lines with local timestamp and count:
```
2025-11-01T05:00:00 5
2025-11-01T05:30:00 12
2025-11-01T06:00:00 14
```




