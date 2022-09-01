package co.andrescol.mc.plugin.compassradar;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public abstract class Lang {
    protected static YamlConfiguration resource;

    protected static String RELOADED = "&eReload successful.";

    protected static String NO_SUBCOMMAND = "&eSubcommand unknown";

    public static String NO_PERMISSION;

    public static String PREFFIX;

    public static String NEAREST_PLAYER;

    public static String NO_NEAREST;

    public static String NEAREST_LOCATION;

    public static String ADDLOCATION_SUCCESSFUL;

    public static String REMOVELOCATION_SUCCESSFUL;

    public static String NEED_COMPASS_IN_HAND;

    public static String PLAYER_TRACKER_DISABLE;

    public static String PLAYER_TRACKER_DISABLE_IN_WORLD;

    public static String LOCATION_TRACKER_DISABLE;

    public static String LOCATION_TRACKER_DISABLE_IN_WORLD;

    public static String TEAM_SKYWARS;

    public static String FACTIONS;

    public static String CMD_RELOAD;

    public static String CMD_PLAYER;

    public static String CMD_LOCATION;

    public static String CMD_ADD;

    public static String CMD_ADD_RELATIVE;

    public static String CMD_LOCATIONS;

    public static String CMD_REMOVE;

    public static String HELP_BEGIN;

    public static String HELP_END;

    private static String HELP;

    public static void init() {
        initResource();
        initStrings();
        HELP = "&7---------" + PREFFIX + "&7----------\n" +
                HELP_BEGIN +
                "\n&7compass reload: " + CMD_RELOAD +
                "\n&7compass player: " + CMD_PLAYER +
                "\n&7compass location: " + CMD_LOCATION +
                "\n&7compass add <name>: " + CMD_ADD +
                "\n&7compass add <name> <world> <x> <y> <z>: " + CMD_ADD_RELATIVE +
                "\n&7compass remove <name>: " + CMD_REMOVE +
                "\n&7compass locations: " + CMD_LOCATIONS + "\n" +
                HELP_END;
    }

    public static void initResource() {
        File file = new File(Main.getPlugin().getDataFolder(), "lang.yml");
        if (!file.exists()) {
            Main.getPlugin().saveResource("lang.yml", false);
            file = new File(Main.getPlugin().getDataFolder(), "lang.yml");
        }
        resource = new YamlConfiguration();
        try {
            resource.load(file);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void initStrings() {
        byte b;
        int i;
        Field[] arrayOfField;
        for (i = (arrayOfField = Lang.class.getFields()).length, b = 0; b < i; ) {
            Field field = arrayOfField[b];
            if (field.getType().equals(String.class) && Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                try {
                    if (name.equals("HELP_BEGIN")) {
                        HELP_BEGIN = "";
                        List<String> list = resource.getStringList(name);
                        int size = list.size();
                        for (int j = 0; j < size; j++) {
                            String s = list.get(j);
                            if (j == size - 1) {
                                HELP_BEGIN = HELP_BEGIN + s;
                            } else {
                                HELP_BEGIN = HELP_BEGIN + s + "\n";
                            }
                        }
                    } else if (name.equals("HELP_END")) {
                        HELP_END = "";
                        List<String> list = resource.getStringList(name);
                        int size = list.size();
                        for (int j = 0; j < size; j++) {
                            String s = list.get(j);
                            if (j == size - 1) {
                                HELP_END = HELP_END + s;
                            } else {
                                HELP_END = HELP_END + s + "\n";
                            }
                        }
                    } else {
                        field.set(null, resource.getString(field.getName()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            b++;
        }
    }

    public static String getHelpMessage() {
        return HELP;
    }

    public static void finish() {
        byte b;
        int i;
        Field[] arrayOfField;
        for (i = (arrayOfField = Lang.class.getFields()).length, b = 0; b < i; ) {
            Field field = arrayOfField[b];
            try {
                field.set(null, null);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            b++;
        }
    }
}
