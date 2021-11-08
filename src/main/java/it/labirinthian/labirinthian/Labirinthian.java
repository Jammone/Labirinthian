package it.labirinthian.labirinthian;

import Commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Labirinthian extends JavaPlugin {
    private static Labirinthian instance;
    private static CommandHandler commandHandler;
    public static final String BASE_PERM = "base.";
    private Maze.WriteableMaze m_maze;

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

        Maze maze= generateMaze(20,20);
        maze.printMaze();
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

}
