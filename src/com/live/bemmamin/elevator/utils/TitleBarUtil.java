package com.live.bemmamin.elevator.utils;

import com.live.bemmamin.elevator.Direction;
import com.live.bemmamin.elevator.PlayerData;
import com.live.bemmamin.elevator.Variables;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class TitleBarUtil {
   private static boolean error = false;
   private static Class<?> packetClass;
   private static Constructor<?> packetPlayOutTitle;
   private static Constructor<?> post1_8_Chat;
   private static Constructor<?> playOut1_8;
   private static Object title1_8;
   private static Object subtitle1_8;
   private static Method chat1_8;

   public static void sendTitleBar(Player player, Direction direction) {
      if (Variables.getInstance().isTitleBarEnabled()) {
         PlayerData playerData = PlayerData.getPlayerData(player);
         String title = (direction.equals(Direction.UP) ? Variables.getInstance().getTitleBarUp() : Variables.getInstance().getTitleBarDown()).replaceAll("%floor%", String.valueOf(playerData.getCurrentFloor())).replaceAll("%totalFloors%", String.valueOf(playerData.getTotalFloors()));
         String subtitle = (direction.equals(Direction.UP) ? Variables.getInstance().getSubtitleBarUp() : Variables.getInstance().getSubtitleBarDown()).replaceAll("%floor%", String.valueOf(playerData.getCurrentFloor())).replaceAll("%totalFloors%", String.valueOf(playerData.getTotalFloors()));
         if (!Variables.version("1.8") && !Variables.version("1.9") && !Variables.version("1.10")) {
            player.sendTitle(title, subtitle, Variables.getInstance().getTitleBarFadeIn(), Variables.getInstance().getTitleBarStay(), Variables.getInstance().getTitleBarFadeOut());
         } else if (!error) {
            try {
               Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
               Object playerCon = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
               if (!title.isEmpty()) {
                  playerCon.getClass().getMethod("sendPacket", packetClass).invoke(playerCon, playOut1_8.newInstance(title1_8, !Variables.version("1.8") ? post1_8_Chat.newInstance(title) : chat1_8.invoke((Object)null, "{'text': '" + title + "'}")));
               }

               if (!subtitle.isEmpty()) {
                  playerCon.getClass().getMethod("sendPacket", packetClass).invoke(playerCon, playOut1_8.newInstance(subtitle1_8, !Variables.version("1.8") ? post1_8_Chat.newInstance(subtitle) : chat1_8.invoke((Object)null, "{'text': '" + subtitle + "'}")));
               }

               playerCon.getClass().getMethod("sendPacket", packetClass).invoke(playerCon, packetPlayOutTitle.newInstance(Variables.getInstance().getTitleBarFadeIn(), Variables.getInstance().getTitleBarStay(), Variables.getInstance().getTitleBarFadeOut()));
            } catch (Exception var7) {
               var7.printStackTrace();
            }

         }
      }
   }

   private static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
      return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
   }

   private static String getServerVersion() {
      return Bukkit.getServer().getClass().getPackage().getName().substring(23);
   }

   static {
      try {
         if (Variables.version("1.8") || Variables.version("1.9") || Variables.version("1.10")) {
            packetPlayOutTitle = getNmsClass("PacketPlayOutTitle").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            packetClass = getNmsClass("Packet");
            if (!getServerVersion().equalsIgnoreCase("v1_8_R1")) {
               if (!Variables.version("1.8")) {
                  post1_8_Chat = getNmsClass("ChatComponentText").getConstructor(String.class);
               } else {
                  chat1_8 = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
               }

               title1_8 = getNmsClass("PacketPlayOutTitle$EnumTitleAction").getField("TITLE").get((Object)null);
               subtitle1_8 = getNmsClass("PacketPlayOutTitle$EnumTitleAction").getField("SUBTITLE").get((Object)null);
               playOut1_8 = getNmsClass("PacketPlayOutTitle").getConstructor(getNmsClass("PacketPlayOutTitle$EnumTitleAction"), getNmsClass("IChatBaseComponent"));
            } else {
               chat1_8 = getNmsClass("ChatSerializer").getMethod("a", String.class);
               title1_8 = getNmsClass("EnumTitleAction").getField("TITLE").get((Object)null);
               subtitle1_8 = getNmsClass("EnumTitleAction").getField("SUBTITLE").get((Object)null);
               playOut1_8 = getNmsClass("PacketPlayOutTitle").getConstructor(getNmsClass("EnumTitleAction"), getNmsClass("IChatBaseComponent"));
            }
         }
      } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException var1) {
         MessageUtil.send((Player)null, "&c[&eSimple Elevators&c] &cCouldn't initialize title constants.");
         error = true;
         var1.printStackTrace();
      }

   }
}
