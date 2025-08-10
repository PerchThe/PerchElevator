package me.perch.elevator.elevator;

import me.perch.elevator.ElevatorPlugin;
import me.perch.elevator.Events;
import me.perch.elevator.Variables;
import java.time.Instant;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown {
   private final Player player;
   private final long targetTime;

   public Cooldown(Player player) {
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
      }).runTaskLater(ElevatorPlugin.getPlugin(ElevatorPlugin.class), Variables.getInstance().getElevatorCooldown());
   }

   public long getTimeLeft() {
      long timeLeft = this.targetTime - Instant.now().getEpochSecond();
      return timeLeft <= 0L ? 1L : timeLeft;
   }
}
