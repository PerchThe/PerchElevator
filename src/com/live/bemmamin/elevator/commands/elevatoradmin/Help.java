package com.live.bemmamin.elevator.commands.elevatoradmin;

import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.utils.MessageUtil;
import org.bukkit.entity.Player;

public class Help extends AbstractSubCommand {
   Help(String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
   }

   public void onCommand(Player player, String[] args) {
      MessageUtil.send(player, "&9&m&l----------------------------------");
      MessageUtil.send(player, " &7&oBelow is a list of all Simple Elevator admin commands:");
      MessageUtil.sendJSON(player, " &7&l● &e/elevatoradmin [help]", "&eOpens this help page.", "/elevatoradmin");
      MessageUtil.sendJSON(player, " &7&l● &e/elevatoradmin reload", "&eMade some configuration changes?\n\n&7&oClick to reload all config files.", "/elevatoradmin reload");
      MessageUtil.sendJSON(player, " &7&l● &e/elevatoradmin info", "&eDisplays info about Simple Elevators.\n\n&7Click to run.", "/elevatoradmin info");
      if (player != null) {
         MessageUtil.send(player, " &7&oHover over any command to see usage!");
      }

      MessageUtil.send(player, "&9&m&l----------------------------------");
   }
}
