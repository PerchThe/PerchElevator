package me.perch.elevator;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.perch.elevator.commands.CommandPerchElevator;
import me.perch.elevator.elevator.ElevationHandler;
import me.perch.elevator.elevator.ElevatorBossBar;
import me.perch.elevator.files.MessagesFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ElevatorPlugin extends JavaPlugin {

   @SuppressWarnings("UnstableApiUsage")
   @Override
   public void onLoad() {
      this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
              event -> event.registrar().register(new CommandPerchElevator().constructCommand(), "PerchElevators Command")
      );
   }

   public void onEnable() {
      MessagesFile.getInstance();
      this.getServer().getPluginManager().registerEvents(new Events(this), this);
      this.getServer().getPluginManager().registerEvents(new ElevationHandler(), this);
      this.saveDefaultConfig();
      Variables.getInstance().loadVariables();
   }

   public void onDisable() {
      PlayerData.getPLAYER_DATA_MAP().values().forEach((playerData) -> {
         playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
      });
   }
}