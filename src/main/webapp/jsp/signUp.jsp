<%@ page import="java.net.URLEncoder,java.sql.*, javax.mail.*, javax.mail.internet.*, javax.activation.*, java.util.Properties,org.mindrot.jbcrypt.BCrypt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Success</title>
    <link rel="icon" href="../images/Hacker.png" type="images/x-icon">
</head>
<body>
<%
    String firstName = request.getParameter("firstName");
    String secondName = request.getParameter("secondName");
    String userName = request.getParameter("userName");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmPassword");
    String phoneNumber = request.getParameter("phoneNumber");
    String isStudent = request.getParameter("isStudent");

    if (firstName != null && password.equals(confirmPassword)) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone", "root", "");

            String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String verificationCode = Integer.toString((int)(Math.random() * 900000) + 100000);

            String query = "INSERT INTO users (firstName, secondName, userName, email, password, phoneNumber, isStudent, verification_code, email_verified_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NULL)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, secondName);
            pstmt.setString(3, userName);
            pstmt.setString(4, email);
            pstmt.setString(5, encryptedPassword);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, isStudent);
            pstmt.setString(8, verificationCode);
            pstmt.executeUpdate();

            // Send email
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session ses = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("cyberzonever88@gmail.com", "liuykwhykqzeukem");
                }
            });

            Message message = new MimeMessage(ses);
            message.setFrom(new InternetAddress("cyberzonever88@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Email Verification");
            message.setContent("<p>Your Verification code is: <b>" + verificationCode + "</b></p>", "text/html");
            Transport.send(message);

            response.sendRedirect("emailVerification.jsp?email=" + URLEncoder.encode(email, "UTF-8"));
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                out.println("<p>Error on closing the connection: " + ex.getMessage() + "</p>");
            }
        }
    } else {
        out.println("<p>Error: Input data is not valid or passwords do not match.</p>");
    }
%>
</body>
</html>
