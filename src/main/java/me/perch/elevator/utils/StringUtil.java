package me.perch.elevator.utils;

import java.util.Arrays;
import org.bukkit.Bukkit;

public final class StringUtil {
   /** @deprecated */
   @Deprecated
   public static void debug(Object... o) {
      Bukkit.getServer().getLogger().info(Arrays.toString(o));
   }
}
