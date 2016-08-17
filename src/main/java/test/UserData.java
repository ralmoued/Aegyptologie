
package test;

import java.io.Serializable;
/**
 *
 * @author ralmoued
 */


public class UserData implements Serializable {

   private static final long serialVersionUID = 1L;
	
private String message;

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getGreetingMessage(){
      return getMessage();
   }
}