package gui;
import javax.swing.*;
import util.DarkTheme;
import javax.swing.table.DefaultTableModel;
import util.DarkTheme;
import java.awt.*;
import util.DarkTheme;
import java.util.List;
import util.DarkTheme;
import model.Question;
import util.DarkTheme;
import dao.QuestionDAO;
import util.DarkTheme;
public class ManageQuestionsWindow extends JFrame {
    private QuestionDAO questionDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    public ManageQuestionsWindow() {
        this.questionDAO = new QuestionDAO();
        setTitle("Manage Questions");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        loadQuestions();
    }
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("Manage Questions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton addButton = new JButton("+ Add Question");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        addButton.setForeground(DarkTheme.BG_DARK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddQuestionDialog());
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);
        String[] columnNames = { "ID", "Question", "Answer" };
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
        JButton viewButton = new JButton("View Details");
        viewButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewButton.addActionListener(e -> viewQuestionDetails());
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton.setBackground(DarkTheme.BUTTON_DANGER);
        deleteButton.setForeground(DarkTheme.BG_DARK);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteSelectedQuestion());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshButton.addActionListener(e -> loadQuestions());
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void loadQuestions() {
        tableModel.setRowCount(0);
        List<Question> questions = questionDAO.getAllQuestions();
        for (Question question : questions) {
            String truncatedQuestion = question.getQuestionText().length() > 50
                    ? question.getQuestionText().substring(0, 50) + "..."
                    : question.getQuestionText();
            Object[] row = {
                    question.getQuestionId(),
                    truncatedQuestion,
                    question.getCorrectOption()
            };
            tableModel.addRow(row);
        }
    }
    private void showAddQuestionDialog() {
        JDialog dialog = new JDialog(this, "Add New Question", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Question:"), gbc);
        JTextArea questionArea = new JTextArea(3, 30);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        JScrollPane questionScroll = new JScrollPane(questionArea);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(questionScroll, gbc);
        JTextField optionAField = new JTextField(30);
        JTextField optionBField = new JTextField(30);
        JTextField optionCField = new JTextField(30);
        JTextField optionDField = new JTextField(30);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Option A:"), gbc);
        gbc.gridx = 1;
        panel.add(optionAField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Option B:"), gbc);
        gbc.gridx = 1;
        panel.add(optionBField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Option C:"), gbc);
        gbc.gridx = 1;
        panel.add(optionCField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Option D:"), gbc);
        gbc.gridx = 1;
        panel.add(optionDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Correct Answer:"), gbc);
        String[] answers = { "A", "B", "C", "D" };
        JComboBox<String> answerCombo = new JComboBox<>(answers);
        gbc.gridx = 1;
        panel.add(answerCombo, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(DarkTheme.BUTTON_PRIMARY);
        saveButton.setForeground(DarkTheme.BG_DARK);
        saveButton.addActionListener(e -> {
            String questionText = questionArea.getText().trim();
            String optionA = optionAField.getText().trim();
            String optionB = optionBField.getText().trim();
            String optionC = optionCField.getText().trim();
            String optionD = optionDField.getText().trim();
            String correctAnswer = (String) answerCombo.getSelectedItem();
            if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() ||
                    optionC.isEmpty() || optionD.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Question question = new Question();
            question.setQuestionText(questionText);
            question.setOptionA(optionA);
            question.setOptionB(optionB);
            question.setOptionC(optionC);
            question.setOptionD(optionD);
            question.setCorrectOption(correctAnswer.charAt(0));
            if (questionDAO.addQuestion(question)) {
                JOptionPane.showMessageDialog(dialog, "Question added successfully!");
                loadQuestions();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add question!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    private void viewQuestionDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to view!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int questionId = (int) tableModel.getValueAt(selectedRow, 0);
        Question question = questionDAO.getQuestionById(questionId);
        if (question != null) {
            String details = String.format(
                    "Question: %s\n\nA) %s\nB) %s\nC) %s\nD) %s\n\nCorrect Answer: %s",
                    question.getQuestionText(),
                    question.getOptionA(),
                    question.getOptionB(),
                    question.getOptionC(),
                    question.getOptionD(),
                    question.getCorrectOption());
            JTextArea textArea = new JTextArea(details);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Question Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void deleteSelectedQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to delete!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int questionId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this question?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (questionDAO.deleteQuestion(questionId)) {
                JOptionPane.showMessageDialog(this, "Question deleted successfully!");
                loadQuestions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete question!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
