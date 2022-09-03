package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassLocation;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.Tools;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LocationTrackSubCommand extends ASubCommand {

    public LocationTrackSubCommand() {
        super("location", "compassradar.use.locations");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
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
            CompassLocation nearestLocation = CompassLocation.getNearestLocation(player);
            String message;
            if (nearestLocation != null) {
                player.setCompassTarget(nearestLocation.getLocation());
                message = AMessage.getMessage(Message.NEAREST_LOCATION, nearestLocation.getName(), nearestLocation.getDist());
            } else {
                message = AMessage.getMessage(Message.NO_NEAREST);
            }
            Tools.msgItemRename(player, itemStack, message);
        } else {
            AMessage.sendMessage(commandSender, Message.NEED_COMPASS_IN_HAND);
        }
        return true;
    }

    @Override
    public boolean goodUsage(CommandSender sender, Command command, String label, String[] args) {
        return sender instanceof Player;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}
