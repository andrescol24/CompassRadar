package co.andrescol.mc.plugin.compassradar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Tools {
    public static String msg(String text, String... replacement) {
        String output = text;
        if (replacement != null)
            for (int i = 1; i <= replacement.length; i++)
                output = output.replaceFirst("%" + i + "%", replacement[i - 1]);
        return ChatColor.translateAlternateColorCodes('&', output);
    }

    public static String msg(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String msgWithPreffix(String text) {
        return ChatColor.translateAlternateColorCodes('&', Lang.PREFFIX.concat(text));
    }

    public static void msgItemRename(Player p, ItemStack item, String msg) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(msg(msg));
        item.setItemMeta(itemMeta);
        UpdateCompassTask task = new UpdateCompassTask(p, item);
        task.runTaskLater(Main.getPlugin(), 100L);
    }

    public static String msgHook(CompassPlayer player) {
        if (Main.isHookFactions())
            return msg(Lang.FACTIONS, player.getPlayer().getName(), player.getFaction(), Integer.toString(player.getDist()));
        return msg(Lang.NEAREST_PLAYER, player.getPlayer().getName(), Integer.toString(player.getDist()));
    }
}
