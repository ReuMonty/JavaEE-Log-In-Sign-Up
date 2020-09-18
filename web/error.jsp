<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Displays an Error Message">
        <title>An Error Occurred</title>
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
                margin: 8px 45%;
                border: none;
                cursor: pointer;
                width: 10%;
                font-family:"Titillium";
                font-size: 15px;
                position: absolute;
                bottom: 40%;
            }

            .content{
                background-color: white;
                padding: 1px 1% 1px 1%;
                /*                margin: 30px 40% 0;*/
                margin-left: 42%;
                margin-right: 42%;
                text-align: center;
                font-size: 20px;
                position: absolute;
                top: 20%;
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
            <h1 style="color: #A361F7; text-align: center">Incorrect Credentials</h1>
        </div>
        <br>        
        <br>
        <form action="index.jsp">
            <input type="submit" value="BACK" class="back">
        </form>

        <footer>
            <%=getServletContext().getInitParameter("footer")%> <br>
            <%
                Date date = (Date) getServletContext().getAttribute("date");
                out.print("Time and Date accessed: " + date);
            %>    
        </footer>
    </body>
</html>
