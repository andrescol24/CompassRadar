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

public class RemoveLocationSubCommand extends ASubCommand {

    public RemoveLocationSubCommand() {
        super("remove", "compassradar.cmd.removelocation");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable()) {
            if (CompassLocationData.getInstance().deleteCompassLocation(args[1])) {
                AMessage.sendMessage(commandSender, Message.REMOVE_LOCATION_SUCCESSFUL, args[1]);
            } else {
                AMessage.sendMessage(commandSender, Message.REMOVE_LOCATION_LOCATION_DOES_NOT_EXIST, args[1]);
            }
        } else {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE);
        }
        return true;
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 2;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
