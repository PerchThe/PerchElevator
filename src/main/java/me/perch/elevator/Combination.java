package me.perch.elevator;

import me.perch.elevator.utils.SEMaterial;

public class Combination {
   private final String comboString;
   private final SEMaterial topComboMaterial;
   private final SEMaterial botComboMaterial;
   private final int customMaxDistance;

   Combination(String comboString) throws IllegalArgumentException {
      this.comboString = comboString;
      String[] split = comboString.replaceAll(" ", "").split(",");
      this.topComboMaterial = split[0].equalsIgnoreCase("any") ? null : SEMaterial.match(split[0] + (split[0].contains(":") ? "" : ":0"));
      this.botComboMaterial = split.length != 1 && !split[1].equalsIgnoreCase("any") ? SEMaterial.match(split[1] + (split[1].contains(":") ? "" : ":0")) : null;
      if (this.topComboMaterial == null && !split[0].equalsIgnoreCase("any") || this.botComboMaterial == null && split.length != 1 && !split[1].equalsIgnoreCase("any")) {
         throw new IllegalArgumentException();
      } else {
         try {
            this.customMaxDistance = split.length == 3 ? Integer.parseInt(split[2]) : -1;
         } catch (NumberFormatException var4) {
            throw new IllegalArgumentException();
         }
      }
   }

   public String toString() {
      return "Combination{comboString='" + this.comboString + '\'' + ", topComboMaterial=" + this.topComboMaterial + ", botComboMaterial=" + this.botComboMaterial + '}';
   }

   public String getComboString() {
      return this.comboString;
   }

   public SEMaterial getTopComboMaterial() {
      return this.topComboMaterial;
   }

   public SEMaterial getBotComboMaterial() {
      return this.botComboMaterial;
   }

   public int getCustomMaxDistance() {
      return this.customMaxDistance;
   }
}
