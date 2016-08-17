
package test;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ralmoued
 */
@ManagedBean
@RequestScoped
public class ToggleImageView {

    private String image;

    @PostConstruct
    public void initialize() {
        image = "Family.png";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}