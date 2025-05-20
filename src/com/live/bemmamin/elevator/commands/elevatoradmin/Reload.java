package com.live.bemmamin.elevator.commands.elevatoradmin;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.Variables;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import org.bukkit.entity.Player;

public class Reload extends AbstractSubCommand {
   private final Main main;

   Reload(Main main, String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
      this.main = main;
   }

   public void onCommand(Player player, String[] args) {
      this.main.reloadConfig();
      this.main.saveDefaultConfig();
      this.main.onDisable();
      Variables.reload();
      MessagesFile.reload();
      MessageUtil.send(player, MessagesFile.getInstance().getConfigurationsReloaded());
   }
}
