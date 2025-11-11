package gui;
import javax.swing.*;
import java.awt.*;
import service.AdminService;
import util.DarkTheme;
public class AdminDashboard extends JFrame {
    private AdminService adminService = new AdminService();
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DarkTheme.BG_DARK);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(DarkTheme.BG_DARK);
        JLabel titleLabel = new JLabel("👨‍💼 Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(DarkTheme.ACCENT_BLUE);
        JButton manageStudentsButton = createStyledButton("👥 Manage Students", DarkTheme.BUTTON_PRIMARY);
        manageStudentsButton.addActionListener(e -> {
            new ManageStudentsWindow().setVisible(true);
        });
        JButton manageQuestionsButton = createStyledButton("📝 Manage Questions", DarkTheme.ACCENT_PURPLE);
        manageQuestionsButton.addActionListener(e -> {
            new ManageQuestionsWindow().setVisible(true);
        });
        JButton viewResultsButton = createStyledButton("📊 View All Results", DarkTheme.ACCENT_ORANGE);
        viewResultsButton.addActionListener(e -> {
            new ViewAllResultsWindow().setVisible(true);
        });
        JButton logoutButton = createStyledButton("🚪 Logout", DarkTheme.BUTTON_DANGER);
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        panel.add(titleLabel);
        panel.add(manageStudentsButton);
        panel.add(manageQuestionsButton);
        panel.add(viewResultsButton);
        panel.add(logoutButton);
        add(panel);
    }
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(DarkTheme.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
