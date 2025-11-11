package gui;
import javax.swing.*;
import util.DarkTheme;
import javax.swing.table.DefaultTableModel;
import util.DarkTheme;
import java.awt.*;
import util.DarkTheme;
import java.util.List;
import util.DarkTheme;
import model.Result;
import util.DarkTheme;
import model.Student;
import util.DarkTheme;
import dao.ResultDAO;
import util.DarkTheme;
import dao.StudentDAO;
import util.DarkTheme;
import service.ExamService;
import util.DarkTheme;
import util.GradeCalculator;
import util.DarkTheme;
public class ViewAllResultsWindow extends JFrame {
    private ResultDAO resultDAO;
    private StudentDAO studentDAO;
    private ExamService examService;
    private DefaultTableModel tableModel;
    private JTable table;
    public ViewAllResultsWindow() {
        this.resultDAO = new ResultDAO();
        this.studentDAO = new StudentDAO();
        this.examService = new ExamService();
        setTitle("All Exam Results");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        loadResults();
    }
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("All Exam Results");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        int totalStudents = studentDAO.getStudentCount();
        int totalQuestions = new dao.QuestionDAO().getQuestionCount();
        List<Result> allResults = resultDAO.getAllResults();
        double avgScore = 0;
        if (!allResults.isEmpty()) {
            double sum = 0;
            for (Result r : allResults) {
                sum += r.getPercentage();
            }
            avgScore = sum / allResults.size();
        }
        JPanel studentsPanel = createStatCard("Total Students", String.valueOf(totalStudents),
                DarkTheme.BUTTON_PRIMARY);
        JPanel questionsPanel = createStatCard("Total Questions", String.valueOf(totalQuestions),
                DarkTheme.ACCENT_PURPLE);
        JPanel examsPanel = createStatCard("Total Exams", String.valueOf(allResults.size()), DarkTheme.BUTTON_DANGER);
        JPanel avgPanel = createStatCard("Average Score", String.format("%.1f%%", avgScore), DarkTheme.BUTTON_SUCCESS);
        statsPanel.add(studentsPanel);
        statsPanel.add(questionsPanel);
        statsPanel.add(examsPanel);
        statsPanel.add(avgPanel);
        String[] columnNames = { "Result ID", "Student Name", "Score", "Total", "Percentage", "Grade", "Date" };
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
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshButton.addActionListener(e -> {
            loadResults();
            getContentPane().remove(1); 
            createUI();
            revalidate();
            repaint();
        });
        buttonPanel.add(refreshButton);
        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.PAGE_END);
    }
    private void loadResults() {
        tableModel.setRowCount(0);
        List<Result> results = resultDAO.getAllResults();
        for (Result result : results) {
            Student student = studentDAO.getStudentById(result.getStudentId());
            String studentName = student != null ? student.getName() : "Unknown";
            String grade = GradeCalculator.calculateGrade(result.getPercentage());
            Object[] row = {
                    result.getResultId(),
                    studentName,
                    result.getScore(),
                    result.getTotalQuestions(),
                    String.format("%.2f%%", result.getPercentage()),
                    grade,
                    result.getExamDate().toString()
            };
            tableModel.addRow(row);
        }
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
