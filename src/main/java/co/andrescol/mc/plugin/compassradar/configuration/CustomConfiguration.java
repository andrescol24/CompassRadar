package co.andrescol.mc.plugin.compassradar.configuration;

import co.andrescol.mc.library.configuration.AConfigurationKey;
import co.andrescol.mc.library.configuration.AConfigurationObject;
import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.object.CompassLocation;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    private HashMap<String, CompassLocation> locations;

    @Override
    public void setValues() {
        super.setValues();
        if (this.locationEnable) {
            this.locations = new HashMap<>();
            ConfigurationSection loc = CompassRadarPlugin.getInstance().getConfig().getConfigurationSection("trackerLocation.locations");
            for (String key : loc.getKeys(false))
                this.locations.put(key, new CompassLocation(Objects.requireNonNull(loc.getConfigurationSection(key))));
        }
    }

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

    public HashMap<String, CompassLocation> getLocations() {
        return locations;
    }
}
