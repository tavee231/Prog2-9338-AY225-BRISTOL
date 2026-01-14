import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AttendanceChecker {

    private static ArrayList<String> attendanceList = new ArrayList<>();

    public static void main(String[] args) {
    
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(400, 400);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLocationRelativeTo(null); 

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Attendance Name:");
        JTextField nameField = new JTextField();

        JLabel courseLabel = new JLabel("Course / Year:");
        JTextField courseField = new JTextField();

        JLabel timeLabel = new JLabel("Time In:");
        JTextField timeField = new JTextField();

        JLabel signatureLabel = new JLabel("E-Signature:");

        SignaturePanel signaturePanel = new SignaturePanel();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(courseLabel);
        panel.add(courseField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(signatureLabel);
        panel.add(signaturePanel); 

        frame.add(panel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        JButton exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();
                String timeIn = timeField.getText().trim();
                Image signatureImage = signaturePanel.getSignatureImage();

                if (name.isEmpty() || course.isEmpty() || timeIn.isEmpty() || signatureImage == null) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields and provide an e-signature.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
             
                    String attendanceInfo = "Name: " + name + "\nCourse/Year: " + course + "\nTime In: " + timeIn + "\nE-Signature: Signature Provided";
                    
                    attendanceList.add(attendanceInfo);

                    JOptionPane.showMessageDialog(frame, "Attendance Submitted:\n"
                            + attendanceInfo);


                    showAttendanceList();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  
            }
        });

        frame.setVisible(true);
    }

    static class SignaturePanel extends JPanel {
        private BufferedImage image;
        private Graphics2D g2d;
        private int x1, y1, x2, y2;

        public SignaturePanel() {
    
            setPreferredSize(new Dimension(300, 100));
            image = new BufferedImage(300, 100, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    x1 = e.getX();
                    y1 = e.getY();
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    x2 = e.getX();
                    y2 = e.getY();
                    g2d.drawLine(x1, y1, x2, y2);
                    x1 = x2;
                    y1 = y2;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }

        public Image getSignatureImage() {
            return image.getWidth() == 0 || image.getHeight() == 0 ? null : image;
        }
    }

    private static void showAttendanceList() {
        StringBuilder listBuilder = new StringBuilder("Attendance List:\n\n");
        for (String record : attendanceList) {
            listBuilder.append(record).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, listBuilder.toString(), "Attendance List", JOptionPane.INFORMATION_MESSAGE);
    }
}
