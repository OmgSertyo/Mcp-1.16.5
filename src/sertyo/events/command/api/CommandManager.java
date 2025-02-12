package sertyo.events.command.api;

import com.darkmagician6.eventapi.EventManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandManager {
    private final ArrayList<CommandExecuter> commands = new ArrayList();
    @Getter
    private static String prefix = ".";

    public CommandManager() {
        EventManager.register(new CommandHandler(this));
   /*     this.commands.add(new PrefixCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new FriendCommand());
        this.commands.add(new StaffCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new MacroCommand());
        this.commands.add(new ParseCommand());
        this.commands.add(new HelpCommand());*/
    }

    public List<CommandExecuter> getCommands() {
        return this.commands;
    }

    public boolean execute(String args) {
        Iterator var2 = this.commands.iterator();

        CommandExecuter command;
        String[] split;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            command = (CommandExecuter)var2.next();
            split = args.substring(1).split(" ");
        } while(!split[0].equalsIgnoreCase(command.name));

        try {
            command.execute(split);
        } catch (Exception var6) {
            command.error();
        }

        return true;
    }

    public static void setPrefix(String prefix) {
        CommandManager.prefix = prefix;
    }
}
