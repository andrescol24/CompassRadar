package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.data.CompassLocationData;
import co.andrescol.mc.plugin.compassradar.object.TrackedPosition;
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
        if (!e.getPlayer().hasPermission("compassradar.use")) return;
        if (Material.COMPASS != e.getPlayer().getInventory().getItemInMainHand().getType()) return;

        Player player = e.getPlayer();
        World world = player.getWorld();
        TrackedPosition nearestLocation = null;
        TrackedPosition nearestPlayer = null;
        ItemStack compass = player.getInventory().getItemInMainHand();

        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable() && !configuration.getPlayerDisableWorlds().contains(world.getName())) {
            nearestLocation = CompassLocationData.getInstance().getNearestLocation(player).orElse(null);
        }

        if (configuration.isPlayerEnable() && !configuration.getLocationDisableWorlds().contains(world.getName())) {
            nearestPlayer = Tools.getNearestPlayer(world.getPlayers(), player).orElse(null);
        }

        Message message;
        TrackedPosition positionToGo;
        if (nearestLocation != null && nearestPlayer != null) {
            if (nearestPlayer.distance() < nearestLocation.distance()) {
                positionToGo = nearestPlayer;
                message = Message.NEAREST_PLAYER;
            } else {
                positionToGo = nearestLocation;
                message = Message.NEAREST_LOCATION;
            }
        } else if (nearestPlayer != null) {
            positionToGo = nearestPlayer;
            message = Message.NEAREST_PLAYER;
        } else if (nearestLocation != null) {
            positionToGo = nearestLocation;
            message = Message.NEAREST_LOCATION;
        } else {
            positionToGo = new TrackedPosition("", player, 0, player.getLocation());
            message = Message.NO_NEAREST;
        }
        Tools.showMessageInItem(positionToGo, compass, message);
    }
}
