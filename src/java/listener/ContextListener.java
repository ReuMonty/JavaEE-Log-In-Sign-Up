/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import model.Database;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author PC
 */
public class ContextListener implements ServletContextListener{                 //creates a ContextListener object
    public void contextInitialized(ServletContextEvent event){
        ServletContext sc = event.getServletContext();                          
        Date d = new Date();
        sc.setAttribute("date", d);
        
        String driver = sc.getInitParameter("driverClass");
        String dbUsername = sc.getInitParameter("dbUsername");
        String dbPassword = sc.getInitParameter("dbPassword");
        StringBuffer url = new StringBuffer(sc.getInitParameter("driverURL"))
                .append("://")
                .append(sc.getInitParameter("hostName"))
                .append(":")
                .append(sc.getInitParameter("port"))
                .append("/")
                .append(sc.getInitParameter("database"));
        Database db = new Database(driver,url.toString(),dbUsername,dbPassword);
        sc.setAttribute("db",db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
