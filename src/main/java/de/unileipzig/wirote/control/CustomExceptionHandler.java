
/*
 CustomExceptionHandler Class, which handels with errors. 
 If any exception is thrown, it will redirect to error.xhtml
 */
package de.unileipzig.wirote.control;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            
            // get the exception from context
            Throwable t = context.getException();
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
                /* Here you can use the Throwable object in order to verify the exceptions you want to handle
                 in the application */
                NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(fc, null, "/error?faces-redirect=true");
                fc.renderResponse();
            } finally {
                i.remove();
            }
        }
// Call the parent exception handler’s handle() method
        getWrapped().handle();
    }
}
