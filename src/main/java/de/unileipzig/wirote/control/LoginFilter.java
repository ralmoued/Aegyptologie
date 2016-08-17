
package de.unileipzig.wirote.control;

    
import de.unileipzig.wirote.model.Login;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
/**
 *
 * @author ralmoued
 */

/**
 * Filter Klasse
 * Filter prüft, ob Login hat loginIn Eigenschaft auf true gesetzt.
 * Wenn er nicht gesetzt wird die Anfrage wird an den login.xhtml Seite umgeleitet.
 *
 * @author itcuties
 *
 */
public class LoginFilter implements Filter {
 
    /**
     * Prüft, ob Benutzer angemeldet ist 
     * Wenn nicht angemeldet sind, wird es an die index.xhtml Seite umgeleitet werden.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        Login loginBean = (Login)((HttpServletRequest)request).getSession().getAttribute("login");
        // System.out.println("loginBean value: " + loginBean);
        // Zum ersten Anwendungsanforderung gibt es keine loginBean in der Session so Benutzer einloggen muss.
        // Bei anderen Fragen loginBean vorhanden ist, aber wir müssen überprüfen, ob Benutzer sich erfolgreich angemeldet hat.
        if (loginBean == null || !loginBean.isLoggedIn()) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
        }
         
        chain.doFilter(request, response);
             
    }
 
    @Override
    public void init(FilterConfig config) throws ServletException {
        // Hier ist nichts zu tun!
    }
 
    @Override
    public void destroy() {
        // Hier ist nichts zu tun!
    }  
     
}
