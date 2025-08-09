package me.perch.elevator.utils;

import me.perch.elevator.Direction;
import me.perch.elevator.PlayerData;
import me.perch.elevator.Variables;
import org.bukkit.entity.Player;

public final class TitleBarUtil {
   public void sendTitleBar(Player player, Direction direction) {
      Variables variables = Variables.getInstance();
      if (!variables.isTitleBarEnabled()) return;

      PlayerData playerData = PlayerData.getPlayerData(player);
      boolean isUpDirection = direction.equals(Direction.UP);

      String title = formatMessage (
              isUpDirection ? variables.getTitleBarUp() : variables.getTitleBarDown(),
              playerData
      );

      String subtitle = formatMessage(
              isUpDirection ? variables.getSubtitleBarUp() : variables.getSubtitleBarDown(),
              playerData
      );

      player.sendTitle(
              title,
              subtitle,
              variables.getTitleBarFadeIn(),
              variables.getTitleBarStay(),
              variables.getTitleBarFadeOut()
      );
   }

   private static String formatMessage(String message, PlayerData playerData) {
      return message
              .replaceAll("%floor%", String.valueOf(playerData.getCurrentFloor()))
              .replaceAll("%totalFloors%", String.valueOf(playerData.getTotalFloors()));
   }
}
