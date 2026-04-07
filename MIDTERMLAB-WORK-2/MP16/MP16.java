import java.io.*;
import java.util.*;

public class MP16 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== MP16 - Random 10 Rows ===");
        System.out.print("Enter CSV file path: ");
        String path = sc.nextLine();

        List<String> dataLines = new ArrayList<>();
        String header = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    header = line;
                    first = false;
                } else {
                    dataLines.add(line);
                }
            }

            // Shuffle for random selection
            Collections.shuffle(dataLines);
            System.out.println("\nHeader: " + header);
            System.out.println("\nRandom 10 rows:");
            System.out.println("=".repeat(80));
            for (int i = 0; i < Math.min(10, dataLines.size()); i++) {
                System.out.printf("[%d] %s\n", i+1, dataLines.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
    // Comments: Loads CSV, shuffles data rows, displays header + 10 random rows
}

