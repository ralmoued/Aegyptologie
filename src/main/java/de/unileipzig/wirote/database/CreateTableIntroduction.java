
package de.unileipzig.wirote.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mirage
 */
public class CreateTableIntroduction {
public static void main(String args[]) {
   Connection con;
  String createTable = "CREATE TABLE  introduction "
                + "(Words VARCHAR(5000) NOT NULL,"
                + "Language  VARCHAR(16) NOT NULL)";
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
