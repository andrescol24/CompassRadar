package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassLocation;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListLocationsSubCommand extends ASubCommand {

    public ListLocationsSubCommand() {
        super("locations", "compassradar.listlocation");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable()) {
            String listString = CompassLocation.getList();
            if (listString != null)
                AMessage.sendMessage(commandSender, Message.LOCATIONS, CompassLocation.getList());
            else
                AMessage.sendMessage(commandSender, Message.NO_LOCATIONS_FOUND);
        } else {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE);
        }
        return true;
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        return new ArrayList<>();
    }
}
