package Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Reminder implements Runnable{

    private final  int time;
    private int id;

    public Reminder(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        if(Bukkit.getWorld("world") == null) return;
        for(Player p: Bukkit.getWorld("world").getPlayers()){
            p.sendMessage(ChatColor.GREEN+""+ "tra "+time+" minuti si aprirà il labirinto!Preparati e usa "+ChatColor.AQUA+""+
                    "/warp labirinto"+ChatColor.GREEN+""+" per unirti");
        }
        if(Bukkit.getWorld("nether")== null) return;
        for(Player p: Bukkit.getWorld("nether").getPlayers()){
            p.sendMessage(ChatColor.GREEN+""+ "tra "+time+" minuti si aprirà il labirinto!Preparati e usa "+ChatColor.AQUA+""+
                    "/warp labirinto"+ChatColor.GREEN+""+" per unirti");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
