package com.live.bemmamin.elevator;

import com.live.bemmamin.elevator.utils.MessageUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateChecker implements Listener {
   private final int ID = 44462;
   private final JavaPlugin javaPlugin;
   private final String localPluginVersion;
   private String spigotPluginVersion;

   UpdateChecker(Main main) {
      this.javaPlugin = main;
      this.localPluginVersion = main.getDescription().getVersion();
   }

   void checkForUpdate() {
      (new BukkitRunnable() {
         public void run() {
            Bukkit.getScheduler().runTaskAsynchronously(UpdateChecker.this.javaPlugin, () -> {
               try {
                  HttpsURLConnection connection = (HttpsURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=44462")).openConnection();
                  connection.setRequestMethod("GET");
                  UpdateChecker.this.spigotPluginVersion = (new BufferedReader(new InputStreamReader(connection.getInputStream()))).readLine();
               } catch (IOException var2) {
                  Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUpdate checker failed!"));
                  var2.printStackTrace();
                  this.cancel();
                  return;
               }

               if (!UpdateChecker.this.isLatestVersion()) {
                  MessageUtil.send((Player)null, "&7[&eSimple Elevators&7] &fA new update is available at:");
                  MessageUtil.send((Player)null, "&bhttps://www.spigotmc.org/resources/44462/updates");
                  Bukkit.getScheduler().runTask(UpdateChecker.this.javaPlugin, () -> {
                     Bukkit.getPluginManager().registerEvents(new Listener() {
                        @EventHandler(
                           priority = EventPriority.MONITOR
                        )
                        public void onPlayerJoin(PlayerJoinEvent event) {
                           Player player = event.getPlayer();
                           if (SEPerm.UPDATE.hasPermission(player)) {
                              MessageUtil.send(event.getPlayer(), "&7[&eSimple Elevators&7] &fA new update is available at:");
                              MessageUtil.send(event.getPlayer(), "&bhttps://www.spigotmc.org/resources/44462/updates");
                           }

                        }
                     }, UpdateChecker.this.javaPlugin);
                  });
                  this.cancel();
               }
            });
         }
      }).runTaskTimer(this.javaPlugin, 0L, 12000L);
   }

   private boolean isLatestVersion() {
      try {
         int[] local = Arrays.stream(this.localPluginVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
         int[] spigot = Arrays.stream(this.spigotPluginVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
         return (Boolean)IntStream.range(0, local.length).filter((i) -> {
            return local[i] != spigot[i];
         }).limit(1L).mapToObj((i) -> {
            return local[i] >= spigot[i];
         }).findFirst().orElse(true);
      } catch (NumberFormatException var3) {
         return this.localPluginVersion.equals(this.spigotPluginVersion);
      }
   }

   public String getLocalPluginVersion() {
      return this.localPluginVersion;
   }

   public String getSpigotPluginVersion() {
      return this.spigotPluginVersion;
   }
}
