<%@ page import="javax.servlet.http.*" %>
<%
// Invalidate the session to log out the user
session.invalidate();

// Output a message to confirm the logout
response.getWriter().write("Logged out");
%>
