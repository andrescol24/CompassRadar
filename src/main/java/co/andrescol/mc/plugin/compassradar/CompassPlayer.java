package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public class CompassPlayer {
    private Player player;

    private int dist;

    private CompassPlayer() {
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getDist() {
        return this.dist;
    }


    public static CompassPlayer getNearest(List<Player> players, Player player) {
        CompassPlayer nearest = new CompassPlayer();
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        nearest.dist = (configuration.getMaxPlayer() == 0) ? Integer.MAX_VALUE : configuration.getMaxPlayer();
        for (Player p : players) {
            if (p != player &&
                    p.getGameMode().equals(GameMode.SURVIVAL)) {
                int aux = (int) p.getLocation().distance(player.getLocation());
                if (aux < nearest.dist) {
                    nearest.dist = aux;
                    nearest.player = p;
                }
            }
        }
        if (nearest.player != null)
            return nearest;
        return null;
    }
}
