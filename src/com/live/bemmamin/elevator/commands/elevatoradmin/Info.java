package com.live.bemmamin.elevator.commands.elevatoradmin;

import com.live.bemmamin.elevator.Main;
import com.live.bemmamin.elevator.SEPerm;
import com.live.bemmamin.elevator.commands.AbstractSubCommand;
import com.live.bemmamin.elevator.utils.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Info extends AbstractSubCommand {
   private final Main main;

   Info(Main main, String name, SEPerm permission, boolean playerOnly, String... aliases) {
      super(name, permission, playerOnly, aliases);
      this.main = main;
   }

   public void onCommand(Player player, String[] args) {
      MessageUtil.send(player, "&9&m&l----------------------------------");
      MessageUtil.sendJSON(player, " &7&l● &eAuthor: &aBenz56", "&eClick to see all plugins by Benz56!", "https://www.spigotmc.org/resources/authors/benz56.171209/", Action.OPEN_URL);
      MessageUtil.send(player, " &7&l● &eServer version: &a" + Bukkit.getVersion());
      MessageUtil.send(player, " &7&l● &ePlugin version: &a" + this.main.getUpdateChecker().getLocalPluginVersion());
      MessageUtil.send(player, " &7&l● &eLatest version: &a" + (this.main.getUpdateChecker().getSpigotPluginVersion() == null ? "&cunknown" : this.main.getUpdateChecker().getSpigotPluginVersion()));
      if (player != null) {
         if (this.main.getUpdateChecker().getSpigotPluginVersion() != null && !this.main.getUpdateChecker().getLocalPluginVersion().equals(this.main.getUpdateChecker().getSpigotPluginVersion())) {
            MessageUtil.sendJSON(player, " &7&l● &eClick here to Update", "&eOpens the plugin page on Spigot!", "https://www.spigotmc.org/resources/44462/updates", Action.OPEN_URL);
         }

         MessageUtil.sendJSON(player, " &7&l● &eClick for Support", "&eClick to join the Benzoft Discord server!", "https://discordapp.com/invite/8YVeFHe", Action.OPEN_URL);
      }

      MessageUtil.sendJSON(player, "&9&m&l----------------------------------", (String)null, (String)null, (Action)null);
   }
}
