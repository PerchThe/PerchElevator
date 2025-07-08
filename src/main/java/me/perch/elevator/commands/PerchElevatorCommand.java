package me.perch.elevator.commands;

import me.perch.elevator.Variables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PerchElevatorCommand implements CommandExecutor {

   private final JavaPlugin plugin;

   public PerchElevatorCommand(JavaPlugin plugin) {
      this.plugin = plugin;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
         if (sender.hasPermission("perchelevator.reload")) {
            Variables.reload();
            sender.sendMessage("§aPerchElevator config reloaded.");
         } else {
            sender.sendMessage("§cYou do not have permission to reload PerchElevator.");
         }
         return true;
      }
      sender.sendMessage("§eUsage: /perchelevator reload");
      return true;
   }
}