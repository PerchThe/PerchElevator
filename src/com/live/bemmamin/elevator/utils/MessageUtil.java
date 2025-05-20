package com.live.bemmamin.elevator.utils;

import com.live.bemmamin.elevator.files.MessagesFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MessageUtil {
   public static String translate(String string) {
      return translateHexColorCodes(ChatColor.translateAlternateColorCodes('&', string));
   }

   private static String translateHexColorCodes(String message) {
      Matcher matcher = Pattern.compile("&#([A-Fa-f0-9]{6})").matcher(message);
      StringBuffer buffer = new StringBuffer(message.length() + 32);

      while(matcher.find()) {
         String group = matcher.group(1);
         String replacement = "§x" + (String)group.chars().mapToObj((value) -> {
            return '§' + String.valueOf((char)value);
         }).collect(Collectors.joining());
         matcher.appendReplacement(buffer, replacement);
      }

      return matcher.appendTail(buffer).toString();
   }

   public static void send(Player player, String message) {
      message = message.replaceAll("%prefix%", MessagesFile.getInstance().getPrefix());
      if (!message.isEmpty()) {
         message = translate(message);
         boolean actionbar = message.toLowerCase().startsWith("<actionbar>");
         if (actionbar) {
            message = message.substring(11);
         }

         if (player != null && actionbar) {
            ActionbarUtil.sendMessage(player, message);
         } else if (!message.contains("[\"") && !message.contains("\"]")) {
            if (player != null) {
               player.sendMessage(message);
            } else {
               Bukkit.getServer().getConsoleSender().sendMessage(message);
            }
         } else {
            BaseComponent mainComponent = (new CustomJSONParser(message)).parseMessage();
            if (player != null) {
               player.spigot().sendMessage(mainComponent);
            } else {
               Bukkit.getServer().getConsoleSender().sendMessage(mainComponent.toLegacyText());
            }
         }
      }

   }

   public static void sendJSON(Player player, String message, String hoverMessage) {
      sendJSON(player, message, hoverMessage, (String)null);
   }

   public static void sendJSON(Player player, String message, String hoverMessage, String clickEvent) {
      sendJSON(player, message, hoverMessage, clickEvent, Action.RUN_COMMAND);
   }

   public static void sendJSON(Player player, String message, String hoverMessage, String clickEvent, Action action) {
      if (player != null) {
         TextComponent tc = new TextComponent(translate(message));
         if (hoverMessage != null) {
            tc.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(translate(hoverMessage))).create()));
         }

         if (clickEvent != null) {
            tc.setClickEvent(new ClickEvent(action, clickEvent));
         }

         player.spigot().sendMessage(tc);
      } else {
         Bukkit.getServer().getConsoleSender().sendMessage(translate(message.replaceAll("●", "-")));
      }

   }
}
