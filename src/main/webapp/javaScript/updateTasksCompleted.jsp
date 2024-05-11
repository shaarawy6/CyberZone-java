<%@ page import="java.sql.*, javax.servlet.http.*, javax.servlet.*" %>
<%
// Establish a database connection
String servername = "localhost";
String username = "root";
String password = "";
String database = "cyberzone";
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

int userId = Integer.parseInt(request.getParameter("userId"));
int moduleId = Integer.parseInt(request.getParameter("moduleId"));
int taskId = Integer.parseInt(request.getParameter("taskId"));

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost/cyberzone", "root", "");

    // Check if the task has already been marked as completed
    String query = "SELECT id FROM taskCompletion WHERE userId = ? AND moduleId = ? AND taskId = ?";
    pstmt = conn.prepareStatement(query);
    pstmt.setInt(1, userId);
    pstmt.setInt(2, moduleId);
    pstmt.setInt(3, taskId);
    rs = pstmt.executeQuery();

    if (!rs.next()) {
        // If the task has not been completed yet, mark it as completed
        String insertQuery = "INSERT INTO taskCompletion (userId, moduleId, taskId, isCompleted, completionDate) VALUES (?, ?, ?, 1, NOW())";
        pstmt.close(); // Close previous statement
        pstmt = conn.prepareStatement(insertQuery);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, moduleId);
        pstmt.setInt(3, taskId);
        int success = pstmt.executeUpdate();

        if (success > 0) {
            // Update the user's progress in the userProgress table
            String updateProgressSql = "UPDATE userProgress SET progress = (SELECT COUNT(*) FROM taskCompletion WHERE moduleId = ? AND userId = ? AND isCompleted = 1) / (SELECT COUNT(*) FROM tasks WHERE moduleId = ?) * 100 WHERE userId = ? AND moduleId = ?";
            pstmt.close(); // Close previous statement
            pstmt = conn.prepareStatement(updateProgressSql);
            pstmt.setInt(1, moduleId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, moduleId);
            pstmt.setInt(4, userId);
            pstmt.setInt(5, moduleId);
            pstmt.executeUpdate();
            out.println("Task completed and progress updated successfully.");
        } else {
            out.println("Error marking task as completed: " + pstmt.getWarnings());
        }
    } else {
        out.println("Task already marked as completed.");
    }
} catch (Exception e) {
    out.println("Database connection or operation failed: " + e.getMessage());
} finally {
    if (rs != null) try { rs.close(); } catch (SQLException ex) { /* ignored */ }
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { /* ignored */ }
    if (conn != null) try { conn.close(); } catch (SQLException ex) { /* ignored */ }
}
%>
