/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.itextpdf.text.BaseColor;
import model.Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import java.io.FileOutputStream;
import java.util.Date;



/**
 *
 * @author PC
 */
public class RecordsServlet extends HttpServlet {

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
    String records;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Database db = (Database) getServletContext().getAttribute("db");
        con = db.getConn();
        records = config.getInitParameter("query");
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("user");
            String role = (String) session.getAttribute("role");
            StringBuffer fileName = new StringBuffer("inline;filename=").append(user).append("_").append(role).append(".pdf");
            
            response.setHeader("Content-Disposition",fileName.toString());
            response.setContentType("application/pdf");
            response.setContentType("application/OCTET-STREAM");
            
            //for printing the table of records
            try {
                PreparedStatement ps = con.prepareStatement(records);
                ps.setString(1, user);
                ResultSet table = ps.executeQuery();
                int colCount = table.getMetaData().getColumnCount();
                
                if(request.getParameter("view") != null){   //printing records in view.jsp
                    request.setAttribute("results",table);
                    request.setAttribute("columns",colCount);
                    request.getRequestDispatcher("view.jsp").forward(request, response);
                }
                
                if(request.getParameter("pdf") != null){    //printing pdf   
                    
                        Document doc = new Document();
                        Rectangle rec = new Rectangle(PageSize.LETTER);
                        doc.setPageSize(rec);      
                        
                    try {
                        PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
                        FooterPageEvent event = new FooterPageEvent();
                        writer.setPageEvent(event);
                        
                        doc.open();
                        
                        Paragraph header = new Paragraph(getServletContext().getInitParameter("header"));
                        header.setAlignment(Element.ALIGN_CENTER);
                        
                        Paragraph title = new Paragraph("List of Registered Users");
                        title.setAlignment(Element.ALIGN_CENTER);
                        
                        Paragraph space = new Paragraph(" ");
                        
                        //creates table
                        PdfPTable pTable = new PdfPTable(2);
                        for(int i = 1; i <= colCount; i++){ 
                            pTable.addCell(table.getMetaData().getColumnName(i));
                        }
                        while(table.next()){
                            for(int i = 1; i <= colCount; i++){  
                                pTable.addCell(table.getString(table.getMetaData().getColumnName(i)));
                            }
                        }          
                        
                        Paragraph pUser = new Paragraph("Report Generated by: " + user);
                        
                        doc.add(header);
                        doc.add(space);
                        doc.add(title);
                        doc.add(space);
                        doc.add(pTable);
                        doc.add(space);
                        doc.add(pUser);                        
                        doc.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                                       
                } 
                
            } catch (SQLException ex) {
                Logger.getLogger(RecordsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //for logging out
            if(request.getParameter("logOut") != null){
//                HttpSession session = request.getSession();
                session.removeAttribute("user");
                session.removeAttribute("role");
                session.invalidate();
                
                response.sendRedirect("index.jsp");
            } 
    }
    
    class FooterPageEvent extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addFooter(writer);
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{24, 2, 1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            footer.addCell(new Phrase("Date and Time: " + new Date() + "\n" + getServletContext().getInitParameter("footer"), new Font(Font.FontFamily.HELVETICA, 10) ));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
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
