package de.unileipzig.wirote.control;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Used for managing i18n in application for each user
 *
 * @author ralmoued
 *
 */
@ManagedBean(name = "langSwitcher")
@RequestScoped
public class LanguageSwitcher implements Serializable {

    private Locale locale;

    private static final long serialVersionUID = 2756934361134603857L;

    public void init() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        System.out.println("Language Locale " + locale);

//        String lang = locale.toString();
//        System.out.println("Language String " + lang);
//        return lang;
    }

//    public String calculateLocale() {
//        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//        String language = locale.toString();
//        String code = setLanguage(language);
//        System.out.println("Language code " + code);
//        return code;
//    }
    public Locale getLang() {
        return locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public String setLang(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        System.out.println("Language Locale " + locale);

        String lang = locale.toString();
        System.out.println("Language String " + lang);
        return lang;
    }
}
