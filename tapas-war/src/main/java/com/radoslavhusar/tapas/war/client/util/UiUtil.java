package com.radoslavhusar.tapas.war.client.util;

public class UiUtil {

   public static String formatPhase(String phaseName) {

      String pshaseNameNoSpaces = phaseName.replaceAll("\\s+", "");
      //phaseName.trim(); // should is now useless

      if (pshaseNameNoSpaces.length() <= 3) {
         return pshaseNameNoSpaces.toUpperCase();
      } else {
         return pshaseNameNoSpaces.substring(0, 3).toUpperCase();
      }
   }

   private UiUtil() {
      // Not instantiable, its a static util class.
   }
}
