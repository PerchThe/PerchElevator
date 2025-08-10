package me.perch.elevator.elevator;

import me.perch.elevator.Direction;
import me.perch.elevator.Variables;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArrowCreator {

   public void arrow(Player p, Direction direction) {
      Location pLoc = p.getLocation();
      double arrowSize = Variables.getInstance().getArrowSize();
      double[] color;
      if (direction == Direction.UP) {
         color = this.color(Variables.getInstance().getArrowColorUp());
      } else {
         color = this.color(Variables.getInstance().getArrowColorDown());
      }

      for(double i = 0.0D; i < arrowSize * 10.0D; ++i) {
         Location particleLine = new Location(pLoc.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getY() + arrowSize - i / 10.0D, pLoc.getBlockZ() + 0.5D);
         this.particle(particleLine, color);
      }

      Location[] inclinedLine = this.facing(p, pLoc, arrowSize);
      if (inclinedLine != null) {
         for(int i = 0; i < 2; ++i) {
            Location start;
            Vector end;
            if (direction == Direction.UP) {
               start = new Location(pLoc.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY() + arrowSize, pLoc.getBlockZ() + 0.5D);
               end = (i == 0) ? inclinedLine[2].toVector() : inclinedLine[3].toVector();
            } else {
               start = new Location(pLoc.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY(), pLoc.getBlockZ() + 0.5D);
               end = (i == 0) ? inclinedLine[0].toVector() : inclinedLine[1].toVector();
            }

            start.setDirection(end.subtract(start.toVector()));
            Vector increase = start.getDirection().multiply(0.1D);
            double distance = (direction == Direction.UP) ? start.distance(inclinedLine[2]) : start.distance(inclinedLine[0]);

            for(double k = 0.0D; k < distance / 2.0D * arrowSize; k += 0.1D) {
               Location loc = start.add(increase);
               this.particle(loc, color);
            }
         }
      }
   }

   private void particle(Location location, double[] color) {
      // 1.21.5+ only: use Particle.REDSTONE and DustOptions
      Color bukkitColor = Color.fromRGB((int)(color[0] * 255), (int)(color[1] * 255), (int)(color[2] * 255));
      Particle.DustOptions dustOptions = new Particle.DustOptions(bukkitColor, 1.0F);
      location.getWorld().getPlayers().forEach(player ->
              player.spawnParticle(Particle.DUST, location, 1, 0.0D, 0.0D, 0.0D, 0.0D, dustOptions)
      );
   }

   private Location[] facing(Player p, Location pLoc, double arrowSize) {
      float yaw = (p.getLocation().getYaw() - 90.0F) % 360.0F;
      if (yaw < 0.0F) {
         yaw = (float)(yaw + 360.0D);
      }

      if ((!(yaw >= 45.0F) || !(yaw <= 135.0F)) && (!(yaw >= 225.0F) || !(yaw <= 315.0F))) {
         return (!(yaw >= 135.0F) || !(yaw <= 225.0F)) && !(yaw >= 315.0F) && !(yaw <= 45.0F) ? null : new Location[]{
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY() + 1, pLoc.getBlockZ() + 0.5D + 0.7D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY() + 1, pLoc.getBlockZ() + 0.5D - 0.7D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY() + arrowSize - 1.0D, pLoc.getBlockZ() + 0.5D + 0.7D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D, pLoc.getBlockY() + arrowSize - 1.0D, pLoc.getBlockZ() + 0.5D - 0.7D)
         };
      } else {
         return new Location[]{
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D + 0.7D, pLoc.getBlockY() + 1, pLoc.getBlockZ() + 0.5D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D - 0.7D, pLoc.getBlockY() + 1, pLoc.getBlockZ() + 0.5D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D + 0.7D, pLoc.getBlockY() + arrowSize - 1.0D, pLoc.getBlockZ() + 0.5D),
                 new Location(p.getWorld(), pLoc.getBlockX() + 0.5D - 0.7D, pLoc.getBlockY() + arrowSize - 1.0D, pLoc.getBlockZ() + 0.5D)
         };
      }
   }

   private double[] color(String color_StringName) {
      Random rand = new Random();
      if (Objects.equals(color_StringName, "random")) {
         List<String> random_color = Arrays.asList("&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f");
         color_StringName = random_color.get(rand.nextInt(15));
      }

      String var5 = color_StringName.toLowerCase();
      byte var6 = -1;
      switch(var5.hashCode()) {
      }

      double[] colorValue;
      switch(var6) {
         default:
            colorValue = new double[]{0.33333D, 1.0D, 0.33333D};
      }

      return colorValue;
   }
}