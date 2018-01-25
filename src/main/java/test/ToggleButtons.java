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
 * @author ralmoued
 */
@ManagedBean(name = "toggleButtons")
@SessionScoped
public class ToggleButtons {
    
    private boolean enabled;

    public void toggle() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
     public void enabeling() {
        enabled = true;
    }
}
