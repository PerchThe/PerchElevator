package me.perch.elevator.elevator;

import me.perch.elevator.Direction;
import me.perch.elevator.Variables;
import me.perch.elevator.utils.SEMaterial;
import java.util.Objects;
import java.util.TreeSet;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class ElevatorCheck {
   private final Player player;
   private final CombinationData combinationData;
   private final ElevatorType type;
   private final TreeSet<Integer> totalFloors = new TreeSet<>();

   public ElevatorCheck(Player player, CombinationData combinationData, ElevatorType type) {
      this.player = player;
      this.combinationData = combinationData;
      this.type = type;
   }

   public void calculateFloors(Location location) {
      if (!location.getBlock().getType().name().endsWith("BED") && !location.getBlock().getType().name().startsWith("BED")) {
         for (int y = Variables.getInstance().getMinY(); y < Variables.getInstance().getMaxY(); ++y) {
            Location target_top = new Location(location.getWorld(), location.getBlockX(), y, location.getBlockZ());
            Location target_bottom = new Location(location.getWorld(), location.getBlockX(), y - 1, location.getBlockZ());
            Location blockFeet = new Location(location.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 1, target_top.getBlockZ());
            Location blockBody = new Location(location.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 2, target_top.getBlockZ());
            if ((this.combinationData.getTopMaterial() == null
                    || SEMaterial.match(target_top.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getTopMaterial())
                    && this.isElevator(target_top, target_bottom, blockFeet, blockBody)) {
               this.totalFloors.add(y);
            }
         }
      }
   }

   private boolean isElevator(Location targetTop, Location targetBottom, Location blockFeet, Location blockBody) {
      if (this.combinationData.getBotMaterial() == null
              || (SEMaterial.match(targetBottom.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getBotMaterial()
              && SEMaterial.match(targetBottom.getBlock().getRelative(BlockFace.DOWN), false).getData() == this.combinationData.getBotMaterial().getData())) {
         SEMaterial topMatch = SEMaterial.match(targetTop.getBlock().getRelative(BlockFace.DOWN), false);
         if (this.combinationData.getTopMaterial() == null
                 && !Objects.equals(SEMaterial.SpecialType.of(topMatch), this.combinationData.getTopSpecialType())) {
            return false;
         } else if (this.combinationData.getTopMaterial() != null
                 && this.type != ElevatorType.PLATE
                 && !topMatch.isSpecialType(SEMaterial.SpecialType.TRAPDOOR)
                 && topMatch.getData() != this.combinationData.getTopMaterial().getData()) {
            return false;
         } else if (this.type == ElevatorType.BLOCK) {
            return Variables.getInstance().getIgnoreList() == null
                    || (Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockFeet.getBlock().getRelative(BlockFace.DOWN), true))
                    && Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockBody.getBlock().getRelative(BlockFace.DOWN), true)));
         } else if (this.type != ElevatorType.NON_OCCLUDING) {
            return true;
         } else {
            return Variables.getInstance().getIgnoreList() == null
                    || Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockFeet.getBlock().getRelative(BlockFace.DOWN), true));
         }
      } else {
         return false;
      }
   }

   public boolean hasElevator(Direction direction) {
      return direction == Direction.UP ? this.hasElevatorUP() : this.hasElevatorDOWN();
   }

   private boolean isIntentionalJump() {
      return player.getVelocity().getY() > 0.25 && !player.isFlying();
   }

   private boolean hasElevatorUP() {
      if (!isIntentionalJump()) return false;
      final int startY = player.getLocation().getBlockY() + 2;
      for (int y = startY; y < Variables.getInstance().getMaxY(); ++y) {
         Location target_top = new Location(player.getWorld(), player.getLocation().getBlockX(), y, player.getLocation().getBlockZ());
         Location target_bottom = new Location(player.getWorld(), player.getLocation().getBlockX(), y - 1, player.getLocation().getBlockZ());
         Location blockFeet = new Location(player.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 1, target_top.getBlockZ());
         Location blockBody = new Location(player.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 2, target_top.getBlockZ());
         if ((this.combinationData.getTopMaterial() == null
                 || SEMaterial.match(target_top.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getTopMaterial())
                 && this.isElevator(target_top, target_bottom, blockFeet, blockBody)) {
            return true;
         }
      }
      return isIntentionalJump() && this.hasLazyFloor(Direction.UP, this.player.getLocation().getBlockY());
   }

   private boolean hasElevatorDOWN() {
      int currentPlayerY = (int)(Math.ceil(this.player.getLocation().getY()) - 1.0D);
      for (int y = currentPlayerY; y > Variables.getInstance().getMinY(); --y) {
         Location target_top = new Location(this.player.getWorld(), this.player.getLocation().getBlockX(), y, this.player.getLocation().getBlockZ());
         Location target_bottom = new Location(this.player.getWorld(), this.player.getLocation().getBlockX(), y - 1, this.player.getLocation().getBlockZ());
         Location blockFeet = new Location(this.player.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 1, target_top.getBlockZ());
         Location blockBody = new Location(this.player.getWorld(), target_top.getBlockX(), target_top.getBlockY() + 2, target_top.getBlockZ());
         if ((this.combinationData.getTopMaterial() == null
                 || SEMaterial.match(target_top.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getTopMaterial())
                 && this.isElevator(target_top, target_bottom, blockFeet, blockBody)) {
            return true;
         }
      }
      return this.hasLazyFloor(Direction.DOWN, this.player.getLocation().getBlockY());
   }

   int getLazyFloor(Direction direction, int fromY) {
      if (direction == Direction.UP) {
         if (!Variables.getInstance().isLazyCheckTop()) return Integer.MIN_VALUE;
      } else if (!Variables.getInstance().isLazyCheckBottom()) {
         return Integer.MIN_VALUE;
      }
      for (int y = fromY + (direction == Direction.UP ? 2 : -3);
           y != (direction == Direction.UP ? Variables.getInstance().getMaxY() : Variables.getInstance().getMinY());
           y += direction == Direction.UP ? 1 : -1) {
         Location location = new Location(this.player.getWorld(), this.combinationData.getTopBlock().getX(), y, this.combinationData.getTopBlock().getZ());
         if (location.getBlock().getType().isSolid()
                 && location.getWorld().getBlockAt(location.clone().add(0.0D, 1.0D, 0.0D)).isEmpty()
                 && location.getWorld().getBlockAt(location.clone().add(0.0D, 2.0D, 0.0D)).isEmpty()
                 && (Variables.getInstance().getLazyCheckWhitelist().isEmpty()
                 || Variables.getInstance().isLazyCheckInvert() == !Variables.getInstance().getLazyCheckWhitelist().contains(SEMaterial.match(location.getBlock(), false)))) {
            return y;
         }
      }
      return Integer.MIN_VALUE;
   }

   private boolean hasLazyFloor(Direction direction, int fromY) {
      return this.getLazyFloor(direction, fromY) != Integer.MIN_VALUE;
   }

   public int getNumberOfFloors() {
      return this.totalFloors.size()
              + (!this.totalFloors.isEmpty()
              ? (this.hasLazyFloor(Direction.UP, this.totalFloors.last()) ? 1 : 0)
              + (this.hasLazyFloor(Direction.DOWN, this.totalFloors.first()) ? 1 : 0)
              : 0);
   }

   public int getCurrentFloor(int blockY) {
      Integer floor = this.totalFloors.floor(blockY + 1);
      return floor == null ? 0
              : this.totalFloors.headSet(floor, true).size()
              + (this.hasLazyFloor(Direction.DOWN, this.totalFloors.first()) ? 1 : 0);
   }
}
