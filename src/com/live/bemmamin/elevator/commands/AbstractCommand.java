package com.live.bemmamin.elevator.commands;

import org.bukkit.entity.Player;

abstract class AbstractCommand {
   private final String commandName;

   public abstract void onCommand(Player var1, String[] var2);

   public String getCommandName() {
      return this.commandName;
   }

   public AbstractCommand(String commandName) {
      this.commandName = commandName;
   }
}
