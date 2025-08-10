package me.perch.elevator.utils;

import me.perch.elevator.Variables;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class ActionbarUtil {

   public static void sendActionbar(Player player, String message) {
      Variables variables = Variables.getInstance();
      if (!variables.isAbEnabled()) return;

      player.sendActionBar(Component.text(message));

   }
}