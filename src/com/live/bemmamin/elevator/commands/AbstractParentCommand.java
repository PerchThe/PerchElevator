package com.live.bemmamin.elevator.commands;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.files.MessagesFile;
import com.live.bemmamin.elevator.utils.MessageUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public abstract class AbstractParentCommand extends AbstractCommand implements TabExecutor {
   private final Main main;
   private final Set<AbstractSubCommand> subCommands;

   protected AbstractParentCommand(Main main, String commandName, AbstractSubCommand... subCommands) {
      super(commandName);
      this.main = main;
      this.subCommands = new HashSet(Arrays.asList(subCommands));
   }

   public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
      Player player = commandSender instanceof Player ? (Player)commandSender : null;
      if (!SEPerm.COMMANDS.hasPermission(player)) {
         MessageUtil.send(player, MessagesFile.getInstance().getNoCommands());
         return true;
      } else {
         if (args.length != 0) {
            Iterator var6 = this.subCommands.iterator();

            while(var6.hasNext()) {
               AbstractSubCommand subCommand = (AbstractSubCommand)var6.next();
               if (subCommand.getCommandName().equalsIgnoreCase(args[0]) || !subCommand.getAliases().stream().noneMatch((alias) -> {
                  return alias.equalsIgnoreCase(args[0]);
               })) {
                  if (!subCommand.getPermission().hasPermission(player)) {
                     MessageUtil.send(player, MessagesFile.getInstance().getInvalidPermission());
                     return true;
                  } else {
                     if (player == null && subCommand.isPlayerOnly()) {
                        MessageUtil.send((Player)null, MessagesFile.getInstance().getPlayerOnly());
                     } else {
                        subCommand.onCommand(player, args);
                     }

                     return true;
                  }
               }
            }
         }

         this.onCommand(player, args);
         return true;
      }
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length > 1) {
         return this.subCommands.stream().filter((abstractSubCommand) ->
                 abstractSubCommand.getPermission().hasPermission(sender) &&
                         (abstractSubCommand.getCommandName().equalsIgnoreCase(args[0]) ||
                                 abstractSubCommand.getAliases().stream().anyMatch((a) -> a.equalsIgnoreCase(args[0])))
         ).findFirst().map((abstractSubCommand) ->
                 abstractSubCommand.onTabComplete(sender instanceof Player ? (Player)sender : null, Arrays.copyOfRange(args, 1, args.length))
         ).orElse(null);
      } else {
         return SEPerm.COMMANDS.hasPermission(sender) ? this.subCommands.stream().filter((abstractSubCommand) ->
                 abstractSubCommand.getPermission().hasPermission(sender)
         ).map(AbstractCommand::getCommandName).filter((name) ->
                 name.startsWith(args[0].toLowerCase())
         ).collect(Collectors.toList()) : null;
      }
   }

   public Main getMain() {
      return this.main;
   }

   public Set<AbstractSubCommand> getSubCommands() {
      return this.subCommands;
   }
}
