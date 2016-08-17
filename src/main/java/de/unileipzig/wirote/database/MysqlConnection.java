package de.unileipzig.wirote.database;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Arrays;

/**
 *
 * @author ralmoued
 * 
 * Eine singleton Datenbank Zugriffsklasse für MySQL
 */
public final class MysqlConnection {

    private static Connection con;
    
    public static Connection connection(){
        String url = "jdbc:mysql://localhost:3306/";
        String DB = "aegyptologie";
        String username = "root";
        String password = "root";

        try {
            //Verbindung mit der Datenbank 
            Class.forName("com.mysql.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.err.print("ClassNotFoundException: ");
            System.err.println(ex.getMessage());
        }
        try {
            con = (Connection) DriverManager.getConnection(url+DB, username, password);
            return con;
        } catch (SQLException ex) {
          System.out.println("failure");
      	  System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        
        return con;
    }

}
