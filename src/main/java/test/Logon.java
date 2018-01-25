package test;


import de.unileipzig.wirote.database.MysqlConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ralmoued
 */
@ManagedBean (name = "logon")
@SessionScoped

public class Logon implements Serializable{
    
   private static final long serialVersionUID = 1L;

    private final transient Connection con;

    private String benutzername;
    private String passwort;
    private String message;
    private boolean loggedOn;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public Logon() {
        con = MysqlConnection.connection();
    }

    /**
     * Setter und Getter für Message (Status des Logon)
     *
     * @return message
     */
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * überprüfen der Benutzername und das Passwort, ob der Benutzername und das
     * Passwort korrekt sind.
     *
     * @param code which will be passed to errorMsg to difine the language of
     * the Error message.
     *
     * @return true falls die login erfolgreich ist.
     */
    public boolean checkLogin(String code) {
        loggedOn = false;
        try (Statement stmt = con.createStatement();) {
            String sql = "SELECT * FROM administrator "
                    + "Where Benutzername='" + benutzername + "' "
                    + "AND Passwort='" + passwort + "' ";
            ResultSet result = stmt.executeQuery(sql);

            // wenn dieser Benutzer bereits vorhanden
            if (result.next()) {
                System.out.println("Hallo " + benutzername);
                loggedOn = true;
            } else {
                System.out.println("failure");
                //setMessage("Benutzername oder Passwort ist nicht korrekt");
                errorMsg(code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedOn;
    }

    /**
     * falls der Benutzername und das Passwort korrekt sind.
     * @param code which will be passed to checkLogin method to define the language of
     * the Error message. code will be passed from jsf page.
     * @return Bearbeitungsseite
     */
    public String bearbeitungsseite(String code) {
        boolean check = checkLogin(code);
        while (check) {
            System.out.println(check);
            return "/Bearbeitung/editieren?faces-redirect=true";
        }
        return null;
    }

    /**
     * machen loggedIn = false ( nicht mehr logged in) und umleiteng zu index
     * Seite
     *
     * @return
     */
    public String logout() {
        loggedOn = false;
        return "/index?faces-redirect=true";
    }

    /**
     * Zeig Message falls das Anmelden Ausfällt
     *
     * @param code     */
    public void errorMsg(String code) {
        String errorMsg = "";
        if (null != code) switch (code) {
            case "de":
                errorMsg = "Benutzername oder Passwort ist nicht korrekt";
                break;
            case "ar":
                errorMsg = "\u0627\u0633\u0645 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645 \u0623\u0648 \u0643\u0644\u0645\u0629 \u0627\u0644\u0645\u0631\u0648\u0631 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d\u0629";
                break;
            default:
                errorMsg = "Username or Password is not correct";
                break;
        }
    
    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg);

    FacesContext.getCurrentInstance ().addMessage(null, msg);
    }

}
