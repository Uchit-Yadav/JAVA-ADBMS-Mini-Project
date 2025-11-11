package gui;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Student;
import model.Question;
import model.Result;
import service.ExamService;
import dao.QuestionDAO;
import dao.ResultDAO;
import util.GradeCalculator;
import util.DarkTheme;
public class ExamWindow extends JFrame {
    private Student student;
    private ExamService examService;
    private QuestionDAO questionDAO;
    private ResultDAO resultDAO;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private ButtonGroup optionGroup;
    public ExamWindow(Student student) {
        this.student = student;
        this.examService = new ExamService();
        this.questionDAO = new QuestionDAO();
        this.resultDAO = new ResultDAO();
        setTitle("Exam - " + student.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DarkTheme.BG_DARK);
        questions = questionDAO.getRandomQuestions(5);
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available! Please ask admin to add questions.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        displayQuestion();
    }
    private void displayQuestion() {
        getContentPane().removeAll();
        Question question = questions.get(currentQuestionIndex);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(DarkTheme.BG_DARK);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DarkTheme.BG_DARK);
        JLabel titleLabel = new JLabel("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(DarkTheme.ACCENT_BLUE);
        JLabel scoreLabel = new JLabel("Score: " + score + "/" + currentQuestionIndex);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(DarkTheme.TEXT_SECONDARY);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(scoreLabel, BorderLayout.EAST);
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        questionPanel.setBackground(DarkTheme.BG_DARK);
        JTextArea questionText = new JTextArea(question.getQuestionText());
        questionText.setWrapStyleWord(true);
        questionText.setLineWrap(true);
        questionText.setEditable(false);
        questionText.setFont(new Font("Arial", Font.PLAIN, 16));
        questionText.setForeground(DarkTheme.TEXT_PRIMARY);
        questionText.setBackground(DarkTheme.BG_MEDIUM);
        questionText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DarkTheme.INPUT_BORDER),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        optionsPanel.setBackground(DarkTheme.BG_DARK);
        optionGroup = new ButtonGroup();
        JRadioButton optionA = new JRadioButton("A. " + question.getOptionA());
        JRadioButton optionB = new JRadioButton("B. " + question.getOptionB());
        JRadioButton optionC = new JRadioButton("C. " + question.getOptionC());
        JRadioButton optionD = new JRadioButton("D. " + question.getOptionD());
        optionA.setFont(new Font("Arial", Font.PLAIN, 14));
        optionB.setFont(new Font("Arial", Font.PLAIN, 14));
        optionC.setFont(new Font("Arial", Font.PLAIN, 14));
        optionD.setFont(new Font("Arial", Font.PLAIN, 14));
        optionA.setForeground(DarkTheme.TEXT_PRIMARY);
        optionB.setForeground(DarkTheme.TEXT_PRIMARY);
        optionC.setForeground(DarkTheme.TEXT_PRIMARY);
        optionD.setForeground(DarkTheme.TEXT_PRIMARY);
        optionA.setBackground(DarkTheme.BG_DARK);
        optionB.setBackground(DarkTheme.BG_DARK);
        optionC.setBackground(DarkTheme.BG_DARK);
        optionD.setBackground(DarkTheme.BG_DARK);
        optionA.setActionCommand("A");
        optionB.setActionCommand("B");
        optionC.setActionCommand("C");
        optionD.setActionCommand("D");
        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);
        optionsPanel.add(optionA);
        optionsPanel.add(Box.createVerticalStrut(10));
        optionsPanel.add(optionB);
        optionsPanel.add(Box.createVerticalStrut(10));
        optionsPanel.add(optionC);
        optionsPanel.add(Box.createVerticalStrut(10));
        optionsPanel.add(optionD);
        questionPanel.add(questionText, BorderLayout.NORTH);
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(DarkTheme.BG_DARK);
        JButton nextButton = new JButton(
                currentQuestionIndex == questions.size() - 1 ? "Submit Exam" : "Next Question");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        nextButton.setForeground(DarkTheme.TEXT_PRIMARY);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(e -> {
            if (optionGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selectedAnswer = optionGroup.getSelection().getActionCommand();
            char selectedChar = selectedAnswer.charAt(0);
            if (selectedChar == question.getCorrectOption()) {
                score++;
            }
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                finishExam();
            }
        });
        buttonPanel.add(nextButton);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        revalidate();
        repaint();
    }
    private void finishExam() {
        int totalQuestions = questions.size();
        double percentage = (score * 100.0) / totalQuestions;
        String grade = GradeCalculator.calculateGrade(percentage);
        Result result = new Result();
        result.setStudentId(student.getStudentId());
        result.setScore(score);
        result.setTotalQuestions(totalQuestions);
        result.setPercentage(percentage);
        resultDAO.saveResult(result);
        String message = String.format(
                "Exam Completed!\n\n" +
                        "Score: %d/%d\n" +
                        "Percentage: %.2f%%\n" +
                        "Grade: %s\n\n" +
                        "Result saved successfully!",
                score, totalQuestions, percentage, grade);
        JOptionPane.showMessageDialog(this, message, "Exam Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
