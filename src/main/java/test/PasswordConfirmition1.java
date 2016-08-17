/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Mirage
 */
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "pass1")
@SessionScoped
public class PasswordConfirmition1 {

    private String password;
    private String PassConfirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassConfirm() {
        return PassConfirm;
    }

    public void setPassConfirm(String PassConfirm) {
        this.PassConfirm = PassConfirm;
    }

    public void checkMatching() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pwd1 = request.getParameter("pwdConfirmation:pwd1");
        String pwd2 = request.getParameter("pwdConfirmation:pwd2");
        System.out.println("password id " + pwd1);
        System.out.println("confirm is " + pwd2);
        // Let required="true" do its job.
        if (password.equals(PassConfirm)) {
            updateMsg();
        } else if (!password.equals(PassConfirm)) {
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
