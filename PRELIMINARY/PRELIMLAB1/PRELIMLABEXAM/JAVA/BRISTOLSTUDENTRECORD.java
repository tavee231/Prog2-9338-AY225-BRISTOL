/**
 * Programmer Identifier: [Bristol, Steven Jandrei M.] [25-2154 -508]
 */
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 
public class BRISTOLSTUDENTRECORD extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtID, txtName, txtGrade;
 
    public BRISTOLSTUDENTRECORD() {
        // Requirement: Title with Identifier
        setTitle("Records - [Your Name] [Your ID]");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
        // UI Setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        table = new JTable(model);
       
        JPanel inputPanel = new JPanel();
        txtID = new JTextField(5);
        txtName = new JTextField(10);
        txtGrade = new JTextField(5);
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
 
        inputPanel.add(new JLabel("ID:")); inputPanel.add(txtID);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("Grade:")); inputPanel.add(txtGrade);
        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);
 
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
 
        // CRUD: Create
        btnAdd.addActionListener(e -> {
            model.addRow(new Object[]{txtID.getText(), txtName.getText(), txtGrade.getText()});
            txtID.setText(""); txtName.setText(""); txtGrade.setText("");
        });
 
        // CRUD: Delete
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) model.removeRow(selectedRow);
        });
 
        loadData();
    }
 
    private void loadData() {
        // Requirement: Try-catch for File I/O
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Mapping: StudentID, first_name + last_name, PRELIM EXAM
                model.addRow(new Object[]{data[0], data[1] + " " + data[2], data[6]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading CSV: " + e.getMessage());
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BRISTOLSTUDENTRECORD().setVisible(true));
    }
}