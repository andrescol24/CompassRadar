package co.andrescol.mc.plugin.compassradar.hook;

import co.andrescol.mc.library.plugin.APlugin;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class HookManager {

    private final List<HookeablePlugin> hooks;

    private HookManager() {
        this.hooks = new LinkedList<>();
        CustomConfiguration configuration = APlugin.getConfigurationObject();
        if (Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null && configuration.isSuperiorSkyBlockEnabled()) {
            this.hooks.add(new SuperiorSkyblockHook());
            APlugin.getInstance().info("Hooked with SuperiorSkyblock2!");
        }
    }

    public List<Player> filterEnemiesByHooks(Player player, List<Player> enemies) {
        for (HookeablePlugin hook : this.hooks) {
            enemies = hook.removeAlies(player, enemies);
        }
        return enemies;
    }

    /*
    ====================================================
    ===================  Singleton  ====================
    ====================================================
     */
    private static HookManager instance;

    public static HookManager getInstance() {
        if (instance == null) {
            instance = new HookManager();
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }
}
