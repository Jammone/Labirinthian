package it.labirinthian.labirinthian;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.awt.*;

public class Maze{
    private Vertex[][] maze;

    private Vertex start;
    private Vertex end;

    private int width;
    private int height;

    //don't allow MazeSolver to access arbitrary parts of the maze.
    private Hashtable legalObservations;

    private int numObservations;

    public Maze() {}

    Maze(Vertex[][] mz, Vertex st, Vertex nd, int w, int h ) {
        width = w;
        height = h;
        maze = mz;
        start = st;
        end = nd;
        legalObservations = new Hashtable();
        legalObservations.put(start.getP(),start.getP());
        start.Observe();
        legalObservations.put(end.getP(),end.getP());
        end.Observe();

        numObservations = 2;
    }

    public int getNumObservations()   {
        return numObservations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //Observe location p, from the location from
    public LinkedList<Pair> Observe(Pair p) {

        //you cannot observe arbitrary squares in the maze, only those next to those immediately reachable from those you have already observed
        if( legalObservations.containsKey(p) )
        {

            Vertex vx = maze[p.x][p.y];

            if(!vx.wasObserved())
                numObservations++;

            vx.Observe();

            LinkedList<Pair> neighbors = vx.getNeighbors();
            Iterator<Pair> iter = neighbors.iterator();
            while(iter.hasNext())
            {
                Pair q = iter.next();
                legalObservations.put(q,q);
            }

            return vx.getNeighbors();

        }
        else{
            System.out.println("You can only observe squares next to those that have been observed!!");
            return null;
        }
    }

    public Pair getStartLocation()
    {
        return start.getP();
    }

    public Pair getEndLocation()
    {
        return end.getP();
    }

    public void visualize(Graphics g) {
        Graphics2D gobj = (Graphics2D)g;

        gobj.scale(2.0,2.0);
        gobj.translate(10.0,20.0);
        //gobj.setBackground(Color.white);

        gobj.setColor(Color.black);

        int D = 4;


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                boolean isStart = (start.getP().x == i && start.getP().y == j);
                boolean isEnd = (end.getP().x == i && end.getP().y == j);

                LinkedList<Pair> neighbors = maze[i][j].getNeighbors();
                if( !neighbors.contains(new Pair(i-1,j)))
                    gobj.drawLine(D*i,D*j, D*i,D*j+D);
                if( !neighbors.contains(new Pair(i+1,j)))
                    gobj.drawLine(D*i+D,D*j,D*i+D,D*j+D);
                if( !neighbors.contains(new Pair(i,j-1)))
                    gobj.drawLine(D*i,D*j, D*i+D,D*j);
                if( !neighbors.contains(new Pair(i,j+1)))
                    gobj.drawLine(D*i,D*j+D,D*i+D,D*j+D);

                if(isStart)
                {
                    gobj.setColor(Color.green);
                    gobj.fillRect(D*i+1,D*j+1,D-2,D-2);
                    gobj.setColor(Color.black);
                }

                if(isEnd)
                {
                    gobj.setColor(Color.blue);
                    gobj.fillRect(D*i+1,D*j+1,D-2,D-2);
                    gobj.setColor(Color.black);
                }

            }
        }


    }

    public Vertex[][] getMaze() {
        return maze;
    }

    public void setMaze(Vertex[][] maze) {
        this.maze = maze;
    }

    public void printMaze(Location loc) {
        int D = 4;
        Location aux = loc;
        Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc).setType(Material.ACACIA_LOG);
        Location locl;
        Location locr;
        Location locf;
        Location locb;
        Location diag1;
        Location diag2;
        Location diag3;
        Location diag4;

        int i = 0;
        int j = 0;
        Location asdf;
        asdf = new Location(loc.getWorld(), loc.getX() , loc.getY(), loc.getZ() );
        for (int ax1 = 0; ax1 < 2 * width; ax1++){
            for (int ax2 = 0; ax2 < 2 * width; ax2++) {
                asdf = new Location(loc.getWorld(), loc.getX() + ax1, loc.getY() + 3, loc.getZ() + ax2);
                Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(asdf).setType(Material.AIR);
                asdf = new Location(loc.getWorld(), loc.getX() + ax1, loc.getY() + 1, loc.getZ() + ax2);
                Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(asdf).setType(Material.AIR);
                asdf = new Location(loc.getWorld(), loc.getX() + ax1, loc.getY() + 2, loc.getZ() + ax2);
                Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(asdf).setType(Material.AIR);
                asdf = new Location(loc.getWorld(), loc.getX() + ax1, loc.getY(), loc.getZ() + ax2);
                Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(asdf).setType(Material.AIR);
            }
        }

        while(true){
                //Bukkit.getScheduler().scheduleSyncDelayedTask(Labirinthian.getInstance(),new myTask(i,j,aux),80+30*i+30*j);
                boolean isStart = (start.getP().x == i && start.getP().y == j);
                boolean isEnd = (end.getP().x == i && end.getP().y == j);
                LinkedList<Pair> neighbors = maze[i][j].getNeighbors();
                //System.out.println("neighbors size: " + neighbors.size());
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(aux).setType(Material.POLISHED_DIORITE);
                if (!neighbors.contains(new Pair(i, j + 1))){
                    //Bukkit.getPlayer("Nonick").sendMessage(ChatColor.YELLOW+""+ "   il blocco [" +i +"] [" +j+"+1]("+i+","+(j+1)+") è muro");

                    locl = new Location(aux.getWorld(), aux.getX(), aux.getY()+3, aux.getZ()+1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locl).setType(Material.STONE_BRICKS);
                    locl = new Location(aux.getWorld(), aux.getX(), aux.getY()+1, aux.getZ()+1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locl).setType(Material.STONE_BRICKS);
                    locl = new Location(aux.getWorld(), aux.getX(), aux.getY()+2, aux.getZ()+1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locl).setType(Material.STONE_BRICKS);
                }
                if( !neighbors.contains(new Pair(i,j-1))) {
                    //Bukkit.getPlayer("Nonick").sendMessage(ChatColor.YELLOW+""+ "   il blocco [" +i +"] [" +(j)+"-1]("+i+ ","+(j-1)+") è muro");
                    locr = new Location(aux.getWorld(), aux.getX(), aux.getY()+2, aux.getZ()-1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locr).setType(Material.STONE_BRICKS);
                    locr = new Location(aux.getWorld(), aux.getX(), aux.getY()+1, aux.getZ()-1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locr).setType(Material.STONE_BRICKS);
                    locr = new Location(aux.getWorld(), aux.getX(), aux.getY()+3, aux.getZ()-1);
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locr).setType(Material.STONE_BRICKS);
                }
                if( !neighbors.contains(new Pair(i-1,j))) {
                    //Bukkit.getPlayer("Nonick").sendMessage(ChatColor.YELLOW+""+ "   il blocco [" +i +"-1] [" +(j)+"] ("+(i-1)+ ","+j+") è muro");
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+1, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.STONE_BRICKS);
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+2, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.STONE_BRICKS);
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+3, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.STONE_BRICKS);
                }
                if( !neighbors.contains(new Pair(i+1,j))) {
                    //Bukkit.getPlayer("Nonick").sendMessage(ChatColor.YELLOW+""+ "   il blocco  [" +i +"+1] [" +(j)+"] ("+(i+1)+ ","+j+") è muro");
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+3, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.STONE_BRICKS);
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+1, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.STONE_BRICKS);
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+2, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.STONE_BRICKS);
                }

                //fill the cross spaces
                locl = new Location(aux.getWorld(), aux.getX(), aux.getY(), aux.getZ()+1);
                if(Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locl).getType().equals(Material.AIR))
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locl).setType(Material.POLISHED_DIORITE);
                locr = new Location(aux.getWorld(), aux.getX(), aux.getY(), aux.getZ()-1);
                if(Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locr).getType().equals(Material.AIR)) Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locr).setType(Material.DIAMOND_BLOCK);
                locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY(), aux.getZ() );
                if(Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).getType().equals(Material.AIR))
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.POLISHED_DIORITE);
                locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY(), aux.getZ());
                if(Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).getType().equals(Material.AIR))
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.POLISHED_DIORITE);
                //fill the diag spaces
                diag1 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+1, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag1).setType(Material.STONE_BRICKS);
                diag1 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+2, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag1).setType(Material.STONE_BRICKS);
                diag1 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+3, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag1).setType(Material.STONE_BRICKS);

                diag2 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+1, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag2).setType(Material.STONE_BRICKS);
                diag2 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+2, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag2).setType(Material.STONE_BRICKS);
                diag2 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+3, aux.getZ()+1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag2).setType(Material.STONE_BRICKS);

                diag3 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+1, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag3).setType(Material.STONE_BRICKS);
                diag3 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+2, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag3).setType(Material.STONE_BRICKS);
                diag3 =new Location(aux.getWorld(), aux.getX()-1, aux.getY()+3, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag3).setType(Material.STONE_BRICKS);

                diag4 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+1, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag4).setType(Material.STONE_BRICKS);
                diag4 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+2, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag4).setType(Material.STONE_BRICKS);
                diag4 =new Location(aux.getWorld(), aux.getX()+1, aux.getY()+3, aux.getZ()-1);
                Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(diag4).setType(Material.STONE_BRICKS);


                if(isStart){
                    Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(aux).setType(Material.GOLD_BLOCK);
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+1, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.AIR);
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+2, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.AIR);
                    locb = new Location(aux.getWorld(), aux.getX()-1, aux.getY()+3, aux.getZ() );
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locb).setType(Material.AIR);
                }
                if(isEnd) {
                    Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(aux).setType(Material.COAL_BLOCK);
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+1, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.AIR);
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+2, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.AIR);
                    locf = new Location(aux.getWorld(), aux.getX()+1, aux.getY()+3, aux.getZ());
                    Bukkit.getWorld(aux.getWorld().getName()).getBlockAt(locf).setType(Material.AIR);
                }

                if(j< width-1){
                    j++;
                    aux = new Location(aux.getWorld(), aux.getX(),aux.getY(),aux.getZ()+2);
                    // Bukkit.getPlayer("Nonick").sendMessage(ChatColor.AQUA+""+ "spostandoci di Z+2");

                }else {
                    // Bukkit.getPlayer("Nonick").sendMessage(ChatColor.RED + "" + "max colonne raggiunto");
                    if (i < height - 1) {
                        i++;
                        j = 0;
                        aux = new Location(aux.getWorld(), aux.getX() + 2, aux.getY(), aux.getZ() - 2 * (height - 1));
                        // Bukkit.getPlayer("Nonick").sendMessage(ChatColor.AQUA + "" + "spostandoci di x+2");
                    } else {
                        // Bukkit.getPlayer("Nonick").sendMessage(ChatColor.RED + "" + "max righe raggiunto");
                        return;
                    }
                }
        }

    }

    public class WriteableMaze
    {

        public WriteableMaze(int w, int h, boolean fullyConnected)
        {
            width = w;
            height = h;
            maze = new Vertex[width][height];
            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++){
                    LinkedList<Pair> neighbors = new LinkedList<Pair>();

                    if(fullyConnected){
                        if( i > 0)
                            neighbors.add(new Pair(i-1,j));
                        if(j > 0)
                            neighbors.add(new Pair(i,j-1));
                        if(i < width-1)
                            neighbors.add(new Pair(i+1,j));
                        if(j < height-1)
                            neighbors.add(new Pair(i,j+1));
                    }


                    LinkedList<Pair> legalneighbors = new LinkedList<Pair>();
                    if( i > 0)
                        legalneighbors.add(new Pair(i-1,j));
                    if(j > 0)
                        legalneighbors.add(new Pair(i,j-1));
                    if(i < width-1)
                        legalneighbors.add(new Pair(i+1,j));
                    if(j < height-1)
                        legalneighbors.add(new Pair(i,j+1));

                    maze[i][j] = new Vertex(new Pair(i,j),legalneighbors,neighbors);
                }
            }
            legalObservations = new Hashtable();
        }

        public Vertex getVertex(int x, int y) {
            return maze[x][y];
        }

        public Vertex[][] getMaze() {
            return maze;
        }

        public void setMaze(Vertex[][] mz)
        {
            maze = mz;
        }

        public void setStart(Pair p) {
            Vertex st = maze[p.x][p.y];
            start = st;
            legalObservations.put(start,start);
            start.Observe();
        }

        public void setEnd(Pair p)
        {
            Vertex nd = maze[p.x][p.y];
            end = nd;
            legalObservations.put(end,end);
            end.Observe();
        }

        public boolean addEdge(Pair p, Pair q)
        {
            Vertex v1 = maze[p.x][p.y];
            Vertex v2 = maze[q.x][q.y];
            boolean b1 = v1.addNeighbor(q);
            boolean b2 = v2.addNeighbor(p);

            return b1 && b2;

        }


        public void remEdge(Pair p, Pair q)
        {
            Vertex v1 = maze[p.x][p.y];
            Vertex v2 = maze[q.x][q.y];
            v1.remNeighbor(q);
            v2.remNeighbor(p);
        }


        public Maze getFixedMaze(){
            Maze mz = new Maze(maze,start,end,width,height);
            return mz;
        }

    }

}
