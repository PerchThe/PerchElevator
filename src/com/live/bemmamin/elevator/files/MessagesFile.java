package com.live.bemmamin.elevator.files;

import java.io.IOException;

public final class MessagesFile extends AbstractFile {
   private static MessagesFile file;
   private String prefix;
   private String invalidPermission;
   private String playerOnly;
   private String invalidArguments;
   private String invalidCreateInput;
   private String invalidMaterialInput;
   private String unknownCombination;
   private String invalidElevatorDestination;
   private String elevatorDestinationOccupied;
   private String elevatorDestinationTooGreat;
   private String elevatorPlaced;
   private String elevatorNotPlaced;
   private String disabledWorld;
   private String noCommands;
   private String tooGreatDistanceUp;
   private String tooGreatDistanceDown;
   private String inCombat;
   private String enemyFaction;
   private String configurationsReloaded;

   private MessagesFile() {
      super("messages.yml");
      this.setDefaults();
   }

   public static MessagesFile getInstance() {
      file = file == null ? new MessagesFile() : file;
      return file;
   }

   public static void reload() {
      file = new MessagesFile();
      file.setDefaults();
   }

   public void setDefaults() {
      this.setHeader(new String[]{"This is the Message file for all Simple Elevator messages.", "", "All messages are fully customizable and support color codes, formatting and ASCII symbols.", "HEX color codes are supported in 1.16+. The format is &#XXXXXX e.g. '&#XXXXXXYour message'", "Set the Prefix and use %prefix% to add the corresponding prefix to a message.", "Prepend any message with <ActionBar> to send it as an ActionBar message.", "Leave a message blank ('') to disable it.", "", "You can also create messages with Hover and Click events. Syntax options: (Space between comma and quote is NOT allowed)", " - [\"Message\",\"/Command\"]", " - [\"Message\",\"Hover\"]", " - [\"Message\",\"/Command\",\"Hover\"]", " - [\"Message\",\"/Command\",\"Suggest\"]", " - [\"Message\",\"/Command\",\"Hover\",\"Suggest\"]", "You can add as many events to a message as you want. Example:", "'%prefix% &cInvalid arguments! [\"&c&n&oHelp\",\"/se help\",\"&aClick to get help!\"]'", "The \"Suggest\" tag is used if the click event should suggest the command. Default is Run."});
      this.prefix = (String)this.add("Prefix", "&7[&eSimple Elevators&7]");
      this.invalidPermission = (String)this.add("Messages.General.InvalidPermission", "%prefix% &cYou do not have permission to do this!");
      this.playerOnly = (String)this.add("Messages.General.PlayerOnly", "%prefix% &cCommand can only be used as a Player!");
      this.noCommands = (String)this.add("Messages.General.NoCommands", "Unknown command. Type \"/help\" for help.");
      this.invalidArguments = (String)this.add("Messages.General.InvalidArguments", "%prefix% &cInvalid arguments! [\"&c&n&oHelp\",\"/elevator help\",\"&aClick to get help!\"]");
      this.invalidCreateInput = (String)this.add("Messages.General.InvalidCreateInput", "%prefix% &cInvalid combination and/or distance input!");
      this.invalidMaterialInput = (String)this.add("Messages.General.InvalidMaterialInput", "%prefix% &cCombination contains (an) invalid material(s).");
      this.invalidElevatorDestination = (String)this.add("Messages.General.InvalidElevatorDestination", "%prefix% &cCannot place destination Elevator!");
      this.elevatorDestinationOccupied = (String)this.add("Messages.General.ElevatorDestinationOccupied", "%prefix% &cDestination location occupied! Remove blocks at Destination.");
      this.elevatorDestinationTooGreat = (String)this.add("Messages.General.ElevatorDestinationTooGreat", "%prefix% &cDestination Elevator distance too great! Max: %maxDistance% blocks");
      this.unknownCombination = (String)this.add("Messages.General.UnknownCombination", "%prefix% &cUnknown combination input! Combinations range from (1 - %combinations%)");
      this.elevatorPlaced = (String)this.add("Messages.General.ElevatorPlaced", "%prefix% &aElevator successfully placed!");
      this.elevatorNotPlaced = (String)this.add("Messages.General.ElevatorNotPlaced", "%prefix% &cCannot place an Elevator here!");
      this.disabledWorld = (String)this.add("Messages.General.ElevatorNotPlaced", "%prefix% &cElevators are not enabled in this world!");
      this.tooGreatDistanceUp = (String)this.add("Messages.General.TooGreatDistanceUp", "%prefix% &cThe distance to the next floor is too great!");
      this.tooGreatDistanceDown = (String)this.add("Messages.General.TooGreatDistanceDown", "%prefix% &cThe distance to the previous floor is too great!");
      this.inCombat = (String)this.add("Messages.CombatLogX.InCombat", "%prefix% &cYou can't use jumppads when in combat!");
      this.enemyFaction = (String)this.add("Messages.FactionsUUID.EnemyFaction", "%prefix% &cYou can't use elevators in enemy territory!");
      this.configurationsReloaded = (String)this.add("Messages.Admin.ConfigurationsReloaded", "%prefix% &aConfiguration files successfully reloaded!");
      this.save();
   }

   void save() {
      try {
         this.getConfig().save(this.getFile());
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getInvalidPermission() {
      return this.invalidPermission;
   }

   public String getPlayerOnly() {
      return this.playerOnly;
   }

   public String getInvalidArguments() {
      return this.invalidArguments;
   }

   public String getInvalidCreateInput() {
      return this.invalidCreateInput;
   }

   public String getInvalidMaterialInput() {
      return this.invalidMaterialInput;
   }

   public String getUnknownCombination() {
      return this.unknownCombination;
   }

   public String getInvalidElevatorDestination() {
      return this.invalidElevatorDestination;
   }

   public String getElevatorDestinationOccupied() {
      return this.elevatorDestinationOccupied;
   }

   public String getElevatorDestinationTooGreat() {
      return this.elevatorDestinationTooGreat;
   }

   public String getElevatorPlaced() {
      return this.elevatorPlaced;
   }

   public String getElevatorNotPlaced() {
      return this.elevatorNotPlaced;
   }

   public String getDisabledWorld() {
      return this.disabledWorld;
   }

   public String getNoCommands() {
      return this.noCommands;
   }

   public String getTooGreatDistanceUp() {
      return this.tooGreatDistanceUp;
   }

   public String getTooGreatDistanceDown() {
      return this.tooGreatDistanceDown;
   }

   public String getInCombat() {
      return this.inCombat;
   }

   public String getEnemyFaction() {
      return this.enemyFaction;
   }

   public String getConfigurationsReloaded() {
      return this.configurationsReloaded;
   }
}
