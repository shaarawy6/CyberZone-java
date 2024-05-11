<%@ page import="java.sql.*, javax.servlet.http.*" %>
<%@ page import = "org.json.*"%>
<%@ page contentType="application/json; charset=UTF-8" %>

<%
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
JSONObject json = new JSONObject();

try {
    // Load the JDBC driver
    Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver class name for newer versions

    // Establish a connection
    String url = "jdbc:mysql://localhost/cyberzone";
    conn = DriverManager.getConnection(url, "root", "");

    // Check if the user is logged in
    Integer userId = (Integer) session.getAttribute("userId"); // No casting if it's null
    if (userId != null) {
        String sql = "SELECT progress FROM userProgress WHERE userId = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            json.put("progress", rs.getInt("progress"));
        } else {
            json.put("progress", 0); // Default to 0 if no progress found
        }
    } else {
        json.put("error", "User not logged in");
    }
} catch (ClassNotFoundException e) {
    json.put("error", "JDBC Driver not found: " + e.getMessage());
} catch (SQLException e) {
    json.put("error", "Database connection failed: " + e.getMessage());
} catch (Exception e) {
    json.put("error", "An error occurred: " + e.getMessage());
} finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {}
    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
    try { if (conn != null) conn.close(); } catch (Exception e) {}
    response.getWriter().print(json.toString());
}
%>
