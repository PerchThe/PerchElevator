package me.perch.elevator.files;

import me.perch.elevator.ElevatorPlugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

abstract class AbstractFile {
   private final File file;
   private final YamlConfiguration config;

   AbstractFile(String name) {
      this.file = new File((ElevatorPlugin.getPlugin(ElevatorPlugin.class)).getDataFolder(), name);
      if (!this.file.exists()) {
         try {
            this.file.createNewFile();
         } catch (IOException exception) {
         }
      }

      this.config = YamlConfiguration.loadConfiguration(this.file);
   }

   Object add(String path, Object value) {
      if (this.config.contains(path)) {
         return this.config.get(path);
      } else {
         this.config.set(path, value);
         return value;
      }
   }

   void setHeader(String... lines) {
      this.config.options().header(String.join("\n", lines) + "\n");
   }

   void save() {
      try {
         this.config.save(this.file);
      } catch (IOException exception) {
         exception.printStackTrace();
      }

   }

   public abstract void setDefaults();

   protected File getFile() {
      return this.file;
   }

   protected YamlConfiguration getConfig() {
      return this.config;
   }
}
