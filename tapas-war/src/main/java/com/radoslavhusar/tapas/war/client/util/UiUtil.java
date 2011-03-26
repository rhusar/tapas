package com.radoslavhusar.tapas.war.client.util;

public class UiUtil {

   public static String formatPhase(String phaseName) {

      phaseName.replaceAll(" ", "");
      phaseName.trim(); // should be useless

      if (phaseName.length() <= 4) {
         return phaseName;
      } else {
         return phaseName.substring(0, 4);
      }
   }

   private UiUtil() {
      // Not instantiable, its a static util class.
   }
}
