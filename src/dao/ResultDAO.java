package dao;
import db.DatabaseConnection;
import model.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ResultDAO {
    public boolean saveResult(Result result) {
        String sql = "INSERT INTO results (student_id, score, total_questions, percentage) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, result.getStudentId());
            stmt.setInt(2, result.getScore());
            stmt.setInt(3, result.getTotalQuestions());
            stmt.setDouble(4, result.getPercentage());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error saving result: " + e.getMessage());
            return false;
        }
    }
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, s.name FROM results r " +
                "JOIN students s ON r.student_id = s.student_id " +
                "ORDER BY r.exam_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Result result = new Result();
                result.setResultId(rs.getInt("result_id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setStudentName(rs.getString("name"));
                result.setScore(rs.getInt("score"));
                result.setTotalQuestions(rs.getInt("total_questions"));
                result.setPercentage(rs.getDouble("percentage"));
                result.setExamDate(rs.getTimestamp("exam_date"));
                results.add(result);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching results: " + e.getMessage());
        }
        return results;
    }
    public List<Result> getResultsByStudentId(int studentId) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, s.name FROM results r " +
                "JOIN students s ON r.student_id = s.student_id " +
                "WHERE r.student_id = ? ORDER BY r.exam_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Result result = new Result();
                result.setResultId(rs.getInt("result_id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setStudentName(rs.getString("name"));
                result.setScore(rs.getInt("score"));
                result.setTotalQuestions(rs.getInt("total_questions"));
                result.setPercentage(rs.getDouble("percentage"));
                result.setExamDate(rs.getTimestamp("exam_date"));
                results.add(result);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student results: " + e.getMessage());
        }
        return results;
    }
    public Result getLatestResultByStudentId(int studentId) {
        String sql = "SELECT r.*, s.name FROM results r " +
                "JOIN students s ON r.student_id = s.student_id " +
                "WHERE r.student_id = ? ORDER BY r.exam_date DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Result result = new Result();
                result.setResultId(rs.getInt("result_id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setStudentName(rs.getString("name"));
                result.setScore(rs.getInt("score"));
                result.setTotalQuestions(rs.getInt("total_questions"));
                result.setPercentage(rs.getDouble("percentage"));
                result.setExamDate(rs.getTimestamp("exam_date"));
                return result;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching latest result: " + e.getMessage());
        }
        return null;
    }
    public int getExamCountByStudentId(int studentId) {
        String sql = "SELECT COUNT(*) FROM results WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error counting exams: " + e.getMessage());
        }
        return 0;
    }
    public double getAverageScoreByStudentId(int studentId) {
        String sql = "SELECT AVG(percentage) FROM results WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error calculating average: " + e.getMessage());
        }
        return 0.0;
    }
}
