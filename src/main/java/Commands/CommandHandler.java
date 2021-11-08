package Commands;


import it.labirinthian.labirinthian.Labirinthian;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

import static it.labirinthian.labirinthian.Labirinthian.BASE_PERM;


public class CommandHandler implements CommandExecutor {

    private List<SubCommand> subCommands;
    private Map<Class<? extends SubCommand>, SubCommand> subCommandsByClass;

    public CommandHandler() {
        subCommands = new ArrayList<>();
        subCommandsByClass = new HashMap<>();
        registerSubCommand(new HelpCommand());
    }

    public void registerSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
        subCommandsByClass.put(subCommand.getClass(), subCommand);
    }

    public List<SubCommand> getSubCommands() {
        return new ArrayList<>(subCommands);
    }

    public SubCommand getSubCommand(Class<? extends SubCommand> subCommandClass) {
        return subCommandsByClass.get(subCommandClass);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Base Plugin v" + Labirinthian.getInstance().getDescription().getVersion() + " by Nonick96");
            if (sender.hasPermission(BASE_PERM + "help")) {
                sender.sendMessage("Commands: /" + label + " help");
            }
            return true;
        }
        for (SubCommand subCommand : subCommands) {
            // se inizia con /base
            if (subCommand.isValidTrigger(args[0])) {

                if (!subCommand.hasPermission(sender)) {
                    sender.sendMessage("You don't have permission.");
                    return true;
                }

                if (args.length - 1 >= subCommand.getMinimumArguments()) {
                    try {
                        subCommand.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
                    } catch (CommandException e) {
                        sender.sendMessage(e.getMessage());
                    }
                } else {
                    sender.sendMessage("Usage: /" + label + " " + subCommand.getName() + " " + subCommand.getPossibleArguments());
                }

                return true;
            }
        }

        sender.sendMessage("Unknown sub-command. Type \"/" + label + " help\" for a list of commands.");
        return true;
    }
}
