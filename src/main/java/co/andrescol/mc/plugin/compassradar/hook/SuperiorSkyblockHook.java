package co.andrescol.mc.plugin.compassradar.hook;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.entity.Player;

import java.util.List;

public class SuperiorSkyblockHook implements HookeablePlugin {

    @Override
    public List<Player> removeAlies(Player player, List<Player> otherPlayers) {
        Island island = SuperiorSkyblockAPI.getPlayer(player).getIsland();
        if (island != null) {
            return otherPlayers.stream()
                    .filter( p -> !island.isMember(SuperiorSkyblockAPI.getPlayer(p))).toList();
        }
        return otherPlayers;
    }
}
