package me.perch.elevator.elevator;

import me.perch.elevator.Combination;
import me.perch.elevator.Variables;
import me.perch.elevator.utils.SEMaterial;
import java.util.List;
import java.util.stream.Stream;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.DaylightDetector;

public final class CombinationCheck {
   public static CombinationData isCombination(Block top, Block bottom) {
      SEMaterial topBlockMaterial;
      if (!Variables.PRE_1_13 && top.getBlockData() instanceof DaylightDetector) {
         topBlockMaterial = SEMaterial.match(top.getType().name() + (((DaylightDetector)top.getBlockData()).isInverted() ? "_INVERTED" : ""));
      } else {
         topBlockMaterial = SEMaterial.match(top.getType().name() + (!Variables.PRE_1_13 ? ":0" : ":" + top.getData()));
      }

      SEMaterial botBlockMaterial = SEMaterial.match(bottom.getType().name() + (!Variables.PRE_1_13 ? ":0" : ":" + bottom.getData()));
      if (Stream.of(SEMaterial.AIR, SEMaterial.CAVE_AIR, SEMaterial.VOID_AIR).anyMatch((seMaterial) -> {
         return seMaterial == topBlockMaterial;
      })) {
         return null;
      } else {
         List<Combination> combinations = Variables.getInstance().getCombinations();

         for(int i = 0; i < combinations.size(); ++i) {
            Combination combination = combinations.get(i);
            if ((topBlockMaterial == combination.getTopComboMaterial() || combination.getTopComboMaterial() == null) && (botBlockMaterial == combination.getBotComboMaterial() || combination.getBotComboMaterial() == null)) {
               return new CombinationData(combination.getTopComboMaterial() == null ? null : top, combination.getBotComboMaterial() == null ? null : bottom, i + 1, SEMaterial.SpecialType.of(topBlockMaterial), combination.getCustomMaxDistance());
            }
         }

         return null;
      }
   }
}
