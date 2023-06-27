package co.andrescol.mc.plugin.compassradar.hook;

import org.bukkit.entity.Player;

import java.util.List;

public interface HookeablePlugin {
    List<Player> removeAlies(Player player, List<Player> otherPlayers);
}
