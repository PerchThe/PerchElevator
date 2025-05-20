package com.live.bemmamin.elevator.elevator;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.Variables;
import com.live.bemmamin.elevator.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
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
      if (Variables.getInstance().isBossBarEnabled() && totalFloors >= 2) {
         this.currentFloor = currentFloor;
         this.totalFloors = Math.max(currentFloor, totalFloors);
         if (this.bossBar == null) {
            this.bossBar = Bukkit.createBossBar("", Variables.getInstance().getBossBarColor(), Variables.getInstance().getBossBarStyle(), new BarFlag[0]);
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {
               if (this.bossBar != null && this.player != null) {
                  this.bossBar.addPlayer(this.player);
               }
            }, 1L);
         }

         double progress = this.currentFloor != 0 && this.totalFloors != 0 ? (double)this.currentFloor / (double)this.totalFloors : 0.0D;
         if (!(progress < 0.0D) && !(progress > 1.0D)) {
            this.bossBar.setProgress(progress);
            this.bossBar.setTitle(MessageUtil.translate(Variables.getInstance().getBossBarMessage().replaceAll("%floor%", String.valueOf(this.currentFloor)).replaceAll("%totalFloors%", String.valueOf(this.totalFloors))));
         } else {
            this.hide();
         }
      }
   }

   public void hide() {
      if (this.bossBar != null) {
         this.bossBar.removePlayer(this.player);
         this.bossBar = null;
      }
   }

   public int getCurrentFloor() {
      return this.currentFloor;
   }

   public int getTotalFloors() {
      return this.totalFloors;
   }
}
