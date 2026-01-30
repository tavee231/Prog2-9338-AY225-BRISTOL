import java.util.Scanner;

public class PrelimGradeCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printHeader();

            // Read validated inputs
            double attendance = readDoubleInRange(sc, " ENTER ATTENDANCE 0-100: ", 0, 100);
            double lab1 = readDoubleInRange(sc, " ENTER LAB 1 SCORE 0-100: ", 0, 100);
            double lab2 = readDoubleInRange(sc, " ENTER LAB 2 SCORE 0-100: ", 0, 100);
            double lab3 = readDoubleInRange(sc, " ENTER LAB 3 SCORE 0-100: ", 0, 100);

            calculateAndDisplay(attendance, lab1, lab2, lab3);

            // Casio-style navigation
            System.out.print("\n [AC] New Calc  [OFF] Exit (any key/OFF): ");
            String choice = sc.nextLine().trim().toUpperCase();
            if (choice.equals("OFF")) {
                running = false;
                System.out.println(" SYSTEM POWER OFF...");
            }
        }

        sc.close();
    }

    private static double readDoubleInRange(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value < min || value > max) {
                    System.out.printf("  [ ERROR ] Value must be between %.0f and %.0f. Try again.%n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  [ ERROR ] Invalid number. Please enter numeric values only.");
            }
        }
    }

    private static void printHeader() {
        System.out.println("\n==================================");
        System.out.println("|        CASIO  fx-991 JAVA      |");
        System.out.println("|--------------------------------|");
        System.out.println("|  PRELIMINARY GRADE ANALYZER    |");
        System.out.println("==================================");
    }

    private static void calculateAndDisplay(double att, double l1, double l2, double l3) {
        // Compute Logic
        double labAverage = (l1 + l2 + l3) / 3.0;
        double classStanding = (att * 0.40) + (labAverage * 0.60);

        // Required Exams: (Target - (CS * 0.70)) / 0.30
        double reqPass = (75 - (classStanding * 0.70)) / 0.30;
        double reqExc = (100 - (classStanding * 0.70)) / 0.30;

        String passRemark = "";

        // Logic Checks & Clamping
        if (reqPass > 100) {
            reqPass = 100;
            passRemark = " (IMPOSSIBLE)";
        } else if (reqPass <= 0) {
            reqPass = 0;
            passRemark = " (PASSED)";
        }

        if (reqExc > 100) reqExc = 100;
        else if (reqExc <= 0) reqExc = 0;

        // Result Screen
        System.out.println("\n----------------------------------");
        System.out.println("           --- RESULTS ---        ");
        System.out.printf(" CLASS STANDING : %10.2f%n", classStanding);
        System.out.printf(" LAB AVERAGE    : %10.2f%n", labAverage);
        System.out.println("----------------------------------");
        System.out.printf(" TO PASS 75     : %10.2f%%%s%n", reqPass, passRemark);
        System.out.printf(" TO EXCEL 100   : %10.2f%%%n", reqExc);
        System.out.println("----------------------------------");
    }
}