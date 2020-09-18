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

/**
 *
 * @author PC
 */
public class SignUpServlet extends HttpServlet {

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
    String queryUname;
    String queryAdd;
    String signUpUname = null;
    String signUpPword = null;
    String confirmPword = null;
    String role = null;
            
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        Database db = (Database) getServletContext().getAttribute("db");
        con = db.getConn();
        queryUname = config.getInitParameter("queryCheckUname");
        queryAdd = config.getInitParameter("queryAddUser");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            signUpUname = request.getParameter("uname");
            
            try {
                PreparedStatement ps = con.prepareStatement(queryUname); //SELECT * FROM UserDB WHERE USERNAME = ?
                ps.setString(1, signUpUname);    //uses entered string as parameter
                ResultSet userInfo = ps.executeQuery();     // stores resulting table
                
                if(userInfo.next()){ //if a table is returned (when username is in DB)
                    RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                    rd.include(request, response);
                    out.print("<center><p style=\"color: #d63447; "
                                                + "padding: 1px 3% 1% 3%; "
                                                + "margin: 0 40% 0;"
                                                + "position: absolute;" 
                                                + "top: 40%;"
                                                + "background-color: white\">");
                    out.print("The username you have entered is already in use. Try another.");                   
                    out.print("</p></center>");
                } else {    //no table is returned (username entered is not in DB)
                    signUpPword = request.getParameter("pword");
                    confirmPword = request.getParameter("confPword");
                    
                    if(signUpPword.equals(confirmPword)){   //password is same with confirm password
                        role = request.getParameter("role");
                        
                        String encryptedPword = Security.encrypt(signUpPword);
                        
                        PreparedStatement ps2 = con.prepareStatement(queryAdd);
                        ps2.setString(1, signUpUname);
                        ps2.setString(2, encryptedPword);
                        ps2.setString(3, role);
                        int up = ps2.executeUpdate(); 
                        
                        RequestDispatcher rd = request.getRequestDispatcher("signUp.jsp");
                        rd.include(request, response);
                        out.print("<center><p style=\"color: #d63447; "
                                                    + "padding: 1% 7% 1% 7%; "
                                                    + "margin: 0 40% 0;"
                                                    + "border-style: groove;"
                                                    + "position: absolute;" 
                                                    + "top: 35%;"
                                                    + "background-color: white\">");
                        out.print("<b>Sign Up successful!</b>");                   
                        out.print("</p></center>");
                        
                    } else {    //password & confirm password are not the same
                        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                        rd.include(request, response);
                        out.print("<center><p style=\"color: #d63447; "
                                                    + "padding: 1% 5% 1% 5%; "
                                                    + "margin: 0 40% 0;"
                                                    + "position: absolute;" 
                                                    + "top: 40%;"
                                                    + "background-color: white\">");
                        out.print("Those passwords do not match. Try again.");                   
                        out.print("</p></center>");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
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
