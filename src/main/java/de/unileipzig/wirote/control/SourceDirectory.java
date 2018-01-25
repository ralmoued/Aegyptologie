/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unileipzig.wirote.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ralmoued
 */
@ManagedBean(name = "sourceDirectory")
@SessionScoped
public class SourceDirectory {

    private final String userGuideFolder = "uploads/user_guide";
    private final String empPhotoFolder = "uploads/employees";

    public String getUserGuideFolder() {
        return Paths.get(System.getProperty("catalina.base") + "/" + userGuideFolder + "/").toString();
    }

    public String getEmpPhotoFolder() {
        return Paths.get(System.getProperty("catalina.base") + "/" + empPhotoFolder).toString();
    }

    //https://stackoverflow.com/a/4543951/6654570
    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String filename = context.getExternalContext().getRequestParameterMap().get("filename");
            return new DefaultStreamedContent(new FileInputStream(new File(getEmpPhotoFolder(), filename)));
        }
    }
}
