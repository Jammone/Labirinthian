package PrizeHandler;

import it.labirinthian.labirinthian.Labirinthian;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

public class PrizeHandler {
    private Location chest_loc;
    private Location gate_location;
    private int active_price=1;
    private UUID price_UUID;
    private LinkedList<Player> challengers = new LinkedList<>();
    private LinkedList<Player> winners = new LinkedList<>();
    private Chest chest;
    private boolean open = false;

    public PrizeHandler(Location chest_loc, Location gate) {
        this.chest_loc = chest_loc;
        this.gate_location = gate;

        Block block = chest_loc.getWorld().getBlockAt(chest_loc);
        if (block.getType() != Material.CHEST) {
            System.out.println("Errore: cassa premi non trovata in posizione specificata block");
            //chest_loc.getBlock().setType(Material.GOLD_BLOCK);
            //Bukkit.getPlayer("Nonick96").teleport(chest_loc);
          return;
        }
        BlockState chestState = block.getState();
        if (chestState instanceof Chest) {
            this.chest = (Chest) chestState;
            chest.getInventory().clear();
            ItemStack is = new ItemStack(Material.DIAMOND_BLOCK,1);
            ItemMeta itemMeta = is.getItemMeta();
            itemMeta.setLore(Arrays.asList("Il primo premio del Labirinto"));
            is.setItemMeta(itemMeta);
            chest.getInventory().addItem(is);
        }else
            System.out.println("Errore: cassa premi non trovata in posizione specificata BlockState");
        System.out.println("cassa premi  trovata in posizione specificata ");

    }

    public Location getchest_loc() {
        return chest_loc;
    }


    public void announce(Player winner){
        this.winners.add(winner);
        if(active_price == 1){
            for(Player p :chest_loc.getWorld().getPlayers()){
                if(p!= winner)
                    p.sendTitle("&b "+winner.getName()+" ha risolto il labirinto!!","Affrettati, il secondo posto è" +
                            "ancora incerto!",10,70,20);
                else
                    p.sendTitle("Congratulazioni!"," hai risolto il labirinto! Ritorna domani per la prossima sfida!",
                            10,70,20);
            }
            System.out.println(winner.getName()+" ha vinto il primo premio al labirinto");
            activatePrice(2);
            return;
        }
        if(active_price == 2){
            for(Player p : chest_loc.getWorld().getPlayers()){
                if(p!= winner)
                    p.sendTitle("&b "+winner.getName()+" è il secondo classificato!!",
                            "Chi otterrà il terzo posto?",10,70,20);
                else
                    p.sendTitle("Congratulazioni!"," hai risolto il labirinto! Ritorna domani per la prossima sfida!",
                            10,70,20);
            }
            System.out.println(winner.getName()+" ha vinto il secondo premio al labirinto");

            activatePrice(3);
            return;
        }
        if(active_price == 3){
            for(Player p : chest_loc.getWorld().getPlayers()){
                if(p!= winner)
                    p.sendTitle("&b "+winner.getName()+" è il terzo classificato nel risolvere il labirinto!!",
                            "",10,70,20);
                    //C'è comunque un premio se risolvi il labirinto!
                else
                    p.sendTitle("Congratulazioni!","hai risolto il labirinto! Ritorna domani per la prossima sfida!",
                            10,70,20);
            }
            System.out.println(winner.getName()+" ha vinto il terzo premio al labirinto");

            activatePrice(4);
            return;
        }
        if(active_price == 4) {
            winner.sendTitle("Congratulazioni!"," hai risolto il labirinto! Ritorna domani per la prossima sfida!",
                    10,70,20);
            System.out.println(winner.getName()+" ha vinto al labirinto");

        }

    }

    private void activatePrice(int i) {
        if(chest==null)return;
        Inventory inv = chest.getInventory();

        if(i==2){
            ItemStack is = new ItemStack(Material.ELYTRA,1);
            ItemMeta itemMeta = is.getItemMeta();
            itemMeta.setLore(Arrays.asList("Il secondo premio del Labirinto"));
            is.setItemMeta(itemMeta);
            inv.addItem(is);
            this.active_price=2;
            return;
        }
        if(i==3){
            ItemStack is = new ItemStack(Material.EMERALD,10);
            ItemMeta itemMeta = is.getItemMeta();
            itemMeta.setLore(Arrays.asList("Il terzo premio del Labirinto"));
            is.setItemMeta(itemMeta);
            inv.addItem(is);
            this.active_price=3;
            return;
        }
        if(i==4){
            ItemStack is = new ItemStack(Material.IRON_INGOT,60);
            ItemMeta itemMeta = is.getItemMeta();
            itemMeta.setLore(Arrays.asList("Il premio del Labirinto"));
            is.setItemMeta(itemMeta);
            inv.addItem(is);
            this.active_price=4;
            return;
        }
    }

    public LinkedList<Player> getChallengers() {
        return challengers;
    }

    public LinkedList<Player> getWinners() {
        return winners;
    }

    public void intialize(){
        Location upgate = new Location(gate_location.getWorld(), gate_location.getX(), gate_location.getY()+1, gate_location.getZ());
        Location upgate2 = new Location(gate_location.getWorld(), gate_location.getX(), gate_location.getY()+2, gate_location.getZ());
        Bukkit.getWorld(upgate.getWorld().getName()).getBlockAt(upgate).setType(Material.BARRIER);
        Bukkit.getWorld(upgate.getWorld().getName()).getBlockAt(upgate2).setType(Material.BARRIER);
        if(chest==null)return;
        Inventory inv = chest.getInventory();
        ItemStack is = new ItemStack(Material.DIAMOND,1);
        NamespacedKey key = new NamespacedKey(Labirinthian.getInstance(), "price");
        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
        is.setItemMeta(itemMeta);
        inv.addItem(is);
        this.active_price=1;
        this.open=false;
    }

    public void open() {
        Location upgate = new Location(gate_location.getWorld(), gate_location.getX(), gate_location.getY()+1, gate_location.getZ());
        Location upgate2 = new Location(gate_location.getWorld(), gate_location.getX(), gate_location.getY()+2, gate_location.getZ());
        Bukkit.getWorld(upgate.getWorld().getName()).getBlockAt(upgate).setType(Material.AIR);
        Bukkit.getWorld(upgate.getWorld().getName()).getBlockAt(upgate2).setType(Material.AIR);
        for(Player p :chest_loc.getWorld().getPlayers()){
            p.sendTitle(ChatColor.GOLD+""+ "Il labirinto è ora aperto!!",
                    ChatColor.GOLD+""+"Buona fortuna",10,70,20);
        }
        this.open=true;
        System.out.println("Labirinto aperto!");
    }

    public boolean isOpen() {
        return open;
    }

    public int getActive_price() {
        return active_price;
    }
}
