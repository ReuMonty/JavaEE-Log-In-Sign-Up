<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Provides text fields for information needed to Sign Up">
        <title>Sign Up</title>
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

            input[type=text], input[type=password], .roles {
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
                margin: 2% 40% 5%;
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
            <%=getServletContext().getInitParameter("header")%>
        </header>

        <div class="content">
            <h1 style="color: #A361F7; text-align: center">Welcome! Please login</h1>
            <form method="POST" action="SignUp">
                <label><b>Username</b></label><br>
                <input type="text" name="uname" placeholder="Enter Username"><br><br>
                <label><b>Role</b></label><br>
                <select class="roles" name="role">
                    <option value="Guest">Guest</option>
                    <option value="Admin">Admin</option>
                </select><br><br>
                <label><b>Password</b></label><br>
                <input type="password" name="pword" placeholder="Enter Password"><br><br>
                <label><b>Confirm Password</b></label><br>
                <input type="password" name="confPword" placeholder="Confirm Password"><br><br>
                <input type="submit" name="signUp" value="SIGN UP">
            </form>
            <form action="index.jsp">
                <input type="submit" value="BACK" class="back">
            </form>
        </div>

        <footer>
            <%=getServletContext().getInitParameter("footer")%> <br>
            <%
                Date date = (Date) getServletContext().getAttribute("date");
                out.print("Time and Date accessed: " + date);
            %>    
        </footer>      
    </body>


</html>
