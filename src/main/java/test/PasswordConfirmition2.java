/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean(name = "pass2")
@SessionScoped
public class PasswordConfirmition2 {

    public String password;
    public String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void validatePassword(ComponentSystemEvent event) {

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        confirmPassword = (String) uiInputConfirmPassword.getLocalValue();                
        if (confirmPassword == null) {
            confirmPassword = "";
        } else {
            confirmPassword = uiInputConfirmPassword.getLocalValue().toString();
        }
    }

    public void checkPasswordMatching() {
        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {//we delete this line since we have reguire treue and require message
        } else if (password.equals(confirmPassword)) {
            updateMsg();
        } else if (!password.equals(confirmPassword)) {
            errorMsg();
        }
    }
    
    public void updateMsg() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Update is done");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void errorMsg() {
        String errorMsg = "No matching";
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
