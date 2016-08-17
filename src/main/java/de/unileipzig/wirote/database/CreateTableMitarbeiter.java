
package de.unileipzig.wirote.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ralmoued
 */
public class CreateTableMitarbeiter {
 public static void main(String args[]) {
   Connection con;
   String createTable = "CREATE TABLE  mitarbeiter" 
          + "(Id INT (8) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
           + "Vorname VARCHAR(24) NOT NULL,"
           + "Nachname VARCHAR (50) NOT NULL,"
           + "Titel VARCHAR (16),"
           + "Email VARCHAR (60),"
           + "Telephon VARCHAR (32),"
           + "BeaschaeftigungsDauer VARCHAR(50),"
           + "KurzLebenslauf VARCHAR(5000))";
    Statement stmt;
    try {
          con = MysqlConnection.connection();
          stmt = con.createStatement();
          stmt.executeUpdate(createTable);
          stmt.close();
          con.close();
    } catch(SQLException ex) {
          System.err.println("SQLException: " + ex.getMessage());
    }
 }
}
