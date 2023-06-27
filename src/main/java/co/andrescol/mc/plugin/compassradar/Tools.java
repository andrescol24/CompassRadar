package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.hook.HookManager;
import co.andrescol.mc.plugin.compassradar.object.TrackedPosition;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

public interface Tools {

    static void showMessageInItem(TrackedPosition trackedPosition, ItemStack item, Message message) {
        int distance = Tools.calculateDistance(trackedPosition.player().getLocation(), trackedPosition.destination());
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();

        String messageToShow;
        if (message == Message.NEAREST_PLAYER && distance < configuration.getPlayerNameUntil()) {
            messageToShow = AMessage.getMessage(Message.NEAREST_PLAYER_WITHOUT_NAME, distance);
        } else {
            messageToShow = AMessage.getMessage(message, trackedPosition.destinationName(), distance);
        }

        trackedPosition.player().setCompassTarget(trackedPosition.destination());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(messageToShow);
        item.setItemMeta(itemMeta);

        CleanCompassNameTask task = new CleanCompassNameTask(trackedPosition.player(), item);
        task.runTaskLater(CompassRadarPlugin.getInstance(), 200);
    }

    static int calculateDistance(Location from, Location to) {
        from.setY(0);
        to.setY(0);
        return (int) from.distance(to);
    }

    static Optional<TrackedPosition> getNearestPlayer(List<Player> enemies, Player player) {
        enemies = HookManager.getInstance().filterEnemiesByHooks(player, enemies);
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        Player nearestPlayer = null;
        int nearestDistance = (configuration.getMaxPlayer() == 0) ? Integer.MAX_VALUE : configuration.getMaxPlayer();
        for (Player otherPlayer : enemies) {
            int distance = calculateDistance(otherPlayer.getLocation(), player.getLocation());
            if (otherPlayer != player && otherPlayer.getGameMode().equals(GameMode.SURVIVAL)) {
                if (distance < nearestDistance && distance > configuration.getStopTrackingAt()) {
                    nearestDistance = distance;
                    nearestPlayer = otherPlayer;
                }
            }
        }
        if (nearestPlayer != null)
            return Optional.of(new TrackedPosition(nearestPlayer.getName(), player, nearestDistance, nearestPlayer.getLocation()));
        return Optional.empty();
    }
}
