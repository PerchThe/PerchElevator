package me.perch.elevator;

import me.perch.elevator.elevator.ElevatorSound;
import me.perch.elevator.utils.MessageUtil;
import me.perch.elevator.utils.SEMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;

public final class Variables {
   public static final boolean PRE_1_13;
   private static final int SERVER_DEFAULT_MIN_Y;
   private static final int SERVER_DEFAULT_MAX_Y;
   private static Variables vars;
   private final Main main;
   private int maxDistance;
   private int minY;
   private int maxY;
   private boolean createReplace;
   private List<SEMaterial> createReplaceBlacklist;
   private boolean abEnabled;
   private String floorUp;
   private String floorDown;
   private boolean arrowEnabled;
   private boolean arrowCurrent;
   private boolean arrowDestination;
   private String arrowColorUp;
   private String arrowColorDown;
   private double arrowSize;
   private List<Combination> combinations;
   private List<String> enabledWorlds;
   private List<SEMaterial> ignoreList;
   private long delay;
   private boolean titleBarEnabled;
   private String titleBarUp;
   private String subtitleBarUp;
   private String titleBarDown;
   private String subtitleBarDown;
   private int titleBarFadeIn;
   private int titleBarStay;
   private int titleBarFadeOut;
   private long elevatorCooldown;
   private String elevatorCooldownMessage;
   private String elevatorCanceledMessage;
   private String elevatorDelayMessage;
   private boolean bossBarEnabled;
   private BarColor bossBarColor;
   private BarStyle bossBarStyle;
   private String bossBarMessage;
   private ElevatorSound elevatorSoundUp;
   private ElevatorSound elevatorSoundDown;
   private boolean lazyCheckTop;
   private boolean lazyCheckBottom;
   private List<SEMaterial> lazyCheckWhitelist;
   private boolean lazyCheckInvert;
   private boolean temporaryInvulnerable;
   private boolean backCompatibility;

   private Variables() {
      this.minY = SERVER_DEFAULT_MIN_Y;
      this.maxY = SERVER_DEFAULT_MAX_Y;
      this.main = Main.getPlugin(Main.class);
      this.loadVariables();
   }

   public static Variables getInstance() {
      vars = vars == null ? new Variables() : vars;
      return vars;
   }

   public static boolean version(String version) {
      return Bukkit.getVersion().contains(version);
   }

   public static void reload() {
      vars = new Variables();
      vars.loadVariables();
   }

   private static List<SEMaterial> ignoreListConvert(String stringList) {
      if (stringList.equalsIgnoreCase("all")) {
         return null;
      } else {
         List<SEMaterial> materials = new ArrayList();
         String[] var2 = stringList.replace(" ", "").split(",");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String mat = var2[var4];
            SEMaterial match = SEMaterial.match(mat);
            if (match != null) {
               materials.add(match);
            } else {
               Bukkit.getConsoleSender().sendMessage(MessageUtil.translate("&c[&eSimple Elevators&c] Unknown material in ignore list: &c" + mat));
            }
         }

         return materials;
      }
   }

   void loadVariables() {
      FileConfiguration config = this.main.getConfig();
      this.maxDistance = config.getInt("maxDistance");
      if (config.getBoolean("overrideY", false)) {
         this.minY = config.getInt("minY", SERVER_DEFAULT_MIN_Y);
         this.maxY = config.getInt("maxY", SERVER_DEFAULT_MAX_Y);
      }

      this.createReplace = config.getBoolean("createReplace");
      this.abEnabled = config.getBoolean("actionBarEnabled");
      this.floorUp = ChatColor.translateAlternateColorCodes('&', config.getString("floorUp"));
      this.floorDown = ChatColor.translateAlternateColorCodes('&', config.getString("floorDown"));
      this.arrowEnabled = config.getBoolean("arrow_enabled");
      this.arrowCurrent = config.getBoolean("arrow_currentFloor");
      this.arrowDestination = config.getBoolean("arrow_destinationFloor");
      this.arrowColorUp = config.getString("arrow_color_up");
      this.arrowColorDown = config.getString("arrow_color_down");
      this.arrowSize = config.getDouble("arrow_size");
      this.combinations = (List)config.getStringList(config.contains("Combinations") ? "Combinations" : "combinations").stream().flatMap((combo) -> {
         try {
            return Stream.of(new Combination(combo));
         } catch (IllegalArgumentException var2) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.translate("&c[&eSimple Elevators&c] Unknown material or height in combo: " + combo));
            return Stream.empty();
         }
      }).collect(Collectors.toList());
      this.enabledWorlds = config.getStringList("enabledworlds");
      this.ignoreList = ignoreListConvert(config.getString("ignoreList", ""));
      this.createReplaceBlacklist = this.stringToMaterialList(config, "createReplaceBlacklist");
      this.delay = config.getLong("delay", 0L);
      this.titleBarEnabled = config.getBoolean("titleBarEnabled", true);
      this.titleBarUp = MessageUtil.translate(config.getString("floorUpTitle", "&a&lUP"));
      this.subtitleBarUp = MessageUtil.translate(config.getString("floorUpSubTitle", ""));
      this.titleBarDown = MessageUtil.translate(config.getString("floorDownTitle", "&c&lDOWN"));
      this.subtitleBarDown = MessageUtil.translate(config.getString("floorDownSubTitle", ""));
      this.titleBarFadeIn = config.getInt("titleBarFadeIn", 5);
      this.titleBarStay = config.getInt("titleBarStay", 15);
      this.titleBarFadeOut = config.getInt("titleBarFadeOut", 5);
      this.elevatorCooldown = config.getLong("elevatorCooldown", 0L);
      this.elevatorCooldownMessage = MessageUtil.translate(config.getString("elevatorCooldownMessage", "&cPlease wait %cooldown% seconds before using an elevator again!"));
      this.elevatorCanceledMessage = MessageUtil.translate(config.getString("elevatorCanceledMessage", "&c&lElevation canceled!"));
      this.elevatorDelayMessage = MessageUtil.translate(config.getString("elevatorDelayMessage", "&a&lElevation in %seconds% seconds!"));
      this.bossBarEnabled = config.getBoolean("bossBarEnabled", true);
         this.bossBarColor = BarColor.valueOf(config.getString("bossBarColor", "RED"));
         this.bossBarStyle = BarStyle.valueOf(config.getString("bossBarStyle", "SOLID"));
         this.bossBarMessage = config.getString("bossBarMessage", "&eFloor %floor% of %totalFloors%");
      this.elevatorSoundUp = new ElevatorSound(config.getConfigurationSection("elevatorSound.up"), 0);
      this.elevatorSoundDown = new ElevatorSound(config.getConfigurationSection("elevatorSound.down"), 1);
      this.lazyCheckTop = config.getBoolean("lazyCheck.top", false);
      this.lazyCheckBottom = config.getBoolean("lazyCheck.bottom", false);
      this.lazyCheckWhitelist = this.stringToMaterialList(config, "lazyCheck.whitelist");
      this.lazyCheckInvert = config.getBoolean("lazyCheck.invert", false);
      this.temporaryInvulnerable = config.getBoolean("temporaryInvulnerable", false);
      this.backCompatibility = config.getBoolean("backCompatibility", true);
   }

   private List<SEMaterial> stringToMaterialList(FileConfiguration config, String path) {
      return (List)Arrays.stream(config.getString(path, "").replace(" ", "").split(",")).filter((s) -> {
         return !s.isEmpty();
      }).map((entry) -> {
         SEMaterial match = SEMaterial.match(entry);
         if (match == null) {
            Bukkit.getConsoleSender().sendMessage(MessageUtil.translate("&c[&eSimple Elevators&c] Unknown material in " + path + ": &c" + entry));
         }

         return match;
      }).filter(Objects::nonNull).collect(Collectors.toList());
   }

   public Main getMain() {
      return this.main;
   }

   public int getMaxDistance() {
      return this.maxDistance;
   }

   public int getMinY() {
      return this.minY;
   }

   public int getMaxY() {
      return this.maxY;
   }

   public boolean isCreateReplace() {
      return this.createReplace;
   }

   public List<SEMaterial> getCreateReplaceBlacklist() {
      return this.createReplaceBlacklist;
   }

   public boolean isAbEnabled() {
      return this.abEnabled;
   }

   public String getFloorUp() {
      return this.floorUp;
   }

   public String getFloorDown() {
      return this.floorDown;
   }

   public boolean isArrowEnabled() {
      return this.arrowEnabled;
   }

   public boolean isArrowCurrent() {
      return this.arrowCurrent;
   }

   public boolean isArrowDestination() {
      return this.arrowDestination;
   }

   public String getArrowColorUp() {
      return this.arrowColorUp;
   }

   public String getArrowColorDown() {
      return this.arrowColorDown;
   }

   public double getArrowSize() {
      return this.arrowSize;
   }

   public List<Combination> getCombinations() {
      return this.combinations;
   }

   public List<String> getEnabledWorlds() {
      return this.enabledWorlds;
   }

   public List<SEMaterial> getIgnoreList() {
      return this.ignoreList;
   }

   public long getDelay() {
      return this.delay;
   }

   public boolean isTitleBarEnabled() {
      return this.titleBarEnabled;
   }

   public String getTitleBarUp() {
      return this.titleBarUp;
   }

   public String getSubtitleBarUp() {
      return this.subtitleBarUp;
   }

   public String getTitleBarDown() {
      return this.titleBarDown;
   }

   public String getSubtitleBarDown() {
      return this.subtitleBarDown;
   }

   public int getTitleBarFadeIn() {
      return this.titleBarFadeIn;
   }

   public int getTitleBarStay() {
      return this.titleBarStay;
   }

   public int getTitleBarFadeOut() {
      return this.titleBarFadeOut;
   }

   public long getElevatorCooldown() {
      return this.elevatorCooldown;
   }

   public String getElevatorCooldownMessage() {
      return this.elevatorCooldownMessage;
   }

   public String getElevatorCanceledMessage() {
      return this.elevatorCanceledMessage;
   }

   public String getElevatorDelayMessage() {
      return this.elevatorDelayMessage;
   }

   public boolean isBossBarEnabled() {
      return this.bossBarEnabled;
   }

   public BarColor getBossBarColor() {
      return this.bossBarColor;
   }

   public BarStyle getBossBarStyle() {
      return this.bossBarStyle;
   }

   public String getBossBarMessage() {
      return this.bossBarMessage;
   }

   public ElevatorSound getElevatorSoundUp() {
      return this.elevatorSoundUp;
   }

   public ElevatorSound getElevatorSoundDown() {
      return this.elevatorSoundDown;
   }

   public boolean isLazyCheckTop() {
      return this.lazyCheckTop;
   }

   public boolean isLazyCheckBottom() {
      return this.lazyCheckBottom;
   }

   public List<SEMaterial> getLazyCheckWhitelist() {
      return this.lazyCheckWhitelist;
   }

   public boolean isLazyCheckInvert() {
      return this.lazyCheckInvert;
   }

   public boolean isTemporaryInvulnerable() {
      return this.temporaryInvulnerable;
   }

   public boolean isBackCompatibility() {
      return this.backCompatibility;
   }

   static {
      PRE_1_13 = Stream.of("1.8", "1.9", "1.10", "1.11", "1.12").anyMatch(Bukkit.getVersion()::contains);

      if (!PRE_1_13 && !Stream.of("1.13", "1.14", "1.15", "1.16").anyMatch(Bukkit.getVersion()::contains)) {
         SERVER_DEFAULT_MIN_Y = -64;
         SERVER_DEFAULT_MAX_Y = 321;
      } else {
         SERVER_DEFAULT_MIN_Y = 0;
         SERVER_DEFAULT_MAX_Y = 257;
      }
   }
}