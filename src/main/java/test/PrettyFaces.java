/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mirage
 */

@ManagedBean(name = "paramter")
@SessionScoped
public class PrettyFaces {
    
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String language(){
        return "en";
    }
    
    
    @Override
    public String toString() {
        return "PrettyFaces{" + "lang=" + lang + '}';
    }
    
    
    
}
