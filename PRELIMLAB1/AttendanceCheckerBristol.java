package PRELIMLAB1;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceCheckerBristol {

    private static HashSet<String> nameSet = new HashSet<>();
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(900, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout(10, 10));

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(18);
        JTextField courseField = new JTextField(18);

        JTextField timeField = new JTextField(18);
        timeField.setEditable(false);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timeField.setText(LocalDateTime.now().format(formatter));

        SignaturePanel signaturePanel = new SignaturePanel();

        JButton submitBtn = new JButton("Submit");
        JButton clearSigBtn = new JButton("Clear Signature");
        JButton resetBtn = new JButton("Reset Attendance");
        JButton exitBtn = new JButton("Exit");

        // ---- Layout form ----
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Course / Year:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Time In:"), gbc);
        gbc.gridx = 1;
        formPanel.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("E-Signature:"), gbc);
        gbc.gridx = 1;
        formPanel.add(signaturePanel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(clearSigBtn, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(submitBtn);
        btnPanel.add(resetBtn);
        btnPanel.add(exitBtn);

        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(btnPanel, gbc);

        // ================= TABLE PANEL =================
        String[] columns = {"Name", "Course / Year", "Time In", "Signature"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) return ImageIcon.class; // Signature column
                return String.class;
            }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(60); // Make room for signature thumbnail
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Attendance Records"));

        // ================= ADD TO FRAME =================
        frame.add(formPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ================= ACTIONS =================
        clearSigBtn.addActionListener(e -> signaturePanel.clear());

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();
            String timeIn = timeField.getText();

            if (name.isEmpty() || course.isEmpty() || !signaturePanel.hasSignature()) {
                JOptionPane.showMessageDialog(frame,
                        "Complete all fields and sign.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nameSet.contains(name.toLowerCase())) {
                JOptionPane.showMessageDialog(frame,
                        "This name already exists.",
                        "Duplicate",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            nameSet.add(name.toLowerCase());

            // Save signature as PNG
            String fileName = name.replaceAll("\\s+", "_") + "_signature.png";
            saveSignature(signaturePanel, fileName);

            // Create thumbnail for table
            ImageIcon signatureIcon = new ImageIcon(new ImageIcon(fileName)
                    .getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH));

            // Add to table
            tableModel.addRow(new Object[]{name, course, timeIn, signatureIcon});

            // Save to text/csv files
            saveToFiles(name, course, timeIn);

            JOptionPane.showMessageDialog(frame, "Attendance recorded!");

            // Reset fields
            nameField.setText("");
            courseField.setText("");
            signaturePanel.clear();
            timeField.setText(LocalDateTime.now().format(formatter));
        });

        resetBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to reset the attendance list?",
                    "Confirm Reset",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.setRowCount(0);
                nameSet.clear();
                JOptionPane.showMessageDialog(frame, "Attendance list reset.");
            }
        });

        exitBtn.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    // ================= FILE SAVE =================
    private static void saveToFiles(String name, String course, String time) {
        try (FileWriter txt = new FileWriter("attendance.txt", true);
             FileWriter csv = new FileWriter("attendance.csv", true)) {

            txt.write("Name: " + name + "\nCourse: " + course +
                    "\nTime In: " + time + "\n\n");
            csv.write(name + "," + course + "," + time + "\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // ================= SAVE SIGNATURE =================
    private static void saveSignature(SignaturePanel panel, String fileName) {
        try {
            BufferedImage image = new BufferedImage(
                    panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            panel.paint(g2d);
            g2d.dispose();
            javax.imageio.ImageIO.write(image, "png", new java.io.File(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // ================= SIGNATURE PANEL =================
    static class SignaturePanel extends JPanel {
        private BufferedImage image;
        private Graphics2D g2d;
        private boolean signed = false;
        private int x1, y1;

        public SignaturePanel() {
            setPreferredSize(new Dimension(300, 100));
            setBorder(BorderFactory.createLineBorder(Color.GRAY));

            image = new BufferedImage(300, 100, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    x1 = e.getX();
                    y1 = e.getY();
                    signed = true;
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int x2 = e.getX();
                    int y2 = e.getY();
                    g2d.drawLine(x1, y1, x2, y2);
                    x1 = x2;
                    y1 = y2;
                    repaint();
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }

        public void clear() {
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            g2d.setComposite(AlphaComposite.SrcOver);
            signed = false;
            repaint();
        }

        public boolean hasSignature() {
            return signed;
        }
    }
}
