package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.CompassTracker;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.object.TrackedPosition;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationTrackSubCommand extends ASubCommand {

    public LocationTrackSubCommand() {
        super("location", "compassradar.cmd.location");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        Player player = (Player) commandSender;
        World world = player.getWorld();

        if (!configuration.isPlayerEnable()) {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE);
            return true;
        } else if (configuration.getLocationDisableWorlds().contains(world.getName())) {
            AMessage.sendMessage(commandSender, Message.LOCATION_TRACKER_DISABLE_IN_WORLD);
            return true;
        }

        ItemStack itemStack;
        if ((itemStack = player.getInventory().getItemInMainHand()).getType() == Material.COMPASS) {
            Optional<TrackedPosition> nearestLocationOptional = CompassTracker.getNearestLocation(player);
            TrackedPosition positionToGo;
            Message message;
            if (nearestLocationOptional.isPresent()) {
                positionToGo = nearestLocationOptional.get();
                message = Message.NEAREST_LOCATION;
            } else {
                positionToGo = new TrackedPosition("", player, 0, player.getLocation());
                message = Message.NO_NEAREST;
            }
            CompassTracker.showMessageInItem(positionToGo, itemStack, message);
        } else {
            AMessage.sendMessage(commandSender, Message.NEED_COMPASS_IN_HAND);
        }
        return true;
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return sender instanceof Player;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
