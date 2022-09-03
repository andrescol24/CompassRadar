package co.andrescol.mc.plugin.compassradar.command;

import co.andrescol.mc.library.command.AMainCommand;
import co.andrescol.mc.plugin.compassradar.command.subcommand.*;

public class CompassCommandExecutor extends AMainCommand {

    public CompassCommandExecutor() {
        addSubCommand(new ListLocationsSubCommand());
        addSubCommand(new PlayerTrackSubCommand());
        addSubCommand(new RemoveLocationSubCommand());
        addSubCommand(new AddLocationSubCommand());
        addSubCommand(new LocationTrackSubCommand());
    }
}
