<%-- 
    Document   : success
    Created on : 03 10, 20, 10:18:41 PM
    Author     : PC
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Records</title>
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
            
            #result td, #result th {
                border: 1px solid #ddd;
                padding: 8px;
            }
            
            #result {
                width: 100%;
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
            <h1 style="color: #A361F7; text-align: center">List of Users</h1>
            <%
                response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
                
                if(session.getAttribute("user")== null){
                    response.sendRedirect("index.jsp");
                }
                
                ResultSet results = (ResultSet) request.getAttribute("results");
                Integer columns = (Integer) request.getAttribute("columns");
            %>

            <table align="center" id="result">
                <tr>
                    <% for(int i = 1; i <= columns; i++){ %>
                    <th><%=results.getMetaData().getColumnName(i)%></th>
                <% } %> 
                </tr>               
                <%  
                    while(results.next()) {
                %>              
                    <tr class="rows">
                <%      for(int i = 1; i <= columns; i++){  %>
                        <td><%=results.getString(results.getMetaData().getColumnName(i)) %></td>
                    <%  } %>
                    </tr>   
             <% } %>
             </table>
             
             <br>
         
            <form method="POST" action="Records">
                <input type="submit" name="pdf" value="GENERATE PDF REPORT" class="back">
                <input type="submit" name="logOut" value="LOGOUT" class="back">                
            </form>
            <form action="success.jsp">
                <input type="submit" value="BACK" class="back">
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
