package co.andrescol.mc.plugin.compassradar.Hooks;

import co.andrescol.mc.plugin.compassradar.CompassPlayer;
import co.andrescol.mc.plugin.compassradar.Main;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionsHook {

    public static CompassPlayer getNearest(List<Player> players, Player player) {
        int dist;
        String faction = null;
        Player playerN = null;
        dist = (Main.getConfiguration().getMaxPlayerDistance() == 0) ? Integer.MAX_VALUE : Main.getConfiguration().getMaxPlayerDistance();
        MPlayer mPlayer = MPlayer.get(player);
        Faction fP, fPlayer = mPlayer.getFaction();
        for (Player p : players) {
            if (p != player) {
                MPlayer mP = MPlayer.get(p);
                fP = mP.getFaction();
                if (p.getGameMode().equals(GameMode.SURVIVAL)) {
                    if (Main.getConfiguration().onlyNE()) {
                        if (fPlayer.getRelationTo(fP) == Rel.NEUTRAL || fPlayer.getRelationTo(fP) == Rel.ENEMY) {
                            int i = (int) p.getLocation().distance(player.getLocation());
                            if (i < dist) {
                                dist = i;
                                playerN = p;
                                faction = fP.getName();
                            }
                        }
                        continue;
                    }
                    int aux = (int) p.getLocation().distance(player.getLocation());
                    if (aux < dist) {
                        dist = aux;
                        playerN = p;
                        faction = fP.getName();
                    }
                }
            }
        }
        if (playerN != null)
            return new CompassPlayer(playerN, dist, faction);
        return null;
    }
}
