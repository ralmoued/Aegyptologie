package de.unileipzig.wirote.control;


import de.unileipzig.wirote.model.Wbuch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ralmoued
 */
@ManagedBean(name = "ring")
@SessionScoped
public class Dictionary  implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Wbuch> wbuch;
    private Wbuch selectedWb;
    private Wbuch wb;
    

    @PostConstruct
    public void init() {
        wbuch = new ArrayList<>();

         wb = new Wbuch();
         wbuch = wb.getWbList();
        }

    public List<Wbuch> getWbuch() {
        return wbuch;
    }

    public void setWbuch(List<Wbuch> wbuch) {
        this.wbuch = wbuch;
    }

    public Wbuch getSelectedWb() {
        return selectedWb;
    }

    public void setSelectedWb(Wbuch selectedWb) {
        this.selectedWb = selectedWb;
    }

}