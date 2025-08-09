package me.perch.elevator;

import me.perch.elevator.elevator.CombinationCheck;
import me.perch.elevator.elevator.CombinationData;
import me.perch.elevator.elevator.Cooldown;
import me.perch.elevator.elevator.Elevator;
import me.perch.elevator.elevator.ElevatorBossBar;
import me.perch.elevator.elevator.ElevatorCheck;
import me.perch.elevator.elevator.ElevatorType;
import me.perch.elevator.files.MessagesFile;
import me.perch.elevator.utils.ActionbarUtil;
import me.perch.elevator.utils.MessageUtil;
import me.perch.elevator.utils.SEMaterial;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Events implements Listener {
    public static final Map<Player, Cooldown> elevationCooldown = new HashMap<>();
    private static final DecimalFormat LOCATION_DECIMAL_FORMAT = new DecimalFormat("#.####");
    private static final NumberFormat NUMBER_FORMAT_INSTANCE;
    private final ElevatorPlugin elevatorPlugin;

    Events(ElevatorPlugin elevatorPlugin) {
        this.elevatorPlugin = elevatorPlugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData.removePlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Ignore elevator logic if the player is in knockback (recently damaged)
        if (player.getNoDamageTicks() > 0) {
            return;
        }

        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            if (Variables.getInstance().isBossBarEnabled()) {
                PlayerData.getOptionalPlayerData(player).flatMap(PlayerData::getElevatorBossBar).ifPresent(ElevatorBossBar::hide);
            }
        } else {
            String worldCurrent = player.getWorld().getName();
            List<String> worldList = Variables.getInstance().getEnabledWorlds();
            ElevatorType type = null;
            Location fromLoc = this.loc4Decimals(event.getFrom());
            Location toLoc = this.loc4Decimals(event.getTo());
            this.loc4Decimals(player.getLocation());
            boolean specialBlock = Arrays.asList(0.8125D, 0.875D).contains(fromLoc.getY() - (double)fromLoc.getBlockY()) && fromLoc.getBlockX() == toLoc.getBlockX() && fromLoc.getBlockZ() == toLoc.getBlockZ();
            boolean updateBossBar = fromLoc.getBlockX() != toLoc.getBlockX() || fromLoc.getBlockY() != toLoc.getBlockY() && Math.abs(fromLoc.getY() - toLoc.getY()) < 1.0D || fromLoc.getBlockZ() != toLoc.getBlockZ() && Variables.getInstance().isBossBarEnabled();
            if ((worldList.contains(worldCurrent) || worldList.isEmpty()) && (!(toLoc.getY() <= fromLoc.getY()) || updateBossBar)) {
                Block toBlock = (new Location(toLoc.getWorld(), (double)toLoc.getBlockX(), (double)(toLoc.getY() > fromLoc.getY() ? fromLoc : toLoc).getBlockY(), (double)toLoc.getBlockZ())).getBlock();
                CombinationData combinationData = null;
                if ((double)fromLoc.getBlockY() == fromLoc.getY() || updateBossBar && !specialBlock) {
                    combinationData = CombinationCheck.isCombination(toBlock.getRelative(BlockFace.DOWN), toBlock.getRelative(BlockFace.DOWN, 2));
                    if (combinationData == null) {
                        combinationData = CombinationCheck.isCombination(toBlock, toBlock.getLocation().add(0.0D, -1.0D, 0.0D).getBlock());
                        if (combinationData != null) {
                            if (combinationData.getTopMaterial() == null || combinationData.getTopMaterial().isSpecialType(SEMaterial.SpecialType.PRESSURE_PLATE)) {
                                type = ElevatorType.PLATE;
                            }
                        } else {
                            type = ElevatorType.BLOCK;
                        }
                    } else {
                        type = ElevatorType.BLOCK;
                    }
                }

                if ((Stream.of(0.0625D, 0.1875D, 0.375D, 0.5D, 0.8125D, 0.875D, 0.4375D).anyMatch((value) -> {
                    return fromLoc.getY() == (double)fromLoc.getBlockY() + value;
                }) || updateBossBar) && combinationData == null) {
                    combinationData = CombinationCheck.isCombination(toBlock.getLocation().add(0.0D, specialBlock ? -1.0D : 0.0D, 0.0D).getBlock(), toBlock.getLocation().add(0.0D, specialBlock ? -2.0D : -1.0D, 0.0D).getBlock());
                    type = ElevatorType.NON_OCCLUDING;
                } else if (!Bukkit.getVersion().contains("1.8") && combinationData != null && combinationData.getTopMaterial() == SEMaterial.END_GATEWAY) {
                    type = ElevatorType.NON_OCCLUDING;
                }

                PlayerData playerData = PlayerData.getPlayerData(player, true);
                ElevatorCheck elevatorCheck = combinationData == null ? null : new ElevatorCheck(player, combinationData, type);
                if (updateBossBar && !specialBlock) {
                    if (combinationData != null) {
                        elevatorCheck.calculateFloors(toLoc);
                        elevatorCheck.getNumberOfFloors();
                        if (elevatorCheck.getNumberOfFloors() > 1) {
                            playerData.getElevatorBossBar().ifPresent((elevatorBossBar) -> {
                                elevatorBossBar.display(elevatorCheck.getCurrentFloor(toLoc.getBlockY()), elevatorCheck.getNumberOfFloors());
                            });
                            playerData.setCurrentFloor(elevatorCheck.getCurrentFloor(toLoc.getBlockY()));
                            playerData.setTotalFloors(elevatorCheck.getNumberOfFloors());
                        } else {
                            playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
                        }
                    } else {
                        playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
                    }

                    if (combinationData == null || combinationData.getTopMaterial() != null && !combinationData.getTopMaterial().isSpecialType(SEMaterial.SpecialType.SLAB) || !player.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
                        return;
                    }
                }

                if (combinationData != null && type != null && Stream.of(0.0625D, 0.1875D, 0.375D, 0.5D, 0.75D, 0.8125D, 0.875D, 0.4375D).noneMatch((value) -> {
                    return toLoc.getY() == (double)fromLoc.getBlockY() + value;
                }) && Stream.of(0.5D, 0.375D, 0.1875D, 0.125D).noneMatch((value) -> {
                    return toLoc.getY() == fromLoc.getY() + value;
                })) {
                    this.handleElevation(player, playerData, combinationData, elevatorCheck, combinationData, type, Direction.UP, fromLoc);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player, false);
        if (playerData != null) {
            playerData.getElevatorBossBar().ifPresent(ElevatorBossBar::hide);
        }
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
            String worldCurrent = player.getWorld().getName();
            List<String> worldList = Variables.getInstance().getEnabledWorlds();
            ElevatorType type = null;
            Location playerLoc = this.loc4Decimals(player.getLocation());
            if ((worldList.contains(worldCurrent) || worldList.isEmpty()) && player.isSneaking()) {
                CombinationData combinationData = null;
                if ((double)playerLoc.getBlockY() == playerLoc.getY()) {
                    combinationData = CombinationCheck.isCombination(playerLoc.getBlock().getRelative(BlockFace.DOWN), playerLoc.getBlock().getRelative(BlockFace.DOWN, 2));
                    if (combinationData == null) {
                        combinationData = CombinationCheck.isCombination(playerLoc.getBlock(), (new Location(player.getWorld(), (double)playerLoc.getBlockX(), (double)(playerLoc.getBlockY() - 1), (double)playerLoc.getBlockZ())).getBlock());
                        if (combinationData != null) {
                            if (combinationData.getTopMaterial() == null || combinationData.getTopMaterial().isSpecialType(SEMaterial.SpecialType.PRESSURE_PLATE)) {
                                type = ElevatorType.PLATE;
                            }
                        } else {
                            type = ElevatorType.BLOCK;
                        }
                    } else {
                        type = ElevatorType.BLOCK;
                    }
                } else if (Stream.of(0.0625D, 0.1875D, 0.375D, 0.5D, 0.8125D, 0.875D, 0.4375D).anyMatch((value) -> {
                    return playerLoc.getY() == (double)playerLoc.getBlockY() + value;
                })) {
                    combinationData = CombinationCheck.isCombination(playerLoc.getBlock(), (new Location(player.getWorld(), (double)playerLoc.getBlockX(), (double)(playerLoc.getBlockY() - 1), (double)playerLoc.getBlockZ())).getBlock());
                    type = ElevatorType.NON_OCCLUDING;
                }

                if (!Bukkit.getVersion().contains("1.8") && combinationData != null && combinationData.getTopMaterial() != null && combinationData.getTopMaterial() == SEMaterial.END_GATEWAY) {
                    type = ElevatorType.NON_OCCLUDING;
                }

                if (combinationData != null && type != null) {
                    this.handleElevation(player, PlayerData.getPlayerData(player, true), combinationData, new ElevatorCheck(player, combinationData, type), combinationData, type, Direction.DOWN, playerLoc);
                }
            }
        }
    }

    private void handleElevation(final Player player, final PlayerData playerData, CombinationData combinationData, final ElevatorCheck finalElevatorCheck, final CombinationData finalComboData, final ElevatorType finalType, final Direction direction, final Location fromLoc) {
        if (finalElevatorCheck.hasElevator(direction)) {
            if (!SEPerm.USE.hasPermission(player) && !SEPerm.USE.hasPermission(player, String.valueOf(combinationData.getComboIndex()))) {
                MessageUtil.send(player, MessagesFile.getInstance().getInvalidPermission());
            } else {
                if (elevationCooldown.containsKey(player)) {
                    MessageUtil.send(player, Variables.getInstance().getElevatorCooldownMessage().replaceAll("%cooldown%", String.valueOf(elevationCooldown.get(player).getTimeLeft())));
                } else if (!playerData.isDelayed() || player.isOp()) {
                    playerData.setDelayed(true);
                    (new BukkitRunnable() {
                        private final Location current = fromLoc.clone();
                        private long ticksWaited = 0L;
                        private int timeLeft = (int)(Variables.getInstance().getDelay() / 20L);

                        public void run() {
                            boolean isOnElevator = player.getLocation().getBlockX() == this.current.getBlockX() && Arrays.asList(this.current.getBlockY(), this.current.getBlockY() - 1, this.current.getBlockY() + 1).contains(player.getLocation().getBlockY()) && player.getLocation().getBlockZ() == this.current.getBlockZ();
                            if (!isOnElevator && Variables.getInstance().getDelay() != 0L) {
                                this.cancel();
                                playerData.setDelayed(false);
                                ActionbarUtil.sendMessage(player, Variables.getInstance().getElevatorCanceledMessage());
                            } else if (this.ticksWaited < Variables.getInstance().getDelay() && !player.isOp()) {
                                this.timeLeft = this.timeLeft <= 0 ? 0 : (int)(Variables.getInstance().getDelay() - this.ticksWaited) / 20;
                                ActionbarUtil.sendMessage(player, Variables.getInstance().getElevatorDelayMessage().replaceAll("%seconds%", String.valueOf(this.timeLeft)));
                                ++this.ticksWaited;
                            } else {
                                this.cancel();
                                playerData.setDelayed(false);
                                (new Elevator(player, this.current, finalComboData, direction, finalType, finalElevatorCheck)).elevator();
                            }
                        }
                    }).runTaskTimer(this.elevatorPlugin, 0L, 1L);
                }
            }
        }
    }

    private Location loc4Decimals(Location location) {
        Location clone = location.clone();

        try {
            clone.setY(NUMBER_FORMAT_INSTANCE.parse(LOCATION_DECIMAL_FORMAT.format(clone.getY()).replaceAll(",", ".")).doubleValue());
        } catch (ParseException var4) {
        }

        return clone;
    }

    static {
        NUMBER_FORMAT_INSTANCE = NumberFormat.getInstance(Locale.ENGLISH);
    }
}