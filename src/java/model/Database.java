/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class Database {
    private Connection conn;
    
    public Connection getConn(){
        return conn;
    }
    
    public Database(String driver, String url, String usernameDb, String passwordDb){
        try {
            Class.forName(driver);
            
            this.conn = DriverManager.getConnection(url, usernameDb, passwordDb);
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}
