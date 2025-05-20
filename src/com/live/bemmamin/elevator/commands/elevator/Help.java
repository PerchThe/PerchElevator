package com.live.bemmamin.elevator.commands.elevator;

import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.utils.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.entity.Player;

public class Help extends AbstractSubCommand {
   Help(String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
   }

   public void onCommand(Player player, String[] args) {
      MessageUtil.send(player, "&9&m&l----------------------------------");
      MessageUtil.send(player, " &7&oBelow is a list of all Simple Elevator commands:");
      MessageUtil.sendJSON(player, " &7&l● &e/elevator [help]", "&eOpens this help page.", "/elevator");
      MessageUtil.sendJSON(player, " &7&l● &e/elevator create <combination> [distance]", "&eCreate an Elevator combination at your feet \nor a set if distance entered.\n\n&7&oClick to suggest command.", "/elevator create ", Action.SUGGEST_COMMAND);
      MessageUtil.sendJSON(player, " &7&l● &e/elevator list", "&eGet a list of all available Elevator combinations\n\n&7&oClick to autocomplete command.", "/elevator list");
      if (player != null) {
         MessageUtil.send(player, " &7&oHover over any command to see usage!");
      }

      MessageUtil.send(player, "&9&m&l----------------------------------");
   }
}
