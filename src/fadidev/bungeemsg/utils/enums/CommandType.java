package fadidev.bungeemsg.utils.enums;

public enum CommandType {

    MESSAGE("Private", true),
    REPLY("Reply", true),
    RELOAD("Reload", false),
    SPY("Spy", false),
    TOGGLE("Toggle", true),
    MUTE("Mute", true),
    GLOBAL("Global", true),
    MUTE_ALL("MuteAll", false),
    IGNORE("Ignore", true),
    REPORT("Report", true),
    HELP_OP("HelpOp", true),
    BROADCAST("Broadcast", true),
    SETRANK("SetRank", true),
    REPORTLIST("ReportList", true);

    private String path;
    private boolean playerTab;

    CommandType(String path, boolean playerTab){
        this.path = path;
        this.playerTab = playerTab;
    }

    public String getPath() {
        return path;
    }

    public boolean hasPlayerTab() {
        return playerTab;
    }
}
