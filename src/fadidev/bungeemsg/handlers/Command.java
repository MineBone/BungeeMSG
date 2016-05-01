package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.List;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.enums.CommandType;

public class Command {

    private static BungeeMSG msg;
    private boolean use;
    private CommandType type;
    private boolean usePermission;
    private String permission;
    private List<String> commands;
    private MessageLoader wrongUsage;
    private MessageLoader noPermission;

    public Command(boolean use, CommandType type, boolean usePermission, String permission, List<String> commands, MessageLoader wrongUsage, MessageLoader noPermission){
        msg = BungeeMSG.getInstance();
        this.use = use;
        this.type = type;
        this.usePermission = usePermission;
        this.permission = permission;

        this.commands = new ArrayList<String>();
        for(String cmd : commands){
            this.commands.add(cmd.toLowerCase());
        }
        this.wrongUsage = wrongUsage;
        this.noPermission = noPermission;
    }

    public boolean isUsed() {
        return use;
    }

    public CommandType getType() {
        return type;
    }

    public boolean usePermission() {
        return usePermission;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getCommands() {
        return commands;
    }

    public MessageLoader getWrongUsage() {
        return wrongUsage;
    }

    public MessageLoader getNoPermission() {
        return noPermission;
    }

    public static Command getCommand(CommandType type) {
        for(Command cmd : msg.getCommands()){
            if(cmd.getType() == type){
                return cmd;
            }
        }
        return null;
    }
}
