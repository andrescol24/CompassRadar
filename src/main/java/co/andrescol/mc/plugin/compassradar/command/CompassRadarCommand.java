package co.andrescol.mc.plugin.compassradar.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import co.andrescol.mc.library.command.AMainCommand;
import org.jetbrains.annotations.NotNull;

public class CompassRadarCommand extends AMainCommand {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		return this.handle(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
		return this.completeTab(sender, command, alias, args);
	}

}
