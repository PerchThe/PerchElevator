package me.perch.elevator;

import me.perch.elevator.commands.CommandRegistry;
import me.perch.elevator.elevator.ElevationHandler;
import me.perch.elevator.elevator.ElevatorBossBar;
import me.perch.elevator.files.MessagesFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ElevatorPlugin extends JavaPlugin {

   public void onEnable() {
      MessagesFile.getInstance();
      this.getServer().getPluginManager().registerEvents(new Events(this), this);
      this.getServer().getPluginManager().registerEvents(new ElevationHandler(), this);
      this.saveDefaultConfig();
      Variables.getInstance().loadVariables();
      CommandRegistry.registerCommands(this);
   }

   public void onDisable() {
      PlayerData.getPLAYER_DATA_MAP().values().forEach((playerData) -> {
         playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
      });
   }
}