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
import model.Result;
import util.DarkTheme;
import dao.ResultDAO;
import util.DarkTheme;
import service.ExamService;
import util.DarkTheme;
import util.GradeCalculator;
import util.DarkTheme;
public class ViewResultsWindow extends JFrame {
    private Student student;
    private ResultDAO resultDAO;
    private ExamService examService;
    public ViewResultsWindow(Student student) {
        this.student = student;
        this.resultDAO = new ResultDAO();
        this.examService = new ExamService();
        setTitle("My Results - " + student.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("My Exam Results");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        List<Result> results = resultDAO.getResultsByStudentId(student.getStudentId());
        if (results.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No exam results found. Take an exam first!", SwingConstants.CENTER);
            noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(headerPanel, BorderLayout.NORTH);
            add(noResultsLabel, BorderLayout.CENTER);
            return;
        }
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        int totalExams = resultDAO.getExamCountByStudentId(student.getStudentId());
        double avgScore = resultDAO.getAverageScoreByStudentId(student.getStudentId());
        Result latestResult = resultDAO.getLatestResultByStudentId(student.getStudentId());
        JPanel examsPanel = createStatCard("Total Exams", String.valueOf(totalExams), DarkTheme.BUTTON_PRIMARY);
        JPanel avgPanel = createStatCard("Average Score", String.format("%.1f%%", avgScore), DarkTheme.ACCENT_PURPLE);
        JPanel latestPanel = createStatCard("Latest Grade",
                latestResult != null ? GradeCalculator.calculateGrade(latestResult.getPercentage()) : "N/A",
                DarkTheme.BUTTON_DANGER);
        statsPanel.add(examsPanel);
        statsPanel.add(avgPanel);
        statsPanel.add(latestPanel);
        String[] columnNames = { "Date", "Score", "Total", "Percentage", "Grade" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Result result : results) {
            String grade = GradeCalculator.calculateGrade(result.getPercentage());
            Object[] row = {
                    result.getExamDate().toString(),
                    result.getScore(),
                    result.getTotalQuestions(),
                    String.format("%.2f%%", result.getPercentage()),
                    grade
            };
            tableModel.addRow(row);
        }
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(DarkTheme.BUTTON_PRIMARY);
        table.getTableHeader().setForeground(DarkTheme.BG_DARK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(DarkTheme.BG_DARK);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(DarkTheme.BG_DARK);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(valueLabel);
        return panel;
    }
}
