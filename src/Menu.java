import model.Question;
import model.Student;
import service.AdminService;
import service.ExamService;
import service.StudentService;
import util.InputUtil;
public class Menu {
    private AdminService adminService;
    private StudentService studentService;
    private ExamService examService;
    public Menu() {
        this.adminService = new AdminService();
        this.studentService = new StudentService();
        this.examService = new ExamService();
    }
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("        📚 STUDENT EXAMINATION SYSTEM 📚");
            System.out.println("=".repeat(60));
            System.out.println("1. 👨‍💼 Admin Login");
            System.out.println("2. 🎓 Student Login");
            System.out.println("3. 📝 Student Registration");
            System.out.println("4. ❌ Exit");
            System.out.println("=".repeat(60));
            int choice = InputUtil.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentLogin();
                    break;
                case 3:
                    studentRegistration();
                    break;
                case 4:
                    System.out.println("\n👋 Thank you for using Student Examination System!");
                    System.out.println("Goodbye! 👋\n");
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }
    private void adminLogin() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            👨‍💼 ADMIN LOGIN");
        System.out.println("=".repeat(60));
        String username = InputUtil.readString("Enter Username: ");
        String password = InputUtil.readPassword("Enter Password: ");
        if (adminService.verifyAdminLogin(username, password)) {
            System.out.println("✅ Login Successful! Welcome Admin!");
            adminMenu();
        } else {
            System.out.println("❌ Invalid credentials! Login failed.");
        }
    }
    private void adminMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                  ADMIN DASHBOARD");
            System.out.println("=".repeat(60));
            System.out.println("1. 👥 Manage Students");
            System.out.println("2. 📋 Manage Questions");
            System.out.println("3. 📊 View Results");
            System.out.println("4. 📈 View Statistics");
            System.out.println("5. 🔙 Logout");
            System.out.println("=".repeat(60));
            int choice = InputUtil.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    manageStudentsMenu();
                    break;
                case 2:
                    manageQuestionsMenu();
                    break;
                case 3:
                    adminService.displayAllResults();
                    InputUtil.waitForEnter();
                    break;
                case 4:
                    adminService.displayStatistics();
                    InputUtil.waitForEnter();
                    break;
                case 5:
                    System.out.println("👋 Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
    private void manageStudentsMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              MANAGE STUDENTS");
            System.out.println("=".repeat(60));
            System.out.println("1. ➕ Add New Student");
            System.out.println("2. 👁️  View All Students");
            System.out.println("3. 🗑️  Delete Student");
            System.out.println("4. 🔙 Back");
            System.out.println("=".repeat(60));
            int choice = InputUtil.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    adminService.displayAllStudents();
                    InputUtil.waitForEnter();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
    private void addStudent() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              ADD NEW STUDENT");
        System.out.println("=".repeat(60));
        String name = InputUtil.readString("Enter Student Name: ");
        String email = InputUtil.readString("Enter Email: ");
        String password = InputUtil.readPassword("Enter Password: ");
        Student student = new Student(name, email, password);
        if (adminService.addStudent(student)) {
            System.out.println("✅ Student added successfully!");
        } else {
            System.out.println("❌ Failed to add student!");
        }
    }
    private void deleteStudent() {
        adminService.displayAllStudents();
        int studentId = InputUtil.readInt("\nEnter Student ID to delete (0 to cancel): ");
        if (studentId == 0) {
            return;
        }
        System.out.print("⚠️  Are you sure you want to delete this student? (yes/no): ");
        String confirm = InputUtil.readString().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            if (adminService.deleteStudent(studentId)) {
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete student!");
            }
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }
    private void manageQuestionsMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              MANAGE QUESTIONS");
            System.out.println("=".repeat(60));
            System.out.println("1. ➕ Add New Question");
            System.out.println("2. 👁️  View All Questions");
            System.out.println("3. 🗑️  Delete Question");
            System.out.println("4. 🔙 Back");
            System.out.println("=".repeat(60));
            int choice = InputUtil.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    addQuestion();
                    break;
                case 2:
                    adminService.displayAllQuestions();
                    InputUtil.waitForEnter();
                    break;
                case 3:
                    deleteQuestion();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
    private void addQuestion() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              ADD NEW QUESTION");
        System.out.println("=".repeat(60));
        String questionText = InputUtil.readString("Enter Question: ");
        String optionA = InputUtil.readString("Enter Option A: ");
        String optionB = InputUtil.readString("Enter Option B: ");
        String optionC = InputUtil.readString("Enter Option C: ");
        String optionD = InputUtil.readString("Enter Option D: ");
        char correctOption;
        while (true) {
            correctOption = Character.toUpperCase(
                    InputUtil.readChar("Enter Correct Option (A/B/C/D): "));
            if ("ABCD".indexOf(correctOption) != -1) {
                break;
            }
            System.out.println("❌ Invalid option! Please enter A, B, C, or D.");
        }
        Question question = new Question(questionText, optionA, optionB, optionC, optionD, correctOption);
        if (adminService.addQuestion(question)) {
            System.out.println("✅ Question added successfully!");
        } else {
            System.out.println("❌ Failed to add question!");
        }
    }
    private void deleteQuestion() {
        adminService.displayAllQuestions();
        int questionId = InputUtil.readInt("\nEnter Question ID to delete (0 to cancel): ");
        if (questionId == 0) {
            return;
        }
        System.out.print("⚠️  Are you sure you want to delete this question? (yes/no): ");
        String confirm = InputUtil.readString().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            if (adminService.deleteQuestion(questionId)) {
                System.out.println("✅ Question deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete question!");
            }
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }
    private void studentRegistration() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            📝 STUDENT REGISTRATION");
        System.out.println("=".repeat(60));
        String name = InputUtil.readString("Enter Your Name: ");
        String email = InputUtil.readString("Enter Your Email: ");
        String password = InputUtil.readPassword("Enter Password (min 6 characters): ");
        if (studentService.registerStudent(name, email, password)) {
            System.out.println("✅ Registration Successful! You can now login.");
        } else {
            System.out.println("❌ Registration Failed!");
        }
    }
    private void studentLogin() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            🎓 STUDENT LOGIN");
        System.out.println("=".repeat(60));
        String email = InputUtil.readString("Enter Email: ");
        String password = InputUtil.readPassword("Enter Password: ");
        Student student = studentService.loginStudent(email, password);
        if (student != null) {
            System.out.println("✅ Login Successful! Welcome " + student.getName() + "!");
            studentMenu(student);
        } else {
            System.out.println("❌ Invalid credentials! Login failed.");
        }
    }
    private void studentMenu(Student student) {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              STUDENT DASHBOARD");
            System.out.println("=".repeat(60));
            System.out.println("Welcome, " + student.getName() + "! 👋");
            System.out.println("=".repeat(60));
            System.out.println("1. 📝 Take Exam");
            System.out.println("2. 📊 View Results");
            System.out.println("3. 👤 View Profile");
            System.out.println("4. 🔙 Logout");
            System.out.println("=".repeat(60));
            int choice = InputUtil.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    examService.startExam(student.getStudentId());
                    InputUtil.waitForEnter();
                    break;
                case 2:
                    studentService.displayStudentResults(student.getStudentId());
                    InputUtil.waitForEnter();
                    break;
                case 3:
                    studentService.displayStudentProfile(student);
                    InputUtil.waitForEnter();
                    break;
                case 4:
                    System.out.println("👋 Logging out...");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}
