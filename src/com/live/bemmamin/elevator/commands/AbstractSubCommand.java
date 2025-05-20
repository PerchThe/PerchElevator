package com.live.bemmamin.elevator.commands;

import com.live.bemmamin.elevator.SEPerm;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;

public abstract class AbstractSubCommand extends AbstractCommand {
   private final Set<String> aliases;
   private final SEPerm permission;
   private final boolean playerOnly;

   protected AbstractSubCommand(String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name.toLowerCase());
      this.permission = permission;
      this.playerOnly = playerOnly;
      this.aliases = new HashSet(Arrays.asList(aliases));
   }

   public List<String> onTabComplete(Player player, String[] args) {
      return Collections.emptyList();
   }

   public Set<String> getAliases() {
      return this.aliases;
   }

   public SEPerm getPermission() {
      return this.permission;
   }

   public boolean isPlayerOnly() {
      return this.playerOnly;
   }
}
