package fadidev.old_plugin;

/**
 * Created by Fadi on 23-4-2016.
 */
public enum OldConfigPath {

    USE_LOG(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.UseLog", "Use"),
    READ_PRIVATE_MESSAGE(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadPrivateMessages", "Read.Private"),
    READ_SPIES(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadSpies", "Read.Spy"),
    READ_TOGGLE(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadToggles", "Read.Toggle"),
    READ_MUTE(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadMutes", "Read.Mute"),
    READ_RELOAD(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadReloads", "Read.Reload"),
    READ_RELOADDATA(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadReloadData", "Read.ReloadData"),
    READ_BANNEDWORD(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadBannedWords", "Read.BannedWord"),
    READ_SPAM(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadSpam", "Read.Spam"),
    READ_ADVERTISE(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadAdvertising", "Read.Advertise"),
    READ_GLOBAL(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadGlobal", "Read.Global"),
    READ_CHANNEL(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadChannels", "Read.Channel"),
    READ_MUTEALL(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadMuteAll", "Read.MuteAll"),
    READ_IGNORE(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadIgnores", "Read.Ignore"),
    READ_REPORT(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadReports", "Read.Report"),
    READ_HELPOP(LoadType.BOOLEAN, "config.yml", "configs/log-cfg.yml", "Log.ReadHelpOps", "Read.HelpOp"),

    USE_ANTISPAM(LoadType.BOOLEAN, "config.yml", "configs/spam-cfg.yml", "AntiSpam.UseAntiSpam", "Use"),
    DUPLICATE_CANCEL(LoadType.BOOLEAN, "config.yml", "configs/spam-cfg.yml", "AntiSpam.CancelDuplicate", "Duplicate.Cancel"),
    DUPLICATE_SENSITIVITY(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.DuplicateSensitivity", "Duplicate.Sensitivity"),
    FASTUSAGE_CANCEL(LoadType.BOOLEAN, "config.yml", "configs/spam-cfg.yml", "AntiSpam.CancelTooFastUsage", "FastUsage.Cancel"),
    FASTUSAGE_TIMER(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.TooFastTimeCheck", "FastUsage.Timer"),
    FASTUSAGE_MAX(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.TooFastMaxUsage", "FastUsage.Max"),
    COOLDOWN_USE(LoadType.BOOLEAN, "config.yml", "configs/spam-cfg.yml", "AntiSpam.UseCooldown", "Cooldown.Use"),
    COOLDOWN_PRIVATE(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.MessageCooldown", "Cooldown.Private"),
    COOLDOWN_GLOBAL(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.GlobalCooldown", "Cooldown.Global"),
    CAPS_CANCEL(LoadType.BOOLEAN, "config.yml", "configs/spam-cfg.yml", "AntiSpam.CancelCaps", "Caps.Cancel"),
    CAPS_MAX(LoadType.INTEGER, "config.yml", "configs/spam-cfg.yml", "AntiSpam.MaxCapsInMessage", "Caps.Max"),

    USE_ANTIAD(LoadType.BOOLEAN, "config.yml", "configs/advertise-cfg.yml", "AntiAdvertise.UseAntiAdvertise", "Use"),
    CANCEL_IPS(LoadType.BOOLEAN, "config.yml", "configs/advertise-cfg.yml", "AntiAdvertise.CancelIPs", "Cancel.IPs"),
    CANCEL_DOMAINS(LoadType.BOOLEAN, "config.yml", "configs/advertise-cfg.yml", "AntiAdvertise.CancelDomainNames", "Cancel.Domains"),
    KICK(LoadType.BOOLEAN, "config.yml", "configs/advertise-cfg.yml", "AntiAdvertise.Kick", "Kick"),

    MESSAGE(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Message", "Private.Commands"),
    REPLY(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Reply", "Reply.Commands"),
    RELOAD(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Reload", "Reload.Commands"),
    SPY(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Spy", "Spy.Commands"),
    TOGGLE(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Toggle", "Toggle.Commands"),
    MUTE(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Mute", "Mute.Commands"),
    GLOBAL(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Global", "Global.Commands"),
    MUTEALL(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.MuteAll", "MuteAll.Commands"),
    IGNORE(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Ignore", "Ignore.Commands"),
    REPORT(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.Report", "Report.Commands"),
    HELPOP(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "Commands.HelpOp", "HelpOp.Commands"),

    AUTO_GROUPS(LoadType.BOOLEAN, "config.yml", "configs/group-cfg.yml", "GlobalChat.UseAutoGlobalChat", "Auto"),
    GROUPS_PERMISSION(LoadType.BOOLEAN, "config.yml", "configs/command-cfg.yml", "GlobalChat.PermissionRequiredForGlobalMessage", "Global.Permission.Use"),
    GLOBAL_MESSAGE(LoadType.STRING, "config.yml", "configs/group-cfg.yml", "GlobalChat.GlobalMessage", "Groups.ALL.Message.Message"),
    GROUPS(LoadType.GROUPS, "config.yml", "configs/group-cfg.yml", "GlobalChat.Groups", "Groups"),

    CHANNELS(LoadType.CHANNELS, "config.yml", "configs/channel-cfg.yml", "GlobalChat.Channels", "Channels"),

    BROADCAST_PERMISSION(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "GlobalChat.Broadcast.Broadcast.Permission", "Broadcast.Permission.Permission"),
    BROADCAST_NO_PERMISSION(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "GlobalChat.Broadcast.Broadcast.NoPermission", "Broadcast.Messages.NoPermission.Message"),
    BROADCAST_WRONG_USAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "GlobalChat.Broadcast.Broadcast.WrongUsage", "Broadcast.Messages.WrongUsage.Message"),
    BROADCAST_MESSAGES(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "GlobalChat.Broadcast.Broadcast.Messages", "Broadcast.Messages.Broadcast.MessageList"),
    BROADCAST_COMMANDS(LoadType.STRINGLIST, "config.yml", "configs/command-cfg.yml", "GlobalChat.Broadcast.Broadcast.Commands", "Broadcast.Commands"),

    ANNOUNCERS(LoadType.ANNOUNCERS, "config.yml", "configs/announcer-cfg.yml", "GlobalChat.AutoAnnounce", "Announcers"),

    PLAYER_SUGGEST_HOVER(LoadType.STRING, "config.yml", "configs/cfg.yml", "PlayerNameSuggest.HoverMessage", "PlayerNameSuggest.HoverMessage"),
    PLAYER_SUGGEST_COMMAND(LoadType.STRING, "config.yml", "configs/cfg.yml", "PlayerNameSuggest.SuggestedCommand", "PlayerNameSuggest.SuggestedCommand"),
    PLAYER_SUGGEST_PLAYER(LoadType.STRING, "config.yml", "configs/cfg.yml", "PlayerNameSuggest.SuggestedPlayer", "PlayerNameSuggest.SuggestedPlayer"),

    TO_RECEIVER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "Receiver", "Private.Messages.ToReceiver.Message"),
    TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "Sender", "Private.Messages.ToSender.Message"),

    ENABLE_REPLYINFO(LoadType.BOOLEAN, "config.yml", "configs/cfg.yml", "EnableInfo", "EnableReplyInfo"),
    REPLY_INFO(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "Info", "Reply.Messages.Info.Message"),

    NOT_ONLINE(LoadType.STRING, "config.yml", "configs/cfg.yml", "PlayerNotOnline", "PlayerNotOnline.Message"),
    NO_RECEIVER(LoadType.STRING, "config.yml", "configs/cfg.yml", "NoReceiver", "NoReceiver.Message"),
    TO_THEMSELVES(LoadType.STRING, "config.yml", "configs/cfg.yml", "ToThemselves", "ToThemselves.Message"),

    WRONG_USAGE_MSG(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageMSG", "Private.Messages.WrongUsage.Message"),
    WRONG_USAGE_REPLY(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageReply", "Reply.Messages.WrongUsage.Message"),
    WRONG_USAGE_TOGGLE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageToggle", "Toggle.Messages.WrongUsage.Message"),
    WRONG_USAGE_MUTE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageMute", "Mute.Messages.WrongUsage.Message"),
    WRONG_USAGE_GLOBAL(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageGlobal", "Global.Messages.WrongUsage.Message"),
    WRONG_USAGE_MUTEALL(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageMuteAll", "MuteAll.Messages.WrongUsage.Message"),
    WRONG_USAGE_IGNORE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageIgnore", "Ignore.Messages.WrongUsage.Message"),
    WRONG_USAGE_REPORT(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageReport", "Report.Messages.WrongUsage.Message"),
    WRONG_USAGE_HELPOP(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "WrongUsageHelpOp", "HelpOp.Messages.WrongUsage.Message"),

    NO_PERMISSION_RELOAD(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoReloadPermission", "Reload.Messages.NoPermission.Message"),
    NO_PERMISSION_SPY(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoSpyPermission", "Spy.Messages.NoPermission.Message"),
    NO_PERMISSION_TOGGLE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoTogglePermission", "Toggle.Messages.NoPermission.Message"),
    NO_PERMISSION_MUTE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoMutePermission", "Mute.Messages.NoPermission.Message"),
    NO_PERMISSION_GLOBAL(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoGlobalPermission", "Global.Messages.NoPermission.Message"),
    NO_PERMISSION_MUTEALL(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoMuteAllPermission", "MuteAll.Messages.NoPermission.Message"),
    NO_PERMISSION_IGNORE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoIgnorePermission", "Ignore.Messages.NoPermission.Message"),
    NO_PERMISSION_PRIVATE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoMessagePermission", "Private.Messages.NoPermission.Message"),
    NO_PERMISSION_REPLY(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoReplyPermission", "Reply.Messages.NoPermission.Message"),

    SPY_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "SpyMessage", "Spy.Messages.Message.Message"),
    SPY_ENABLE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "SpyEnable", "Spy.Messages.Enable.Message"),
    SPY_DISABLE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "SpyDisable", "Spy.Messages.Disable.Message"),

    TELL_SENDER_DISABLED(LoadType.BOOLEAN, "config.yml", "configs/cfg.yml", "TellSenderIfDisabled", "TellSenderIfPrivateDisabled"),
    DISABLED_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "DisabledMessage", "Toggle.Messages.DisabledOther.Message"),
    TOGGLE_ENABLED(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleEnable", "Toggle.Messages.Enable.Message"),
    TOGGLE_DISABLED(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleDisable", "Toggle.Messages.Disable.Message"),
    TOGGLE_ENABLED_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleEnableToSender", "Toggle.Messages.EnableOther.Message"),
    TOGGLE_ENABLED_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleEnableToPlayer", "Toggle.Messages.EnableToPlayer.Message"),
    TOGGLE_DISABLED_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleDisableToSender", "Toggle.Messages.DisableOther.Message"),
    TOGGLE_DISABLED_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleDisableToPlayer", "Toggle.Messages.DisableToPlayer.Message"),
    TOGGLE_ON_SEND(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ToggleOnSend", "Toggle.Messages.DisableToPlayer.Message"),

    MUTE_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MuteToSender", "Mute.Messages.Mute.Message"),
    UNMUTE_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "UnmuteToSender", "Mute.Messages.Unmute.Message"),
    MUTE_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MuteToPlayer", "Mute.Messages.MuteToPlayer.Message"),
    UNMUTE_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "UnmuteToPlayer", "Mute.Messages.UnmuteToPlayer.Message"),
    MUTED_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MutedMessage", "Mute.Messages.Muted.Message"),

    SPAM_DUPLICATE(LoadType.STRING, "config.yml", "configs/spam-cfg.yml", "SpamDuplicate", "Messages.Duplicate.Message"),
    SPAM_TOO_FAST(LoadType.STRING, "config.yml", "configs/spam-cfg.yml", "SpamTooFast", "Messages.TooFast.Message"),
    SPAM_COOLDOWN(LoadType.STRING, "config.yml", "configs/spam-cfg.yml", "SpamCooldown", "Messages.OnCooldown.Message"),

    SECOND_SINGLE(LoadType.STRING, "config.yml", "configs/cfg.yml", "SecondGrammerVariableOne", "SecondGrammer.Single"),
    SECOND_MULTIPLE(LoadType.STRING, "config.yml", "configs/cfg.yml", "SecondGrammerVariableMultiple", "SecondGrammer.Multiple"),

    AD_KICK(LoadType.STRING, "config.yml", "configs/advertise-cfg.yml", "AdvertiseKickMessage", "Messages.KickMessage"),
    AD_MESSAGE(LoadType.STRING, "config.yml", "configs/advertise-cfg.yml", "AdvertiseMessage", "Messages.Message.Message"),

    TYPE_VARIABLE_IP(LoadType.STRING, "config.yml", "configs/cfg.yml", "TypeVariableIPs", "DomainType.IP"),
    TYPE_VARIABLE_DOMAIN(LoadType.STRING, "config.yml", "configs/cfg.yml", "TypeVariableDomainNames", "DomainType.DomainNames"),

    MUTEALL_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MuteAllToSender", "MuteAll.Messages.MuteAll.Message"),
    MUTEALL_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MuteAllToPlayer", "MuteAll.Messages.MuteAllToPlayer.Message"),
    UNMUTEALL_TO_SENDER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "UnmuteAllToSender", "MuteAll.Messages.UnmuteAll.Message"),
    UNMUTEALL_TO_PLAYER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "UnmuteAllToPlayer", "MuteAll.Messages.UnmuteAllToPlayer.Message"),
    MUTEALL_SERVER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "MuteServerToSender", "MuteAll.Messages.MuteServer.Message"),
    UNMUTEALL_SERVER(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "UnmuteServerToSender", "MuteAll.Messages.UnmuteServer.Message"),

    USE_IGNORE_PERM(LoadType.BOOLEAN, "config.yml", "configs/command-cfg.yml", "UseIgnorePermission", "Ignore.Permission.Use"),
    IGNORED(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "Ignore", "Ignore.Messages.Ignore.Message"),
    NO_IGNORE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "NoIgnore", "Ignore.Messages.NoIgnore.Message"),
    IS_IGNORED(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "IsIgnored", "Ignore.Messages.Ignored.Message"),
    TELL_IF_IGNORED(LoadType.BOOLEAN, "config.yml", "configs/cfg.yml", "TellSenderIfIgnored", "TellSenderIfIgnored"),
    IGNORED_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "IgnoredMessage", "Ignore.Messages.IgnoredToPlayer.Message"),

    USE_MESSAGE_PERM(LoadType.BOOLEAN, "config.yml", "configs/command-cfg.yml", "UseMessagePermission", "Private.Permission.Use"),
    USE_REPLY_PERM(LoadType.BOOLEAN, "config.yml", "configs/command-cfg.yml", "UseReplyPermission", "Reply.Permission.Use"),

    REPORT_COOLDOWN(LoadType.INTEGER, "config.yml", "configs/cfg.yml", "ReportCooldown", "Cooldowns.ReportCommand"),
    HELPOP_COOLDOWN(LoadType.INTEGER, "config.yml", "configs/cfg.yml", "HelpOpCooldown", "Cooldowns.HelpOpCommand"),

    REPORT_ON_COOLDOWN(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ReportOnCooldown", "Report.Messages.OnCooldown.Message"),
    REPORT_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ReportedMessage", "Report.Messages.Report.Message"),
    REPORT_TO_STAFF(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "ReportedStaffMessage", "Report.Messages.ReportToStaff.Message"),

    HELPOP_ON_COOLDOWN(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "HelpOpOnCooldown", "HelpOp.Messages.OnCooldown.Message"),
    HELPOP_MESSAGE(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "HelpOpMessage", "HelpOp.Messages.HelpOp.Message"),
    HELPOP_TO_STAFF(LoadType.STRING, "config.yml", "configs/command-cfg.yml", "HelpOpStaffMessage", "HelpOp.Messages.HelpOpToStaff.Message"),

    BANNED_WORDS(LoadType.STRINGLIST, "banned-words.yml", "configs/bannedwords-cfg.yml", "BannedWords", "BannedWords"),
    IPS(LoadType.STRINGLIST, "domain-whitelist.yml", "configs/advertise-cfg.yml", "IPs", "IPs"),
    DOMAINS(LoadType.STRINGLIST, "domain-whitelist.yml", "configs/advertise-cfg.yml", "DomainNames", "DomainNames"),
    MUTED_UUIDS(LoadType.STRINGLIST, "muted.yml", "configs/muted.yml", "MutedUUIDs", "MutedUUIDs");

    private LoadType type;
    private String oldFileName;
    private String newFileName;
    private String oldPath;
    private String newPath;

    OldConfigPath(LoadType type, String oldFileName, String newFileName, String oldPath, String newPath){
        this.type = type;
        this.oldFileName = oldFileName;
        this.newFileName = newFileName;
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    public LoadType getType() {
        return type;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getOldPath() {
        return oldPath;
    }

    public String getNewPath() {
        return newPath;
    }
}
