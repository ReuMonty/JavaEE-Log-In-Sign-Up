/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.Security;
import model.Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class LogInServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    Connection con;
    String query;
    String logInUname = null;
    String logInPword = null;
    String storedUname;
    String storedPword;
    String storedRole;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        Database db = (Database) getServletContext().getAttribute("db");
        con = db.getConn();
        query = config.getInitParameter("query");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            logInUname = request.getParameter("uname");
            logInPword = request.getParameter("pword");
            
            if(request.getParameter("logIn") != null){
                
                if(logInUname.equals("") && logInPword.equals("")){ //if both entry fields are blank
                    //blank credentials                
                    RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                    rd.include(request, response);                
                    out.print("<center><p style=\"color: #d63447; "
                                                + "padding: 1% 5% 1% 5%; "
                                                + "margin-left: 41%;"
                                                + "margin-right: 41%;"
                                                + "position: absolute;" 
                                                + "top: 40%;"
                                                + "background-color: white\">");
                    out.print("No credentials were entered.");                  
                    out.print("</p></center>");
                } else {  // when something is entered in either entry field          
                    try {
                        PreparedStatement ps = con.prepareStatement(query); //SELECT * FROM UserDB WHERE USERNAME = ?
                        ps.setString(1, logInUname);    //uses entered string as parameter
                        ResultSet userInfo = ps.executeQuery();     // stores resulting table

                        if(userInfo.next()){    //if a table is returned (when username is in DB)
                            //correct username
                            do{     //stores column info in separate strings
                                storedUname = userInfo.getString("username");
                                storedPword = userInfo.getString("password");
                                storedRole = userInfo.getString("roles");
                            } while(userInfo.next());
                                
                                String decryptedPword = Security.decrypt(storedPword);
                            
                            if(logInPword.equals(decryptedPword)){ //when password is correct 
                                HttpSession session = request.getSession();
                                session.setAttribute("user",storedUname);
                                session.setAttribute("role",storedRole);
                                
//                                request.setAttribute("username", storedUname);
//                                request.setAttribute("role", storedRole);
                                request.getRequestDispatcher("success.jsp").forward(request, response);
                            } 

                            else if(!logInPword.equals(storedPword)){
                                //incorrect password                     
                                RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                                rd.include(request, response);
                                out.print("<center><p style=\"color: #d63447; "
                                                            + "padding: 1% 3% 1% 3%; "
                                                            + "margin: 0 40% 0;"
                                                            + "position: absolute;" 
                                                            + "top: 40%;"
                                                            + "background-color: white\">");
                                out.print("The password you have entered is incorrect.");                   
                                out.print("</p></center>");
                            }
                        } else {    //no table is returned (username entered is not in DB)
                            //incorrect username
                            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                            rd.include(request, response);
                            out.print("<center><p style=\"color: #d63447; "
                                                        + "padding: 1% 3% 1% 3%; "
                                                        + "margin: 0 40% 0;"
                                                        + "position: absolute;" 
                                                        + "top: 40%;"
                                                        + "background-color: white\">");
                            out.print("The username you entered does not match any account.");                  
                            out.print("</p></center>");
                        } 
                    } catch (SQLException ex) {
                        Logger.getLogger(LogInServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }                
                }
            }
            
            if(request.getParameter("signUp") != null){
                response.sendRedirect("signUp.jsp");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
