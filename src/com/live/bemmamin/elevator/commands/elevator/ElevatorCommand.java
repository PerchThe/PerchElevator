package com.live.bemmamin.elevator.commands.elevator;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.commands.AbstractParentCommand;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import org.bukkit.entity.Player;

public class ElevatorCommand extends AbstractParentCommand {
   public ElevatorCommand(Main main, String commandName) {
      super(main, commandName, new Help("help", SEPerm.COMMANDS_HELP, false, new String[]{"h"}), new Create("create", SEPerm.COMMANDS_CREATE, true, new String[]{"cr", "c"}), new List("list", SEPerm.COMMANDS_LIST, false, new String[]{"li", "l"}));
   }

   public void onCommand(Player player, String[] args) {
      if (SEPerm.COMMANDS_HELP.hasPermission(player)) {
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
