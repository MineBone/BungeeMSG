package fadidev.bungeemsg.utils.enums;

public enum MessageType {

    MESSAGE("Message"),
    MESSAGE_LIST("MessageList"),
    ACTION_BAR("ActionBar"),
    TITLE("Title");

    private String cfgName;

    MessageType(String cfgName){
        this.cfgName = cfgName;
    }

    public String getConfigName() {
        return cfgName;
    }
}