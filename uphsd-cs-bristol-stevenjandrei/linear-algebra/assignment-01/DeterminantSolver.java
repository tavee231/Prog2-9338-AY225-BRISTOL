/**
 * =====================================================
 * Student Name     : [BRISTOL, STEVEN JANDREI M.]
 * Course           : BSCSIT 1203 Programming 2
 * Assignment       : Programming Assignment 1 — 3x3 Matrix Determinant Solver
 * School           : University of Perpetual Help System DALTA, Molino Campus
 * Date             : March 24, 2026
 * GitHub Repo      : https://github.com/[lobriqwds_]/uphsd-cs-[bristol]-[firstname]
 *
 * Description:
 * This program computes the determinant of a hardcoded 3x3 matrix assigned
 * to [BRISTOL, STEVEN JANDREI M.] for Math 101. The solution is computed using cofactor
 * expansion along the first row.
 * =====================================================
 */
public class DeterminantSolver {

    // ── SECTION 1: Matrix Declaration ───────────────────────────────────
    // Corrected: Removed the illegal square brackets from the values.
    static int[][] matrix = {
        { 1, 2, 3 },   // Row 1 of assigned matrix
        { 4, 5, 6 },   // Row 2 of assigned matrix
        { 7, 8, 9 }    // Row 3 of assigned matrix
    };

    // ── SECTION 2: 2×2 Determinant Helper ───────────────────────────────
    static int computeMinor(int a, int b, int c, int d) {
        return (a * d) - (b * c);
    }

    // ── SECTION 3: Matrix Printer ────────────────────────────────────────
    static void printMatrix(int[][] m) {
        System.out.println("┌               ┐");
        for (int[] row : m) {
            System.out.printf("│  %2d  %2d  %2d  │%n", row[0], row[1], row[2]);
        }
        System.out.println("└               ┘");
    }

    // ── SECTION 4: Step-by-Step Determinant Solver ──────────────────────
    static void solveDeterminant(int[][] m) {
        System.out.println("=".repeat(52));
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: [BRISTOL, STEVEN JANDREI M.]");
        System.out.println("  Assigned Matrix:");
        System.out.println("=".repeat(52));
        printMatrix(m);
        System.out.println("=".repeat(52));

        // Step 1: Minor M11
        int minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        System.out.printf("  Step 1 — Minor M₁₁: det([%d,%d],[%d,%d]) = (%d×%d)-(%d×%d) = %d%n",
            m[1][1], m[1][2], m[2][1], m[2][2],
            m[1][1], m[2][2], m[1][2], m[2][1], minor11);

        // Step 2: Minor M12
        int minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        System.out.printf("  Step 2 — Minor M₁₂: det([%d,%d],[%d,%d]) = (%d×%d)-(%d×%d) = %d%n",
            m[1][0], m[1][2], m[2][0], m[2][2],
            m[1][0], m[2][2], m[1][2], m[2][0], minor12);

        // Step 3: Minor M13
        int minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
        System.out.printf("  Step 3 — Minor M₁₃: det([%d,%d],[%d,%d]) = (%d×%d)-(%d×%d) = %d%n",
            m[1][0], m[1][1], m[2][0], m[2][1],
            m[1][0], m[2][1], m[1][1], m[2][0], minor13);

        // Cofactor Terms
        int c11 =  m[0][0] * minor11;
        int c12 = -m[0][1] * minor12;
        int c13 =  m[0][2] * minor13;

        System.out.println();
        System.out.printf("  Cofactor C₁₁ = (+1) × %d × %d = %d%n", m[0][0], minor11, c11);
        System.out.printf("  Cofactor C₁₂ = (-1) × %d × %d = %d%n", m[0][1], minor12, c12);
        System.out.printf("  Cofactor C₁₃ = (+1) × %d × %d = %d%n", m[0][2], minor13, c13);

        // Final Determinant
        int det = c11 + c12 + c13;
        System.out.printf("%n  det(M) = %d + (%d) + %d%n", c11, c12, c13);
        System.out.println("=".repeat(52));
        System.out.printf("  ✓  DETERMINANT = %d%n", det);

        if (det == 0) {
            System.out.println("  ⚠ The matrix is SINGULAR — it has no inverse.");
        }
        System.out.println("=".repeat(52));
    }

    public static void main(String[] args) {
        solveDeterminant(matrix);
    }
}