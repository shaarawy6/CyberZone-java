<%@ page import="java.io.*,javax.servlet.http.*,javax.servlet.*" %>
<%@ page errorPage="error.jsp" %>

<%
// Define the directory containing the downloadable files
String dir = "/downloads/";

// Check if a file has been requested using the GET method in the URL
String file = request.getParameter("file");
if (file != null && !file.isEmpty()) {
    String filePath = getServletContext().getRealPath(dir + file);

    // Set the HTTP header to indicate that the content type is a downloadable binary
    response.setContentType("application/octet-stream");
    // Set another HTTP header to suggest the browser should download the file
    // instead of displaying it, also suggesting the filename to save as
    response.setHeader("Content-Disposition", "attachment; filename=\"" + new File(file).getName() + "\"");

    // Check if the file exists at the specified path
    File downloadFile = new File(filePath);
    if (downloadFile.exists()) {
        // Read the file and send it to the browser, prompting a download
        FileInputStream inStream = new FileInputStream(downloadFile);
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    } else {
        out.println("Requested file does not exist.");
    }
}
%>

<!DOCTYPE html>
<html>

<head>
    <title>Download Apps</title>
    <link rel="stylesheet" href="../css/downloads.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" href="../images/Hacker.png" type="images/x-icon">
</head>

<body>
    <nav>
        <div class="all">
            <div class="logo">
                <a href="../html/index.html">
                    <h1><span>Cyber</span>zone</h1>
                </a>
            </div>
            <div class="choice"> <!--choice for desktop-->
                <a href="../html/home.html">Home</a> <!--<i></i> tag "icon"-->
                <a href="../html/paths.html">Paths</a>
                <a href="../html/modules.html">Modules</a>
                <a href="../jsp/downloads.jsp">Downloads</a>
                <a href="https://cyberzone1.pythonanywhere.com/">Apps</a>
                <a href="../html/ubuntu.html">Ubuntu</i></a>
            </div>
            <div class="register">
                <a href="../html/sign-in.html" class="login"><button>Login</button></a>
                <a href="../html/sign-up.html" class="signup"><button>sign up</button></a>
            </div>
        </div>
    </nav>
    <!-- Create links to download each of the Python scripts -->
    <!-- When a link is clicked, the filename is passed as a parameter in the URL -->
    <footer>
        <div class="footer_main">
            <div class="tag">
                <div class="content" id="Content">
            <div class="box">
                <a href="?file=Applications.zip">
                    <div class="card" id="Apps" style="background-image: url(../images/Applicationss.jpg);">
                        <div class="content_text">
                            <h2>Applications.zip</h2>
                        </div>
                    </div>
                </a>
                <a href="?file=passwordCracker.zip">
                    <div class="card" id="Apps" style="background-image: url(../images/passwordCracker.jpg);">
                        <div class="content_text">
                            <h2>passwordCracker.zip</h2>
                        </div>
                    </div>
                </a>
                <a href="?file=EscapeRoom.zip">
                    <div class="card" id="Apps" style="background-image: url(../images/EscapeRoom.jpg);">
                        <div class="content_text">
                            <h2>EscapeRoom.zip</h2>
                        </div>
                    </div>
                </a>
                <a href="?file=KeyLogger.zip">
                    <div class="card " id="Apps" style="background-image: url(../images/KeyLogger.jpg);">
                        <div class="content_text">
                            <h2>KeyLogger.zip</h2>
                        </div>
                    </div>
                </a>
                <a href="?file=Ransomware.zip">
                    <div class="card" id="Apps" style="background-image: url(../images/Ransomware.jpg);">
                        <div class="content_text">
                            <h2>Ransomware.zip</h2>
                        </div>
                    </div>
                </a>
                <a href="?file=ComputerCrasher.zip">
                    <div class="card" id="Apps" style="background-image: url(../images/ComputerCrasher.jpg);">
                        <div class="content_text">
                            <h2>ComputerCrasher.zip</h2>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <footer>
                <div class="main">
                    <dsiv class="tag">
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
    <script src="https://cdn.jsdelivr.net/npm/tsparticles-slim@2.0.6/tsparticles.slim.bundle.min.js"></script>
    <script src="../javaScript/particles-config.js"></script>
    <script src="../javaScript/progress.js"></script>
</body>

</html>
