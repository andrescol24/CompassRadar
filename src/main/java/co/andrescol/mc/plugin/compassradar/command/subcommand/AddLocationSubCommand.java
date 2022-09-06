package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.data.CompassLocationData;
import co.andrescol.mc.plugin.compassradar.object.CompassLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddLocationSubCommand extends ASubCommand {

    public AddLocationSubCommand() {
        super("add", "  compassradar.cmd.addlocation");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        World world = args.length == 2 ? ((Player) commandSender).getWorld() : Bukkit.getWorld(args[2]);

        if (!configuration.isLocationEnable()) {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE);
            return true;
        } else if (world == null) {
            AMessage.sendMessage(commandSender, Message.WORLD_NOT_EXISTS);
            return true;
        } else if (configuration.getLocationDisableWorlds().contains(world.getName())) {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE_IN_WORLD);
            return true;
        }

        CompassLocation compassLocation;
        if (args.length == 2) { // Add location with current location
            Player player = (Player) commandSender;
            compassLocation = new CompassLocation(player, args[1]);
        } else { // Add specific location
            compassLocation = new CompassLocation(args[1], args[2],
                    Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
        }

        if (CompassLocationData.getInstance().addCompassLocation(compassLocation)) {
            AMessage.sendMessage(commandSender, Message.ADD_LOCATION_SUCCESSFUL);
        } else {
            AMessage.sendMessage(commandSender, Message.ADD_LOCATION_ALREADY_EXISTS, args[1]);
        }
        return true;
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return (args.length == 2 && sender instanceof Player) || args.length == 6;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
