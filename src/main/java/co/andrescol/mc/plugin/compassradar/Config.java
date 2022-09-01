package co.andrescol.mc.plugin.compassradar;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Config {
    private final List<String> playerDisableWorlds = Main.getPlugin().getConfig().getStringList("trackerPlayer.disableInWorlds");
    private final List<String> locationDisbalesWorlds = Main.getPlugin().getConfig().getStringList("trackerLocation.disableInWorlds");
    private final boolean playerEnable = Main.getPlugin().getConfig().getBoolean("trackerPlayer.enable");
    private final boolean locationEnable = Main.getPlugin().getConfig().getBoolean("trackerLocation.enable");
    private final int maxPlayer = Main.getPlugin().getConfig().getInt("trackerPlayer.maxDistance");
    private final int maxLocation = Main.getPlugin().getConfig().getInt("trackerLocation.maxDistance");
    private final boolean onlyNE = Main.getPlugin().getConfig().getBoolean("hooks.factions.onlyNeutralEnemy");
    private HashMap<String, CompassLocation> locations;

    public Config() {
        if (this.locationEnable) {
            this.locations = new HashMap<>();
            ConfigurationSection loc = Main.getPlugin().getConfig().getConfigurationSection("trackerLocation.locations");
            for (String key : loc.getKeys(false))
                this.locations.put(key, new CompassLocation(Objects.requireNonNull(loc.getConfigurationSection(key))));
        }
    }

    public List<String> getPDW() {
        return this.playerDisableWorlds;
    }

    public List<String> getLDW() {
        return this.locationDisbalesWorlds;
    }

    public HashMap<String, CompassLocation> getLocations() {
        return this.locations;
    }

    public boolean isPlayerEnable() {
        return this.playerEnable;
    }

    public boolean isLocationEnable() {
        return this.locationEnable;
    }

    public int getMaxPlayerDistance() {
        return this.maxPlayer;
    }

    public int getMaxLocationDistance() {
        return this.maxLocation;
    }

    public boolean onlyNE() {
        return this.onlyNE;
    }
}
