<%@ page import="java.sql.*, javax.servlet.http.*, javax.servlet.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Intro To Defensive Security</title>
    <link rel="icon" href="../../../images/Introduction-to-Cyber-Security-module1.png" type="images/x-icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../../../css/section-page.css">
    <style>
        ul { padding: 20px 0 0 55px; }
        b { font-weight: bold; color: #ec2f20; font-size: 25px; }
        .button {
            width: 20%; font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
            border: none; background-color: #9a1d14; margin-bottom: 20px; color: white; cursor: pointer;
            padding: 10px 0; border-radius: 15px; backdrop-filter: blur(4px); transition: 1s;
        }
        .button:hover {
            background-color: transparent; box-shadow: 0 0 10px #ec2f20; transition: 1s;
        }
        .input {
            width: 75%; padding: 10px; color: white; font-size: 15px; border: 2px solid #ccc;
            background-color: black; margin-right: 10px; margin-left: 15px; margin-bottom: 20px; border-radius: 15px;
        }
        .task-left .task-title { font-size: 40px; color: #9a1d14; }
    </style>
</head>

<body>
    <script src="https://cdn.jsdelivr.net/npm/tsparticles-slim@2.0.6/tsparticles.slim.bundle.min.js"></script>
    <script src="../../../javaScript/particles-config.js"></script>
    <main class="container">
        <nav>
            <div class="all">
                <div class="logo">
                    <a href="../../../html/index.html"><h1><span>Cyber</span>zone</h1></a>
                </div>
                <div class="choice"> <!--choice for desktop-->
                    <a href="../../../html/home.html">Home</a>
                    <a href="../../../html/paths.html">Paths</a>
                    <a href="../../../html/modules.html">Modules</a>
                    <a href="../../../jsp/downloads.jsp">Downloads</a>
                    <a href="https://cyberzone1.pythonanywhere.com/">Apps</a>
                    <a href="../../../html/ubuntu.html">Ubuntu</a>
                </div>
                <div class="register">
                    <a href="../../../html/sign-in.html" class="login"><button>Login</button></a>
                    <a href="../../../html/sign-up.html" class="signup"><button>sign up</button></a>
                </div>
            </div>
        </nav>
        <div class="banner-container">
            <div class="banner">
                <img class="banner-icon" src="../../../images/Introduction-to-Cyber-Security-module1.png">
                <div class="banner-text" style="padding-left: 40px;">
					<h1>Introduction To Defensive Security</h1>
                    <p>Learn about the Introduction To Defensive Security.</p>
                </div>
            </div>
        </div>
        <div class="content">
            <div class="tasks">
                <%
                Connection con = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/cyberzone", "root", "");
                    String sql = "SELECT * FROM tasks WHERE moduleId = 1 AND sectionId = 2";
                    pstmt = con.prepareStatement(sql);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                %>
                        <div class="task">
                            <div class="task-header task-header-correct-answer">
                                <div class="task-left">
                                    <p class="task-title">Task</p>
                                    <i class="fa-solid fa-circle-check icon-correct-answer"></i>
                                    <%= rs.getString("taskName") %>
                                </div>
                            </div>
                            <div class="task-content">
                                <div class="task-text">
                                    <%= rs.getString("taskDescription") %>
                                </div>
                                <div class="task-question">
                                    <div class="question-title">
                                        <h1>Answer The Question Below</h1>
                                        <hr>
                                    </div>
                                    <div class="task-text">
                                        <%= rs.getString("taskQuestion") %>
                                    </div>
                                    <form class="question-submission" onsubmit="return checkAnswer(event, '<%= rs.getString("correctAnswer") %>', '<%= session.getAttribute("userId") %>', '<%= rs.getString("taskId") %>');">
                                        <input class="input" type="text" placeholder="Answer">
                                        <input class="button" type="submit" value="Submit">
                                    </form>
                                </div>
                            </div>
                        </div>
                <%
                    }
                } catch (Exception e) {
                    out.println("Database connection failed: " + e.getMessage());
                } finally {
                    if (rs != null) try { rs.close(); } catch (Exception e) {}
                    if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
                    if (con != null) try { con.close(); } catch (Exception e) {}
                }
                %>
            </div>
        </div>
        <footer>
            <div class="main">
                <div class="tag">
                    <h1>Core Team</h1>
                    <div class="social_link">
                        <a href="#"><i class="fa-brands fa-facebook-f"></i></a>
                        <a href="#"><i class="fa-brands fa-twitter"></i></a>
                        <a href="#"><i class="fa-brands fa-instagram"></i></a>
                        <a href="#"><i class="fa-brands fa-linkedin-in"></i></a>
                    </div>
                    <div class="copyright">
                        <p>Copyright © 2024 <a href="#">Core</a> All rights reserved</p>
                    </div>
                </div>
            </div>
        </footer>
    </main>
    <script src="../../../javaScript/section.js"></script>
    <script>
        const navBar = document.querySelector('nav');
        function toggleBlurOnScroll() {
            if (window.scrollY > 50) {
                navBar.classList.add('blurred');
            } else {
                navBar.classList.remove('blurred');
            }
        }
        window.addEventListener('scroll', toggleBlurOnScroll);
    </script>
    <script src="../../../javaScript/progress3.js"></script>
</body>

</html>
