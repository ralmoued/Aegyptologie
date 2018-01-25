package de.unileipzig.wirote.control;

/**
 *
 * @author ralmoued
 */
import de.unileipzig.wirote.database.MysqlConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final String userGuideFolder = "uploads/user_guide";
    private final String empPhotoFolder = "uploads/employees";

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
                    + "VALUES('" + fileName + "','" + "/" + userGuideFolder + "/" + "','" + languageCode + "')";

            stmt.executeUpdate(sql);

            System.out.println("file name and path added Successfully");
            fileSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ladt die Pdf Datei hoch
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
                copyFileToResources(file.getFileName(), userGuideFolder);
            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            FacesMessage msg = new FacesMessage("Die Datei ", event.getFile().getFileName() + " konnte nicht hochgeladen werden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Upload the photos of the employees
     *
     * @param event
     */
    public void upload_emp_photo(FileUploadEvent event) {
        file = event.getFile();

        //return the id of the employee and convert it from int to String
        String emp_Id = (String) "" + event.getComponent().getAttributes().get("emp_Id");

        
        
        try {
            copyFileToResources(file.getFileName(), empPhotoFolder);
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (fileSuccess) {
            FacesMessage msg = new FacesMessage("Die Datei ", event.getFile().getFileName() + " ist erfolgreich hochgeladen.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            renameFile(file.getFileName(), emp_Id);

        } else {
            FacesMessage msg = new FacesMessage("Die Datei ", event.getFile().getFileName() + " konnte nicht hochgeladen werden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        
        fileSuccess=false;
    }

    /**
     * Defniert Die Pfad der Datei
     *
     * @return path 
     * NOT USED
     */
    private String getPath() {
//       return FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
        FileSystemView.getFileSystemView().getHomeDirectory();
        String Path = FileSystemView.getFileSystemView().getHomeDirectory() + "/Benutzerhandbuch/";
        System.out.println("Path is : " + Path);
        boolean success = (new File(Path)).mkdir();
        if (success) {
            System.out.println("Directory: " + Path + " created");
        }
        return Path;
    }

    /**
     * return Den aktuelle Benutzername
     *
     * @return NOT USED
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
     * @return the code of the language which stored in the database ('de', 'ar'
     * and 'en')
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

    public void copyFileToResources(String fileName, String uploadFolder) throws IOException {
        //Upload the files to: <Tomcat-installation-directory>/uploads/...
        File uploadDir = new File(System.getProperty("catalina.base"), uploadFolder);
        File uploadFile = new File(uploadDir, fileName);
        // creates the directory if it does not exist
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        System.out.println("uploadDir " + uploadDir.getAbsolutePath());

        //location of the  uploaded file in memory
        InputStream input = file.getInputstream();
        System.out.println("file.getInputstream() " + input);

        //overwrite the destination file if it exists, and copy
        // the file attributes, including the rwx permissions
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,};

        Files.copy(input, uploadFile.toPath(), options);

        fileSuccess = true;
    }

    public void renameFile(String fileName, String emp_Id){       
        try {
            Path source = Paths.get(System.getProperty("catalina.base") + "/" + empPhotoFolder + "/" + fileName);
            System.out.println("source " + source);
            
            CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE};
            
            Files.move(source, source.resolveSibling(emp_Id + ".png"), options);
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
}
