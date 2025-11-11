package gui;
import javax.swing.*;
import java.awt.*;
import model.Student;
import service.ExamService;
import util.DarkTheme;
public class StudentDashboard extends JFrame {
    private Student student;
    private ExamService examService = new ExamService();
    public StudentDashboard(Student student) {
        this.student = student;
        setTitle("Student Dashboard - " + student.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DarkTheme.BG_DARK);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(DarkTheme.BG_DARK);
        JLabel welcomeLabel = new JLabel("Welcome, " + student.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(DarkTheme.ACCENT_BLUE);
        JButton takeExamButton = createStyledButton("📝 Take Exam", DarkTheme.BUTTON_PRIMARY);
        takeExamButton.addActionListener(e -> {
            new ExamWindow(student).setVisible(true);
        });
        JButton viewResultsButton = createStyledButton("📊 View Results", DarkTheme.ACCENT_PURPLE);
        viewResultsButton.addActionListener(e -> {
            new ViewResultsWindow(student).setVisible(true);
        });
        JButton logoutButton = createStyledButton("🚪 Logout", DarkTheme.BUTTON_DANGER);
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        panel.add(welcomeLabel);
        panel.add(takeExamButton);
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
