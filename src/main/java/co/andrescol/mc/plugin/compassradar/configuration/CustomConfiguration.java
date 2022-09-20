package co.andrescol.mc.plugin.compassradar.configuration;

import co.andrescol.mc.library.configuration.AConfigurationKey;
import co.andrescol.mc.library.configuration.AConfigurationObject;

import java.util.List;

public class CustomConfiguration extends AConfigurationObject {
    @AConfigurationKey("trackerPlayer.disableInWorlds")
    private List<String> playerDisableWorlds;

    @AConfigurationKey("trackerLocation.disableInWorlds")
    private List<String> locationDisableWorlds;
    @AConfigurationKey("trackerPlayer.enable")
    private boolean playerEnable;

    @AConfigurationKey("trackerLocation.enable")
    private boolean locationEnable;

    @AConfigurationKey("trackerPlayer.maxDistance")
    private int maxPlayer;

    @AConfigurationKey("trackerLocation.maxDistance")
    private int maxLocation;

    @AConfigurationKey("trackerPlayer.playerNameUntil")
    private int playerNameUntil;

    @AConfigurationKey("trackerPlayer.stopTrackingAt")
    private int stopTrackingAt;

    public List<String> getPlayerDisableWorlds() {
        return playerDisableWorlds;
    }

    public List<String> getLocationDisableWorlds() {
        return locationDisableWorlds;
    }

    public boolean isPlayerEnable() {
        return playerEnable;
    }

    public boolean isLocationEnable() {
        return locationEnable;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getMaxLocation() {
        return maxLocation;
    }

    public int getPlayerNameUntil() {
        return playerNameUntil;
    }

    public int getStopTrackingAt() {
        return stopTrackingAt;
    }
}
