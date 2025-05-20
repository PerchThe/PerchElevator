package com.live.bemmamin.elevator.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

final class CustomJSONParser {
   private final BaseComponent mainComponent = new TextComponent("");
   private final String message;
   private int stringProgress = 0;

   BaseComponent parseMessage() {
      while(true) {
         int eventStart = this.message.indexOf("[\"", this.stringProgress);
         int eventEnd = this.message.indexOf("\"]", this.stringProgress + 1);
         if (eventStart == -1 || eventEnd == -1) {
            if (!this.message.substring(this.stringProgress == 0 ? 0 : this.stringProgress + 2).isEmpty()) {
               this.mainComponent.addExtra(new TextComponent(TextComponent.fromLegacyText(this.message.substring(this.stringProgress == 0 ? 0 : this.stringProgress + 2))));
            }

            return this.mainComponent;
         }

         this.mainComponent.addExtra(new TextComponent(TextComponent.fromLegacyText(this.message.substring(this.stringProgress == 0 ? 0 : this.stringProgress + 2, eventStart))));
         this.stringProgress = eventEnd;
         String[] split = this.message.substring(eventStart + 2, eventEnd).split("\",\"");
         String message = "";
         String command = "";
         String hover = "";
         boolean suggest = false;

         for(int i = 0; i < split.length; ++i) {
            if (i != 0) {
               if (split[i].contains("/") && command.isEmpty()) {
                  command = split[i];
               } else if (split[i].equalsIgnoreCase("Suggest")) {
                  suggest = true;
               } else {
                  hover = split[i];
               }
            } else {
               message = split[i];
            }
         }

         TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(message));
         textComponent.setClickEvent(!command.isEmpty() ? new ClickEvent(suggest ? Action.SUGGEST_COMMAND : Action.RUN_COMMAND, command) : null);
         textComponent.setHoverEvent(!hover.isEmpty() ? new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)) : null);
         this.mainComponent.addExtra(textComponent);
      }
   }

   public CustomJSONParser(String message) {
      this.message = message;
   }
}
