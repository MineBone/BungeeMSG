package fadidev.bungeemsg.handlers;

import java.util.List;

public class Channel {

    private String name;
    private boolean usePermission;
    private String permission;
    private List<String> startSymbols;
    private List<String> toggleSymbols;
    private MessageLoader message;
    private MessageLoader enabledMessage;
    private MessageLoader disabledMessage;

    public Channel(String name, boolean usePermission, String permission, List<String> startSymbols, List<String> toggleSymbols, MessageLoader message, MessageLoader enabledMessage, MessageLoader disabledMessage){
        this.name = name;
        this.usePermission = usePermission;
        this.permission = permission;
        this.startSymbols = startSymbols;
        this.toggleSymbols = toggleSymbols;
        this.message = message;
        this.enabledMessage = enabledMessage;
        this.disabledMessage = disabledMessage;
    }

    public String getName() {
        return name;
    }

    public boolean usePermission() {
        return usePermission;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getStartSymbols() {
        return startSymbols;
    }

    public List<String> getToggleSymbols() {
        return toggleSymbols;
    }

    public MessageLoader getMessage() {
        return message;
    }

    public MessageLoader getEnabledMessage() {
        return enabledMessage;
    }

    public MessageLoader getDisabledMessage() {
        return disabledMessage;
    }
}
