package fadidev.bungeemsg.utils.enums;

public enum LogReadType {

    PRIVATE_MESSAGES("Read.Private"),
    SPIES("Read.Spy"),
    TOGGLES("Read.Toggle"),
    MUTES("Read.Mute"),
    RELOADS("Read.Reload"),
    RELOAD_DATA("Read.ReloadData"),
    BANNED_WORDS("Read.BannedWord"),
    SPAM("Read.Spam"),
    ADVERTISING("Read.Advertise"),
    GLOBAL("Read.Global"),
    CHANNELS("Read.Channel"),
    MUTE_ALL("Read.MuteAll"),
    IGNORES("Read.Ignore"),
    REPORTS("Read.Report"),
    HELP_OPS("Read.HelpOp"),
    RANK_SET("Read.SetRank");

    private String path;

    LogReadType(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
