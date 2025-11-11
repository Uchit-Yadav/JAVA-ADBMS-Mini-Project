package gui;
import javax.swing.*;
import java.awt.*;
import service.StudentService;
import service.AdminService;
import model.Student;
import util.DarkTheme;
public class LoginFrame extends JFrame {
    private StudentService studentService = new StudentService();
    private AdminService adminService = new AdminService();
    public LoginFrame() {
        setTitle("Student Examination System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(DarkTheme.BG_DARK);
        JLabel titleLabel = new JLabel("Student Examination System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(DarkTheme.ACCENT_BLUE);
        String[] loginTypes = { "Student", "Admin" };
        JComboBox<String> loginTypeCombo = new JComboBox<>(loginTypes);
        loginTypeCombo.setBackground(DarkTheme.INPUT_BG);
        loginTypeCombo.setForeground(DarkTheme.TEXT_PRIMARY);
        loginTypeCombo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? DarkTheme.BUTTON_PRIMARY : DarkTheme.INPUT_BG);
                setForeground(DarkTheme.TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return this;
            }
        });
        Object child = loginTypeCombo.getAccessibleContext().getAccessibleChild(0);
        if (child instanceof javax.swing.plaf.basic.ComboPopup) {
            javax.swing.JList<?> list = ((javax.swing.plaf.basic.ComboPopup) child).getList();
            list.setBackground(DarkTheme.INPUT_BG);
            list.setForeground(DarkTheme.TEXT_PRIMARY);
            list.setSelectionBackground(DarkTheme.BUTTON_PRIMARY);
            list.setSelectionForeground(DarkTheme.TEXT_PRIMARY);
        }
        JTextField usernameField = new JTextField();
        usernameField.setBackground(DarkTheme.INPUT_BG);
        usernameField.setForeground(DarkTheme.TEXT_PRIMARY);
        usernameField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DarkTheme.INPUT_BORDER),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(DarkTheme.INPUT_BG);
        passwordField.setForeground(DarkTheme.TEXT_PRIMARY);
        passwordField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DarkTheme.INPUT_BORDER),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(DarkTheme.BG_DARK);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        loginButton.setForeground(DarkTheme.TEXT_PRIMARY);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> {
            String type = (String) loginTypeCombo.getSelectedItem();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (type.equals("Student")) {
                Student student = studentService.loginStudent(username, password);
                if (student != null) {
                    JOptionPane.showMessageDialog(this, "Welcome " + student.getName() + "!");
                    new StudentDashboard(student).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (adminService.verifyAdminLogin(username, password)) {
                    JOptionPane.showMessageDialog(this, "Welcome Admin!");
                    new AdminDashboard().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(DarkTheme.ACCENT_GREEN);
        registerButton.setForeground(DarkTheme.TEXT_PRIMARY);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.addActionListener(e -> showRegisterDialog());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        panel.add(titleLabel);
        panel.add(loginTypeCombo);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(buttonPanel);
        add(panel);
    }
    private void showRegisterDialog() {
        JDialog dialog = new JDialog(this, "Student Registration", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(DarkTheme.BG_DARK);
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(DarkTheme.BG_DARK);
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(DarkTheme.TEXT_PRIMARY);
        JTextField nameField = new JTextField();
        nameField.setBackground(DarkTheme.INPUT_BG);
        nameField.setForeground(DarkTheme.TEXT_PRIMARY);
        nameField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(DarkTheme.TEXT_PRIMARY);
        JTextField emailField = new JTextField();
        emailField.setBackground(DarkTheme.INPUT_BG);
        emailField.setForeground(DarkTheme.TEXT_PRIMARY);
        emailField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(DarkTheme.TEXT_PRIMARY);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(DarkTheme.INPUT_BG);
        passwordField.setForeground(DarkTheme.TEXT_PRIMARY);
        passwordField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setForeground(DarkTheme.TEXT_PRIMARY);
        JPasswordField confirmField = new JPasswordField();
        confirmField.setBackground(DarkTheme.INPUT_BG);
        confirmField.setForeground(DarkTheme.TEXT_PRIMARY);
        confirmField.setCaretColor(DarkTheme.TEXT_PRIMARY);
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(DarkTheme.BUTTON_SUCCESS);
        registerButton.setForeground(DarkTheme.TEXT_PRIMARY);
        registerButton.setBorderPainted(false);
        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Password must be at least 6 characters!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (studentService.registerStudent(name, email, password)) {
                JOptionPane.showMessageDialog(dialog, "Registration successful! You can now login.");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Registration failed! Email may already exist.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(DarkTheme.BG_MEDIUM);
        cancelButton.setForeground(DarkTheme.TEXT_PRIMARY);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> dialog.dispose());
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmLabel);
        panel.add(confirmField);
        panel.add(cancelButton);
        panel.add(registerButton);
        dialog.add(panel);
        dialog.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
