package com.live.bemmamin.elevator.commands;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.commands.elevator.ElevatorCommand;
import com.live.bemmamin.elevator.commands.elevatoradmin.AdminCommand;
import java.util.stream.Stream;

public final class CommandRegistry {
   public static void registerCommands(Main main) {
      Stream.of(new ElevatorCommand(main, "elevator"), new AdminCommand(main, "elevatoradmin")).forEach((command) -> {
         main.getCommand(command.getCommandName()).setExecutor(command);
      });
   }
}
