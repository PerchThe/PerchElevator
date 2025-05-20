package com.live.bemmamin.elevator.commands.elevator;

import com.live.bemmamin.elevator.Combination;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.Variables;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import com.live.bemmamin.elevator.utils.SEMaterial;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class Create extends AbstractSubCommand {
   Create(String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
   }

   public void onCommand(Player player, String[] args) {
      if (args.length != 2 && args.length != 3) {
         MessageUtil.send(player, MessagesFile.getInstance().getInvalidArguments());
      } else if (args.length == 2 && !args[1].matches("^-?[1-9]\\d*$")) {
         MessageUtil.send(player, MessagesFile.getInstance().getInvalidCreateInput());
      } else if (args.length != 3 || args[1].matches("^-?[1-9]\\d*$") && args[2].matches("^-?[1-9]\\d*$")) {
         if (Integer.parseInt(args[1]) <= Variables.getInstance().getCombinations().size() && Integer.parseInt(args[1]) > 0) {
            Location pLoc = player.getLocation();
            java.util.List<String> worldList = Variables.getInstance().getEnabledWorlds();
            if (!worldList.contains(pLoc.getWorld().getName()) && !worldList.isEmpty()) {
               MessageUtil.send(player, MessagesFile.getInstance().getDisabledWorld());
            } else {
               Combination combo = (Combination)Variables.getInstance().getCombinations().get(Integer.parseInt(args[1]) - 1);
               if (combo == null) {
                  MessageUtil.send(player, MessagesFile.getInstance().getInvalidMaterialInput());
                  return;
               }

               int offset = combo.getTopComboMaterial() != null && combo.getTopComboMaterial().isSpecialType(SEMaterial.SpecialType.CARPET, SEMaterial.SpecialType.PRESSURE_PLATE) ? -1 : 0;
               boolean canPlaceElevatorAtFeet = Collections.disjoint(Variables.getInstance().getCreateReplaceBlacklist(), Arrays.asList(SEMaterial.match(pLoc.getBlock().getRelative(BlockFace.DOWN, 1 + offset), false), SEMaterial.match(pLoc.getBlock().getRelative(BlockFace.DOWN, 2 + offset), false)));
               if (args.length == 3 && canPlaceElevatorAtFeet) {
                  int newElevatorDist = -Integer.parseInt(args[2]);
                  Location newEDistYPos = pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + 1 + offset).getLocation();
                  int elevatorDist = (int)Math.floor(pLoc.getBlock().getRelative(BlockFace.DOWN, offset).getLocation().distance(newEDistYPos));
                  if (newEDistYPos.getY() > (double)Variables.getInstance().getMaxY() || newEDistYPos.getY() < (double)Variables.getInstance().getMinY() || elevatorDist < 3) {
                     MessageUtil.send(player, MessagesFile.getInstance().getInvalidElevatorDestination());
                     return;
                  }

                  if (elevatorDist > Variables.getInstance().getMaxDistance() && !SEPerm.DISTANCE_BYPASS.hasPermission(player)) {
                     MessageUtil.send(player, MessagesFile.getInstance().getElevatorDestinationTooGreat().replaceAll("%maxDistance%", String.valueOf(Variables.getInstance().getMaxDistance())));
                     return;
                  }

                  java.util.List<Block> destinationElevatorBlocks = Arrays.asList(pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist - 1 + offset), pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + offset), pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + 1 + offset), pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + 2 + offset));
                  if (!Variables.getInstance().isCreateReplace() && !destinationElevatorBlocks.stream().allMatch((block) -> {
                     return block.getType() == Material.AIR;
                  })) {
                     MessageUtil.send(player, MessagesFile.getInstance().getElevatorDestinationOccupied());
                     return;
                  }

                  if (Variables.getInstance().isCreateReplace() && !Collections.disjoint(Variables.getInstance().getCreateReplaceBlacklist(), (Collection)destinationElevatorBlocks.stream().map((block) -> {
                     return SEMaterial.match(block, false);
                  }).collect(Collectors.toSet()))) {
                     MessageUtil.send(player, MessagesFile.getInstance().getElevatorNotPlaced());
                     return;
                  }

                  pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist - 1 + offset).setType(Material.AIR);
                  pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + offset).setType(Material.AIR);
                  this.updateBlock(combo.getTopComboMaterial(), pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + 1 + offset).getState());
                  this.updateBlock(combo.getBotComboMaterial(), pLoc.getBlock().getRelative(BlockFace.DOWN, newElevatorDist + 2 + offset).getState());
               }

               if (pLoc.getY() >= (double)Variables.getInstance().getMinY() && pLoc.getY() <= (double)Variables.getInstance().getMaxY() && canPlaceElevatorAtFeet) {
                  this.updateBlock(combo.getTopComboMaterial(), pLoc.getBlock().getRelative(BlockFace.DOWN, 1 + offset).getState());
                  this.updateBlock(combo.getBotComboMaterial(), pLoc.getBlock().getRelative(BlockFace.DOWN, 2 + offset).getState());
                  MessageUtil.send(player, MessagesFile.getInstance().getElevatorPlaced());
               } else {
                  MessageUtil.send(player, MessagesFile.getInstance().getElevatorNotPlaced());
               }
            }

         } else {
            MessageUtil.send(player, MessagesFile.getInstance().getUnknownCombination().replaceAll("%combinations%", String.valueOf(Variables.getInstance().getCombinations().size())));
         }
      } else {
         MessageUtil.send(player, MessagesFile.getInstance().getInvalidCreateInput());
      }
   }

   private void updateBlock(SEMaterial material, BlockState state) {
      if (material != null) {
         state.setType(material.getMaterial());
         state.setRawData(material.getData());
         state.update(true);
      }
   }

   public java.util.List<String> onTabComplete(Player player, String[] args) {
      return args.length == 1 ? (java.util.List)IntStream.range(1, Variables.getInstance().getCombinations().size() + 1).mapToObj(String::valueOf).collect(Collectors.toList()) : Collections.emptyList();
   }
}
