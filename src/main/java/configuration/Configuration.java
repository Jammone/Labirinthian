package configuration;

import Console.ConsoleLogger;
import PrizeHandler.PrizeHandler;
import Tasks.OpenLabTask;
import Tasks.Reminder;
import it.labirinthian.labirinthian.Labirinthian;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Configuration {
    //public static double spaceBetweenLines;
    public static int maze_width;
    public static int maze_height;
    public static String world;

    public static Double maze_corner_x;
    public static Double maze_corner_y;
    public static Double maze_corner_z;

    public static Double maze_gate_x;
    public static Double maze_gate_y;
    public static Double maze_gate_z;

    public static Double chestprice_x;
    public static Double chestprice_y;
    public static Double chestprice_z;

    public static String maze_firstprice_type;
    public static String maze_secondprice_type;
    public static String maze_thirdprice_type;
    public static String maze_genericprice_type;
    public static Date event_time;

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

         maze_width = config.getInt(ConfigNode.MAZE_WIDTH.getPath());
         maze_height= config.getInt(ConfigNode.MAZE_HEIGHT.getPath());;
         world = config.getString(ConfigNode.MAZE_CORNER_LOCATION_WORLD.getPath());

         maze_corner_x = config.getDouble(ConfigNode.MAZE_CORNER_LOCATION_X.getPath());
         maze_corner_y = config.getDouble(ConfigNode.MAZE_CORNER_LOCATION_Y.getPath());
         maze_corner_z = config.getDouble(ConfigNode.MAZE_CORNER_LOCATION_Z.getPath());
         Location corner = new Location(Bukkit.getWorld(world),maze_corner_x,maze_corner_y,maze_corner_z);
        Labirinthian.setCorner_location(corner);

         maze_gate_x = config.getDouble(ConfigNode.GATE_LOCATION_X.getPath());
         maze_gate_y = config.getDouble(ConfigNode.GATE_LOCATION_Y.getPath());
         maze_gate_z = config.getDouble(ConfigNode.GATE_LOCATION_Z.getPath());
         Location gate = new Location(Bukkit.getWorld(world),maze_gate_x,maze_gate_y,maze_gate_z);

         chestprice_x = config.getDouble(ConfigNode.PRIZEBLOCK_LOCATION_X.getPath());
         chestprice_y = config.getDouble(ConfigNode.PRIZEBLOCK_LOCATION_Y.getPath());
         chestprice_z = config.getDouble(ConfigNode.PRIZEBLOCK_LOCATION_Z.getPath());
         Location chest_loc = new Location(Bukkit.getWorld(world),chestprice_x,chestprice_y,chestprice_z);

         maze_firstprice_type = config.getString(ConfigNode.FIRST_PRIZEBLOCK_PRIZE.getPath());

         maze_secondprice_type = config.getString(ConfigNode.SECOND_PRIZEBLOCK_PRIZE.getPath());

         maze_thirdprice_type = config.getString(ConfigNode.THIRD_PRIZEBLOCK_PRIZE.getPath());


         maze_genericprice_type = config.getString(ConfigNode.GENERIC_PRIZEBLOCK_PRIZE.getPath());
         SimpleDateFormat format = new SimpleDateFormat("HH:mm");

         String et =config.getString(ConfigNode.EVENT_HOUR.getPath());
        try {
            event_time = format.parse(et);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        event_time.setYear(now.getYear());
        event_time.setMonth(now.getMonth());
        event_time.setDate(now.getDate());

        System.out.println("ora odierna:  "+now);
        System.out.println("ora dell'evento" +event_time);
        //System.out.println("time in mills per now: "+now.getTime());
        //System.out.println("time in mills per event: "+event_time.getTime());

        long difference = event_time.getTime() - now.getTime();

        PrizeHandler pz = new PrizeHandler(chest_loc, gate);
        Labirinthian.setPrizeHandler(pz);
        if(difference <=0 ) {
            long minute_diff = difference / 60 / 1000;
            System.out.println("evento già passato per oggi! non farò nulla, differenza in min "+ minute_diff );
        }else{
            System.out.println("evento dovrebbe partire tra (minuti):"+ difference / 60/ 1000/60+", avvio la task");
            pz.intialize();
            OpenLabTask openLabTask = new OpenLabTask();
            int j = Bukkit.getScheduler().scheduleSyncDelayedTask(Labirinthian.getInstance(), openLabTask, (difference/1000)*20);
            System.out.println("Task prenotata");
            openLabTask.setId(j);
            Labirinthian.getInstance().setOpenLabTask(openLabTask) ;
        }

        int dieciMin = event_time.getMinutes()-10;
        String dieciMin_string = Integer.toString(event_time.getHours())+":"+Integer.toString(dieciMin);
        Date diecimin_diff = null;
        try {
            diecimin_diff = format.parse(dieciMin_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        diecimin_diff.setYear(now.getYear());
        diecimin_diff.setMonth(now.getMonth());
        diecimin_diff.setDate(now.getDate());

        long difference_10m_current = diecimin_diff.getTime() - now.getTime();
        if(difference_10m_current <=0 ) {
            System.out.println("reminder 10 min prima non si fa, manca meno!");
        }else{
            System.out.println("reminder 10 min in generazione partire tra (minuti):"+ difference_10m_current/ 60/ 1000/60+" avvio la task");
            pz.intialize();
            Reminder rm = new Reminder(10);
            int i = Bukkit.getScheduler().scheduleSyncDelayedTask(Labirinthian.getInstance(), rm, (difference_10m_current/1000)*20);
            System.out.println("Task prenotata");
            rm.setId(i);
            Labirinthian.getInstance().setReminder(rm);
        }

    }
}
