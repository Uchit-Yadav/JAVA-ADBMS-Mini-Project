package service;
import dao.QuestionDAO;
import dao.ResultDAO;
import model.Question;
import model.Result;
import util.InputUtil;
import java.util.List;
public class ExamService {
    private QuestionDAO questionDAO;
    private ResultDAO resultDAO;
    private static final int QUESTIONS_PER_EXAM = 5; 
    public ExamService() {
        this.questionDAO = new QuestionDAO();
        this.resultDAO = new ResultDAO();
    }
    public void startExam(int studentId) {
        int totalQuestions = questionDAO.getQuestionCount();
        if (totalQuestions < QUESTIONS_PER_EXAM) {
            System.out.println("\n❌ Not enough questions in the database!");
            System.out.println("Minimum " + QUESTIONS_PER_EXAM + " questions required. Current: " + totalQuestions);
            return;
        }
        List<Question> questions = questionDAO.getRandomQuestions(QUESTIONS_PER_EXAM);
        if (questions.isEmpty()) {
            System.out.println("❌ Failed to fetch questions!");
            return;
        }
        displayExamInstructions();
        int score = conductExam(questions);
        double percentage = (score * 100.0) / questions.size();
        displayExamResult(score, questions.size(), percentage);
        Result result = new Result(studentId, score, questions.size(), percentage);
        if (resultDAO.saveResult(result)) {
            System.out.println("✅ Result saved successfully!");
        } else {
            System.out.println("⚠️  Result displayed but not saved to database.");
        }
    }
    private void displayExamInstructions() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                  📝 EXAM INSTRUCTIONS 📝");
        System.out.println("=".repeat(70));
        System.out.println("• Total Questions: " + QUESTIONS_PER_EXAM);
        System.out.println("• Each question has 4 options (A, B, C, D)");
        System.out.println("• Enter your answer as A, B, C, or D");
        System.out.println("• You cannot go back to previous questions");
        System.out.println("• All questions are mandatory");
        System.out.println("=".repeat(70));
        System.out.println("\nPress Enter to start the exam...");
        InputUtil.readString();
    }
    private int conductExam(List<Question> questions) {
        int score = 0;
        int questionNumber = 1;
        for (Question question : questions) {
            System.out.println("\n\n");
            System.out.println("=".repeat(70));
            System.out.println("Question " + questionNumber + " of " + questions.size());
            System.out.println("=".repeat(70));
            question.displayQuestion();
            char answer = getValidAnswer();
            if (Character.toUpperCase(answer) == Character.toUpperCase(question.getCorrectOption())) {
                score++;
                System.out.println("✅ Correct!");
            } else {
                System.out.println("❌ Wrong! Correct answer was: " + question.getCorrectOption());
            }
            if (questionNumber < questions.size()) {
                System.out.println("\nPress Enter to continue...");
                InputUtil.readString();
            }
            questionNumber++;
        }
        return score;
    }
    private char getValidAnswer() {
        while (true) {
            System.out.print("\nYour Answer (A/B/C/D): ");
            String input = InputUtil.readString().trim().toUpperCase();
            if (input.length() == 1 && "ABCD".contains(input)) {
                return input.charAt(0);
            } else {
                System.out.println("❌ Invalid input! Please enter A, B, C, or D.");
            }
        }
    }
    private void displayExamResult(int score, int totalQuestions, double percentage) {
        System.out.println("\n\n");
        System.out.println("=".repeat(70));
        System.out.println("                  🎯 EXAM RESULT 🎯");
        System.out.println("=".repeat(70));
        System.out.println("\n📊 Your Performance:");
        System.out.println("   Correct Answers: " + score + " out of " + totalQuestions);
        System.out.println("   Wrong Answers: " + (totalQuestions - score));
        System.out.println("   Percentage: " + String.format("%.2f%%", percentage));
        System.out.println("\n🎓 Grade: " + getGrade(percentage));
        System.out.println("\n" + getPerformanceMessage(percentage));
        System.out.println("=".repeat(70));
    }
    private String getGrade(double percentage) {
        if (percentage >= 90)
            return "A+ (Outstanding!)";
        else if (percentage >= 80)
            return "A (Excellent!)";
        else if (percentage >= 70)
            return "B (Good!)";
        else if (percentage >= 60)
            return "C (Average)";
        else if (percentage >= 50)
            return "D (Below Average)";
        else
            return "F (Fail)";
    }
    private String getPerformanceMessage(double percentage) {
        if (percentage >= 90) {
            return "🌟 Fantastic job! You're a star performer!";
        } else if (percentage >= 80) {
            return "👏 Excellent work! Keep it up!";
        } else if (percentage >= 70) {
            return "👍 Good effort! You're doing well!";
        } else if (percentage >= 60) {
            return "📚 Not bad! Practice more to improve!";
        } else if (percentage >= 50) {
            return "💪 Keep trying! You can do better!";
        } else {
            return "📖 Don't give up! Study more and try again!";
        }
    }
    public int getQuestionsPerExam() {
        return QUESTIONS_PER_EXAM;
    }
}
