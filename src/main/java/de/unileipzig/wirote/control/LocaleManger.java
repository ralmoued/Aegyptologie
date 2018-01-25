package de.unileipzig.wirote.control;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ralmoued
 */
@ManagedBean(name = "localeManger")
@SessionScoped
public class LocaleManger implements Serializable {

    private Locale locale;
    private static final long serialVersionUID = 2756934361134603857L;

    @PostConstruct
    public void init() {
        // locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public String setLocale(String language) {
        this.locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

        //Reload (refresh) the current page/Redirect
        String pageName = finalRequestedPath();
        System.out.println("requested page is:" + pageName);
        if(pageName.contains("/")){
            return null;
        }
        return pageName + "?faces-redirect=true&includeViewParams=true";
    }

    // set the dir=rtl in case Arabic
    public String getDir() {
        if (getLanguage().equalsIgnoreCase("ar")) {
            return "rtl";
        } else {
            return "ltr";
        }
    }

    // Get the requested URL
    //Remove all charachters ends with "/"
    //Return the Name of Requested page
    //If requested URL is: /Wirote/mitarbeiter.xhtml
    //It will return: mitarbeiter.xhtml
    public String finalRequestedPath() {
        String path = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getRequestURI();
        System.out.println("RequestedPath is:" + path);
        int counter = 0;
        for (int i = 0; i < path.length(); i++) {
            counter++;
            if (path.charAt(i) == ('/')) {
                counter = 0;
            }
        }
      //In case the requested URL ends with "/"
        if (counter == 0) {
            return path;
        } 
            return path.substring(path.length() - counter, path.length() - 1);
    }
}
