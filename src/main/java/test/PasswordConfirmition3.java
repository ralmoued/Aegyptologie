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
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean(name = "pass3")
@SessionScoped
@FacesValidator("passwordValidator")
public class PasswordConfirmition3 implements Validator {

    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String password = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
                .get("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                .toString();

        // Let required="true" do its job.
        if (password == null || password.isEmpty() || confirmPassword == null
                || confirmPassword.isEmpty()) {
            return;
        }
        
        else if (password.equals(confirmPassword)) {
            updateMsg();
        }

        else if (!password.equals(confirmPassword)) {
            uiInputConfirmPassword.setValid(false);
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
