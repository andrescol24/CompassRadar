package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.plugin.compassradar.Hooks.FactionsHook;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompassCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdName, String[] args) {
        if (sender.hasPermission("compassradar.help")) if (args.length == 0) {
            sender.sendMessage(Tools.msg(Lang.getHelpMessage()));
        } else {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "locations" -> {
                    if (sender.hasPermission("compassradar.listlocations")) {
                        if (Main.getConfiguration().isLocationEnable()) {
                            sender.sendMessage(Tools.msg(CompassLocation.getList()));
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE));
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                    }
                    return true;
                }
                case "player" -> {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("compassradar.use.players")) {
                            if (Main.getConfiguration().isPlayerEnable()) {
                                Player player = (Player) sender;
                                World world = player.getWorld();
                                if (!Main.getConfiguration().getPDW().contains(world.getName())) {
                                    ItemStack compass;
                                    if ((compass = player.getInventory().getItemInMainHand()).getType() == Material.COMPASS) {
                                        CompassPlayer target;
                                        if (Main.isHookFactions()) {
                                            target = FactionsHook.getNearest(world.getPlayers(), player);
                                        } else {
                                            target = CompassPlayer.getNearest(world.getPlayers(), player);
                                        }
                                        if (target == null) {
                                            Tools.msgItemRename(player, compass, Tools.msg(Lang.NO_NEAREST));
                                        } else {
                                            player.setCompassTarget(target.getPlayer().getLocation());
                                            Tools.msgItemRename(player, compass, Tools.msgHook(target));
                                        }
                                    } else {
                                        sender.sendMessage(Tools.msgWithPreffix(Lang.NEED_COMPASS_IN_HAND));
                                    }
                                } else {
                                    sender.sendMessage(Tools.msgWithPreffix(Lang.PLAYER_TRACKER_DISABLE_IN_WORLD));
                                }
                            } else {
                                sender.sendMessage(Tools.msgWithPreffix(Lang.PLAYER_TRACKER_DISABLE));
                            }
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix("&e Only players"));
                    }
                    return true;
                }
                case "reload" -> {
                    if (sender.hasPermission("compassradar.reload")) {
                        try {
                            Main.getPlugin().reload();
                            sender.sendMessage(Tools.msgWithPreffix(Lang.RELOADED));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                    }
                    return true;
                }
                case "remove" -> {
                    if (args.length == 2) {
                        if (sender.hasPermission("compassradar.removelocation")) {
                            if (Main.getConfiguration().isLocationEnable()) {
                                try {
                                    CompassLocation.deleteLoc(args[1]);
                                    sender.sendMessage(Tools.msgWithPreffix(Lang.REMOVELOCATION_SUCCESSFUL));
                                } catch (Exception e) {
                                    sender.sendMessage(Tools.msgWithPreffix(e.getMessage()));
                                }
                            } else {
                                sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE));
                            }
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix(Lang.NO_SUBCOMMAND));
                    }
                    return true;
                }
                case "add" -> {
                    if (args.length == 2) {
                        if (sender instanceof Player) {
                            if (sender.hasPermission("compassradar.addlocation")) {
                                Player player = (Player) sender;
                                if (Main.getConfiguration().isLocationEnable()) {
                                    if (!Main.getConfiguration().getLDW().contains(player.getWorld().getName())) {
                                        try {
                                            CompassLocation.addLocation(player, args[1]);
                                            sender.sendMessage(Tools.msgWithPreffix(Lang.ADDLOCATION_SUCCESSFUL));
                                        } catch (Exception e) {
                                            sender.sendMessage(Tools.msgWithPreffix("One error has occurred. Check the console"));
                                        }
                                    } else {
                                        sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE_IN_WORLD));
                                    }
                                } else {
                                    sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE));
                                }
                            } else {
                                sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                            }
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix("&e Only players"));
                        }
                    } else if (args.length == 6) {
                        if (sender.hasPermission("compassradar.addloc")) {
                            if (Main.getConfiguration().isLocationEnable()) {
                                if (!Main.getConfiguration().getLDW().contains(args[2])) {
                                    try {
                                        CompassLocation.addLocation(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                                        sender.sendMessage(Tools.msgWithPreffix(Lang.ADDLOCATION_SUCCESSFUL));
                                    } catch (Exception exception) {
                                        sender.sendMessage(Tools.msgWithPreffix(exception.getMessage()));
                                    }
                                } else {
                                    sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE_IN_WORLD));
                                }
                            } else {
                                sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE));
                            }
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix("&cArguments unkown. try:\n" + Lang.getHelpMessage()));
                    }
                    return true;
                }
                case "location" -> {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("compassradar.use.locations")) {
                            Player player = (Player) sender;
                            World world = player.getWorld();
                            if (Main.getConfiguration().isLocationEnable()) {
                                if (!Main.getConfiguration().getLDW().contains(world.getName())) {
                                    ItemStack itemStack;
                                    if ((itemStack = player.getInventory().getItemInMainHand()).getType() == Material.COMPASS) {
                                        CompassLocation lt = CompassLocation.getNearestLocation(player);
                                        if (lt == null) {
                                            Tools.msgItemRename(player, itemStack, Tools.msg(Lang.NO_NEAREST));
                                        } else {
                                            player.setCompassTarget(lt.getLocation());
                                            Tools.msgItemRename(player, itemStack, Tools.msg(Lang.NEAREST_LOCATION, lt.getName(), Integer.toString(lt.getDist())));
                                        }
                                    } else {
                                        sender.sendMessage(Tools.msgWithPreffix(Lang.NEED_COMPASS_IN_HAND));
                                    }
                                } else {
                                    sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE_IN_WORLD));
                                }
                            } else {
                                sender.sendMessage(Tools.msgWithPreffix(Lang.LOCATION_TRACKER_DISABLE));
                            }
                        } else {
                            sender.sendMessage(Tools.msgWithPreffix(Lang.NO_PERMISSION));
                        }
                    } else {
                        sender.sendMessage(Tools.msgWithPreffix("&e Only players"));
                    }
                    return true;
                }
            }
            sender.sendMessage(Tools.msgWithPreffix(Lang.NO_SUBCOMMAND));
        }
        return true;
    }
}
