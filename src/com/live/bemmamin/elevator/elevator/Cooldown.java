package com.live.bemmamin.elevator.elevator;

import com.live.bemmamin.elevator.Events;
import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.Variables;
import java.time.Instant;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown {
   private final Player player;
   private final long targetTime;

   Cooldown(Player player) {
      this.player = player;
      this.targetTime = Instant.now().getEpochSecond() + Variables.getInstance().getElevatorCooldown() / 20L;
      this.startCooldown();
   }

   private void startCooldown() {
      (new BukkitRunnable() {
         public void run() {
            this.cancel();
            Events.elevationCooldown.remove(Cooldown.this.player);
         }
      }).runTaskLater(Main.getPlugin(Main.class), Variables.getInstance().getElevatorCooldown());
   }

   public long getTimeLeft() {
      long timeLeft = this.targetTime - Instant.now().getEpochSecond();
      return timeLeft <= 0L ? 1L : timeLeft;
   }
}
