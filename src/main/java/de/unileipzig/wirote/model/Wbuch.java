package de.unileipzig.wirote.model;

import de.unileipzig.wirote.database.MysqlConnection;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ralmoued
 */
/**
 * Diese Klasse verwaltet den Wörterbüchern und bietet Methoden, um Bücher
 * hinzufügen, ändern und löschen.
 *
 * @author ralmoued
 */
@ManagedBean(name = "wbuch")
@SessionScoped
public class Wbuch implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Wbuch> wortList;
    private List<Wbuch> wbList;

    private int id;
    private String datei;
    private int seiten;
    private int wbuch;
    private String kurztitel;
    private int baende;
    private String autor;
    private String titel;
    private String erscheinungsort;
    private String erscheinungsjahr;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public Wbuch() {

    }

    /**
     * Setter und Getter für id, titel und text
     *
     * @return
     */
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDatei() {
        return datei;
    }

    public void setDatei(String datei) {
        this.datei = datei;
    }

    public int getSeiten() {
        return seiten;
    }

    public void setSeiten(int Seiten) {
        this.seiten = Seiten;
    }

    public int getWbuch() {
        return wbuch;
    }

    public void setWbuch(int wbuch) {
        this.wbuch = wbuch;
    }

    public String getKurztitel() {
        return kurztitel;
    }

    public void setKurztitel(String kurztitel) {
        this.kurztitel = kurztitel;
    }

    public int getBaende() {
        return baende;
    }

    public void setBaende(int baende) {
        this.baende = baende;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getErscheinungsort() {
        return erscheinungsort;
    }

    public void setErscheinungsort(String erscheinungsort) {
        this.erscheinungsort = erscheinungsort;
    }

    public String getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public void setErscheinungsjahr(String erscheinungsjahr) {
        this.erscheinungsjahr = erscheinungsjahr;
    }

    /**
     * Lädt die Titel und Text von Jedem Wörterbuch von DB
     *
     * @return
     */
    public List<Wbuch> getWbList() {
        wortList = new ArrayList<>();
        try (Statement stmt = MysqlConnection.connection().createStatement();) {
            String sql = "SELECT datei, wbuch,kurztitel, baende, autor,"
                    + " titel, erscheinungsort, erscheinungsjahr "
                    + "FROM Woerterbuchseite, Publikation "
                    + "WHERE Woerterbuchseite.wbuch=Publikation.wbuchid "
                    + "AND Woerterbuchseite.seite=1";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Wbuch wb;
                wb = new Wbuch();
                wb.setDatei(result.getString("datei"));
                wb.setWbuch(result.getInt("wbuch"));
                wb.setKurztitel(result.getString("kurztitel"));
                wb.setBaende(result.getInt("baende"));
                wb.setAutor(result.getString("autor"));
                wb.setTitel(result.getString("titel"));
                wb.setErscheinungsort(result.getString("erscheinungsort"));
                wb.setErscheinungsjahr(result.getString("erscheinungsjahr"));

                wortList.add(wb);
            }
        } catch (SQLException e) {
        }
        return wortList;

    }

    /**
     * Zeilen Zähler in der Datenbank (wie viel Wörterbücher)
     *
     * @return
     */
    public final int getWbCount() {
        int rowCount = 0;
        String sql = "select COUNT(*) from Woerterbuchseite, Publikation "
                + "where Woerterbuchseite.wbuch=Publikation.wbuchid  "
                + "AND Woerterbuchseite. seite = 1";

        try (Statement stmt = MysqlConnection.connection().createStatement();) {
            //Zeilenzähler
            ResultSet result = stmt.executeQuery(sql);
            result.next();
            //Berechnung Anzahl der Zeilen
            rowCount = result.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    /**
     * toString Methode
     *
     * @return
     */
    @Override
    public String toString() {
        return "Wbuch{" + "id=" + id + ", datei=" + datei + ", seite=" + seiten + ", wbuch=" + wbuch + ", kurztitel=" + kurztitel + ", baende=" + baende + ", autor=" + autor + ", titel=" + titel + ", erscheinungsort=" + erscheinungsort + ", erscheinungsjahr=" + erscheinungsjahr + '}';
    }

    /**
     * hashCode Methode
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.datei);
        hash = 71 * hash + this.seiten;
        hash = 71 * hash + this.wbuch;
        hash = 71 * hash + Objects.hashCode(this.kurztitel);
        hash = 71 * hash + this.baende;
        hash = 71 * hash + Objects.hashCode(this.autor);
        hash = 71 * hash + Objects.hashCode(this.titel);
        hash = 71 * hash + Objects.hashCode(this.erscheinungsort);
        hash = 71 * hash + Objects.hashCode(this.erscheinungsjahr);
        return hash;
    }

    /**
     * equals Methode
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wbuch other = (Wbuch) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.datei, other.datei)) {
            return false;
        }
        if (this.seiten != other.seiten) {
            return false;
        }
        if (this.wbuch != other.wbuch) {
            return false;
        }
        if (!Objects.equals(this.kurztitel, other.kurztitel)) {
            return false;
        }
        if (this.baende != other.baende) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.titel, other.titel)) {
            return false;
        }
        if (!Objects.equals(this.erscheinungsort, other.erscheinungsort)) {
            return false;
        }
        return Objects.equals(this.erscheinungsjahr, other.erscheinungsjahr);
    }

}
