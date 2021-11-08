package Commands;

import it.labirinthian.labirinthian.Labirinthian;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;

import static it.labirinthian.labirinthian.Labirinthian.BASE_PERM;


public class HelpCommand extends SubCommand {
    public HelpCommand() {
        super("help");
        setPermission(BASE_PERM + "help");
    }


    @Override
    public String getPossibleArguments() {
        return "";
    }

    @Override
    public int getMinimumArguments() { return 0; }

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws CommandException {
        sender.sendMessage("lista comandi di Casper: ");
        for (SubCommand subCommand : Labirinthian.getCommandHandler().getSubCommands()) {
            sender.sendMessage("- "+ subCommand.getName());
        }
    }

    @Override
    public List<String> getTutorial() {
        return null;
    }

    @Override
    public SubCommandType getType() {
        return SubCommandType.HIDDEN;
    }
}
