
package de.unileipzig.wirote.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ralmoued
 */
public class CreateTablebenutzerhandbuch {
    
 public static void main(String args[]) {
   Connection con;
   String createTable = "CREATE TABLE  benutzerhandbuch " 
          + "(Dateiname VARCHAR(255) NOT NULL,"
           + "Pfad VARCHAR (255) NOT NULL,"
           + "Language VARCHAR(16) NOT NULL)";
    Statement stmt;
    try {
          con = MysqlConnection.connection();
          stmt = con.createStatement();
          stmt.executeUpdate(createTable);
          stmt.close();
          con.close();
    } catch(SQLException ex) {
          System.err.println("SQLException: " + 
ex.getMessage());
    }
 }
}
