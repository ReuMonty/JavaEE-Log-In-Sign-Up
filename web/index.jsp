<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Provides text fields for Log In credentials and an option to Sign Up">
        <title>Log In or Sign Up</title>
        <style>
            body{
                margin: 0%;
            }

            header{
                background-color: #6f88fc;
                color: #45e3ff;
                padding: 1%;
                margin: 0%;
                font-size: 30px;
                font-family: "Bebas Regular"
            }

            body{
                background-color: #FEFFF1;
                font-family:"Titillium";

            }

            input[type=text], input[type=password] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
                font-size: 16px;
            }

            input[type=submit]{
                background-color: #A162F7;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
                font-family:"Titillium";
                font-size: 15px;
            }

            .content{
                background-color: white;
                padding: 1px 1% 1% 1%;
                margin: 6% 40% 10%;
                font-size: 17px;
            }

            footer{
                position: fixed;
                bottom: 0;
                left: 0;
                width: 100%;
                background-color: #6f88fc;
                color: #45e3ff;
                padding: 2%;
                font-size: 15px;
            }
        </style>
    </head>

    <body>
        <header>
            <!--gets content of context-param "header" and places it in the header (string)-->
            <%=getServletContext().getInitParameter("header")%>
        </header>

        <div class="content">
            <h1 style="color: #A361F7; text-align: center">Welcome! Please login</h1>
            <!--text inputted will be sent to LogInServlet-->
            <form method="POST" action="LogIn"> 
                <label><b>Username</b></label><br>
                <input type="text" name="uname" placeholder="Enter Username"><br><br>
                <label><b>Password</b></label><br>
                <input type="password" name="pword" placeholder="Enter Password"><br><br>
                <input type="submit" name="logIn" value="LOG IN">
                <input type="submit" name="signUp" value="SIGN UP">
            </form>
        </div>

        <footer>
            <!--gets content of context-param "footer" and places it in the footer (string)-->            
            <%=getServletContext().getInitParameter("footer")%> <br>
            <%
                Date date = (Date) getServletContext().getAttribute("date");
                out.print("Time and Date accessed: " + date);
            %>    
        </footer>      
    </body>


</html>
