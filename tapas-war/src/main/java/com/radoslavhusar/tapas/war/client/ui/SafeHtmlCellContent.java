package com.radoslavhusar.tapas.war.client.ui;

import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * This probably should not have been done, not sure why.
 * 
 * @author rhusar
 */
public class SafeHtmlCellContent implements SafeHtml {

   private String html;

   public SafeHtmlCellContent(String html) {
      this.html = html;
   }

   @Override
   public String asString() {
      return html;
   }
}
