package configuration;

import Console.ConsoleLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Configuration {


    public static void load(Plugin plugin) {
        // se non esiste la crea
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource("config.yml", true);
        }

        // caricamento del file
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            ConsoleLogger.log(Level.WARNING, "The configuration is not a valid YAML file! Please check it with a tool like http://yaml-online-parser.appspot.com/");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            ConsoleLogger.log(Level.WARNING, "I/O error while reading the configuration. Was the file in use?");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleLogger.log(Level.WARNING, "Unhandled exception while reading the configuration!");
            return;
        }

        boolean needsSave = false;

        // salva i valori di default
        for (ConfigNode node : ConfigNode.values()) {
            if (!config.isSet(node.getPath())) {
                needsSave = true;
                config.set(node.getPath(), node.getDefaultValue());
            }
        }

        // salvataggio
        if (needsSave) {
            config.options().header(String.join("\n",
                    "  Plugin created by Jammone.",
                    " "));
            config.options().copyHeader(true);
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
                ConsoleLogger.log(Level.WARNING, "I/O error while saving the configuration. Was the file in use?");
            }
        }


        //pingerTrimMotd = config.getBoolean(ConfigNode.BUNGEE_PINGER_TRIM_MOTD.getPath());

        //pingerServers = new HashMap<>();


    }
}
