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
 * Diese Klasse verwaltet den impressum und bietet Methoden, zu hinzufÃ¼gen,
 * Ã¤ndern und lÃ¶schen.
 *
 * @author ralmoued
 */
@ManagedBean(name = "imp")
@SessionScoped
public class Impressum implements Serializable {

    //TODO: Englischen Ursprungstext anzeigen
    private static final long serialVersionUID = 1L;

    private final transient Connection con;
    private String impressum;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public Impressum() {
        con = MysqlConnection.connection();
    }

    /**
     * Setter fÃ¼r impressum
     *
     * @return impressum
     */
    public String getImpressum() {
        return impressum;
    }

    /**
     * Getter fÃ¼r impressum
     *
     * @param impressum
     */
    public void setImpressum(String impressum) {
        this.impressum = impressum;
    }

    /**
     * Einfügen impressum in DB
     *
     * @param code
     */
    public void insertImpressum(String code) {
        if (impressum != null && !impressum.isEmpty()) {

            {
                try (Statement stmt = con.createStatement();) {
                    //Zeilenzähler
                    ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM impressum");
                    result.next();
                    //Berechnung Anzahl der Zeilen
                    int rowCount = result.getInt(1);
                    //falls die Datenbank leer ist dann(INSERT), ansonst (UPDATE)
                    String sql;
                    if (rowCount > 0 && language().contains(code)) {
                        sql = "UPDATE impressum SET Impressum='" + impressum + "' "
                                + "WHERE Language ='" + code + "'";
                    } else {
                        sql = "INSERT INTO impressum"
                                + " (Impressum, Language) VALUES('" + impressum + "',"
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
     * Lädt die impressum Text aus DB
     *
     * @param code
     * @return
     */
    public String impressumFromDatabase(String code) {
        try (Statement stmt = con.createStatement()) {
            String sql = "SELECT Impressum FROM impressum Where Language='" + code + "'";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                impressum = result.getString("Impressum");
            }
        } catch (SQLException e) {
            System.out.println("Sql state" + e.getSQLState());
            System.out.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
            e.getStackTrace();
            System.out.println("Got an SQLException: " + e.getMessage());
        } catch (NullPointerException e) {
            e.getStackTrace();
            System.out.println("NPE Stack Trace: " + Arrays.toString(e.getStackTrace()));
            System.out.println("Got an NullPointerException: " + e.getMessage());
        }
        return impressum;
    }

    /**
     * Zeig Nachricht, dass Update der Impressum erfolgreich ist
     *
     * @param code
     */
    public void addMsg(String code) {
        String message;
        if (code.equals("de")){
            message = "Die Deutsch Impressum ist Aktualisiert";
        }
        else{
            message = "Die English Impressum ist Aktualisiert";
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Reseted den Impressum text
     */
    public void reset() {
        this.impressum = null;
    }

    /**
     * Define in which language the About text stroed in the data base If code=
     * en_us then we have English text or German text if code=de or both texts
     * if code= de en_us
     *
     * @return the code of the language which stored in the database ('de' and
     * 'en_us')
     */
    public String language() {
        String code = "";

        try (Statement stmt = con.createStatement();) {

            String sql = "SELECT Language FROM impressum";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                code += result.getString("Language") + " ";
            }
        } catch (SQLException e) {
            System.out.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }

        System.out.println(code);
        return code;
    }
}
