package de.unileipzig.wirote.model;

import de.unileipzig.wirote.database.MysqlConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * Diese Klasse verwaltet den Mitarbeiter und bietet Methoden, um Mitarbeiter
 * hinzufügen, ändern und löschen.
 *
 * @author ralmoued
 */
@ManagedBean(name = "mitarbeiter")
@SessionScoped
public class Mitarbeiter implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient Connection con;
    private List<Mitarbeiter> mitarbeiterList;

    private int id;
    private String vorname;
    private String nachname;
    private String titel;
    private String email;
    private String telephon;
    private String beaschaeftigungsDauer;
    private String kurzLebenslauf;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public Mitarbeiter() {
        con = MysqlConnection.connection();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    public String getBeaschaeftigungsDauer() {
        return beaschaeftigungsDauer;
    }

    public void setBeaschaeftigungsDauer(String beaschaeftigungsDauer) {
        this.beaschaeftigungsDauer = beaschaeftigungsDauer;
    }

    public String getKurzLebenslauf() {
        return kurzLebenslauf;
    }

    public void setKurzLebenslauf(String kurzLebenslauf) {
        this.kurzLebenslauf = kurzLebenslauf;
    }

    /**
     * define the id of the Mitarbeiter to be updated set the aktulle
     * Mitarbeiter datei in den Felder (aus Datenbank)
     *
     * @param id
     * @return
     */
    public String setAktualisierung(int id) {
        this.id = id;

        try (Statement stmt = con.createStatement();) {
            String sql = "SELECT Vorname, Nachname, Titel, Email, Telephon,"
                    + "BeaschaeftigungsDauer, KurzLebenslauf FROM mitarbeiter "
                    + "WHERE Id=" + id;
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                vorname = result.getString("Vorname");
                nachname = result.getString("Nachname");
                titel = result.getString("Titel");
                email = result.getString("Email");
                telephon = result.getString("Telephon");
                beaschaeftigungsDauer = result.getString("BeaschaeftigungsDauer");
                kurzLebenslauf = result.getString("KurzLebenslauf");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "updateMitrbeiter?faces-redirect=true";
    }

    /**
     * define the id of the Mitarbeiter to be updated
     *
     * @param id
     * @param vorname
     * @param nachname
     * @return
     */
    public String setMitarbeiterDetails(int id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        return "mitarbeiterDetails?faces-redirect=true";
    }

    /**
     * define the id of the Mitarbeiter to be updated view parameters in URL
     *
     * @param id
     * @param vorname
     * @param nachname
     * @return
     */
    public String setEinzelheit(int id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        return "einzelMitarbeiter?faces-redirect=true&includeViewParams=true";
    }

    /**
     * Neuer Mitarbeiter hinzufugen
     *
     * @return
     */
    public String neuerMitabeiter() {
        reset();
        return "neuerMitarbeiter?faces-redirect=true&includeViewParams=true";
    }

    /**
     * LÃ¤dt die Mitarbeiter Attributen aus DB
     *
     * @return
     */
    public List<Mitarbeiter> getMitarbList() {
        mitarbeiterList = new ArrayList<>();

        try (Statement stmt = con.createStatement();) {
            String sql = "SELECT Id, Vorname, Nachname, Titel, Email, Telephon,"
                    + "BeaschaeftigungsDauer, KurzLebenslauf FROM mitarbeiter";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Mitarbeiter mit = new Mitarbeiter();
                mit.setId(result.getInt("Id"));
                mit.setVorname(result.getString("Vorname"));
                mit.setNachname(result.getString("Nachname"));
                mit.setTitel(result.getString("Titel"));
                mit.setEmail(result.getString("Email"));
                mit.setTelephon(result.getString("Telephon"));
                mit.setBeaschaeftigungsDauer(result.getString("BeaschaeftigungsDauer"));
                mit.setKurzLebenslauf(result.getString("KurzLebenslauf"));

                mitarbeiterList.add(mit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mitarbeiterList;
    }

    /**
     * EinfÃ¼gen die Mitarbeiter Attribute in DB
     *
     */
    public void addAction() {
        try (Statement stmt = con.createStatement();) {
            String sql = "INSERT INTO mitarbeiter(Vorname, Nachname, Titel,"
                    + " Email, Telephon, BeaschaeftigungsDauer, KurzLebenslauf) "
                    + "VALUES('" + vorname + "','" + nachname + "','" + titel + "','"
                    + email + "','" + telephon + "','" + beaschaeftigungsDauer
                    + "','" + kurzLebenslauf + "')";

            stmt.executeUpdate(sql);

            System.out.println("Data Added Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * LÃ¶schen Attribute von DB
     *
     * @param id
     */
    public void deleteAction(int id) {
        try (Statement stmt = con.createStatement();) {
            String sql = "DELETE FROM mitarbeiter WHERE Id=" + id;
            stmt.executeUpdate(sql);

            System.out.println(sql);
            System.out.println("Data deleted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aktualisierung Text und Titel in Datenbank
     *
     */
    public void update() {

        try (Statement stmt = con.createStatement();) {
            String sql = "UPDATE mitarbeiter SET Vorname='" + vorname + "',"
                    + "Nachname='" + nachname + "',"
                    + "Titel='" + titel + "',"
                    + "Email='" + email + "',"
                    + "Telephon='" + telephon + "',"
                    + "BeaschaeftigungsDauer='" + beaschaeftigungsDauer + "',"
                    + "KurzLebenslauf='" + kurzLebenslauf + "'"
                    + "WHERE Id=" + id;
            stmt.executeUpdate(sql);

            System.out.println("Data updated Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * LÃ¤dt die Attributen von konkreten Mitarbeiter aus DB
     *
     * @return
     */
    public List<Mitarbeiter> getMitarbeiterDetails() {
        mitarbeiterList = new ArrayList<>();

        try (Statement stmt = con.createStatement();) {
            String sql = "SELECT Id, Vorname, Nachname, Titel, Email, Telephon,"
                    + "BeaschaeftigungsDauer, KurzLebenslauf FROM mitarbeiter "
                    + "WHERE Vorname='" + vorname + "' "
                    + "AND Nachname='" + nachname + "'";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Mitarbeiter mit = new Mitarbeiter();
                mit.setId(result.getInt("Id"));
                mit.setVorname(result.getString("Vorname"));
                mit.setNachname(result.getString("Nachname"));
                mit.setTitel(result.getString("Titel"));
                mit.setEmail(result.getString("Email"));
                mit.setTelephon(result.getString("Telephon"));
                mit.setBeaschaeftigungsDauer(result.getString("BeaschaeftigungsDauer"));
                mit.setKurzLebenslauf(result.getString("KurzLebenslauf"));

                mitarbeiterList.add(mit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mitarbeiterList;
    }

    /**
     * Make each of firstName and lastName connected by "_" if they consist more
     * than one token. This is just for naming for the photos of each employee.
     * to retrieve the correct photo for each employee profile.
     *
     * @param name
     * @return name{0}_name{1}_name{2}....name{n}
     */
    public String finalName(String name) {
        String name2 = "";
        StringTokenizer breaker = new StringTokenizer(name);
        System.out.println(breaker.countTokens());
        while (breaker.hasMoreTokens()) {
            name2 += breaker.nextToken() + "_";
        }

        System.out.println(name2);
        StringBuilder buf = new StringBuilder(name2);
        buf.deleteCharAt(name2.length() - 1);
        System.out.println(buf.toString());
        return buf.toString();
    }

    /**
     * Rest den text und Titel in Rich Text
     */
    public void reset() {
        this.vorname = null;
        this.nachname = null;
        this.titel = null;
        this.email = null;
        this.telephon = null;
        this.beaschaeftigungsDauer = null;
        this.kurzLebenslauf = null;
    }

    //Clear the form even in case error in validation
    // recommended to be used. Better than reset()
    // used in neuerMitarbeiter.xhtml in clear button
    public void clearForm(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
                .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); //Optional

        this.vorname = null;
        this.nachname = null;
        this.titel = null;
        this.email = null;
        this.telephon = null;
        this.beaschaeftigungsDauer = null;
        this.kurzLebenslauf = null;
    }

    /**
     * //display msg telling that new mitarbeiter added successfully
     */
    public void addMsg() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Der Mitarbeiter ist erfolgreich hinzugefügt");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Zeig Nachricht, dass der Mitarbeiter gelÃ¶scht ist
     *
     */
    public void delMsg() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Der Mitarbeiter ist erfolgreich gelöscht");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Zeig Nachricht, dass die Datei des Mitarbeiter ist erfolgreich
     * Aktualisiert
     */
    public void updateMsg() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Die Datei des Mitarbeiter ist erfolgreich Aktualisiert");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    /**
//     * tostring
//     *
//     * @return
//     */
//    @Override
//    public String toString() {
//        return "Mitarbeiter{" + "id=" + id + ", vorname=" + vorname
//                + ", nachname=" + nachname + ", titel=" + titel + ", email=" + email
//                + ", telephon=" + telephon + ", beaschaeftigungsDauer=" + beaschaeftigungsDauer
//                + ", kurzLebenslauf=" + kurzLebenslauf + '}';
//    }
}
