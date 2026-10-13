package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    
    public static List<String[]> readCsv(String filePath) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                // Basic CSV parser handling quoted commas
                List<String> values = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                boolean inQuotes = false;
                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        values.add(sb.toString().trim());
                        sb = new StringBuilder();
                    } else {
                        sb.append(c);
                    }
                }
                values.add(sb.toString().trim());
                
                String[] valArray = values.toArray(new String[0]);
                records.add(valArray);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + filePath + " - " + e.getMessage());
        }
        return records;
    }
}
