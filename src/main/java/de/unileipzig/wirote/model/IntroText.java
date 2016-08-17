package de.unileipzig.wirote.model;

import de.unileipzig.wirote.database.MysqlConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Diese Klasse verwaltet den Einleitungstext und bietet Methoden, um diesen zu
 * Ã¤ndern.
 *
 * @author ralmoued
 */
@ManagedBean(name = "intro")
@SessionScoped
public class IntroText implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient Connection con;
    private String text;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public IntroText() {
        con = MysqlConnection.connection();
    }

    /**
     * Setter fuer text
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Getter fuer text
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Einfügen Einleitungstext in DB
     *
     * @param code
     */
    public void insertEinleitung(String code) {
        if (text != null && !text.isEmpty()) {

            {
                try (Statement stmt = con.createStatement();) {
                    //Zeilenzähler
                    ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM introduction");
                    result.next();
                    //Berechnung Anzahl der Zeilen
                    int rowCount = result.getInt(1);
                    //falls die Datenbank leer ist dann(INSERT), ansonst (UPDATE)
                    String sql;
                    if (rowCount > 0 && language().contains(code)) {
                        sql = "UPDATE introduction SET Words='" + text + "' "
                                + "WHERE Language ='" + code + "'";
                    } else {
                        sql = "INSERT INTO introduction"
                                + " (Words, Language) VALUES('" + text + "',"
                                + "'" + code + "')";
                    }
                    stmt.executeUpdate(sql);
                    System.out.println("Data updated Successfully");
                } catch (SQLException e) {
                    System.out.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    /**
     * Lädt den Einleitungstext aus DB
     *
     * @param code
     * @return
     */
    public String introductionFromDatabase(String code) {

        try (Statement stmt = con.createStatement();) {

            String sql = "SELECT Words FROM introduction Where Language='" + code + "'";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                text = result.getString("Words");
            }
        } catch (SQLException e) {
            System.out.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }
        return text;
    }

    /**
     * Zeig Nachricht, dass Update der Editierungstext erfolgreich ist
     *
     * @param code
     */
    public void addMsg(String code) {
        String message;
        if (code.equals("de")) {
            message = "Die Deutsch Einleitung ist Aktualisiert";
        } else {
            message = "Die English Einleitung ist Aktualisiert";
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Reseted den Editierung text
     */
    public void reset() {
        this.text = null;
        System.out.println("reset is called");
    }

    /**
     * Define in which language the introduction text stroed in the data base If
     * code= en_us then we have English text or German text if code=de or both
     * texts if code= de en_us
     *
     * @return the code of the language which stored in the database ('de' and
     * 'en_us')
     */
    public String language() {
        String code = "";

        try (Statement stmt = con.createStatement();) {

            String sql = "SELECT Language FROM introduction";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                code += result.getString("Language") + " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(code);
        return code;
    }
}
