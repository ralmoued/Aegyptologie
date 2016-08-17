package test;

/**
 *
 * @author Mirage
 */
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "userAjaxData", eager = true)
@ViewScoped
public class UserAjaxData implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean status = false;
    private String name;
    private String imageName;
    private boolean image1 = false;
    private boolean image2 = false;
    private boolean image3 = false;
    private boolean image4 = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWelcomeMessage() {
        if ("".equals(name) || name == null) {
            return "";
        } else {
            return "Welcome " + name;
        }
    }

    public void handleEvent(AjaxBehaviorEvent event) {
        name = name + " Memo and Roro";
    }

    public void toggleStatus() {
        status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    
    public void slectedGroup(AjaxBehaviorEvent event) {
        imageName = (String) event.getComponent().getAttributes().get("image");
    }

}
