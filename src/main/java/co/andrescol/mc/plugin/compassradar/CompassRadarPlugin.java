package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.library.plugin.APlugin;
import co.andrescol.mc.plugin.compassradar.command.CompassRadarCommand;
import org.bukkit.event.HandlerList;

public class CompassRadarPlugin extends APlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("compassradar").setExecutor(new CompassRadarCommand());
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
}
