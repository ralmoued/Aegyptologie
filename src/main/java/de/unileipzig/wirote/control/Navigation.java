package de.unileipzig.wirote.control;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ralmoued
 */
@ManagedBean
@SessionScoped
public class Navigation implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    /**
     * Anmelden Redirect to editierenseite.
     *
     * @return editierenseite.
     */
    public String anmelden() {
        return "/Bearbeitung/editieren?faces-redirect=true";
    }

    /**
     * Abmelden Redirect to index page.
     *
     * @return index page name.
     */
    public String abmelden() {
        return "/index?faces-redirect=true";
    }

    /**
     * Redirect to same page. Just like refresh the page
     *NOT USED
     */
    public void redirectTosameURL() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
