package service;
import dao.QuestionDAO;
import dao.ResultDAO;
import dao.StudentDAO;
import model.Question;
import model.Result;
import model.Student;
import java.util.List;
public class AdminService {
    private StudentDAO studentDAO;
    private QuestionDAO questionDAO;
    private ResultDAO resultDAO;
    public AdminService() {
        this.studentDAO = new StudentDAO();
        this.questionDAO = new QuestionDAO();
        this.resultDAO = new ResultDAO();
    }
    public boolean verifyAdminLogin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }
    public boolean addStudent(Student student) {
        if (studentDAO.emailExists(student.getEmail())) {
            System.out.println("❌ Email already exists!");
            return false;
        }
        return studentDAO.addStudent(student);
    }
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
    public boolean deleteStudent(int studentId) {
        return studentDAO.deleteStudent(studentId);
    }
    public void displayAllStudents() {
        List<Student> students = getAllStudents();
        if (students.isEmpty()) {
            System.out.println("📭 No students found!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-10s %-25s %-35s%n", "ID", "NAME", "EMAIL");
        System.out.println("=".repeat(80));
        for (Student student : students) {
            System.out.printf("%-10d %-25s %-35s%n",
                    student.getStudentId(),
                    student.getName(),
                    student.getEmail());
        }
        System.out.println("=".repeat(80));
        System.out.println("Total Students: " + students.size());
    }
    public boolean addQuestion(Question question) {
        return questionDAO.addQuestion(question);
    }
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }
    public boolean deleteQuestion(int questionId) {
        return questionDAO.deleteQuestion(questionId);
    }
    public void displayAllQuestions() {
        List<Question> questions = getAllQuestions();
        if (questions.isEmpty()) {
            System.out.println("📭 No questions found!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("QUESTION BANK");
        System.out.println("=".repeat(80));
        for (Question question : questions) {
            System.out.println("\nID: " + question.getQuestionId());
            System.out.println("Q: " + question.getQuestionText());
            System.out.println("   A. " + question.getOptionA());
            System.out.println("   B. " + question.getOptionB());
            System.out.println("   C. " + question.getOptionC());
            System.out.println("   D. " + question.getOptionD());
            System.out.println("   ✓ Correct Answer: " + question.getCorrectOption());
            System.out.println("-".repeat(80));
        }
        System.out.println("Total Questions: " + questions.size());
    }
    public List<Result> getAllResults() {
        return resultDAO.getAllResults();
    }
    public void displayAllResults() {
        List<Result> results = getAllResults();
        if (results.isEmpty()) {
            System.out.println("📭 No results found!");
            return;
        }
        System.out.println("\n" + "=".repeat(90));
        System.out.printf("%-10s %-25s %-15s %-15s %-20s%n",
                "RESULT ID", "STUDENT NAME", "SCORE", "PERCENTAGE", "DATE");
        System.out.println("=".repeat(90));
        for (Result result : results) {
            System.out.printf("%-10d %-25s %-15s %-15s %-20s%n",
                    result.getResultId(),
                    result.getStudentName(),
                    result.getScore() + "/" + result.getTotalQuestions(),
                    String.format("%.2f%%", result.getPercentage()),
                    result.getExamDate().toString().substring(0, 19));
        }
        System.out.println("=".repeat(90));
        System.out.println("Total Results: " + results.size());
    }
    public void displayStatistics() {
        int totalStudents = studentDAO.getStudentCount();
        int totalQuestions = questionDAO.getQuestionCount();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           SYSTEM STATISTICS");
        System.out.println("=".repeat(50));
        System.out.println("📚 Total Questions: " + totalQuestions);
        System.out.println("👥 Total Students: " + totalStudents);
        System.out.println("=".repeat(50));
    }
}
