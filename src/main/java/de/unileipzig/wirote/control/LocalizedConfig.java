
package de.unileipzig.wirote.control;

import javax.servlet.ServletContext;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.transposition.LocaleTransposition;

/**
 *
 * @author ralmoued
 */
public class LocalizedConfig extends HttpConfigurationProvider
{//D:\Users\ralmoued\Documents\NetBeansProjects\mavenproject1\src\main\resources\I18n\lang.properties
   @Override
   public Configuration getConfiguration(final ServletContext context)
   {
      return ConfigurationBuilder.begin()
               .addRule(Join.path("/{language}/{path}").to("/{path}.xhtml"))
               .where("path").transposedBy(LocaleTransposition.bundle("I18n.lang", "language"));
   }

    @Override
    public int priority() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}