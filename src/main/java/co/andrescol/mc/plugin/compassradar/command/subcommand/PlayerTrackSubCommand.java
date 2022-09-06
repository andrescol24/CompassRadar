package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.Tools;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.configuration.Message;
import co.andrescol.mc.plugin.compassradar.object.TrackedPosition;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class PlayerTrackSubCommand extends ASubCommand {

    public PlayerTrackSubCommand() {
        super("player", "compassradar.cmd.player");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        Player player = (Player) commandSender;
        World world = player.getWorld();

        if (!configuration.isPlayerEnable()) {
            AMessage.sendMessage(commandSender, Message.PLAYER_TRACKER_DISABLE);
            return true;
        } else if (configuration.getPlayerDisableWorlds().contains(world.getName())) {
            AMessage.sendMessage(commandSender, Message.PLAYER_TRACKER_DISABLE_IN_WORLD);
            return true;
        }

        ItemStack compass;
        if ((compass = player.getInventory().getItemInMainHand()).getType() == Material.COMPASS) {
            Optional<TrackedPosition> playerFoundOptional = Tools.getNearestPlayer(world.getPlayers(), player);
            TrackedPosition positionToGo;
            Message message;
            if (playerFoundOptional.isPresent()) {
                positionToGo = playerFoundOptional.get();
                message = Message.NEAREST_PLAYER;
            } else {
                positionToGo = new TrackedPosition("", player, 0, player.getLocation());
                message = Message.NO_NEAREST;
            }
            Tools.showMessageInItem(positionToGo, compass, message);
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
        return null;
    }
}
