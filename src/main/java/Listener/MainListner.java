package Listener;

import PrizeHandler.PrizeHandler;
import it.labirinthian.labirinthian.Labirinthian;
import it.labirinthian.labirinthian.Maze;
import it.labirinthian.labirinthian.Pair;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;

public class MainListner implements Listener {
    int i=0; int j=0;
    Location aux;

    public MainListner(Location location) {
        this.aux = location;
    }

    @EventHandler
    public void playerClick(InventoryCloseEvent e){
        if(!(e.getPlayer() instanceof  Player)){return;}
        Player player = (Player) e.getPlayer();

        Inventory chestinv = e.getInventory();

        if(!chestinv.getType().equals(InventoryType.CHEST)){
            return;
        }

        PrizeHandler prizeHandler = Labirinthian.getPrizeHandler();
        if(!chestinv.getLocation().equals(prizeHandler.getchest_loc())) return;

        if(chestinv.isEmpty()){
            prizeHandler.announce(player);
            return;
        }
        //la chest dei premi non è vuota
        for(ItemStack itemStack: chestinv.getContents()) {
           if(isNewPrice(itemStack)){
               System.out.println("il nuovo premio è ancora dentro");
               return;
           }
        }
        prizeHandler.announce(player);
    }


    @EventHandler
    public void InventoryOpen(InventoryOpenEvent e){
        Inventory chestinv = e.getInventory();
        Player player = (Player) e.getPlayer();

        if(!chestinv.getType().equals(InventoryType.CHEST)){
            return;
        }

        PrizeHandler prizeHandler = Labirinthian.getPrizeHandler();

        if(chestinv.getLocation().equals(prizeHandler.getchest_loc())){
            //il player vincitore ha tentato di aprire la cassa dei premi!
            if(prizeHandler.getWinners().contains(player)) {
                player.sendMessage(ChatColor.RED+""+ "Hai già preso il premio per oggi, torna domani...");
                //e.setCancelled(true);
            }
        }


    }


    private boolean isNewPrice(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PrizeHandler prizeHandler = Labirinthian.getPrizeHandler();
        int active_price = prizeHandler.getActive_price();
        if(active_price ==1) {
            List<String> lore = itemMeta.getLore();
            if (lore == null) return false;
            if (lore.get(0).equalsIgnoreCase("Il primo premio del Labirinto")) {
                return true;
            } else
                return false;
        }
        if(active_price ==2) {
            List<String> lore = itemMeta.getLore();
            if (lore == null) return false;
            if (lore.get(0).equalsIgnoreCase("Il secondo premio del Labirinto")) {
                return true;
            } else
                return false;
        }
        if(active_price ==3) {
            List<String> lore = itemMeta.getLore();
            if (lore == null) return false;
            if (lore.get(0).equalsIgnoreCase("Il terzo premio del Labirinto")) {
                return true;
            } else
                return false;
        }
        if(active_price ==4) {
            List<String> lore = itemMeta.getLore();
            if (lore == null) return false;
            if (lore.get(0).equalsIgnoreCase("Il  premio del Labirinto")) {
                return true;
            } else
                return false;
        }
        return false;
    }

    @EventHandler
    public void playerclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();

        Location playerLocation = p.getLocation();
        Location labLocation = Labirinthian.getPrizeHandler().getchest_loc();

        World playerWorld = playerLocation.getWorld();
        World labworld =  labLocation.getWorld();

        Block clickedBlock = e.getClickedBlock();

        if (p.getInventory().getItemInHand().getType() != Material.STICK) return;
        if(playerWorld == null || labworld == null) return;
        if(!playerWorld.getName().equalsIgnoreCase(labworld.getName())) return;
        if(clickedBlock == null) return;

        int x1 = (int) playerLocation.getX();
        int x2 =  (int)labLocation.getX();
        int y1 = (int) playerLocation.getY();
        int y2 =  (int)labLocation.getY();

        double distance = Math.sqrt( (x1-x2)^2 + (y1-y2)^2 );

        if(distance >= 150) return;
        Location location = new Location(clickedBlock.getWorld(),clickedBlock.getX(),clickedBlock.getY()+1,clickedBlock.getZ());
       // if(location.getBlock().getType().equals(Material.AIR))
            p.sendBlockChange(location,Material.REDSTONE_WIRE,(byte)0);

    }

}
