
package de.unileipzig.wirote.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ralmoued
 */
public class CreateDatabase {

    /**
     * Erzeug die Datenbank
     *
     * @param args
     */
    public static void main(String args[]) {

        Connection con;
        String createTable = "CREATE DATABASE IF NOT EXISTS aegyptologie ";
        Statement stmt;
        try {
            con = MysqlConnection.connection();
            stmt = con.createStatement();
            stmt.executeUpdate(createTable);
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: "
                    + ex.getMessage());
        }
    }
}
