package co.andrescol.mc.plugin.compassradar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class CleanCompassNameTask extends BukkitRunnable {
    private final ItemStack compass;
    private final Player player;

    public CleanCompassNameTask(Player player, ItemStack item) {
        this.compass = item;
        this.player = player;
    }

    public void run() {
        if (!this.player.getInventory().getItemInMainHand().equals(this.compass)) {
            cancel();
        } else {
            ItemStack help = new ItemStack(Material.COMPASS);
            ItemMeta itemMeta = this.compass.getItemMeta();
            itemMeta.setDisplayName(Objects.requireNonNull(help.getItemMeta()).getDisplayName());
            this.compass.setItemMeta(itemMeta);
        }
    }
}
