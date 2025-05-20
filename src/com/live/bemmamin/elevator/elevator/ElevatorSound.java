package com.live.bemmamin.elevator.elevator;

import com.live.bemmamin.elevator.utils.MessageUtil;
import com.live.bemmamin.elevator.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ElevatorSound {
   private final boolean enabled;
   private final boolean world;
   private final float volume;
   private final float pitch;
   private final String sound;

   public ElevatorSound(ConfigurationSection configurationSection, int defaultPitch) {
      this.enabled = configurationSection == null || configurationSection.getBoolean("enabled", true);
      this.world = configurationSection != null && configurationSection.getBoolean("world", false);
      this.sound = configurationSection != null ? configurationSection.getString("sound", "ORB_PICKUP") : "ORB_PICKUP";
      this.pitch = configurationSection != null ? (float)configurationSection.getDouble("pitch", (double)defaultPitch) : (float)defaultPitch;
      this.volume = configurationSection != null ? (float)configurationSection.getDouble("volume", 1.0D) : 1.0F;
   }

   void playSound(Player player) {
      if (this.enabled) {
         try {
            SoundUtil.valueOf(this.sound).playSound(player, this.volume, this.pitch, this.world);
         } catch (NullPointerException | IllegalArgumentException var5) {
            try {
               if (this.world) {
                  player.getWorld().playSound(player.getLocation(), Sound.valueOf(this.sound.toUpperCase()), this.volume, this.pitch);
               } else {
                  player.playSound(player.getLocation(), Sound.valueOf(this.sound.toUpperCase()), this.volume, this.pitch);
               }
            } catch (NullPointerException | IllegalArgumentException var4) {
               Bukkit.getConsoleSender().sendMessage(MessageUtil.translate("&7[&cSimple Elevators&7] &cConfig contains invalid sound enum."));
            }
         }

      }
   }
}
