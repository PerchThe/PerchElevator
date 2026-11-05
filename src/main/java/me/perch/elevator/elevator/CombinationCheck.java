package me.perch.elevator.elevator;

import org.bukkit.block.Block;

public final class CombinationCheck {
    private CombinationCheck() {
    }
    public static CombinationData isCombination(Block top, Block bottom) {
        if (ElevatorRule.isElevatorPair(top, bottom)) {
            return new CombinationData(top, bottom, 1, null, -1);
        }
        return null;
    }
}
