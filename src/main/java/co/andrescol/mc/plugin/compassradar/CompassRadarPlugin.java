package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.library.plugin.APlugin;
import co.andrescol.mc.plugin.compassradar.command.CompassCommandExecutor;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.hook.HookManager;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class CompassRadarPlugin extends APlugin<CustomConfiguration> {
	private CompassListener listener;

	public void onEnable() {
		this.listener = new CompassListener();
		Objects.requireNonNull(getCommand("compassradar")).setExecutor(new CompassCommandExecutor());
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(this.listener, this);
	}

	public void onDisable() {
		PlayerInteractEvent.getHandlerList().unregister(this.listener);
		this.listener = null;
		HookManager.destroy();
	}

	@Override
	protected void initializeCustomConfiguration() {
		this.configurationObject = new CustomConfiguration();
	}
}
