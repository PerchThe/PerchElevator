package me.perch.elevator.elevator;

import me.perch.elevator.Direction;
import me.perch.elevator.Events;
import me.perch.elevator.PlayerData;
import me.perch.elevator.SEPerm;
import me.perch.elevator.Variables;
import me.perch.elevator.files.MessagesFile;
import me.perch.elevator.utils.ActionbarUtil;
import me.perch.elevator.utils.MessageUtil;
import me.perch.elevator.utils.TitleBarUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class Elevator {
   private final ArrowCreator arrowCreator = new ArrowCreator();
   private final Player player;
   private final Location current;
   private final CombinationData combinationData;
   private final Direction direction;
   private final ElevatorType type;
   private final ElevatorCheck elevatorCheck;
   private final PlayerData playerData;

   public Elevator(Player player, Location current, CombinationData combinationData, Direction direction, ElevatorType type, ElevatorCheck elevatorCheck) {
      this.player = player;
      this.current = current;
      this.combinationData = combinationData;
      this.direction = direction;
      this.type = type;
      this.elevatorCheck = elevatorCheck;
      this.playerData = PlayerData.getPlayerData(player);
   }

   public void elevator() {
      int currentBlockY = (int) Math.ceil(this.player.getLocation().getY());

      if (this.direction.equals(Direction.UP)) {
         if (!scanAndElevate(currentBlockY + 1, Variables.getInstance().getMaxY(), 1)) {
            tryLazyElevation();
         }
      } else if (this.direction.equals(Direction.DOWN)) {
         if (!scanAndElevate(currentBlockY - 1, Variables.getInstance().getMinY(), -1)) {
            tryLazyElevation();
         }
      }
   }

   private boolean scanAndElevate(int startY, int endY, int step) {
      for (int y = startY; step > 0 ? y < endY : y > endY; y += step) {
         if (ElevatorRule.isFloor(this.player.getWorld(), this.current.getBlockX(), y, this.current.getBlockZ())) {
            Location destination = new Location(
                    this.player.getWorld(),
                    this.current.getBlockX() + 0.5D,
                    y,
                    this.current.getBlockZ() + 0.5D,
                    this.player.getLocation().getYaw(),
                    this.player.getLocation().getPitch());
            elevate(destination, (int) Math.floor(this.current.distance(destination)));
            return true;
         }
      }
      return false;
   }

   private void elevate(Location destination, int elevatorDist) {
      int maxDist = this.combinationData.hasCustomMaxDistance() ? this.combinationData.getCustomMaxDistance() : Variables.getInstance().getMaxDistance();
      boolean allowableDistance = elevatorDist <= maxDist;

      if (!allowableDistance && !SEPerm.DISTANCE_BYPASS.hasPermission(this.player) && !playerMaxDist(this.player, elevatorDist)) {
         MessageUtil.send(this.player, this.direction == Direction.UP ? MessagesFile.getInstance().getTooGreatDistanceUp() : MessagesFile.getInstance().getTooGreatDistanceDown());
         return;
      }

      boolean vanished = this.player.getMetadata("vanished").stream().anyMatch(MetadataValue::asBoolean);

      if (!vanished && Variables.getInstance().isArrowEnabled() && Variables.getInstance().isArrowCurrent() && !Variables.version("1.7")) {
         this.arrowCreator.arrow(this.player, this.direction);
      }

      ElevationHandler.elevate(this.player, destination);

      this.playerData.getElevatorBossBar().ifPresent(elevatorBossBar ->
              elevatorBossBar.display(elevatorBossBar.getCurrentFloor() + (this.direction == Direction.UP ? 1 : -1), elevatorBossBar.getTotalFloors())
      );

      this.playerData.setCurrentFloor(this.playerData.getCurrentFloor() + (this.direction == Direction.UP ? 1 : -1));

      if (Variables.getInstance().getElevatorCooldown() > 0L && !SEPerm.COOLDOWN_BYPASS.hasPermission(this.player)) {
         Events.elevationCooldown.put(this.player, new Cooldown(this.player));
      }

      if (!vanished) {
         (this.direction == Direction.UP ? Variables.getInstance().getElevatorSoundUp() : Variables.getInstance().getElevatorSoundDown()).playSound(this.player);
         if (Variables.getInstance().isArrowEnabled() && Variables.getInstance().isArrowDestination() && !Variables.version("1.7")) {
            this.arrowCreator.arrow(this.player, this.direction);
         }
      }

      if (!Variables.version("1.7")) {
         ActionbarUtil.sendActionbar(this.player,
                 (this.direction == Direction.UP ? Variables.getInstance().getFloorUp() : Variables.getInstance().getFloorDown())
                         .replaceAll("%floor%", String.valueOf(this.playerData.getCurrentFloor()))
                         .replaceAll("%totalFloors%", String.valueOf(this.playerData.getTotalFloors())));
         TitleBarUtil.sendTitleBar(this.player, this.direction);
      }
   }

   private void tryLazyElevation() {
      int lazyFloor = this.elevatorCheck.getLazyFloor(this.direction, this.player.getLocation().getBlockY());
      if (lazyFloor == Integer.MIN_VALUE) return;

      Location destination = new Location(
              this.player.getWorld(),
              this.current.getBlockX() + 0.5D,
              lazyFloor + 1,
              this.current.getBlockZ() + 0.5D,
              this.player.getLocation().getYaw(),
              this.player.getLocation().getPitch()
      );

      elevate(destination, (int) Math.floor(this.current.distance(destination)));
      this.playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
   }

   private boolean playerMaxDist(Player player, int elevatorDistance) {
      for (int i = 0; i <= Math.abs(Variables.getInstance().getMinY()) + Variables.getInstance().getMaxY(); ++i) {
         if (SEPerm.DISTANCE.hasPermission(player, String.valueOf(i)) && i >= elevatorDistance) {
            return true;
         }
      }
      return false;
   }
}
