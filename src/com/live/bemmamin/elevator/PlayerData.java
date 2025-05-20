package com.live.bemmamin.elevator;

import com.live.bemmamin.elevator.elevator.ElevatorBossBar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class PlayerData {
   private static final Map<UUID, PlayerData> PLAYER_DATA_MAP = new HashMap();
   private final ElevatorBossBar elevatorBossBar;
   private boolean isDelayed = false;
   private int currentFloor;
   private int totalFloors;
   private boolean sendInCombat = true;

   private PlayerData(Player player) {
      this.elevatorBossBar = new ElevatorBossBar(player);
   }

   public static PlayerData getPlayerData(Player player) {
      return getOptionalPlayerData(player, false).orElse(null);
   }

   static PlayerData getPlayerData(Player player, boolean createIfNotPresent) {
      return getOptionalPlayerData(player, createIfNotPresent).orElse(null);
   }

   static Optional<PlayerData> getOptionalPlayerData(Player player) {
      return getOptionalPlayerData(player, false);
   }

   private static Optional<PlayerData> getOptionalPlayerData(Player player, boolean createIfNotPresent) {
      return createIfNotPresent ? Optional.of(PLAYER_DATA_MAP.computeIfAbsent(player.getUniqueId(), (k) -> {
         return new PlayerData(player);
      })) : Optional.ofNullable(PLAYER_DATA_MAP.get(player.getUniqueId()));
   }

   static void removePlayerData(Player player) {
      PLAYER_DATA_MAP.remove(player.getUniqueId());
   }

   public Optional<ElevatorBossBar> getElevatorBossBar() {
      return Optional.of(this.elevatorBossBar);
   }

   void startInCombatCooldown() {
      this.sendInCombat = false;
      (new BukkitRunnable() {
         public void run() {
            PlayerData.this.sendInCombat = true;
         }
      }).runTaskLater(Main.getPlugin(Main.class), 20L);
   }

   public boolean isDelayed() {
      return this.isDelayed;
   }

   public int getCurrentFloor() {
      return this.currentFloor;
   }

   public int getTotalFloors() {
      return this.totalFloors;
   }

   public boolean isSendInCombat() {
      return this.sendInCombat;
   }

   public void setDelayed(boolean isDelayed) {
      this.isDelayed = isDelayed;
   }

   public void setCurrentFloor(int currentFloor) {
      this.currentFloor = currentFloor;
   }

   public void setTotalFloors(int totalFloors) {
      this.totalFloors = totalFloors;
   }

   public void setSendInCombat(boolean sendInCombat) {
      this.sendInCombat = sendInCombat;
   }

   public static Map<UUID, PlayerData> getPLAYER_DATA_MAP() {
      return PLAYER_DATA_MAP;
   }
}
