import java.io.*;
import java.util.*;

public class MP05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== MP05 Column Extractor ===");
        System.out.print("Enter CSV file path: ");
        String path = sc.nextLine();
        System.out.print("Enter column index (0-based): ");
        int colIdx = sc.nextInt();
        sc.nextLine();

        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",",-1); // -1 keeps empty trailing fields
                if (first) {
                    System.out.println("Headers: " + String.join(" | ", fields));
                    first = false;
                } else {
                    rows.add(fields);
                }
            }
            System.out.println("\nColumn " + colIdx + ":");
            System.out.println("=".repeat(50));
            for (int i = 0; i < Math.min(20, rows.size()); i++) {
                String[] row = rows.get(i);
                if (colIdx < row.length) {
                    System.out.println(row[colIdx].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
    // Comments: Basic CSV column extraction with user prompt, header display, limited output
}

