<%-- 
    Document   : success
    Created on : 03 10, 20, 10:18:41 PM
    Author     : PC
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Account</title>
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
                text-align: center;
                font-size: 20px;
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
            <%= 
                getServletContext().getInitParameter("header")
            %>
        </header>
        
        <div class="content"> 
            <h1 style="color: #A361F7; text-align: center">Welcome User!</h1>
            <%
                String username = (String) session.getAttribute("user");
                String role = (String) session.getAttribute("role");
                
                response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
                
                if(session.getAttribute("user")== null){
                    response.sendRedirect("index.jsp");
                }
            %>
            <p>
                <b>Username</b><br>
                <%=username%>
            </p>
            <p>
                <b>Role</b><br> 
                <%=role%>
            </p>
            <br>
            <form method="POST" action="Records">
                <input type="submit" name="view" value="VIEW ALL RECORDS" class="back">
                <input type="submit" name="pdf" value="GENERATE PDF REPORT" class="back">
                <input type="submit" name="logOut" value="LOGOUT" class="back">
            </form>
        </div>
            
        <footer>
            <%= 
                getServletContext().getInitParameter("footer")
            %> <br>
            <%
                Date date = (Date)getServletContext().getAttribute("date");
                out.print("Time and Date accessed: " + date);
            %>    
        </footer>
    </body>
</html>
