package co.andrescol.mc.plugin.compassradar.data;

import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import co.andrescol.mc.plugin.compassradar.Tools;
import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import co.andrescol.mc.plugin.compassradar.object.CompassLocation;
import co.andrescol.mc.plugin.compassradar.object.TrackedPosition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CompassLocationData {

    private List<CompassLocation> locations;
    private static final String LOCATIONS_FILE_NAME = "locations.yml";
    private static final String LOCATIONS_SECTION = "locations";

    private CompassLocationData() {
        try {
            File file = new File(CompassRadarPlugin.getInstance().getDataFolder(), LOCATIONS_FILE_NAME);
            if (!file.exists()) {
                CompassRadarPlugin.getInstance().saveResource(LOCATIONS_FILE_NAME, false);
            }
            FileConfiguration yaml = new YamlConfiguration();
            yaml.load(file);
            ConfigurationSection section = yaml.getConfigurationSection(LOCATIONS_SECTION);

            this.locations = new LinkedList<>();
            if (section != null) {
                section.getKeys(false).forEach(key -> locations.add(new CompassLocation(Objects.requireNonNull(section.getConfigurationSection(key)))));
            }
        } catch (Exception e) {
            CompassRadarPlugin.getInstance().error("Error initializing CompassLocationData", e);
        }
    }

    public Optional<TrackedPosition> getNearestLocation(Player player) {
        CompassLocation nearestLocation = null;
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        int nearestDistance = (configuration.getMaxLocation() == 0) ? Integer.MAX_VALUE : configuration.getMaxLocation();

        for (CompassLocation compassLocation : this.locations) {
            int actualDistance = Tools.calculateDistance(player.getLocation(), compassLocation.getLocation());
            if (actualDistance < nearestDistance) {
                nearestDistance = actualDistance;
                nearestLocation = compassLocation;
            }
        }
        return nearestLocation != null
                ? Optional.of(new TrackedPosition(nearestLocation.getName(), player, nearestDistance, nearestLocation.getLocation()))
                : Optional.empty();
    }

    public boolean addCompassLocation(CompassLocation compassLocation) {
        if (this.locations.contains(compassLocation)) return false;
        this.locations.add(compassLocation);
        this.saveCompassLocation(compassLocation);
        return true;
    }

    public boolean deleteCompassLocation(String name) {
        try {
            if (this.locations.removeIf(x -> x.getName().equals(name))) {
                File file = new File(CompassRadarPlugin.getInstance().getDataFolder(), LOCATIONS_FILE_NAME);
                FileConfiguration yaml = new YamlConfiguration();
                yaml.load(file);
                ConfigurationSection section = yaml.getConfigurationSection(LOCATIONS_SECTION);
                section.set(name, null);
                yaml.save(file);
                return true;
            }
        } catch (Exception e) {
            CompassRadarPlugin.getInstance().error("Error deleting compass location: {}", e, name);
        }
        return false;
    }

    private void saveCompassLocation(CompassLocation compassLocation) {
        try {
            File file = new File(CompassRadarPlugin.getInstance().getDataFolder(), LOCATIONS_FILE_NAME);
            FileConfiguration yaml = new YamlConfiguration();
            yaml.load(file);
            ConfigurationSection section = yaml.getConfigurationSection(LOCATIONS_SECTION);
            if (section == null) section = yaml.createSection(LOCATIONS_SECTION);
            ConfigurationSection compassLocationSection = section.createSection(compassLocation.getName());
            compassLocationSection.set("x", compassLocation.getLocation().getX());
            compassLocationSection.set("y", compassLocation.getLocation().getY());
            compassLocationSection.set("z", compassLocation.getLocation().getZ());
            compassLocationSection.set("world", compassLocation.getLocation().getWorld().getName());
            yaml.save(file);
        } catch (Exception e) {
            CompassRadarPlugin.getInstance().error("Error saving new compass location: {}", e, compassLocation.getName());
        }
    }

    public String getListOfCompassLocationAsString() {
        if (this.locations.isEmpty()) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            this.locations.forEach(x -> builder.append(x.toString()).append("\n"));
            return builder.toString();
        }
    }

    /*
    ===================================================== SINGLETON =======================================================================
     */

    private static CompassLocationData instance;

    public static CompassLocationData getInstance() {
        if (instance == null)
            instance = new CompassLocationData();
        return instance;
    }
}
