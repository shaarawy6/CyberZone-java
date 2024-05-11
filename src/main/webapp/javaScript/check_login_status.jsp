<%@ page import="java.sql.*, javax.servlet.http.*, org.json.JSONObject" %>
<%@ page contentType="application/json; charset=UTF-8" %>

<%
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
JSONObject responseJson = new JSONObject();

try {
    // Load JDBC driver and establish a connection
    Class.forName("com.mysql.cj.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost/cyberzone", "root", "");

    // Check if user is logged in
    Integer userId = Integer.parseInt((session.getAttribute("userId")).toString());
    if (userId != null) {
        // Prepare and execute the SQL statement
        String query = "SELECT progress FROM userProgress WHERE userId = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        // Fetch the progress data
        int progress = 0; // Default to 0 if no data found
        if (rs.next()) {
            progress = rs.getInt("progress");
        }
        responseJson.put("isLoggedIn", true);
        responseJson.put("progress", progress);
    } else {
        responseJson.put("isLoggedIn", false);
    }
} catch (ClassNotFoundException e) {
    responseJson.put("error", "JDBC Driver not found: " + e.getMessage());
} catch (SQLException e) {
    responseJson.put("error", "Database connection failed: " + e.getMessage());
} finally {
    if (rs != null) try { rs.close(); } catch (Exception e) {}
    if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
    if (conn != null) try { conn.close(); } catch (Exception e) {}
}

// Output the JSON response
response.getWriter().write(responseJson.toString());
%>
