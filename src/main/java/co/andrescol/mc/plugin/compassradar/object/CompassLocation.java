package co.andrescol.mc.plugin.compassradar.object;

import co.andrescol.mc.plugin.compassradar.CompassRadarPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CompassLocation {
    private final String name;
    private final Location location;

    public CompassLocation(ConfigurationSection section) {
        this.name = section.getName();
        World world = CompassRadarPlugin.getInstance().getServer().getWorld(Objects.requireNonNull(section.getString("world")));
        this.location = new Location(world, section.getInt("x"), section.getInt("y"), section.getInt("z"));
    }

    public CompassLocation(Player player, String name) {
        this.name = name;
        this.location = player.getLocation();
    }

    public CompassLocation(String name, String world, int x, int y, int z) {
        this.name = name;
        this.location = new Location(CompassRadarPlugin.getInstance().getServer().getWorld(world), x, y, z);
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
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

    public String toString() {
        return "&e" + this.name + ": " + "&7" + Objects.requireNonNull(this.location.getWorld()).getName() + " /" + this.location.getBlockX() + "/" + this.location.getY() + "/" + this.location.getBlockZ();
    }
}
