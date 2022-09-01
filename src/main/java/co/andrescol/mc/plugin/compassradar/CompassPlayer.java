package co.andrescol.mc.plugin.compassradar;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public class CompassPlayer {
    private Player player;

    private int dist;

    private String faction;

    private CompassPlayer() {
    }

    public CompassPlayer(Player p, int d, String ToF) {
        this.player = p;
        this.dist = d;
        this.faction = ToF;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getDist() {
        return this.dist;
    }

    public String getFaction() {
        return this.faction;
    }

    public static CompassPlayer getNearest(List<Player> players, Player player) {
        CompassPlayer nearest = new CompassPlayer();
        nearest.dist = (Main.getConfiguration().getMaxPlayerDistance() == 0) ? Integer.MAX_VALUE : Main.getConfiguration().getMaxPlayerDistance();
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
