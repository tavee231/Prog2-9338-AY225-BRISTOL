import java.io.*;
import java.util.*;

public class MP09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== MP09 Dataset Statistics ===");
        System.out.print("Enter CSV file path: ");
        String path = sc.nextLine();

        int totalRows = 0, totalCols = 0, passCount = 0;
        int scoreSum = 0, maxScore = 0, minScore = Integer.MAX_VALUE;
        Map<String, Integer> examCounts = new HashMap<>();
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (first) {
                    totalCols = fields.length;
                    first = false;
                } else {
                    totalRows++;
                    rows.add(fields);
                    // Assume Score in col 6, Result in col 7, Exam in col 3
                    if (fields.length > 7) {
                        try {
                            int score = Integer.parseInt(fields[6].trim());
                            scoreSum += score;
                            maxScore = Math.max(maxScore, score);
                            minScore = Math.min(minScore, score);
                            if (fields[7].contains("PASS")) passCount++;
                        } catch (NumberFormatException ignored) {}
                    }
                    // Exam count
                    if (fields.length > 3) {
                        String exam = fields[3].trim();
                        examCounts.put(exam, examCounts.getOrDefault(exam, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            sc.close();
            return;
        }

        double avgScore = totalRows > 0 ? (double) scoreSum / totalRows : 0;
        double passRate = totalRows > 0 ? (double) passCount / totalRows * 100 : 0;

        System.out.println("\n=== STATISTICS ===");
        System.out.printf("Total data rows: %d\n", totalRows);
        System.out.printf("Total columns: %d\n", totalCols);
        System.out.printf("Average score: %.1f\n", avgScore);
        System.out.printf("Score range: %d - %d\n", minScore, maxScore);
        System.out.printf("Pass rate: %.1f%%\n", passRate);
        System.out.println("Top exams:");
        examCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(e -> System.out.printf("  %s: %d\n", e.getKey(), e.getValue()));

        sc.close();
    }
    // Comments: Comprehensive stats with score parsing, pass rate, exam distribution, error handling
}

