package com.live.bemmamin.elevator.commands.elevatoradmin;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.commands.AbstractParentCommand;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import org.bukkit.entity.Player;

public class AdminCommand extends AbstractParentCommand {
   public AdminCommand(Main main, String commandName) {
      super(main, commandName, new Help("help", SEPerm.ADMIN, false, new String[]{"h"}), new Reload(main, "reload", SEPerm.ADMIN, false, new String[]{"rel", "r"}), new Info(main, "info", SEPerm.ADMIN, false, new String[]{"inf", "i"}));
   }

   public void onCommand(Player player, String[] args) {
      if (SEPerm.ADMIN.hasPermission(player)) {
         this.getSubCommands().stream().filter((subCommand) -> {
            return subCommand.getCommandName().equalsIgnoreCase("help");
         }).findFirst().ifPresent((subCommand) -> {
            subCommand.onCommand(player, args);
         });
      } else {
         MessageUtil.send(player, MessagesFile.getInstance().getInvalidPermission());
      }

   }
}
