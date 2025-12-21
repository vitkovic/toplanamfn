package toplana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LatestPointReading {

    static class Reading {
        String pointCode;      // cleaned code, e.g. "072910026"
        LocalDateTime time;
        double value;

        Reading(String pointCode, LocalDateTime time, double value) {
            this.pointCode = pointCode;
            this.time = time;
            this.value = value;
        }
    }

    public void readFile() {
        String csvFile = Paths.get("c:\\Utrosena E sa svih merila - dnevni.csv").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        List<Reading> readings = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // skip header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length >= 3) {
                    // Example Point name: "07.291.0026|BNT27A ..."
                    String rawPoint = parts[0].trim();
                    String pointCode = rawPoint.split("\\|")[0]        // take "07.291.0026"
                                             .replace(".", "");       // remove dots → "072910026"

                    LocalDateTime time = LocalDateTime.parse(parts[1].trim(), formatter);
                    double value = Double.parseDouble(parts[2].trim());
                    readings.add(new Reading(pointCode, time, value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Keep only the latest reading per Point
        Map<String, Reading> latestReadings = readings.stream()
                .collect(Collectors.toMap(
                        r -> r.pointCode,
                        r -> r,
                        (r1, r2) -> r1.time.isAfter(r2.time) ? r1 : r2
                ));

        // Sort by point code
        latestReadings.values().stream()
                .sorted(Comparator.comparing(r -> r.pointCode));
    }
}