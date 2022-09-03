package co.andrescol.mc.plugin.compassradar.command.subcommand;

import co.andrescol.mc.library.command.ASubCommand;
import co.andrescol.mc.library.configuration.AMessage;
import co.andrescol.mc.plugin.compassradar.CompassPlayer;
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

import java.util.List;

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
            CompassPlayer playerFound = CompassPlayer.getNearest(world.getPlayers(), player);
            String message;
            if (playerFound != null) {
                player.setCompassTarget(playerFound.getPlayer().getLocation());
                message = AMessage.getMessage(Message.NEAREST_PLAYER, playerFound.getPlayer().getName(), playerFound.getDist());
            } else {
                message = AMessage.getMessage(Message.NO_NEAREST);
            }
            Tools.msgItemRename(player, compass, message);
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
