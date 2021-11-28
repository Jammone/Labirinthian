package Commands.MyCommands;

import Commands.SubCommand;
import Tasks.OpenLabTask;
import Tasks.Reminder;
import it.labirinthian.labirinthian.Labirinthian;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Status extends SubCommand {

    public Status() {
        super("status");
    }

    @Override
    public String getPossibleArguments() {
        return null;
    }

    @Override
    public int getMinimumArguments() {
        return 0;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws CommandException {
        Reminder reminder = Labirinthian.getInstance().getReminder();
        OpenLabTask openLabTask = Labirinthian.getInstance().getOpenLabTask();
        sender.sendMessage("aò");
        if(openLabTask !=null){
            sender.sendMessage(ChatColor.GREEN+""+ "--> evento in programma,"+ChatColor.AQUA+""+" task preparata");
        }else{
            sender.sendMessage(ChatColor.YELLOW+""+ "--> evento non in programma è già passata?");
        }
        if(reminder !=null){
            sender.sendMessage(ChatColor.GREEN+""+ "--> reminder in programma,"+ChatColor.AQUA+""+" task preparata");
        }else{
            sender.sendMessage(ChatColor.YELLOW+""+ "--> reminder non in programma è già passato?");
        }
        sender.sendMessage(ChatColor.GREEN+""+"--> stato del labirinto: " +(Labirinthian.getPrizeHandler().isOpen()?ChatColor.AQUA+""+"aperto":ChatColor.RED+""+"chiuso"));
    }

    @Override
    public List<String> getTutorial() {
        return null;
    }

    @Override
    public SubCommandType getType() {
        return null;
    }
}
