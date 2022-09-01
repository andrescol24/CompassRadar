package co.andrescol.mc.plugin.compassradar;

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
        this.location = new Location(Main.getPlugin().getServer().getWorld(Objects.requireNonNull(section.getString("world"))),
                section.getInt("x"), section.getInt("y"), section.getInt("z"));
    }

    public CompassLocation(Player player, String name) {
        this.name = name;
        this.location = player.getLocation();
    }

    public CompassLocation(String name, String world, int x, int y, int z) {
        this.name = name;
        this.location = new Location(Main.getPlugin().getServer().getWorld(world), x, y, z);
    }

    public static CompassLocation getNearestLocation(Player p) {
        CompassLocation nearest = null;
        int dist = (Main.getConfiguration().getMaxLocationDistance() == 0) ? Integer.MAX_VALUE : Main.getConfiguration().getMaxLocationDistance();
        Set<Map.Entry<String, CompassLocation>> set = Main.getConfiguration().getLocations().entrySet();
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

    public static void addLocation(Player player, String name) throws Exception {
        HashMap<String, CompassLocation> list = Main.getConfiguration().getLocations();
        if (list.containsKey(name))
            throw new Exception("this location with this name already exist");
        CompassLocation cl = new CompassLocation(player, name);
        list.put(name, cl);
        cl.save();
    }

    public static void addLocation(String name, String world, int x, int y, int z) throws Exception {
        HashMap<String, CompassLocation> list = Main.getConfiguration().getLocations();
        if (Main.getPlugin().getServer().getWorld(world) == null)
            throw new Exception("&cThe world " + world + " doesnt exist");
        if (list.containsKey(name))
            throw new Exception("&cthis location with this name already exist");
        CompassLocation cl = new CompassLocation(name, world, x, y, z);
        list.put(name, cl);
        cl.save();
    }

    public void save() {
        ConfigurationSection section = Main.getPlugin().getConfig().getConfigurationSection("trackerLocation.locations");
        ConfigurationSection loc = section.createSection(this.name);
        loc.set("x", this.location.getBlockX());
        loc.set("y", this.location.getBlockY());
        loc.set("z", this.location.getBlockZ());
        loc.set("world", Objects.requireNonNull(this.location.getWorld()).getName());
        Main.getPlugin().saveConfig();
    }

    public static void deleteLoc(String name) throws Exception {
        HashMap<String, CompassLocation> l = Main.getConfiguration().getLocations();
        if (l.containsKey(name)) {
            l.remove(name);
            ConfigurationSection section = Main.getPlugin().getConfig().getConfigurationSection("trackerLocation.locations");
            if (section != null) {
                section.set(name, null);
                Main.getPlugin().saveConfig();
            } else {
                throw new Exception("The section trackerLocation.locations." + name + "doesnt exist, verific your config.yml");
            }
        } else {
            throw new Exception("The location with name " + name + " doesnt exist");
        }
    }

    public static String getList() {
        HashMap<String, CompassLocation> list = Main.getConfiguration().getLocations();
        Set<Map.Entry<String, CompassLocation>> set = list.entrySet();
        if (list.isEmpty())
            return Lang.PREFFIX + "&cThere are not Locations";
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
