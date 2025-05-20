package com.live.bemmamin.elevator;

import com.live.bemmamin.elevator.commands.CommandRegistry;
import com.live.bemmamin.elevator.elevator.ElevationHandler;
import com.live.bemmamin.elevator.elevator.ElevatorBossBar;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   private UpdateChecker updateChecker;

   public void onEnable() {
      try {
         new Metrics(this);
      } catch (NoClassDefFoundError var2) {
      }

      MessagesFile.getInstance();
      this.getServer().getPluginManager().registerEvents(new Events(this), this);
      this.getServer().getPluginManager().registerEvents(new ElevationHandler(), this);
      this.saveDefaultConfig();
      Variables.getInstance().loadVariables();
      this.updateChecker = new UpdateChecker(this);
      if (Variables.getInstance().isUpdateChecker()) {
         this.updateChecker.checkForUpdate();
      }

      CommandRegistry.registerCommands(this);
   }

   public void onDisable() {
      PlayerData.getPLAYER_DATA_MAP().values().forEach((playerData) -> {
         playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
      });
   }


   public UpdateChecker getUpdateChecker() {
      return this.updateChecker;
   }
}
