package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.plugin.compassradar.Hooks.FactionsHook;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CompassListener implements Listener {
    @EventHandler
    public void onCompassTracker(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        CompassLocation location;
        CompassPlayer target;
        ItemStack compass = player.getInventory().getItemInMainHand();
        if (player.hasPermission("compassradar.use") && compass.getType() == Material.COMPASS) {
            if (Main.getConfiguration().getPDW().contains(world.getName()) || Main.getConfiguration().getLDW().contains(world.getName()))
                return;
            target = workPlayer(world, player);
            location = workLocation(player);
            if (target != null && location != null) {
                if (target.getDist() < location.getDist()) {
                    player.setCompassTarget(target.getPlayer().getLocation());
                    Tools.msgItemRename(player, compass, Tools.msgHook(target));
                } else {
                    player.setCompassTarget(location.getLocation());
                    Tools.msgItemRename(player, compass, Tools.msg(Lang.NEAREST_LOCATION, location.getName(), Integer.toString(location.getDist())));
                }
            } else if (target != null) {
                player.setCompassTarget(target.getPlayer().getLocation());
                Tools.msgItemRename(player, compass, Tools.msgHook(target));
            } else if (location != null) {
                player.setCompassTarget(location.getLocation());
                Tools.msgItemRename(player, compass, Tools.msg(Lang.NEAREST_LOCATION, location.getName(), Integer.toString(location.getDist())));
            } else {
                Tools.msgItemRename(player, compass, Lang.NO_NEAREST);
            }
        }
    }

    private CompassPlayer workPlayer(World world, Player player) {
        if (Main.getConfiguration().isPlayerEnable()) {
            CompassPlayer nearest;
            if (Main.isHookFactions()) {
                nearest = FactionsHook.getNearest(world.getPlayers(), player);
            } else {
                nearest = CompassPlayer.getNearest(world.getPlayers(), player);
            }
            return nearest;
        }
        return null;
    }

    private CompassLocation workLocation(Player player) {
        if (Main.getConfiguration().isLocationEnable()) {
            return CompassLocation.getNearestLocation(player);
        }
        return null;
    }
}
