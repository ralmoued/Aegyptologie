
package de.unileipzig.wirote.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ralmoued
 */
public class CreateTableAdministrator {

 public static void main(String args[]) {

   Connection con;
   String createTable = "CREATE TABLE  administrator " 
          + "(Benutzername VARCHAR(32) PRIMARY KEY NOT NULL,"
           + "Passwort VARCHAR (32) NOT NULL)";
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
