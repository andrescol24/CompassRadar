package co.andrescol.mc.plugin.compassradar.object;

import org.bukkit.entity.Player;

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
}
