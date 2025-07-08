package me.perch.elevator.elevator;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.perch.elevator.Main;
import me.perch.elevator.Variables;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class ElevationHandler implements Listener {
   private static final Set<UUID> TEMP_PROTECTED = new HashSet();
   private static final ElevationHandler.SunLightPlugin sunLightPlugin = new ElevationHandler.SunLightPlugin();

   static void elevate(Player player, Location destination) {
      if (Variables.getInstance().isTemporaryInvulnerable()) {
         TEMP_PROTECTED.add(player.getUniqueId());
         Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {
            TEMP_PROTECTED.remove(player.getUniqueId());
         }, 2L);
      }

      if (Variables.getInstance().isBackCompatibility()) {
         List<Location> preTeleportResult = preTeleport(player);
         player.teleport(destination);
         postTeleport(player, preTeleportResult);
      } else {
         player.teleport(destination);
      }

   }

   private static List<Location> preTeleport(Player player) {
      Location essentialsLastLocation = null;
      if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")) {
         try {
            User user = ((Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials")).getUser(player);
            essentialsLastLocation = user != null && user.getLastLocation() != null ? user.getLastLocation().clone() : null;
         } catch (NullPointerException var6) {
         }
      }


      Location sunlightLastLocation = null;
      if (sunLightPlugin.enabled) {
         Location backLocation = sunLightPlugin.getBackLocation(player);
         sunlightLastLocation = backLocation != null ? backLocation.clone() : null;
      }

      return Arrays.asList(essentialsLastLocation, sunlightLastLocation);
   }

   private static void postTeleport(Player player, List<Location> lastLocations) {
      if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials") && lastLocations.get(0) != null) {
         User user = ((Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials")).getUser(player);
         if (user != null && user.getLastLocation() != null) {
            user.setLastLocation((Location)lastLocations.get(0));
         }
      }


      if (sunLightPlugin.enabled && sunLightPlugin.getBackLocation(player) != null) {
         sunLightPlugin.setBackLocation(player, (Location)lastLocations.get(2));
      }

   }

   @EventHandler
   public void onPlayerDamage(EntityDamageEvent event) {
      if (TEMP_PROTECTED.contains(event.getEntity().getUniqueId())) {
         event.setCancelled(true);
      }

   }

   private static class SunLightPlugin {
      private final boolean enabled;
      private Object userManager;
      private Method getOrLoadUser;

      SunLightPlugin() {
         if (this.enabled = Bukkit.getPluginManager().isPluginEnabled("SunLight")) {
            try {
               Plugin sunLight = Bukkit.getServer().getPluginManager().getPlugin("SunLight");
               this.userManager = sunLight.getClass().getMethod("getUserManager").invoke(sunLight);
               this.getOrLoadUser = this.userManager.getClass().getMethod("getOrLoadUser", Player.class);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var2) {
               var2.printStackTrace();
            }
         }

      }

      private Location getBackLocation(Player player) {
         try {
            Object user = this.getOrLoadUser.invoke(this.userManager, player);
            Method getBackLocation = user.getClass().getMethod("getBackLocation");
            return (Location)getBackLocation.invoke(user);
         } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var4) {
            var4.printStackTrace();
            return null;
         }
      }

      private void setBackLocation(Player player, Location location) {
         try {
            Object user = this.getOrLoadUser.invoke(this.userManager, player);
            user.getClass().getMethod("setBackLocation", Location.class).invoke(user, location);
         } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var4) {
            var4.printStackTrace();
         }

      }
   }
}
