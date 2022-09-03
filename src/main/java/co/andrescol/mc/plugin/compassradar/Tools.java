package co.andrescol.mc.plugin.compassradar;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Tools {

    static void msgItemRename(Player p, ItemStack item, String msg) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(msg);
        item.setItemMeta(itemMeta);
        UpdateCompassTask task = new UpdateCompassTask(p, item);
        task.runTaskLater(CompassRadarPlugin.getInstance(), 100L);
    }
}
