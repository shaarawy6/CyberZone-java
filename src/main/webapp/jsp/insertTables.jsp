<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="website.DBInitialization" %>
<%@ page errorPage="error.jsp" %>

<html>
<head>
    <title>Initialize Database</title>
</head>
<body>
<jsp:useBean id="db" class="website.DBInitialization" scope="application" />
<%
    try {
    	
        db.insertApps();
        out.println("<h1>Applications have been added to the database.</h1>");
        System.out.println("Helohelo");
    } catch (Exception e) {
        out.println("<h1>Error adding applications to the database.</h1>");
        e.printStackTrace();
    }
%>

<%
    try {
        db.insertDownloads();
        out.println("<h1>Downloads have been added to the database.</h1>");
    } catch (Exception e) {
        out.println("<h1>Error adding downloads to the database.</h1>");
        e.printStackTrace();
    }
%>

<%
    try {
        db.insertModules();
        out.println("<h1>Modules have been added to the database.</h1>");
    } catch (Exception e) {
        out.println("<h1>Error adding modules to the database.</h1>");
        e.printStackTrace();
    }
%>

<%
    try {
        db.insertPaths();
        out.println("<h1>Path have been added to the database.</h1>");
    } catch (Exception e) {
        out.println("<h1>Error adding path to the database.</h1>");
        e.printStackTrace();
    }
%>

<%
    try {
        db.insertTasks();
        out.println("<h1>tasks have been added to the database.</h1>");
    } catch (Exception e) {
        out.println("<h1>Error adding tasks to the database.</h1>");
        e.printStackTrace();
    }
%>

</body>
</html>