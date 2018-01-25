package de.unileipzig.wirote.control;

import de.unileipzig.wirote.database.MysqlConnection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Diese Klasse zeigt das Handbenutzerbuch in Webseite an.
 *
 * @author ralmoued
 */
@ManagedBean(name = "pdf")
@SessionScoped
public class DisplayPdf implements Serializable {

    private static final long serialVersionUID = 1L;

    // Constants ----------------------------------------------------------------------------------
    private final transient Connection con;
    private String filename;
    private String pfad;
    private int id;

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    /**
     * Initialisiert Mysql-Verbindung
     */
    public DisplayPdf() {
        con = MysqlConnection.connection();
    }

    /**
     * Setters und getters
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Lädt die pdf Dateiname aus DB
     *
     * @param code
     * @return
     */
    public String fileName(String code) {

        try (Statement stmt = con.createStatement()) {
            String sql = "SELECT Dateiname FROM benutzerhandbuch Where Language='" + code + "'";
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                filename = result.getString("Dateiname");
            }
        } catch (SQLException e) {
            System.out.println("Sql state" + e.getSQLState());
            System.out.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
            e.getStackTrace();
            System.out.println("Got an SQLException: " + e.getMessage());
        }
        return filename;
    }

    /**
     * Lädt die pdf Pfad aus DB
     *
     * @param code
     * @return
     */
    public String filePath(String code) {
        System.out.println("vor try block");
        try (Statement stmt = con.createStatement()) {
            String sql = "SELECT Pfad FROM benutzerhandbuch Where Language='" + code + "'";
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                pfad = result.getString("Pfad");
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
        //return path: <Tomcat-installation-directory>/uploads//user_guide
        pfad = System.getProperty("catalina.base") + pfad;
        return pfad;
    }

    /**
     * Zeigt die Pdf Datei auf Webseite an
     *
     * @param code
     * @throws IOException
     */
    public void downloadPDF(String code) throws IOException {

        //Vorbereiten.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file;
        file = new File(filePath(code), fileName(code));
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // �ffnen die Datei.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName(code) + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Schreiben Dateiinhalte zu response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalisieren Aufgabe.
            output.flush();
        } finally {
            // close streams.
            close(output);
            close(input);
        }

        // Informieren JSF, dass es kein Ursache gibt, um response zu behandeln.
        // Dies ist sehr wichtig, sonst wird die folgende Ausnahme in den Protokollen zu erhalten:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
    }

    /**
     *
     * @param resource
     */
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

}
