package me.perch.elevator.elevator;

import me.perch.elevator.Direction;
import me.perch.elevator.Events;
import me.perch.elevator.PlayerData;
import me.perch.elevator.SEPerm;
import me.perch.elevator.Variables;
import me.perch.elevator.files.MessagesFile;
import me.perch.elevator.utils.ActionbarUtil;
import me.perch.elevator.utils.MessageUtil;
import me.perch.elevator.utils.SEMaterial;
import me.perch.elevator.utils.TitleBarUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
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
      int y;
      Location target_top;
      Location target_bottom;
      Location blockFeet;
      Location blockBody;
      Location destination;
      int elevatorDist;
      if (this.direction.equals(Direction.UP)) {
         for (y = currentBlockY + 1; y < Variables.getInstance().getMaxY(); ++y) {
            target_top = new Location(this.player.getWorld(), (double) this.current.getBlockX(), (double) y, (double) this.current.getBlockZ());
            target_bottom = new Location(this.player.getWorld(), (double) this.current.getBlockX(), (double) (y - 1), (double) this.current.getBlockZ());
            blockFeet = new Location(this.player.getWorld(), (double) target_top.getBlockX(), (double) (target_top.getBlockY() + 1), (double) target_top.getBlockZ());
            blockBody = new Location(this.player.getWorld(), (double) target_top.getBlockX(), (double) (target_top.getBlockY() + 2), (double) target_top.getBlockZ());
            if (this.combinationData.getTopMaterial() == null || SEMaterial.match(target_top.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getTopMaterial()) {
               destination = this.getPotentialDestination(target_top, target_bottom, blockFeet, blockBody);
               if (destination != null) {
                  elevatorDist = (int) Math.floor(this.current.distance(destination));
                  this.elevate(destination, elevatorDist);
                  return;
               }
            }
         }
         this.tryLazyElevation();
      } else if (this.direction.equals(Direction.DOWN)) {
         for (y = currentBlockY - 1; y > Variables.getInstance().getMinY(); --y) {
            target_top = new Location(this.player.getWorld(), (double) this.current.getBlockX(), (double) y, (double) this.current.getBlockZ());
            target_bottom = new Location(this.player.getWorld(), (double) this.current.getBlockX(), (double) (y - 1), (double) this.current.getBlockZ());
            blockFeet = new Location(this.player.getWorld(), (double) target_top.getBlockX(), (double) (target_top.getBlockY() + 1), (double) target_top.getBlockZ());
            blockBody = new Location(this.player.getWorld(), (double) target_top.getBlockX(), (double) (target_top.getBlockY() + 2), (double) target_top.getBlockZ());
            if (this.combinationData.getTopMaterial() == null || SEMaterial.match(target_top.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getTopMaterial()) {
               destination = this.getPotentialDestination(target_top, target_bottom, blockFeet, blockBody);
               if (destination != null) {
                  elevatorDist = (int) Math.floor(this.current.distance(destination));
                  this.elevate(destination, elevatorDist);
                  return;
               }
            }
         }
         this.tryLazyElevation();
      }
   }

   private Location getPotentialDestination(Location targetTop, Location target_bottom, Location blockFeet, Location blockBody) {
      if (this.combinationData.getBotMaterial() == null ||
              (SEMaterial.match(target_bottom.getBlock().getRelative(BlockFace.DOWN), false) == this.combinationData.getBotMaterial() &&
                      SEMaterial.match(target_bottom.getBlock().getRelative(BlockFace.DOWN), false).getData() == this.combinationData.getBotMaterial().getData())) {
         if (this.type != ElevatorType.PLATE &&
                 !SEMaterial.match(targetTop.getBlock().getRelative(BlockFace.DOWN), false).isSpecialType(SEMaterial.SpecialType.TRAPDOOR) &&
                 this.combinationData.getTopMaterial() != null &&
                 SEMaterial.match(targetTop.getBlock().getRelative(BlockFace.DOWN), false).getData() != this.combinationData.getTopMaterial().getData()) {
            return null;
         } else {
            if (this.type.equals(ElevatorType.BLOCK)) {
               if (Variables.getInstance().getIgnoreList() != null &&
                       (!Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockFeet.getBlock().getRelative(BlockFace.DOWN), true)) ||
                               !Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockBody.getBlock().getRelative(BlockFace.DOWN), true)))) {
                  return null;
               }
            } else if (this.type.equals(ElevatorType.NON_OCCLUDING) &&
                    Variables.getInstance().getIgnoreList() != null &&
                    !Variables.getInstance().getIgnoreList().contains(SEMaterial.match(blockFeet.getBlock().getRelative(BlockFace.DOWN), true))) {
               return null;
            }

            double offset;
            if (1.0D - (this.current.getY() - (double) ((int) this.current.getY())) != 1.0D) {
               offset = (double) (this.current.getY() < 0.0D ? 0 : 1) - (this.current.getY() - (double) ((int) this.current.getY()));
            } else if (this.type.equals(ElevatorType.PLATE)) {
               offset = 1.0D;
            } else {
               offset = 0.0D;
            }

            // Preserve player's X/Z offset within the block
            double xOffset = this.player.getLocation().getX() - this.player.getLocation().getBlockX();
            double zOffset = this.player.getLocation().getZ() - this.player.getLocation().getBlockZ();
            double destX = targetTop.getBlockX() + xOffset;
            double destZ = targetTop.getBlockZ() + zOffset;
            double destY = (double) targetTop.getBlockY() - offset;

            Location destFeet = new Location(this.player.getWorld(), destX, destY, destZ);
            Location destHead = destFeet.clone().add(0, 1, 0);

            // *** Passability check removed: allow any block ***
            // if (!destFeet.getBlock().isPassable() || !destHead.getBlock().isPassable()) {
            //    return null;
            // }

            return new Location(this.player.getWorld(), destX, destY, destZ, this.player.getLocation().getYaw(), this.player.getLocation().getPitch());
         }
      } else {
         return null;
      }
   }

   private void elevate(Location destination, int elevatorDist) {
      boolean allowableDistance = elevatorDist <= (this.combinationData.hasCustomMaxDistance() ? this.combinationData.getCustomMaxDistance() : Variables.getInstance().getMaxDistance());
      if (!allowableDistance && !SEPerm.DISTANCE_BYPASS.hasPermission(this.player) && !this.playerMaxDist(this.player, elevatorDist)) {
         MessageUtil.send(this.player, this.direction == Direction.UP ? MessagesFile.getInstance().getTooGreatDistanceUp() : MessagesFile.getInstance().getTooGreatDistanceDown());
      } else {
         boolean vanished = this.player.getMetadata("vanished").stream().anyMatch(MetadataValue::asBoolean);
         if (!vanished && Variables.getInstance().isArrowEnabled() && Variables.getInstance().isArrowCurrent() && !Variables.version("1.7")) {
            this.arrowCreator.arrow(this.player, this.direction);
         }

         ElevationHandler.elevate(this.player, destination);
         this.playerData.getElevatorBossBar().ifPresent((elevatorBossBar) -> {
            elevatorBossBar.display(elevatorBossBar.getCurrentFloor() + (this.direction == Direction.UP ? 1 : -1), elevatorBossBar.getTotalFloors());
         });
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
            ActionbarUtil.sendMessage(this.player, (this.direction == Direction.UP ? Variables.getInstance().getFloorUp() : Variables.getInstance().getFloorDown())
                    .replaceAll("%floor%", String.valueOf(this.playerData.getCurrentFloor()))
                    .replaceAll("%totalFloors%", String.valueOf(this.playerData.getTotalFloors())));
            TitleBarUtil.sendTitleBar(this.player, this.direction);
         }
      }
   }

   private void tryLazyElevation() {
      int lazyFloor = this.elevatorCheck.getLazyFloor(this.direction, this.player.getLocation().getBlockY());
      if (lazyFloor != -1) {
         // Preserve X/Z offset for lazy elevation as well
         double xOffset = this.player.getLocation().getX() - this.player.getLocation().getBlockX();
         double zOffset = this.player.getLocation().getZ() - this.player.getLocation().getBlockZ();
         Location destination = new Location(this.player.getWorld(),
                 (double) this.current.getBlockX() + xOffset,
                 (double) (lazyFloor + 1),
                 (double) this.current.getBlockZ() + zOffset,
                 this.player.getLocation().getYaw(),
                 this.player.getLocation().getPitch());
         // *** Passability check removed: allow any block ***
         // Location destHead = destination.clone().add(0, 1, 0);
         // if (!destination.getBlock().isPassable() || !destHead.getBlock().isPassable()) {
         //    return;
         // }
         this.elevate(destination, (int) Math.floor(this.current.distance(destination)));
         this.playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
      }
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