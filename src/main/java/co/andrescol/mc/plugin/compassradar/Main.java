package co.andrescol.mc.plugin.compassradar;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin {
	private static Config config;

	private static Main instance;

	private CompassListener listener;

	private static ConsoleCommandSender ccs;

	private PluginManager pm;

	public void onEnable() {
		instance = this;
		ccs = Bukkit.getConsoleSender();
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		if ((new File(getDataFolder(), "config.yml")).exists())
			reloadConfig();
		Lang.init();
		config = new Config();
		this.pm = getServer().getPluginManager();
		this.listener = new CompassListener();
		Objects.requireNonNull(getCommand("compassradar")).setExecutor(new CompassCommandExecutor());
		this.pm.registerEvents(this.listener, (Plugin) this);
	}

	public void onDisable() {
		PlayerInteractEvent.getHandlerList().unregister(this.listener);
		Lang.finish();
		config = null;
		instance = null;
		ccs = null;
		this.pm = null;
		this.listener = null;
	}

	public static Config getConfiguration() {
		return config;
	}

	public static Main getPlugin() {
		return instance;
	}

	public void reload() {
		onDisable();
		onEnable();
	}

}
