package me.perch.elevator.elevator;

import me.perch.elevator.Direction;
import me.perch.elevator.Variables;
import me.perch.elevator.utils.SEMaterial;
import java.util.TreeSet;
import org.bukkit.Location;
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
      for (int y = Variables.getInstance().getMinY(); y < Variables.getInstance().getMaxY(); ++y) {
         if (ElevatorRule.isFloor(location.getWorld(), location.getBlockX(), y, location.getBlockZ())) {
            this.totalFloors.add(y);
         }
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
         if (ElevatorRule.isFloor(player.getWorld(), player.getLocation().getBlockX(), y, player.getLocation().getBlockZ())) {
            return true;
         }
      }
      return this.hasLazyFloor(Direction.UP, this.player.getLocation().getBlockY());
   }

   private boolean hasElevatorDOWN() {
      int currentPlayerY = (int)(Math.ceil(this.player.getLocation().getY()) - 1.0D);
      for (int y = currentPlayerY; y > Variables.getInstance().getMinY(); --y) {
         if (ElevatorRule.isFloor(this.player.getWorld(), this.player.getLocation().getBlockX(), y, this.player.getLocation().getBlockZ())) {
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
