/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unileipzig.wirote.control;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ralmoued
 */
@ManagedBean(name = "disabeling")
@RequestScoped
public class Disabeling {
    
    private boolean disable;

    // default constructor 
    public Disabeling(){
       this.disable= false;
    }

    public boolean isDisable() {
       return disable;
    }
    public void setDisable(boolean disable) {
       this.disable = disable;
    }
    
}
