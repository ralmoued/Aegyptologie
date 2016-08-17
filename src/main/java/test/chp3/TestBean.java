/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.chp3;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
@ManagedBean
@RequestScoped
public class TestBean implements Serializable {
// ...
private Location location;
// ...
public Location getLocation() {
return location;
}
public void setLocation(Location location) {
this.location = location;
}
// ...
public String proceed() {
return null;
}
}
