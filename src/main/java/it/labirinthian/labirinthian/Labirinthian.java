package it.labirinthian.labirinthian;

import Commands.CommandHandler;
import Console.ConsoleLogger;
import Listener.MainListner;
import PrizeHandler.PrizeHandler;
import Tasks.OpenLabTask;
import Tasks.Reminder;
import configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;

public final class Labirinthian extends JavaPlugin {
    private static Labirinthian instance;
    private static CommandHandler commandHandler;
    public static final String BASE_PERM = "base.";
    private Maze.WriteableMaze m_maze;
    private  static Maze maze;

    private static Location corner_location;
    private int width;
    private int height;
    private static PrizeHandler prizeHandler;
    private OpenLabTask openLabTask;
    private Reminder reminder;



    public static Labirinthian getInstance() {
        return  instance;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        if(instance != null || System.getProperty("Base") != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Base] Non usare /reload o plugman. Usa \"/base reload\" ");
        }
        System.setProperty("Base loded", "true");
        instance = this;
        //Console LOG
        ConsoleLogger.setLogger(instance.getLogger());
        ConsoleLogger.log(Level.INFO, "Ok funziona");

        // Load the configuration.
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
            @Override
            public void run() {
                Configuration.load((Plugin) Labirinthian.getInstance());

                Date actualtime = new Date(System.currentTimeMillis());

                PluginManager pmg = getServer().getPluginManager();
                /* Sezione Handlers & GuiEvents */
                pmg.registerEvents(new MainListner(corner_location), Labirinthian.getInstance());


                maze = generateMaze(Configuration.maze_width,Configuration.maze_height);
                maze.printMaze(corner_location);
            }
        },3*20L);
        this.getCommand("Labirinto").setExecutor(new CommandHandler());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Maze generateMaze(int width, int height) {
        long start = System.nanoTime();
        IGenerator gen = null;

        gen = new WilsonGenerator();

        m_maze = gen.generate(width, height);
        long end = System.nanoTime();

        System.out.println("Took " + (double) (end - start) / 1000000000.0 + " seconds to create maze.");
        return getLastMaze();
    }
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    // Temp for testing.
    public Maze getLastMaze() {
        Vertex[][] verts = m_maze.getMaze();
        Maze fixedMaze = m_maze.getFixedMaze();

        Vertex[][] newVerts = new Vertex[fixedMaze.getWidth()][fixedMaze.getHeight()];
        for (int y = 0; y < fixedMaze.getWidth(); ++y) {
            for (int x = 0; x < fixedMaze.getHeight(); ++x) {
                newVerts[y][x] = new Vertex(verts[y][x].getP(), verts[y][x].getLegalNeighbors(), null);
                newVerts[y][x].setNeighbors(verts[y][x].getNeighbors());
            }
        }

        Maze tmp = new Maze();
        Maze.WriteableMaze wrmaze = tmp.new WriteableMaze(fixedMaze.getWidth(), fixedMaze.getHeight(), false);
        wrmaze.setMaze(newVerts);
        Pair oldStart = fixedMaze.getStartLocation();
        wrmaze.setStart(newVerts[oldStart.x][oldStart.y].getP());
        Pair oldEnd = fixedMaze.getEndLocation();
        wrmaze.setEnd(newVerts[oldEnd.x][oldEnd.y].getP());
        return wrmaze.getFixedMaze();
    }

    public static Maze getMaze() {
        return maze;
    }

    public static void setMaze(Maze maze) {
        Labirinthian.maze = maze;
    }

    public Location getCorner_location() {
        return corner_location;
    }

    public static void setCorner_location(Location cl) {
        corner_location = cl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static PrizeHandler getPrizeHandler() {
        return prizeHandler;
    }

    public static void setPrizeHandler(PrizeHandler pz) {
        prizeHandler = pz;
    }

    public OpenLabTask getOpenLabTask() {
        return openLabTask;
    }

    public void setOpenLabTask(OpenLabTask openLabTask) {
        this.openLabTask = openLabTask;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }
}
