package me.perch.elevator.elevator;

import me.perch.elevator.Combination;
import me.perch.elevator.Variables;
import me.perch.elevator.utils.SEMaterial;
import java.util.List;
import org.bukkit.block.Block;

public final class CombinationCheck {
    public static CombinationData isCombination(Block top, Block bottom) {
        if (top == null || bottom == null) return null;

        SEMaterial topBlockMaterial = SEMaterial.match(top, false);
        SEMaterial botBlockMaterial = SEMaterial.match(bottom, false);

        if (topBlockMaterial != SEMaterial.AIR && topBlockMaterial != SEMaterial.CAVE_AIR && topBlockMaterial != SEMaterial.VOID_AIR) {
            List<Combination> combinations = Variables.getInstance().getCombinations();

            for (int i = 0; i < combinations.size(); ++i) {
                Combination combination = combinations.get(i);
                if ((topBlockMaterial == combination.getTopComboMaterial() || combination.getTopComboMaterial() == null) && (botBlockMaterial == combination.getBotComboMaterial() || combination.getBotComboMaterial() == null)) {
                    return new CombinationData(combination.getTopComboMaterial() == null ? null : top, combination.getBotComboMaterial() == null ? null : bottom, i + 1, SEMaterial.SpecialType.of(topBlockMaterial), combination.getCustomMaxDistance());
                }
            }

        }
        return null;
    }
}