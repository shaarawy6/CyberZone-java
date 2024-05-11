<%@ page import="java.net.URLEncoder, java.sql.*, javax.servlet.*, javax.servlet.http.*, java.io.*, org.mindrot.jbcrypt.BCrypt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="icon" href="../images/Hacker.png" type="images/x-icon">
</head>
<body>
    <% 
        // Starting session
        HttpSession ses = request.getSession();
        boolean redirect = false;
        String redirectURL = "../html/home.html";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");

            if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("signin") != null) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Perform SQL query to check if email and password match
                String sql = "SELECT * FROM users WHERE email = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, email);
                rs = pstmt.executeQuery();

                if (!rs.next()) {
                    out.println("<p>Email not found.</p>");
                } else {
                    String storedPassword = rs.getString("password");
                    if (!BCrypt.checkpw(password, storedPassword)) {
                        out.println("<p>Password is not correct.</p>");
                    } else {
                        if (rs.getString("email_verified_at") == null) {
                            redirectURL = "emailVerification.jsp?email=" + URLEncoder.encode(email, "UTF-8");
                        } else {
                            // Set email in cookie
                            Cookie emailCookie = new Cookie("userEmail", URLEncoder.encode(email, "UTF-8"));
                            emailCookie.setMaxAge(60 * 60 * 24); // Expire in 1 day
                            emailCookie.setPath("/"); // Ensure it's available for the entire domain
                            response.addCookie(emailCookie);
                            out.println("<p>Cookie userEmail should be set.</p>");

                            ses.setAttribute("userId", rs.getString("userId"));
                            out.println("<p>Welcome back! Your email has been saved for a faster experience next time.</p>");
                        }
                        redirect = true;
                    }
                }
            } else {
                // Form submission failed
                out.println("<p>Please provide valid email and password.</p>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
            if (redirect) {
                response.sendRedirect(redirectURL);
            }
        }
    %>
</body>
</html>
