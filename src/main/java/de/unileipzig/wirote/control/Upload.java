package de.unileipzig.wirote.control;

/**
 *
 * @author ralmoued
 */
import de.unileipzig.wirote.database.MysqlConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.swing.filechooser.FileSystemView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Diese Klasse lädt das Handbenutzerbuch hoch und bietet Methoden, um pdf
 * Dateiname, und Pfad in Datenbank zu speichern.
 *
 * @author ralmoued
 */
@ManagedBean(name = "fileUploadController")
@RequestScoped
public class Upload implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient Connection con;
    private UploadedFile file;

    private boolean fileSuccess;

    /**
     * Initialisiert Mysql-Verbindung
     */
    public Upload() {
        con = MysqlConnection.connection();
    }

    /**
     * Setter und Getter für file und messeage (Status des Hochladen)
     *
     * @return
     */
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isFileSuccess() {
        return fileSuccess;
    }

    public void setFileSuccess(boolean fileSuccess) {
        this.fileSuccess = fileSuccess;
    }

    /**
     * Add pdf Dateiname und Pfad in der Datnbank
     *
     * @param fileName
     * @param languageCode
     */
    public void addPdfToDB(String fileName, String languageCode) {
        fileSuccess = false;
        try (Statement stmt = con.createStatement();) {

            //Zeilenzähler
            ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM benutzerhandbuch");
            result.next();
            //Berechnung Anzahl der Zeilen
            int rowCount = result.getInt(1);

            //falls die Datenbank leer ist dann(INSERT), ansonst (UPDATE)
            String sql;
            if (rowCount > 0 && currentLanguage().contains(languageCode)) {
                sql = "Delete From benutzerhandbuch WHERE Language = '" + languageCode + "'";
                stmt.executeUpdate(sql);
            }
            sql = "INSERT INTO benutzerhandbuch(Dateiname, Pfad, Language) "
                    + "VALUES('" + fileName + "','" + getPath() + "','" + languageCode + "')";

            stmt.executeUpdate(sql);

            System.out.println("file name and path added Successfully");
            fileSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ladt die Englische version Pdf Datei hoch
     *
     * @param event
     */
    public void upload(FileUploadEvent event) {
        file = event.getFile();
        
        // Get the languageCode parameter from the upload.xml
        String languageCode = (String) event.getComponent().getAttributes().get("languageCode");
        addPdfToDB(file.getFileName(), languageCode);
        
        if (fileSuccess) {
            FacesMessage msg = new FacesMessage("Die Datei ", event.getFile().getFileName() + " ist erfolgreich hochgeladen.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            try {
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FacesMessage msg = new FacesMessage("Die Datei ", event.getFile().getFileName() + " konnte nicht hochgeladen werden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Ladt die Pdf Datei hoch
     *
     * @param fileName
     * @param in
     */
    public void copyFile(String fileName, InputStream in) {
        try {
            try ( // write the inputStream to a FileOutputStream
                    OutputStream out = new FileOutputStream(new File(getPath() + fileName))) {
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
            }

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Defniert Die Pfad der Datei
     *
     * @return path
     */
    private String getPath() {
//       return FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        FileSystemView.getFileSystemView().getHomeDirectory();
        String Path = FileSystemView.getFileSystemView().getHomeDirectory() + "/Benutzerhandbuch/";
        boolean success = (new File(Path)).mkdir();
        if (success) {
            System.out.println("Directory: " + Path + " created");
        }
        return Path;
    }

    /**
     * return Den aktuelle Benutzername
     *
     * @return
     */
    private String getUsername() {
        String name;
        name = System.getProperty("user.name");
        return name;
    }

    /**
     * Define in which language the "User's Guide" stored in the data base If
     * code= en then we have English text or German text if code=de or both
     * texts if code= de en
     *
     * @return the code of the language which stored in the database ('de', 'ar' and 'en')
     */
    public String currentLanguage() {
        String code = "";

        try (Statement stmt = con.createStatement();) {

            String sql = "SELECT Language FROM benutzerhandbuch";
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
