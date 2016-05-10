package fadidev.bungeemsg.utils.enums;

import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.handlers.MessageLoader;
import fadidev.bungeemsg.handlers.MessageParser;


public enum Message {

    HOVER_MESSAGE(Config.CONFIG, "PlayerNameSuggest.HoverMessage", false),
    SUGGESTED_COMMAND(Config.CONFIG, "PlayerNameSuggest.SuggestedCommand", false),
    SUGGESTED_PLAYER(Config.CONFIG, "PlayerNameSuggest.SuggestedPlayer", false),

    PM_TO_RECEIVER(Config.COMMAND, "Private.Messages.ToReceiver", true),
    PM_TO_SENDER(Config.COMMAND, "Private.Messages.ToSender", true),
    PM_ENABLED(Config.COMMAND, "Toggle.Messages.Enable", true),
    PM_DISABLED(Config.COMMAND, "Toggle.Messages.Disable", true),
    PM_ENABLED_TO_SENDER(Config.COMMAND, "Toggle.Messages.EnableOther", true),
    PM_DISABLED_TO_SENDER(Config.COMMAND, "Toggle.Messages.DisableOther", true),
    PM_ENABLED_TO_PLAYER(Config.COMMAND, "Toggle.Messages.EnableToPlayer", true),
    PM_DISABLED_TO_PLAYER(Config.COMMAND, "Toggle.Messages.DisableToPlayer", true),
    PM_TOGGLED(Config.COMMAND, "Toggle.Messages.Disabled", true),
    PM_TOGGLED_OTHER(Config.COMMAND, "Toggle.Messages.DisabledOther", true),
    REPLY_INFO(Config.COMMAND, "Reply.Messages.Info", true),

    NOT_ONLINE(Config.CONFIG, "PlayerNotOnline", true),
    NO_RECEIVER(Config.CONFIG, "NoReceiver", true),
    TO_THEMSELVES(Config.CONFIG, "ToThemselves", true),

    SPY_MESSAGE(Config.COMMAND, "Spy.Messages.Message", true),
    SPY_ENABLE(Config.COMMAND, "Spy.Messages.Enable", true),
    SPY_DISABLE(Config.COMMAND, "Spy.Messages.Disable", true),

    MUTE_TO_SENDER(Config.COMMAND, "Mute.Messages.Mute", true),
    MUTE_TO_PLAYER(Config.COMMAND, "Mute.Messages.MuteToPlayer", true),
    UNMUTE_TO_SENDER(Config.COMMAND, "Mute.Messages.Unmute", true),
    UNMUTE_TO_PLAYER(Config.COMMAND, "Mute.Messages.UnmuteToPlayer", true),
    MUTE_ALL_TO_SENDER(Config.COMMAND, "MuteAll.Messages.MuteAll", true),
    MUTE_ALL_TO_PLAYER(Config.COMMAND, "MuteAll.Messages.MuteAllToPlayer", true),
    UNMUTE_ALL_TO_SENDER(Config.COMMAND, "MuteAll.Messages.UnmuteAll", true),
    UNMUTE_ALL_TO_PLAYER(Config.COMMAND, "MuteAll.Messages.UnmuteAllToPlayer", true),
    MUTE_SERVER_TO_SENDER(Config.COMMAND, "MuteAll.Messages.MuteServer", true),
    UNMUTE_SERVER_TO_SENDER(Config.COMMAND, "MuteAll.Messages.UnmuteServer", true),
    MUTED(Config.COMMAND, "Mute.Messages.Muted", true),

    SPAM_DUPLICATE(Config.SPAM, "Messages.Duplicate", true),
    SPAM_TOO_FAST(Config.SPAM, "Messages.TooFast", true),
    SPAM_COOLDOWN(Config.SPAM, "Messages.OnCooldown", true),

    SECOND_SINGLE(Config.CONFIG, "SecondGrammer.Single", false),
    SECOND_MULTIPLE(Config.CONFIG, "SecondGrammer.Multiple", false),

    ADVERTISE_KICK(Config.ADVERTISE, "Messages.KickMessage", false),
    ADVERTISE_MESSAGE(Config.ADVERTISE, "Messages.Message", true),

    IP_VARIABLE(Config.CONFIG, "DomainType.IP", false),
    DOMAIN_VARIABLE(Config.CONFIG, "DomainType.DomainNames", false),

    IGNORE_ENABLE(Config.COMMAND, "Ignore.Messages.Ignore", true),
    IGNORE_DISABLE(Config.COMMAND, "Ignore.Messages.NoIgnore", true),
    IGNORED(Config.COMMAND, "Ignore.Messages.Ignored", true),
    IGNORED_YOU(Config.COMMAND, "Ignore.Messages.IgnoredToPlayer", true),

    REPORT_ON_COOLDOWN(Config.COMMAND, "Report.Messages.OnCooldown", true),
    REPORTED_TO_SENDER(Config.COMMAND, "Report.Messages.Report", true),
    REPORTED_TO_STAFF(Config.COMMAND, "Report.Messages.ReportToStaff", true),

    HELPOP_ON_COOLDOWN(Config.COMMAND, "HelpOp.Messages.OnCooldown", true),
    HELPOP_TO_SENDER(Config.COMMAND, "HelpOp.Messages.HelpOp", true),
    HELPOP_TO_STAFF(Config.COMMAND, "HelpOp.Messages.HelpOpToStaff", true),

    BROADCAST(Config.COMMAND, "Broadcast.Messages.Broadcast", true),

    UNKNOWN_RANK(Config.COMMAND, "SetRank.Messages.UnknownRank", true),
    RANK_SET(Config.COMMAND, "SetRank.Messages.RankSet", true),

    REPORTLIST_LAST(Config.COMMAND, "ReportList.Messages.ReportListLast", true),
    REPORTLIST_PLAYER(Config.COMMAND, "ReportList.Messages.ReportListPlayer", true),
    REPORTLIST_DISPLAY(Config.COMMAND, "ReportList.Messages.ReportDisplay", true),
    REPORTLIST_NONE_PLAYER(Config.COMMAND, "ReportList.Messages.NoPlayerReports", true),
    REPORTLIST_NONE(Config.COMMAND, "ReportList.Messages.NoReports", true),

    UNKNOWN_NUMBER(Config.CONFIG, "UnknownNumber", true),
    UNKNOWN_SERVER(Config.CONFIG, "UnknownServer", true),

    SERVER_HOVER(Config.CONFIG, "ServerSuggest.HoverMessage", false),

    ANNOUNCE(Config.COMMAND, "Announce.Messages.Announce", true),
    ANNOUNCE_LISTITEM(Config.COMMAND, "Announce.Messages.ListItem", true),
    ANNOUNCE_NONE(Config.COMMAND, "Announce.Messages.None", true),
    ANNOUNCE_UNKNOWN(Config.COMMAND, "Announce.Messages.Unknown", true),

    IGNORELIST_PRE(Config.COMMAND, "IgnoreList.Messages.IgnoredList", true),
    IGNORELIST_ITEM(Config.COMMAND, "IgnoreList.Messages.IgnoredItem", true),
    IGNORELIST_NONE(Config.COMMAND, "IgnoreList.Messages.None", true);

    private Config config;
    private String path;
    private MessageLoader msgL;
    private boolean normalLoad;

    Message(Config config, String path, boolean normalLoad){
        this.config = config;
        this.path = path;
        this.normalLoad = normalLoad;
    }

    public Config getConfig() {
        return config;
    }

    public String getPath() {
        return path;
    }

    public MessageLoader getMSGLoader() {
        return msgL;
    }

    public void setMSGLoader(MessageLoader msgL) {
        this.msgL = msgL;
    }

    public MessageParser getParser(BungeePlayer bp){
        return msgL.getParser(bp);
    }

    public boolean canLoadNormal() {
        return normalLoad;
    }
}
