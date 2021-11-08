package configuration.Other;


import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileConfig {
    private static Map<String, String> placeholders = new HashMap<>();

    public static void load(Plugin plugin) {
        placeholders.clear();

        File file = new File(plugin.getDataFolder(), "symbols.yml");

        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource("symbols.yml", true);
        }



    }
}
