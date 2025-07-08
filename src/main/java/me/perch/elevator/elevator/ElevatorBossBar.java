package me.perch.elevator.elevator;

import me.perch.elevator.Variables;
import me.perch.elevator.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class ElevatorBossBar {
   private final Player player;
   private BossBar bossBar;
   private int currentFloor;
   private int totalFloors;

   public ElevatorBossBar(Player player) {
      this.player = player;
   }

   public void display(int currentFloor, int totalFloors) {
      if (!Variables.getInstance().isBossBarEnabled() || totalFloors < 2) {
         this.hide();
         return;
      }

      this.currentFloor = currentFloor;
      this.totalFloors = totalFloors;

      if (this.bossBar == null) {
         this.bossBar = Bukkit.createBossBar(
                 "",
                 Variables.getInstance().getBossBarColor(),
                 Variables.getInstance().getBossBarStyle()
         );
      }

      if (this.player != null && this.player.isOnline() && !this.bossBar.getPlayers().contains(this.player)) {
         this.bossBar.addPlayer(this.player);
      }

      double progress = (this.currentFloor > 0 && this.totalFloors > 0)
              ? (double) this.currentFloor / (double) this.totalFloors
              : 0.0D;

      progress = Math.max(0.0D, Math.min(1.0D, progress));

      this.bossBar.setProgress(progress);
      this.bossBar.setTitle(MessageUtil.translate(
              Variables.getInstance().getBossBarMessage()
                      .replace("%floor%", String.valueOf(this.currentFloor))
                      .replace("%totalFloors%", String.valueOf(this.totalFloors))
      ));
      this.bossBar.setVisible(true);

   }

   public void hide() {
      if (this.bossBar != null) {
         this.bossBar.setVisible(false);
         this.bossBar.removePlayer(this.player);
      }
   }

   public int getCurrentFloor() {
      return this.currentFloor;
   }

   public int getTotalFloors() {
      return this.totalFloors;
   }
}