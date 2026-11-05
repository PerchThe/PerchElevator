package me.perch.elevator.elevator;

import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.util.BoundingBox;

public final class ElevatorRule {

   private ElevatorRule() {
   }

   public static boolean isElevatorPair(Block standOn, Block base) {
      return standOn != null && base != null
              && standOn.getType().isSolid()
              && isValidBase(base)
              && isFullBlock(standOn);
   }

   public static boolean isFloor(World world, int x, int blockY, int z) {
      Block standOn = world.getBlockAt(x, blockY - 1, z);
      if (!standOn.getType().isSolid()) {
         return false;
      }
      if (!isValidBase(world.getBlockAt(x, blockY - 2, z)) || !isFullBlock(standOn)) {
         return false;
      }
      return world.getBlockAt(x, blockY, z).isPassable()
              && world.getBlockAt(x, blockY + 1, z).isPassable();
   }

   public static boolean isValidBase(Block base) {
      if (base.getType() == Material.BLACK_CONCRETE) {
         return true;
      }
      if (Tag.BUTTONS.isTagged(base.getType())) {
         BlockData data = base.getBlockData();
         return data instanceof Switch && ((Switch) data).getAttachedFace() == FaceAttachable.AttachedFace.CEILING;
      }
      return false;
   }

   public static boolean isFullBlock(Block block) {
      Material type = block.getType();
      if (!type.isSolid() || Tag.SLABS.isTagged(type) || Tag.STAIRS.isTagged(type)) {
         return false;
      }
      Collection<BoundingBox> boxes = block.getCollisionShape().getBoundingBoxes();
      if (boxes.size() != 1) {
         return false;
      }
      BoundingBox box = boxes.iterator().next();
      return box.getWidthX() >= 1.0D && box.getHeight() >= 1.0D && box.getWidthZ() >= 1.0D;
   }
}
