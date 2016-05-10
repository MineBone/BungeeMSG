package fadidev.bungeemsg.utils;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.enums.Config;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 30-4-2016.
 */
public enum NewConfigPath {

    SUGGEST_SERVER_HOVER("v2.1.0", Config.CONFIG, "ServerSuggest.HoverMessage", "&7Click to connect to %server-name%"),

    ANNOUNCE_USE("v2.1.5", Config.COMMAND, "Announce.Use", true),
    ANNOUNCE_PERMISSION_USE("v2.1.5", Config.COMMAND, "Announce.Permission.Use", true),
    ANNOUNCE_PERMISSION("v2.1.5", Config.COMMAND, "Announce.Permission.Permission", "BungeeMSG.announce"),
    ANNOUNCE_NO_PERM("v2.1.5", Config.COMMAND, "Announce.Messages.NoPermission.Message", "&7You do not have access to this command!"),
    ANNOUNCE_WRONG_USAGE("v2.1.5", Config.COMMAND, "Announce.Messages.WrongUsage.Message", "&7Use &6%cmd% (server) <index>&7 or &6%cmd% list (server)&7."),
    ANNOUNCE_ANNOUNCE("v2.1.5", Config.COMMAND, "Announce.Messages.Announce.Message", "&7You have forced an announcement! (&6#%index%&7)"),
    ANNOUNCE_LISTITEM("v2.1.5", Config.COMMAND, "Announce.Messages.ListItem.Message", "&7Announcement &6#%index%&7:"),
    ANNOUNCE_NONE("v2.1.5", Config.COMMAND, "Announce.Messages.None.Message", "&7That server doesn't have any announcers!"),
    ANNOUNCE_UNKNOWN("v2.1.5", Config.COMMAND, "Announce.Messages.Unknown.Message", "&7Announcement &6#%index%&7 doesn''t exist."),
    ANNOUNCE_COMMANDS("v2.1.5", Config.COMMAND, "Announce.Commands", toList("/announce")),

    IGNORE_LIST_USE("v2.1.5", Config.COMMAND, "IgnoreList.Use", true),
    IGNORE_LIST_PERMISSION_USE("v2.1.5", Config.COMMAND, "IgnoreList.Permission.Use", true),
    IGNORE_LIST_PERMISSION("v2.1.5", Config.COMMAND, "IgnoreList.Permission.Permission", "BungeeMSG.ignorelist"),
    IGNORE_LIST_NO_PERM("v2.1.5", Config.COMMAND, "IgnoreList.Messages.NoPermission.Message", "&7You do not have access to this command!"),
    IGNORE_LIST_WRONG_USAGE("v2.1.5", Config.COMMAND, "IgnoreList.Messages.IgnoredList.Message", "&7Ignored Players:"),
    IGNORE_LIST_IGNORE_LIST("v2.1.5", Config.COMMAND, "IgnoreList.Messages.IgnoredItem.Message", "&7- &6%ignored%"),
    IGNORE_LIST_LISTITEM("v2.1.5", Config.COMMAND, "IgnoreList.Messages.None.Message", "&7You haven't ignored anyone yet."),
    IGNORE_LIST_COMMANDS("v2.1.5", Config.COMMAND, "IgnoreList.Commands", toList("/ignorelist"));

    private BungeeMSG msg;
    private String version;
    private Config cfg;
    private String path;
    private Object value;

    NewConfigPath(String version, Config cfg, String path, Object value){
        this.msg = BungeeMSG.getInstance();
        this.version = version;
        this.cfg = cfg;
        this.path = path;
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public Config getCfg() {
        return cfg;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    public void check(){
        Configuration c = msg.getConfigManager().get(getCfg());
        if(c.get(getPath()) == null){
            c.set(getPath(), getValue());
            msg.getConfigManager().save(getCfg());

            Utils.sendConsoleMSG("§e" + getVersion() + " | New config path | " + getCfg().getFileName() + " | " + getPath());
        }
    }

    public static List<String> toList(String... strings){
        List<String> strList = new ArrayList<>();
        for(String s : strings){
            strList.add(s);
        }
        return strList;
    }
}
