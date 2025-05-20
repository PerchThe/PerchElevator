package com.live.bemmamin.elevator.commands.elevator;

import com.live.bemmamin.elevator.Combination;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.Variables;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.utils.MessageUtil;
import java.util.Iterator;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.entity.Player;

public class List extends AbstractSubCommand {
   List(String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
   }

   public void onCommand(Player player, String[] args) {
      MessageUtil.send(player, "&9&m&l---------------------------------------");
      MessageUtil.send(player, " &7&oBelow is a list of all Simple Elevator combinations:");
      boolean canCreate = SEPerm.COMMANDS_CREATE.hasPermission(player);
      int i = 1;

      for(Iterator var5 = Variables.getInstance().getCombinations().iterator(); var5.hasNext(); ++i) {
         Combination combo = (Combination)var5.next();
         String[] split = combo.getComboString().split(", ");
         MessageUtil.sendJSON(player, "  &6" + i + ". &a" + split[0] + (split.length == 2 ? "&6, &a" + split[1] : ""), canCreate ? "&7Click to suggest create command" : null, canCreate ? "/elevator create " + i + " " : null, Action.SUGGEST_COMMAND);
      }

      if (player != null && canCreate) {
         MessageUtil.send(player, "&7&oClick on a combination to suggest the create command!");
      }

      MessageUtil.send(player, "&9&m&l---------------------------------------");
   }
}
