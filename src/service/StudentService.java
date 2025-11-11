package service;
import dao.ResultDAO;
import dao.StudentDAO;
import model.Result;
import model.Student;
import java.util.List;
public class StudentService {
    private StudentDAO studentDAO;
    private ResultDAO resultDAO;
    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.resultDAO = new ResultDAO();
    }
    public boolean registerStudent(String name, String email, String password) {
        if (!email.contains("@")) {
            System.out.println("❌ Invalid email format!");
            return false;
        }
        if (password.length() < 6) {
            System.out.println("❌ Password must be at least 6 characters!");
            return false;
        }
        if (studentDAO.emailExists(email)) {
            System.out.println("❌ Email already registered!");
            return false;
        }
        Student student = new Student(name, email, password);
        return studentDAO.addStudent(student);
    }
    public Student loginStudent(String email, String password) {
        return studentDAO.loginStudent(email, password);
    }
    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }
    public List<Result> getStudentResults(int studentId) {
        return resultDAO.getResultsByStudentId(studentId);
    }
    public Result getLatestResult(int studentId) {
        return resultDAO.getLatestResultByStudentId(studentId);
    }
    public void displayStudentResults(int studentId) {
        List<Result> results = getStudentResults(studentId);
        if (results.isEmpty()) {
            System.out.println("\n📭 You haven't taken any exams yet!");
            return;
        }
        System.out.println("\n" + "=".repeat(70));
        System.out.println("               YOUR EXAM HISTORY");
        System.out.println("=".repeat(70));
        System.out.printf("%-15s %-15s %-15s %-20s%n",
                "EXAM #", "SCORE", "PERCENTAGE", "DATE");
        System.out.println("=".repeat(70));
        int examNumber = results.size();
        for (Result result : results) {
            System.out.printf("%-15d %-15s %-15s %-20s%n",
                    examNumber--,
                    result.getScore() + "/" + result.getTotalQuestions(),
                    String.format("%.2f%%", result.getPercentage()),
                    result.getExamDate().toString().substring(0, 19));
        }
        System.out.println("=".repeat(70));
        double average = resultDAO.getAverageScoreByStudentId(studentId);
        System.out.println("📊 Total Exams Taken: " + results.size());
        System.out.println("📈 Average Score: " + String.format("%.2f%%", average));
        System.out.println("=".repeat(70));
    }
    public void displayStudentProfile(Student student) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           STUDENT PROFILE");
        System.out.println("=".repeat(50));
        System.out.println("👤 Name: " + student.getName());
        System.out.println("📧 Email: " + student.getEmail());
        System.out.println("🆔 Student ID: " + student.getStudentId());
        int examCount = resultDAO.getExamCountByStudentId(student.getStudentId());
        double average = resultDAO.getAverageScoreByStudentId(student.getStudentId());
        System.out.println("📝 Exams Taken: " + examCount);
        if (examCount > 0) {
            System.out.println("📊 Average Score: " + String.format("%.2f%%", average));
        }
        System.out.println("=".repeat(50));
    }
}
