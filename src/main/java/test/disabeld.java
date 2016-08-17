/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Mirage
 */
@ManagedBean(name = "indexBean", eager = true)
@SessionScoped
public class disabeld {

    private boolean checked;
    private boolean cklicked;
    private boolean changed = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isCklicked() {
        return cklicked;
    }

    public void setCklicked(boolean cklicked) {
        this.cklicked = cklicked;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void isCklicked(ActionEvent event) {
        cklicked = !cklicked;
    }
    
        public void isChanged(ActionEvent event) {
        changed = !changed;
    }
}
