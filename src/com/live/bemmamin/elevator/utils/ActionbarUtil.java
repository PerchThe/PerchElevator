package com.live.bemmamin.elevator.utils;

import com.live.bemmamin.elevator.Variables;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ActionbarUtil {
   private static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);
   private static Class<?> craftPlayerClass;
   private static Constructor<?> ppoc;
   private static Class<?> packet;
   private static Class<?> chat;
   private static Class<?> chatBaseComponent;

   public static void sendMessage(Player player, String message) {
      if (Variables.getInstance().isAbEnabled()) {
         if (player != null && message != null) {
            if (!NMS_VERSION.startsWith("v1_9_R") && !NMS_VERSION.startsWith("v1_8_R")) {
               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            } else {
               try {
                  Object craftPlayer = craftPlayerClass.cast(player);
                  Object object = NMS_VERSION.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(chat.getDeclaredMethod("a", String.class).invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(String.class).newInstance(message);
                  Object packetPlayOutChat = ppoc.newInstance(object, 2);
                  Method handle = craftPlayerClass.getDeclaredMethod("getHandle");
                  Object iCraftPlayer = handle.invoke(craftPlayer);
                  Field playerConnectionField = iCraftPlayer.getClass().getDeclaredField("playerConnection");
                  Object playerConnection = playerConnectionField.get(iCraftPlayer);
                  Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", packet);
                  sendPacket.invoke(playerConnection, packetPlayOutChat);
               } catch (Exception var10) {
                  var10.printStackTrace();
               }

            }
         }
      }
   }

   static {
      try {
         if (NMS_VERSION.startsWith("v1_8_R") || NMS_VERSION.startsWith("v1_9_R")) {
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftPlayer");
            chatBaseComponent = Class.forName("net.minecraft.server." + NMS_VERSION + ".IChatBaseComponent");
            ppoc = Class.forName("net.minecraft.server." + NMS_VERSION + ".PacketPlayOutChat").getConstructor(chatBaseComponent, Byte.TYPE);
            packet = Class.forName("net.minecraft.server." + NMS_VERSION + ".Packet");
            chat = Class.forName("net.minecraft.server." + NMS_VERSION + (NMS_VERSION.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
         }
      } catch (NoSuchMethodException | ClassNotFoundException var1) {
         var1.printStackTrace();
      }

   }
}
