package me.perch.elevator.commands;

import org.bukkit.plugin.java.JavaPlugin;

public final class CommandRegistry {
   public static void registerCommands(JavaPlugin plugin) {
      plugin.getCommand("perchelevator").setExecutor(new PerchElevatorCommand(plugin));
   }
}