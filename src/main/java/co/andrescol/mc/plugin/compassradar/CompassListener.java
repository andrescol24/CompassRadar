package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CompassListener implements Listener {
    @EventHandler
    public void onCompassTracker(PlayerInteractEvent e) {
        if (!e.getPlayer().hasPermission("compassradar.use")) return;

        Player player = e.getPlayer();
        World world = player.getWorld();
        CompassLocation nearestLocation = null;
        CompassPlayer nearestPlayer = null;
        ItemStack compass = player.getInventory().getItemInMainHand();

        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable() && !configuration.getPlayerDisableWorlds().contains(world.getName())) {
            nearestLocation = CompassLocation.getNearestLocation(player);
        }

        if (configuration.isPlayerEnable() && !configuration.getLocationDisableWorlds().contains(world.getName())) {
            nearestPlayer = CompassPlayer.getNearest(world.getPlayers(), player);
        }

        String message;
        if (nearestLocation != null && nearestPlayer != null) {
            if (nearestPlayer.getDist() < nearestLocation.getDist()) {
                player.setCompassTarget(nearestPlayer.getPlayer().getLocation());
                message = AMessage.getMessage(Message.NEAREST_PLAYER, nearestPlayer.getPlayer().getName(), nearestPlayer.getDist());
            } else {
                player.setCompassTarget(nearestLocation.getLocation());
                message = AMessage.getMessage(Message.NEAREST_LOCATION, nearestLocation.getName(), nearestLocation.getDist());
            }
        } else if (nearestPlayer != null) {
            player.setCompassTarget(nearestPlayer.getPlayer().getLocation());
            message = AMessage.getMessage(Message.NEAREST_PLAYER, nearestPlayer.getPlayer().getName(), nearestPlayer.getDist());
        } else if (nearestLocation != null) {
            player.setCompassTarget(nearestLocation.getLocation());
            message = AMessage.getMessage(Message.NEAREST_LOCATION, nearestLocation.getName(), nearestLocation.getDist());
        } else {
            message = AMessage.getMessage(Message.NO_NEAREST);
        }
        Tools.msgItemRename(player, compass, message);
    }
}
