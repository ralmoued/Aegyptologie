/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.chap4;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@RequestScoped
public class Country {

    private String name;
    private String capital;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void findCapital(ValueChangeEvent event) {
        System.out.println("Old selected value is: " + event.getOldValue());
        System.out.println("New selected value is: " + event.getNewValue());
        String selectedCountryName = (String) event.getNewValue();
        if ("USA".equals(selectedCountryName)) {
            capital = "Washington";
        } else if ("Egypt".equals(selectedCountryName)) {
            capital = "Cairo";
        } else if ("Denmark".equals(selectedCountryName)) {
            capital = "Copenhagen";
        }
    }
}
