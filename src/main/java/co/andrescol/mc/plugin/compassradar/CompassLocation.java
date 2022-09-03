package co.andrescol.mc.plugin.compassradar;

import co.andrescol.mc.plugin.compassradar.configuration.CustomConfiguration;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CompassLocation {
    private final String name;
    private final Location location;

    private int dist;

    public CompassLocation(ConfigurationSection section) {
        this.name = section.getName();
        this.location = new Location(CompassRadarPlugin.getInstance().getServer().getWorld(Objects.requireNonNull(section.getString("world"))),
                section.getInt("x"), section.getInt("y"), section.getInt("z"));
    }

    public CompassLocation(Player player, String name) {
        this.name = name;
        this.location = player.getLocation();
    }

    public CompassLocation(String name, String world, int x, int y, int z) {
        this.name = name;
        this.location = new Location(CompassRadarPlugin.getInstance().getServer().getWorld(world), x, y, z);
    }

    public static CompassLocation getNearestLocation(Player p) {
        CompassLocation nearest = null;
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        int dist = (configuration.getMaxLocation() == 0) ? Integer.MAX_VALUE : configuration.getMaxLocation();
        Set<Map.Entry<String, CompassLocation>> set = configuration.getLocations().entrySet();
        for (Map.Entry<String, CompassLocation> entry : set) {
            CompassLocation l = entry.getValue();
            int aux = (int) p.getLocation().distance(l.location);
            if (aux < dist) {
                dist = aux;
                nearest = l;
                nearest.dist = dist;
            }
        }
        return nearest;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public static boolean addLocation(Player player, String name) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        HashMap<String, CompassLocation> list = configuration.getLocations();
        if (list.containsKey(name))
            return false;
        CompassLocation cl = new CompassLocation(player, name);
        list.put(name, cl);
        cl.save();
        return true;
    }

    public static boolean addLocation(String name, String world, int x, int y, int z) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        HashMap<String, CompassLocation> list = configuration.getLocations();
        if (CompassRadarPlugin.getInstance().getServer().getWorld(world) == null)
            return false;
        if (list.containsKey(name))
            return false;
        CompassLocation cl = new CompassLocation(name, world, x, y, z);
        list.put(name, cl);
        cl.save();
        return true;
    }

    public void save() {
        ConfigurationSection section = CompassRadarPlugin.getInstance().getConfig().getConfigurationSection("trackerLocation.locations");
        ConfigurationSection loc = section.createSection(this.name);
        loc.set("x", this.location.getBlockX());
        loc.set("y", this.location.getBlockY());
        loc.set("z", this.location.getBlockZ());
        loc.set("world", Objects.requireNonNull(this.location.getWorld()).getName());
        CompassRadarPlugin.getInstance().saveConfig();
    }

    public static boolean deleteLoc(String name) {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        HashMap<String, CompassLocation> l = configuration.getLocations();
        if (l.containsKey(name)) {
            l.remove(name);
            ConfigurationSection section = CompassRadarPlugin.getInstance().getConfig().getConfigurationSection("trackerLocation.locations");
            if (section != null) {
                section.set(name, null);
                CompassRadarPlugin.getInstance().saveConfig();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getList() {
        CustomConfiguration configuration = CompassRadarPlugin.getConfigurationObject();
        HashMap<String, CompassLocation> list = configuration.getLocations();
        Set<Map.Entry<String, CompassLocation>> set = list.entrySet();
        if (list.isEmpty())
            return null;
        String msg = "&7--------&e[&7List Of Locations&e]&7--------\n";
        for (Map.Entry<String, CompassLocation> e : set)
            msg = e.getValue().toString() + "\n";
        return msg;
    }

    public String toString() {
        return "&e" + this.name + ": " + "&7" + Objects.requireNonNull(this.location.getWorld()).getName() + "/" + this.location.getBlockX() + "/" + this.location.getY() + "/" + this.location.getBlockZ();
    }

    public boolean equals(Object e) {
        if (e instanceof CompassLocation cl) {
            String name = cl.name;
            return this.name.equals(name);
        }
        if (e instanceof String nameOther) {
            return this.name.equals(nameOther);
        }
        return false;
    }

    public int hashCode() {
        int hash = 3;
        hash = 7 * hash + Objects.hash(this.name);
        hash = 7 * hash + Objects.hash(this.location);
        return hash;
    }

    public int getDist() {
        return this.dist;
    }
}
