package co.andrescol.mc.plugin.compassradar.object;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record TrackedPosition(String destinationName, Player player, int distance, Location destination) {

}
