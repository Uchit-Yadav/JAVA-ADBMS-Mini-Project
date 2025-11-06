# 🎓 Student Examination System

A modern Java application with **Dark Theme GUI** for managing student exams using MySQL and JDBC.

## ✨ Features

### 👨‍💼 Admin
- Secure login (admin/admin123)
- Add/view/delete students
- Add/view/delete questions
- View all exam results with statistics
- Complete dark-themed dashboard

### 🎓 Student
- Self-registration with validation
- Login with email/password
- Take randomized exams (5 questions)
- View exam history and results
- Track performance with grades (A+ to F)
- Beautiful dark-themed interface

## 🚀 Quick Start

### 1. Setup Database
```bash
brew services start mysql
mysql -u root -p < database_setup.sql
```

### 2. Configure Password
Edit `src/db/DatabaseConnection.java` line 16:
```java
private static final String PASSWORD = "your_mysql_password";
```

### 3. Run Application
```bash
./run-gui.sh
```

## 🔑 Default Credentials

**Admin:** `admin` / `admin123`  
**Test Student:** `test@student.com` / `student123`

## 🌙 Dark Theme

Professional dark theme with:
- Dark gray backgrounds (#1E1E1E)
- White readable text (#F0F0F0)
- Blue, purple, green, red accent colors
- Consistent styling across all windows
- Easy on the eyes

## 🎮 Usage

### Student Registration
1. Click "Register" button
2. Fill in name, email, password
3. Confirm password
4. Register successfully

### Take an Exam
1. Login as student
2. Click "📝 Take Exam"
3. Answer 5 multiple-choice questions
4. View your grade (A+ to F)

### Admin Management
1. Login as admin
2. Manage Students: Add or remove
3. Manage Questions: Add exam questions
4. View Results: See all performance data

## 📊 Grading System

| Percentage | Grade |
|-----------|-------|
| 90-100% | A+ |
| 80-89% | A |
| 70-79% | B |
| 60-69% | C |
| 50-59% | D |
| Below 50% | F |

## 🛠️ Technologies

- Java 8+
- MySQL Database
- JDBC
- Swing (Dark Theme GUI)
- No external dependencies

## 📁 Project Structure

```
├── src/
│   ├── gui/              # 8 Dark-themed windows
│   ├── dao/              # Database operations
│   ├── model/            # Data models
│   ├── service/          # Business logic
│   ├── db/               # Database connection
│   ├── util/             # Utilities & DarkTheme
│   ├── Main.java         # Console entry
│   └── Menu.java         # Console menus
├── lib/
│   └── mysql-connector-java-8.0.33.jar
├── database_setup.sql
├── run-gui.sh
├── run-console.sh
└── README.md
```

## 🗄️ Database Schema

**Students:** student_id, name, email, password  
**Questions:** question_id, question_text, options (A-D), correct_option  
**Results:** result_id, student_id, score, percentage, exam_date

## 🐛 Troubleshooting

**Database connection error:**
```bash
brew services list
# Check password in src/db/DatabaseConnection.java
```

**No questions available:**
```bash
# Login as admin and add at least 5 questions
```

**Compilation error:**
```bash
rm -rf target
./run-gui.sh
```

## ✅ Features List

- [x] Student registration with validation
- [x] Student/Admin login
- [x] Take randomized exams
- [x] Automatic grading (A+ to F)
- [x] View personal results
- [x] View statistics
- [x] Admin: manage students
- [x] Admin: manage questions
- [x] Admin: view all results
- [x] Dark theme GUI
- [x] Console interface (bonus)

## 🎨 Dark Theme

All GUI windows feature:
- Professional dark backgrounds
- High contrast white text
- Color-coded buttons
- Styled input fields
- Dark dropdown menus
- Consistent design

## 📄 License

Educational project - free to use and modify.

---

**Run: `./run-gui.sh` to start!** 🚀
