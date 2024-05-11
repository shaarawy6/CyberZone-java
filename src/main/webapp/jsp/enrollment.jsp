<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task and Enrollment Management</title>
    <link rel="icon" href="../images/Hacker.png" type="images/x-icon">
</head>

<body>
    <%
    HttpSession ses = request.getSession();
    Connection conn = null;
    Statement stmt = null;
    String message = "";

    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cyberzone", "root", "");
		
        // Check if the user is logged in
        if (ses.getAttribute("userId") == null) {
            System.out.println("<script>alert('Please log in to enroll or complete tasks.'); window.history.back();</script>");
            
            return;
        }
        
        Integer userId = Integer.parseInt((ses.getAttribute("userId")).toString());
        // Handle form submission
        
        System.out.print("ee" + request.getParameter("moduleId") + "dd");
        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("moduleId") != null) {
        	System.out.print("small");
        	System.out.println("entered");
            int moduleId = Integer.parseInt(request.getParameter("moduleId"));
            stmt = conn.createStatement();

            // Check if the user is already enrolled in the module
            String checkEnrollment = "SELECT * FROM enrollment WHERE userId = " + userId + " AND moduleId = " + moduleId;
            ResultSet enrollmentResult = stmt.executeQuery(checkEnrollment);

            if (!enrollmentResult.next()) {
                String enrollmentDate = new java.sql.Date(new java.util.Date().getTime()).toString();
                String enrollSql = "INSERT INTO enrollment (enrollmentDate, userId, moduleId) VALUES ('" + enrollmentDate + "', " + userId + ", " + moduleId + ")";
                System.out.println("enrolled");

                if (stmt.executeUpdate(enrollSql) > 0) {
                    message = "You have been enrolled successfully!";
                    out.println("<script>alert('" + message + "'); window.location='../html/introduction-to-cybersecurity-path/path-page-enrolled.html';</script>");
                    // Initialize the user progress for the module
                    String initProgressSql = "INSERT INTO userProgress (userId, moduleId, progress) VALUES (" + userId + ", " + moduleId + ", 0)";
                    stmt.executeUpdate(initProgressSql);
                	out.println("unsert to userprogrress");

                } else {
                    message = "Error enrolling in the module.";
                }
            } else {
                message = "You are already enrolled in this module!";
                out.println("<script>alert('" + message + "'); window.location='../html/introduction-to-cybersecurity-path/path-page-enrolled.html';</script>");
            }
        }
    } catch (Exception e) {
        out.println("<script>alert('Connection failed: " + e.getMessage() + "'); window.history.back();</script>");
        return;
    } finally {
        if (stmt != null) try { stmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }

    if (!message.isEmpty()) {
    	System.out.print("radom");
        out.println("<script>alert('" + message + "'); window.history.back();</script>");
    }
    %>
</body>

</html>
