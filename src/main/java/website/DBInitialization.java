package website;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;

public class DBInitialization {
    public Connection con;

    public DBInitialization() {
        try {
        	System.out.println("HELLOOLLEH");
            // Load driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            // Establish a connection to database cyberzone
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            System.out.println("Connected");

            // Create a statement for running SQL
            Statement statement = con.createStatement();

            // Create tables in the correct order
            statement.executeUpdate("CREATE IF NOT EXISTS TABLE paths ("
                    + "pathId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "pathName VARCHAR(255)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS downloads ("
                    + "downloadId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "downloadName VARCHAR(255)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS applications ("
                    + "applicationId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "applicationName VARCHAR(255)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "userId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "firstName VARCHAR(50) NOT NULL,"
                    + "secondName VARCHAR(50) NOT NULL,"
                    + "userName VARCHAR(50) NOT NULL UNIQUE,"
                    + "email VARCHAR(100) NOT NULL UNIQUE,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "phoneNumber VARCHAR(20) NOT NULL,"
                    + "isStudent ENUM('yes', 'no') NOT NULL,"
                    + "verification_code INT NOT NULL,"
                    + "email_verified_at TIMESTAMP"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS modules ("
                    + "moduleId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "moduleName VARCHAR(255),"
                    + "pathId INT UNSIGNED,"
                    + "FOREIGN KEY (pathId) REFERENCES paths(pathId) ON DELETE CASCADE"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tasks ("
                    + "taskId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "moduleId INT UNSIGNED,"
                    + "sectionId INT UNSIGNED,"
                    + "taskName VARCHAR(255),"
                    + "taskDescription TEXT,"
                    + "taskQuestion TEXT,"
                    + "correctAnswer VARCHAR(255),"
                    + "FOREIGN KEY (moduleId) REFERENCES modules(moduleId)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS enrollment ("
                    + "enrollmentId INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "enrollmentDate DATE,"
                    + "userId INT UNSIGNED,"
                    + "moduleId INT UNSIGNED,"
                    + "FOREIGN KEY (userId) REFERENCES users(userId),"
                    + "FOREIGN KEY (moduleId) REFERENCES modules(moduleId)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS userProgress ("
                    + "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "userId INT UNSIGNED,"
                    + "moduleId INT UNSIGNED,"
                    + "progress FLOAT UNSIGNED DEFAULT 0,"
                    + "FOREIGN KEY (userId) REFERENCES users(userId),"
                    + "FOREIGN KEY (moduleId) REFERENCES modules(moduleId),"
                    + "UNIQUE KEY unique_user_module (userId, moduleId)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS taskCompletion ("
                    + "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "taskId INT UNSIGNED,"
                    + "userId INT UNSIGNED,"
                    + "moduleId INT UNSIGNED,"
                    + "isCompleted BOOLEAN DEFAULT 0,"
                    + "completionDate TIMESTAMP,"
                    + "FOREIGN KEY (taskId) REFERENCES tasks(taskId),"
                    + "FOREIGN KEY (userId) REFERENCES users(userId),"
                    + "FOREIGN KEY (moduleId) REFERENCES modules(moduleId),"
                    + "UNIQUE KEY unique_user_module_task (userId, moduleId, taskId)"
                    + ")");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException ex) {
            }
        }
    }
     
//    ######## View Data ########
    
    
    public ArrayList<users> viewUsers() {
        ArrayList<users> list = new ArrayList<>();
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM users");

            while (result.next()) {
                users u = new users();
                u.setUserId(result.getInt("userId"));
                u.setFirstName(result.getString("firstName"));
                u.setSecondName(result.getString("secondName"));
                u.setUserName(result.getString("userName"));
                u.setEmail(result.getString("email"));
                u.setPassword(result.getString("password"));
                u.setPhoneNumber(result.getString("phoneNumber"));
                u.setIsStudent(result.getString("isStudent"));
                list.add(u);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
    public ArrayList<paths> viewPaths() {
        ArrayList<paths> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM paths");

            while (result.next()) {
                paths p = new paths();
                p.setPathId(result.getInt("pathId"));
                p.setPathName(result.getString("pathName"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public ArrayList<downloads> viewDownloads() {
        ArrayList<downloads> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM downloads");

            while (result.next()) {
                downloads d = new downloads();
                d.setDownloadId(result.getInt("downloadId"));
                d.setDownloadName(result.getString("downloadName"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<applications> viewApplications() {
        ArrayList<applications> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM applications");

            while (result.next()) {
                applications a = new applications();
                a.setApplicationId(result.getInt("applicationId"));
                a.setApplicationName(result.getString("applicationName"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<modules> viewModules() {
        ArrayList<modules> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM modules");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                modules m = new modules();
                m.setModuleId(result.getInt("moduleId"));
                m.setModuleName(result.getString("moduleName"));

                paths p = new paths();
                p.setPathId(result.getInt("pathId"));
                m.setPathId(p); // Set the paths object to the modules object
                
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public ArrayList<tasks> viewTasks() {
        ArrayList<tasks> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM tasks");

            while (result.next()) {
                tasks t = new tasks();
                t.setTaskId(result.getInt("taskId"));
                
                modules m = new modules();
                m.setModuleId(result.getInt("moduleId"));
                t.setModuleId(m);
                
                t.setSectionId(result.getInt("sectionId"));
                t.setTaskName(result.getString("taskName"));
                t.setTaskDescription(result.getString("taskDescription"));
                t.setTaskQuestion(result.getString("taskQuestion"));
                t.setCorrectAnswer(result.getString("correctAnswer"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public ArrayList<enrollment> viewEnrollments() {
        ArrayList<enrollment> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM enrollment");

            while (result.next()) {
                enrollment e = new enrollment();
                e.setEnrollmentId(result.getInt("enrollmentId"));
                e.setEnrollmentDate(result.getDate("enrollmentDate"));
                
                users u = new users();
                u.setUserId(result.getInt("userId"));
                e.setUserId(u);
                
                modules m = new modules();
                m.setModuleId(result.getInt("moduleId"));
                e.setModuleId(m);
                list.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    
    public ArrayList<userProgress> viewUserProgress() {
        ArrayList<userProgress> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM userProgress");

            while (result.next()) {
                userProgress up = new userProgress();
                up.setId(result.getInt("id"));

                users u = new users();
                u.setUserId(result.getInt("userId"));
                up.setUserId(u);
                
                modules m = new modules();
                m.setModuleId(result.getInt("moduleId"));
                up.setModuleId(m);
                
                up.setProgress(result.getFloat("progress"));
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<taskCompletion> viewTaskCompletions() {
        ArrayList<taskCompletion> list = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM taskCompletion");

            while (result.next()) {
                taskCompletion tc = new taskCompletion();
                tc.setId(result.getInt("id"));
                
                tasks t = new tasks();
                t.setTaskId(result.getInt("taskId"));
                tc.setTaskId(t);

                users u = new users();
                u.setUserId(result.getInt("userId"));
                tc.setUserId(u);
                
                modules m = new modules();
                m.setModuleId(result.getInt("moduleId"));
                tc.setModuleId(m);
                
                tc.setCompleted(result.getBoolean("isCompleted"));
                tc.setCompletionDate(result.getDate("completionDate"));
                list.add(tc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    
// ############# Insertion Functions ########
    
    public void insertApps() {

        try {
            // Establish a connection
        	System.out.println("FIRSTFIRSTFIRST");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            System.out.println("WAITINGWAITING");
            System.out.println("MIDDLEMIDDLE");
            ArrayList<String> arr = new ArrayList(Arrays.asList("Caesar Cipher",  "Playfair Cipher",  "Rail Fence Cipher",  "Vigenère Cipher",  "Hash Generator",  "Hill Cipher",  "Password Generator"));
            for(int i = 0; i < arr.size(); i++)
            {
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO applications (applicationName) VALUES (?)");
            	System.out.println("value "+ i + " placed");
            	pstmt.setString(1, arr.get(i));
            	pstmt.executeUpdate();
            }
            System.out.println("WAITINGWAITING");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
//    Why Written like that
    public void insertModules() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO modules (moduleName, pathId) VALUES (?, ?)");
            
            // Add first set of modules to the batch
//            create class that can carry two different data types
            Object[][] modules = {
                {"Introduction to Cyber Security", 1},
                {"Introduction to Offensive Security", 1},
                {"Introduction to Defensive Security", 1},
                {"Introduction to Cyber Security", 2},
                {"Network Fundamentals", 2},
                {"How The Web Works", 2},
                {"Linux Fundamentals", 2},
                {"Windows Fundamentals", 2}
            };
            
            for (Object[] module : modules) {
                pstmt.setString(1, (String) module[0]);
                pstmt.setInt(2, (Integer) module[1]);
                pstmt.addBatch();
            }
            pstmt.executeBatch(); // Execute the first batch

            pstmt = con.prepareStatement("INSERT INTO modules (moduleName) VALUES (?)");
            pstmt.setString(1, "XSS");
            pstmt.executeUpdate(); // Execute the second batch
            
            System.out.println("New records created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting modules: " + e.getMessage());
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void insertPaths() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO paths (pathName) VALUES (?), (?)");
            
            // Setting path names
            pstmt.setString(1, "Introduction to Cyber Security");
            pstmt.setString(2, "Network Security");

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("New records created successfully");
            } else {
                System.out.println("No new records were created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting paths: " + e.getMessage());
        } finally {
        	try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void insertDownloads() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            // Execute insert SQL statement
            Statement stmt = con.createStatement();
            int count = stmt.executeUpdate("INSERT INTO downloads (downloadName) VALUES " +
                    "('Applications.zip')," +
                    "('passwordCracker.zip')," +
                    "('EscapeRoom.zip')," +
                    "('KeyLogger.zip')," +
                    "('Ransomware.zip')," +
                    "('ComputerCrasher.zip');");

            if (count > 0) {
                System.out.println("New downloads created successfully");
            } else {
                System.out.println("No new downloads were created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        } finally {
        	try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void insertTasks() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyberzone","root","");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO tasks (moduleId, sectionId, taskName, taskDescription, taskQuestion, correctAnswer) VALUES (?, ?, ?, ?, ?, ?)");

            // Define the tasks data
            Object[][] tasks = {
            	    {
	            	     1, 
	            	     1, "<p>What is Offensive Security?</p>",
	            	     "<p>In short, offensive security is the process of breaking into computer systems, exploiting software bugs, and finding loopholes in applications to gain unauthorized access to them. " +
	            	     "To beat a hacker, you need to behave like a hacker, finding vulnerabilities and recommending patches before a cybercriminal does, as you'll do in this room!</p>" +
	            	     "<br>" +
	            	     "<p>On the flip side, there is also defensive security, which is the process of protecting an organization's network and computer systems by analyzing and securing any potential digital threats; learn more in the digital forensics room. " +
	            	     "In a defensive cyber role, you could be investigating infected computers or devices to understand how it was hacked, tracking down cybercriminals, or monitoring infrastructure for malicious activity.</p>",
	            	     "<p>Which of the following options better represents the process where you simulate a hacker's actions to find vulnerabilities in a system?" +
	            	     "<ul>" +
	            	     "<li>Offensive Security</li>" +
	            	     "<li>Defensive Security</li>" +
	            	     "</ul>" +
	            	     "</p>",
	            	     "Offensive security"
            	     }
            	    ,
            	    {
            	    	1,
	        	        1,
	        	        "<p>Hacking your first machine</p>",
	        	        "<p>Before going into cyber security careers and what offensive security is, let's get you hacking (and yes, it's legal, all exercises are fake simulations)</p>" +
	        	        "<br>" +
	        	        "<p>Your first hack</p>" +
	        	        "<p>Click the <i>Start Machine</i> button. Once loaded in Split View in your browser, you will have access to a machine you'll use to hack a fake bank application called FakeBank. If you don't see the machine appear, use the blue Show Split View button on the top-left of this page.</p>" +
	        	        "<br>" +  
	        	        "<p>We will use a command-line application called <i>GoBuster</i> to brute-force FakeBank's website to find hidden directories and pages. GoBuster will take a list of potential page or directory names and tries accessing a website with each of them; if the page exists, it tells you.</p>" +
	        	        "<br>" +
	        	        "<br>" +
	        	        "<b>Step 1) Open a terminal</b>" +
	        	        "<br>" +
	        	        "<p>A terminal, also known as the command-line, allows us to interact with a computer without using a graphical user interface.</p>" +
	        	        "<br>" +
	        	        "<b>Step 2) Find hidden website pages</b>" +
	        	        "<br>" +
	        	        "<p>Most companies will have an admin portal page, giving their staff access to basic admin controls for day-to-day operations. For a bank, an employee might need to transfer money to and from client accounts. Often these pages are not made private, allowing attackers to find hidden pages that show, or give access to, admin controls or sensitive data.</p>" +
	        	        "<br>" +
	        	        "<p>Type the following command into the terminal to find potentially hidden pages on FakeBank's website using GoBuster (a command-line security application).</p>" +
	        	        "<br>" +
	        	        "<p>gobuster -u http://fakebank.com -w wordlist.txt dir</p>" +
	        	        "<br>" +
	        	        "<p>In the command above, -u is used to state the website we're scanning, -w takes a list of words to iterate through to find hidden pages.</p>" +
	        	        "<br>" +
	        	        "<p>You will see that GoBuster scans the website with each word in the list, finding pages that exist on the site. GoBuster will have told you the pages it found in the list of page/directory names (indicated by Status: 200).</p>" +
	        	        "<br>" +
	        	        "<b>Step 3) Hack the bank</b>" +
	        	        "<br>" +
	        	        "<p>This page allows an attacker to steal money from any bank account, which is a critical risk for the bank. As an ethical hacker, you would (with permission) find vulnerabilities in their application and report them to the bank to fix before a hacker exploits them.</p>" +
	        	        "<br>" +
	        	        "<p>Transfer $2000 from the bank account 2276, to your account (account number 8881).</p>",
	        	        "<p>No Question just press submit</p>",
	        	        ""
        	        }
            	    ,
            	    {
	    	    	    1,
	    	    	    1,
	    	    	    "<p>Careers in cyber security</p>",
	    	    	    "<p><b>How can I start learning?</b></p>" +
	    	    	    "<br>" +
	    	    	    "<p>People often wonder how others become hackers (security consultants) or defenders (security analysts fighting cybercrime), and the answer is simple. Break it down, learn an area of cyber security you're interested in, and regularly practice using hands-on exercises. Build a habit of learning a little bit each day on TryHackMe, and you'll acquire the knowledge to get your first job in the industry.</p>" +
	    	    	    "<br>" +
	    	    	    "<p>Trust us; you can do it! Just take a look at some people who have used TryHackMe to get their first security job:</p>" +
	    	    	    "<ul>" +
	    	    	        "<li>Paul went from a construction worker to a security engineer.</li>" +
	    	    	        "<li>Kassandra went from a music teacher to a security professional.</li>" +
	    	    	        "<li>Brandon used TryHackMe while at school to get his first job in cyber.</li>" +
	    	    	    "</ul>" +
	    	    	    "<br>" +
	    	    	    "<p><b>What careers are there?</b></p>" +
	    	    	    "<br>" +
	    	    	    "<p>The cyber careers room goes into more depth about the different careers in cyber. However, here is a short description of a few offensive security roles:</p>" +
	    	    	    "<ul>" +
	    	    	        "<li>Penetration Tester - Responsible for testing technology products for finding exploitable security vulnerabilities.</li>" +
	    	    	        "<li>Red Teamer - Plays the role of an adversary, attacking an organization and providing feedback from an enemy's perspective..</li>" +
	    	    	        "<li>Security Engineer - Design, monitor, and maintain security controls, networks, and systems to help prevent cyberattacks.</li>" +
	    	    	    "</ul>",
	    	    	    "<p>No Question just press submit</p>",
	    	    	    ""
    	    	    }
            	    ,
            	    {
            	    	1,
	    	    	    2,
	    	    	    "<p>Introduction to Defensive Security</p>",
	    	    	    "<p>Offensive security focuses on one thing: breaking into systems. Breaking into systems might be achieved through exploiting bugs, abusing insecure setups, and taking advantage of unenforced access control policies, among other things. Red teams and penetration testers specialize in offensive security.</p>" +
	    	    	    "<br>" +
	    	    	    "<p>Defensive security is somewhat the opposite of offensive security, as it is concerned with two main tasks:</p>" +
	    	    	    "<br>" +
	    	    	    "<ul>" +
	    	    	        "<li>Preventing intrusions from occurring</li>" +
	    	    	        "<li>Detecting intrusions when they occur and responding properly</li>" +
	    	    	    "</ul>" +
	    	    	    "<br>" +
	    	    	    "<p>Blue teams are part of the defensive security landscape.</p>" +
	    	    	    "<br>" +
	    	    	    "<p>Some of the tasks that are related to defensive security include:</p>" +
	    	    	    "<br>" +
	    	    	    "<ul>" +
	    	    	        "<li>User cyber security awareness: Training users about cyber security helps protect against various attacks that target their systems.</li>" +
	    	    	        "<li>Documenting and managing assets: We need to know the types of systems and devices that we have to manage and protect properly.</li>" +
	    	    	        "<li>Updating and patching systems: Ensuring that computers, servers, and network devices are correctly updated and patched against any known vulnerability (weakness).</li>" +
	    	    	        "<li>Setting up preventative security devices: firewall and intrusion prevention systems (IPS) are critical components of preventative security. Firewalls control what network traffic can go inside and what can leave the system or network. IPS blocks any network traffic that matches present rules and attack signatures.</li>" +
	    	    	        "<li>Setting up logging and monitoring devices: Without proper logging and monitoring of the network, it won’t be possible to detect malicious activities and intrusions. If a new unauthorized device appears on our network, we should be able to know.</li>" +
	    	    	    "</ul>" +
	    	    	    "<br>" +
	    	    	    "<p>There is much more to defensive security, and the list above only covers a few common topics.</p>" +
	    	    	    "<br>" +
	    	    	    "<p>In this room, we cover:</p>" +
	    	    	    "<br>" +
	    	    	    "<ul>" +
	    	    	        "<li>Security Operations Center (SOC)</li>" +
	    	    	        "<li>Threat Intelligence</li>" +
	    	    	        "<li>Digital Forensics and Incident Response (DFIR)</li>" +
	    	    	        "<li>Malware Analysis</li>" +
	    	    	    "</ul>",
	    	    	    "<p>Which team focuses on defensive security?</p>",
	    	    	    "blue team"
    	    	    }
            	    ,
            	    {
            	        1,
            	        2,
            	        "<p>Areas of Defensive Security</p>",
            	        "<p>In this task, we will cover two main topics related to defensive security:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Security Operations Center (SOC), where we cover Threat Intelligence</li>" +
            	        "<li>Digital Forensics and Incident Response (DFIR), where we also cover Malware Analysis</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h2>Security Operations Center (SOC)</h2>" +
            	        "<br>" +
            	        "<p>A Security Operations Center (SOC) is a team of cyber security professionals that monitors the network and its systems to detect malicious cyber security events. Some of the main areas of interest for a SOC are:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Vulnerabilities: Whenever a system vulnerability (weakness) is discovered, it is essential to fix it by installing a proper update or patch. When a fix is not available, the necessary measures should be taken to prevent an attacker from exploiting it. Although remediating vulnerabilities is of vital interest to a SOC, it is not necessarily assigned to them.</li>" +
            	        "<li>Policy violations: We can think of a security policy as a set of rules required for the protection of the network and systems. For example, it might be a policy violation if users start uploading confidential company data to an online storage service.</li>" +
            	        "<li>Unauthorized activity: Consider the case where a user’s login name and password are stolen, and the attacker uses them to log into the network. A SOC needs to detect such an event and block it as soon as possible before further damage is done.</li>" +
            	        "<li>Network intrusions: No matter how good your security is, there is always a chance for an intrusion. An intrusion can occur when a user clicks on a malicious link or when an attacker exploits a public server. Either way, when an intrusion occurs, we must detect it as soon as possible to prevent further damage.</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<p>Security operations cover various tasks to ensure protection; one such task is threat intelligence.</p>" +
            	        "<br>" +
            	        "<h2>Threat Intelligence</h2>" +
            	        "<br>" +
            	        "<p>In this context, intelligence refers to information you gather about actual and potential enemies. A threat is any action that can disrupt or adversely affect a system. Threat intelligence aims to gather information to help the company better prepare against potential adversaries. The purpose would be to achieve a threat-informed defense. Different companies have different adversaries. Some adversaries might seek to steal customer data from a mobile operator; however, other adversaries are interested in halting the production in a petroleum refinery. Example adversaries include a nation-state cyber army working for political reasons and a ransomware group acting for financial purposes. Based on the company (target), we can expect adversaries.</p>" +
            	        "<br>" +
            	        "<p>Intelligence needs data. Data has to be collected, processed, and analyzed. Data collection is done from local sources such as network logs and public sources such as forums. Processing of data aims to arrange them into a format suitable for analysis. The analysis phase seeks to find more information about the attackers and their motives; moreover, it aims to create a list of recommendations and actionable steps.</p>" +
            	        "<br>" +
            	        "<p>Learning about your adversaries allows you to know their tactics, techniques, and procedures. As a result of threat intelligence, we identify the threat actor (adversary), predict their activity, and consequently, we will be able to mitigate their attacks and prepare a response strategy.</p>" +
            	        "<br>" +
            	        "<h2>Digital Forensics and Incident Response (DFIR)</h2>" +
            	        "<br>" +
            	        "<p>This section is about Digital Forensics and Incident Response (DFIR), and we will cover:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Digital Forensics</li>" +
            	        "<li>Incident Response</li>" +
            	        "<li>Malware Analysis</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h3>Digital Forensics</h3>" +
            	        "<br>" +
            	        "<p>Forensics is the application of science to investigate crimes and establish facts. With the use and spread of digital systems, such as computers and smartphones, a new branch of forensics was born to investigate related crimes: computer forensics, which later evolved into digital forensics.</p>" +
            	        "<br>" +
            	        "<p>In defensive security, the focus of digital forensics shifts to analyzing evidence of an attack and its perpetrators and other areas such as intellectual property theft, cyber espionage, and possession of unauthorized content. Consequently, digital forensics will focus on different areas such as:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>File System: Analyzing a digital forensics image (low-level copy) of a system’s storage reveals much information, such as installed programs, created files, partially overwritten files, and deleted files.</li>" +
            	        "<li>System memory: If the attacker is running their malicious program in memory without saving it to the disk, taking a forensic image (low-level copy) of the system memory is the best way to analyze its contents and learn about the attack.</li>" +
            	        "<li>System logs: Each client and server computer maintains different log files about what is happening. Log files provide plenty of information about what happened on a system. Some traces will be left even if the attacker tries to clear their traces.</li>" +
            	        "<li>Network logs: Logs of the network packets that have traversed a network would help answer more questions about whether an attack is occurring and what it entails.</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h3>Incident Response</h3>" +
            	        "<br>" +
            	        "<p>An incident usually refers to a data breach or cyber attack; however, in some cases, it can be something less critical, such as a misconfiguration, an intrusion attempt, or a policy violation. Examples of a cyber attack include an attacker making our network or systems inaccessible, defacing (changing) the public website, and data breach (stealing company data). How would you respond to a cyber attack? Incident response specifies the methodology that should be followed to handle such a case. The aim is to reduce damage and recover in the shortest time possible. Ideally, you would develop a plan ready for incident response.</p>" +
            	        "<br>" +
            	        "<p>The four major phases of the incident response process are:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Preparation: This requires a team trained and ready to handle incidents. Ideally, various measures are put in place to prevent incidents from happening in the first place.</li>" +
            	        "<li>Detection and Analysis: The team has the necessary resources to detect any incident; moreover, it is essential to further analyze any detected incident to learn about its severity.</li>" +
            	        "<li>Containment, Eradication, and Recovery: Once an incident is detected, it is crucial to stop it from affecting other systems, eliminate it, and recover the affected systems. For instance, when we notice that a system is infected with a computer virus, we would like to stop (contain) the virus from spreading to other systems, clean (eradicate) the virus, and ensure proper system recovery.</li>" +
            	        "<li>Post-Incident Activity: After successful recovery, a report is produced, and the learned lesson is shared to prevent similar future incidents.</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h2>Malware Analysis</h2>" +
            	        "<br>" +
            	        "<p>Malware stands for malicious software. Software refers to programs, documents, and files that you can save on a disk or send over the network. Malware includes many types, such as:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Virus is a piece of code (part of a program) that attaches itself to a program. It is designed to spread from one computer to another; moreover, it works by altering, overwriting, and deleting files once it infects a computer. The result ranges from the computer becoming slow to unusable.</li>" +
            	        "<li>Trojan Horse is a program that shows one desirable function but hides a malicious function underneath. For example, a victim might download a video player from a shady website that gives the attacker complete control over their system.</li>" +
            	        "<li>Ransomware is a malicious program that encrypts the user’s files. Encryption makes the files unreadable without knowing the encryption password. The attacker offers the user the encryption password if the user is willing to pay a “ransom.”</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<p>Malware analysis aims to learn about such malicious programs using various means:</p>" +
            	        "<ul>" +
            	        "<li>Static analysis works by inspecting the malicious program without running it. Usually, this requires solid knowledge of assembly language (processor’s instruction set, i.e., computer’s fundamental instructions).</li>" +
            	        "<li>Dynamic analysis works by running the malware in a controlled environment and monitoring its activities. It lets you observe how the malware behaves when running.</li>" +
            	        "</ul>",
            	        "<p>What would you call a team of cyber security professionals that monitors a network and its systems for malicious events?</p>",
            	        "SOC"
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Introduction</p>",
            	        "<p>Cyber security careers are becoming more in demand and offer high salaries. There are many different jobs within the security industry, from offensive pentesting (hacking machines and reporting on vulnerabilities) to defensive security (defending against and investigating cyberattacks).</p>" +
            	        "<br>" +
            	        "<p>Why get a career in cyber:</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>High Pay - jobs in security have high starting salaries</li>" +
            	        "<li>Exciting - work can include legally hacking systems or defending against cyber attacks</li>" +
            	        "<li>Be in demand - there are over 3.5 million unfilled cyber positions</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<p>This room helps you break into cyber security by providing information about various cyber security roles; it also links to different learning paths that you can use to start building your cyber skills.</p>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Security Analyst</p>",
            	        "<p>Security analysts are integral to constructing security measures across organisations to protect the company from attacks. Analysts explore and evaluate company networks to uncover actionable data and recommendations for engineers to develop preventative measures. This job role requires working with various stakeholders to gain an understanding of security requirements and the security landscape.</p>" +
            	        "<br>" +
            	        "<h2>Responsibilities</h2>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Working with various stakeholders to analyse the cyber security throughout the company</li>" +
            	        "<li>Compile ongoing reports about the safety of networks, documenting security issues and measures taken in response</li>" +
            	        "<li>Develop security plans, incorporating research on new attack tools and trends, and measures needed across teams to maintain data security.</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h2>Learning Paths</h2>" +
            	        "<br>" +
            	        "<p>TryHackMe's learning paths will give you both the fundamental technical knowledge and hands-on experience, which is crucial to becoming a successful Security Analyst.</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Introduction to Cyber Security</li>" +
            	        "<li>Pre Security</li>" +
            	        "<li>SOC Level 1</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    }
            	    ,
            	    {
            	        1,
            	        3,
            	        "<p>Security Engineer</p>",
            	        "<p>Security engineers develop and implement security solutions using threats and vulnerability data - often sourced from members of the security workforce. Security engineers work across circumventing a breadth of attacks, including web application attacks, network threats, and evolving trends and tactics. The ultimate goal is to retain and adopt security measures to mitigate the risk of attack and data loss.</p>" +
            	        "<br>" +
            	        "<h2>Responsibilities</h2>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Testing and screening security measures across software</li>" +
            	        "<li>Monitor networks and reports to update systems and mitigate vulnerabilities</li>" +
            	        "<li>Identify and implement systems needed for optimal security</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h2>Learning Paths</h2>" +
            	        "<br>" +
            	        "<p>TryHackMe's learning paths will give you both the fundamental technical knowledge and hands-on experience, which is crucial to becoming a successful Security Engineer.</p>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>SOC Level 1</li>" +
            	        "<li>JR Penetration Tester</li>" +
            	        "<li>Offensive Pentesting</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Incident Responder</p>",
            	        "<p>Incident responders respond productively and efficiently to security breaches. Responsibilities include creating plans, policies, and protocols for organisations to enact during and following incidents. This is often a highly pressurised position with assessments and responses required in real-time, as attacks are unfolding. Incident response metrics include MTTD, MTTA, and MTTR - the meantime to detect, acknowledge, and recover (from attacks.) The aim is to achieve a swift and effective response, retain financial standing and avoid negative breach implications. Ultimately, incident responders protect the company's data, reputation, and financial standing from cyber attacks.</p>" +
            	        "<br>" +
            	        "<h2>Responsibilities</h2>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Developing and adopting a thorough, actionable incident response plan</li>" +
            	        "<li>Maintaining strong security best practices and supporting incident response measures</li>" +
            	        "<li>Post-incident reporting and preparation for future attacks, considering learnings and adaptations to take from incidents</li>" +
            	        "</ul>" +
            	        "<br>" +
            	        "<h2>Learning Paths</h2>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>TryHackMe's learning paths will give you both the fundamental technical knowledge and hands-on experience, which is crucial to becoming a successful Incident Responder.</li>" +
            	        "<li>SOC Level 1</li>" +
            	        "</ul>",
            	        "<p>Incident Responder</p>",
            	        ""
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Digital Forensics Examiner</p>",
            	        "<p>If you like to play detective, this might be the perfect job. If you are working as part of a law-enforcement department, you would be focused on collecting and analysing evidence to help solve crimes: charging the guilty and exonerating the innocent. On the other hand, if your work falls under defending a company's network, you will be using your forensic skills to analyse incidents, such as policy violations.</p>" +
            	        "<br>" +
            	        "<h2>Responsibilities</h2>" +
            	        "<br>" +
            	        "<ul>" +
            	        "<li>Collect digital evidence while observing legal procedures</li>" +
            	        "<li>Analyse digital evidence to find answers related to the case</li>" +
            	        "<li>Document your findings and report on the case</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Malware Analyst</p>",
            	        "<h2>A malware analyst's work involves analysing suspicious programs, discovering what they do and writing reports about their findings.</h2><br>" +
            	        "<p>A malware analyst is sometimes called a reverse-engineer as their core task revolves around converting compiled programs from machine language to readable code, usually in a low-level language. This work requires the malware analyst to have a strong programming background, especially in low-level languages such as assembly language and C language. The ultimate goal is to learn about all the activities that a malicious program carries out, find out how to detect it and report it.</p><br>" +
            	        "<h2>Responsibilities</h2><br>" +
            	        "<ul>" +
            	        "<li>Carry out static analysis of malicious programs, which entails reverse-engineering</li>" +
            	        "<li>Conduct dynamic analysis of malware samples by observing their activities in a controlled environment</li>" +
            	        "<li>Document and report all the findings</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    },
            	    {
            	        1,
            	        3,
            	        "<p>Penetration Tester</p>",
            	        "<h2>You may see penetration testing referred to as pentesting and ethical hacking.</h2><br>" +
            	        "<p>A penetration tester's job role is to test the security of the systems and software within a company - this is achieved through attempts to uncover flaws and vulnerabilities through systemised hacking. Penetration testers exploit these vulnerabilities to evaluate the risk in each instance. The company can then take these insights to rectify issues to prevent a real-world cyberattack.</p><br>" +
            	        "<h2>Responsibilities</h2><br>" +
            	        "<ul>" +
            	        "<li>Conduct tests on computer systems, networks, and web-based applications</li>" +
            	        "<li>Perform security assessments, audits, and analyse policies</li>" +
            	        "<li>Evaluate and report on insights, recommending actions for attack prevention</li>" +
            	        "</ul><br>" +
            	        "<h2>Learning Paths</h2><br>" +
            	        "<ul>" +
            	        "<li>TryHackMe's learning paths will give you both the fundamental technical knowledge and hands-on experience, which is crucial to becoming a successful Penetration Tester.</li>" +
            	        "<li>JR Penetration Tester</li>" +
            	        "<li>Offensive Pentesting</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    }
            	    ,
            	    {
            	        1,
            	        3,
            	        "<p>Red Teamer</p>",
            	        "<h2>Red teamers share similarities to penetration testers, with a more targeted job role.</h2><br>" +
            	        "<p>Penetration testers look to uncover many vulnerabilities across systems to keep cyber-defence in good standing, whilst red teamers are enacted to test the company's detection and response capabilities. This job role requires imitating cyber criminals' actions, emulating malicious attacks, retaining access, and avoiding detection. Red team assessments can run for up to a month, typically by a team external to the company. They are often best suited to organisations with mature security programs in place.</p><br>" +
            	        "<h2>Responsibilities</h2><br>" +
            	        "<ul>" +
            	        "<li>Emulate the role of a threat actor to uncover exploitable vulnerabilities, maintain access and avoid detection</li>" +
            	        "<li>Assess organisations' security controls, threat intelligence, and incident response procedures</li>" +
            	        "<li>Evaluate and report on insights, with actionable data for companies to avoid real-world instances</li>" +
            	        "</ul><br>" +
            	        "<h2>Learning Paths</h2><br>" +
            	        "<ul>" +
            	        "<li>TryHackMe's learning paths will give you both the fundamental technical knowledge and hands-on experience, which is crucial to becoming a successful Red Teamer.</li>" +
            	        "<li>JR Penetration Tester</li>" +
            	        "<li>Offensive Pentesting</li>" +
            	        "<li>Red Teamer</li>" +
            	        "</ul>",
            	        "<p>No Question just press submit</p>",
            	        ""
            	    }
            	};


            // Loop through tasks data and add them to the batch
            for (Object[] task : tasks) {
                pstmt.setInt(1, (Integer) task[0]);  // moduleId
                pstmt.setInt(2, (Integer) task[1]);  // sectionId
                pstmt.setString(3, (String) task[2]);  // taskName
                pstmt.setString(4, (String) task[3]);  // taskDescription
                pstmt.setString(5, (String) task[4]);  // taskQuestion
                pstmt.setString(6, (String) task[5]);  // correctAnswer
                pstmt.addBatch();
            }

            // Execute batch insert
            pstmt.executeBatch();
            System.out.println("New records created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting tasks: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
