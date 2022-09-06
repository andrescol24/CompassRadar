package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.data.CompassLocationData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListLocationsSubCommand extends ASubCommand {

    public ListLocationsSubCommand() {
        super("locations", "compassradar.cmd.listlocation");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable()) {
            String listString = CompassLocationData.getInstance().getListOfCompassLocationAsString();
            if (listString != null)
                AMessage.sendMessage(commandSender, Message.LOCATIONS, listString);
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
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
