package model;
import java.sql.Timestamp;
public class Result {
    private int resultId;
    private int studentId;
    private String studentName; 
    private int score;
    private int totalQuestions;
    private double percentage;
    private Timestamp examDate;
    public Result() {
    }
    public Result(int resultId, int studentId, int score, int totalQuestions,
            double percentage, Timestamp examDate) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.examDate = examDate;
    }
    public Result(int studentId, int score, int totalQuestions, double percentage) {
        this.studentId = studentId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
    }
    public int getResultId() {
        return resultId;
    }
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTotalQuestions() {
        return totalQuestions;
    }
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public Timestamp getExamDate() {
        return examDate;
    }
    public void setExamDate(Timestamp examDate) {
        this.examDate = examDate;
    }
    @Override
    public String toString() {
        return "Result{" +
                "Student ID=" + studentId +
                ", Score=" + score + "/" + totalQuestions +
                ", Percentage=" + String.format("%.2f", percentage) + "%" +
                ", Date=" + examDate +
                '}';
    }
}
