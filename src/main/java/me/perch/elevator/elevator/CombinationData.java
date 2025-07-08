package me.perch.elevator.elevator;

import me.perch.elevator.utils.SEMaterial;
import org.bukkit.block.Block;

public class CombinationData {
   private final Block topBlock;
   private final Block botBlock;
   private final SEMaterial topMaterial;
   private final SEMaterial botMaterial;
   private final int comboIndex;
   private final SEMaterial.SpecialType topSpecialType;
   private final int customMaxDistance;

   CombinationData(Block topBlock, Block botBlock, int comboIndex, SEMaterial.SpecialType topSpecialType, int customMaxDistance) {
      this.topBlock = topBlock;
      this.topMaterial = SEMaterial.match(topBlock, false);
      this.botBlock = botBlock;
      this.botMaterial = SEMaterial.match(botBlock, false);
      this.comboIndex = comboIndex;
      this.topSpecialType = topSpecialType;
      this.customMaxDistance = customMaxDistance;
   }

   boolean hasCustomMaxDistance() {
      return this.customMaxDistance != -1;
   }

   public String toString() {
      return "CombinationData{topBlock=" + this.topBlock + ", botBlock=" + this.botBlock + ", comboIndex=" + this.comboIndex + '}';
   }

   public Block getTopBlock() {
      return this.topBlock;
   }

   public Block getBotBlock() {
      return this.botBlock;
   }

   public SEMaterial getTopMaterial() {
      return this.topMaterial;
   }

   public SEMaterial getBotMaterial() {
      return this.botMaterial;
   }

   public int getComboIndex() {
      return this.comboIndex;
   }

   public SEMaterial.SpecialType getTopSpecialType() {
      return this.topSpecialType;
   }

   public int getCustomMaxDistance() {
      return this.customMaxDistance;
   }
}
