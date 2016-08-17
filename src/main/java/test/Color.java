/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Mirage
 */
@ManagedBean
@RequestScoped
public class Color {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public void findColor(ValueChangeEvent event) {
        String selectedColor = (String) event.getNewValue();
        
        if ("Red".equals(selectedColor)) {
            color = "Red color";
        } else if ("Green".equals(selectedColor)) {
            color = "Green color";
        } else if ("Blue".equals(selectedColor)) {
            color = "Blue color";
    }
}
}
