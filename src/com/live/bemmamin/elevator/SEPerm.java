package com.live.bemmamin.elevator;

import org.bukkit.permissions.Permissible;

public enum SEPerm {
   COMMANDS("elevator.commands"),
   COMMANDS_HELP("elevator.commands.help"),
   COMMANDS_CREATE("elevator.commands.create"),
   COMMANDS_LIST("elevator.commands.list"),
   ADMIN("elevator.admin"),
   UPDATE("elevator.update"),
   USE("elevator.use"),
   DISTANCE("elevator.distance"),
   COOLDOWN_BYPASS("elevator.bypass.cooldown"),
   DISTANCE_BYPASS("elevator.bypass.distance");

   private final String permissionString;

   private SEPerm(String permissionString) {
      this.permissionString = permissionString;
   }

   public boolean hasPermission(Permissible permissible) {
      return this.hasPermission(permissible, (String)null);
   }

   public boolean hasPermission(Permissible permissible, String extension) {
      return permissible == null || permissible.hasPermission(extension != null && !extension.isEmpty() ? this.permissionString + "." + extension : this.permissionString);
   }

   public String getPermissionString() {
      return this.permissionString;
   }
}
