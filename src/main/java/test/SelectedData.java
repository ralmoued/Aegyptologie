package test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="selectedData")
@SessionScoped
public class SelectedData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
        
        public String getCorrectAnswer() {
            if (data.equals("5"))
                
                return "True";
            else
                return "false";
        }
}
