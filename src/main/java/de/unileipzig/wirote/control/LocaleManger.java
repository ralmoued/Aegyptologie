package de.unileipzig.wirote.control;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    
    // set the dir=rtl in case Arabic
    public String getDir() {
        if (getLanguage().equalsIgnoreCase("ar")) {
            return "rtl";
        } else {
            return "ltr";
        }
    }
}
