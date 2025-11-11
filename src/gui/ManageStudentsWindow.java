package gui;
import javax.swing.*;
import util.DarkTheme;
import javax.swing.table.DefaultTableModel;
import util.DarkTheme;
import java.awt.*;
import util.DarkTheme;
import java.util.List;
import util.DarkTheme;
import model.Student;
import util.DarkTheme;
import dao.StudentDAO;
import util.DarkTheme;
public class ManageStudentsWindow extends JFrame {
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    public ManageStudentsWindow() {
        this.studentDAO = new StudentDAO();
        setTitle("Manage Students");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        loadStudents();
    }
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("Manage Students");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton addButton = new JButton("+ Add Student");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        addButton.setForeground(DarkTheme.BG_DARK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddStudentDialog());
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);
        String[] columnNames = { "ID", "Name", "Email" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(DarkTheme.BUTTON_PRIMARY);
        table.getTableHeader().setForeground(DarkTheme.BG_DARK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton.setBackground(DarkTheme.BUTTON_DANGER);
        deleteButton.setForeground(DarkTheme.BG_DARK);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteSelectedStudent());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshButton.addActionListener(e -> loadStudents());
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        for (Student student : students) {
            Object[] row = {
                    student.getStudentId(),
                    student.getName(),
                    student.getEmail()
            };
            tableModel.addRow(row);
        }
    }
    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add New Student", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        saveButton.setForeground(DarkTheme.BG_DARK);
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Password must be at least 6 characters!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (studentDAO.emailExists(email)) {
                JOptionPane.showMessageDialog(dialog, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            if (studentDAO.addStudent(student)) {
                JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                loadStudents();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(cancelButton);
        panel.add(saveButton);
        dialog.add(panel);
        dialog.setVisible(true);
    }
    private void deleteSelectedStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student: " + studentName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.deleteStudent(studentId)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
