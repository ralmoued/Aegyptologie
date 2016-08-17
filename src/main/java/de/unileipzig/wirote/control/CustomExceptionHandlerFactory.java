/*
 CustomExceptionHandlerFactory Class and CustomExceptionHandler CustomExceptionHandler will handel 
 every error in this application if happend, after rigestering it in the faces-config
 */
package de.unileipzig.wirote.control;

/**
 *
 * @author Mirage
 */
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

// this injection handles jsf
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = new CustomExceptionHandler(parent.getExceptionHandler());
        return result;
    }
}
