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

public class RemoveLocationSubCommand extends ASubCommand {

    public RemoveLocationSubCommand() {
        super("remove", "compassradar.removelocation");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        if (configuration.isLocationEnable()) {
            if (CompassLocation.deleteLoc(args[1])) {
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
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}
