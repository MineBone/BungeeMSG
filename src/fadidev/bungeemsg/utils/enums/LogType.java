package fadidev.bungeemsg.utils.enums;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;
import fadidev.bungeemsg.handlers.Channel;
import fadidev.bungeemsg.handlers.Group;

public enum LogType {

    DEFAULT("default", "Default Log"),
    SERVER("servers", "Server Log (%server%)"),
    CHANNEL("channels", "Channel Log (%channel%)"),
    GLOBAL("globals", "Global Log (%global%)"),
    PRIVATE_MESSAGES("pms", "PM Log"),
    ALL_CHANNELS("allchannels", "Channels Log"),
    ALL_GLOBALS("allglobals", "Globals Log");

    private String dir;
    private String logName;
    private List<LogReadType> logReadTypes;

    LogType(String dir, String logName, LogReadType... types){
        this.dir = dir;
        this.logName = logName;
        this.logReadTypes = new ArrayList<LogReadType>();
        for(LogReadType type : types){
            this.logReadTypes.add(type);
        }
    }

    public String getDir() {
        return dir;
    }

    public String getLogName() {
        return logName;
    }

    public String getLogName(ServerInfo server){
        return logName.replace("%server%", server.getName());
    }

    public String getLogName(Channel channel){
        return logName.replace("%channel%", channel.getName());
    }

    public String getLogName(Group group){
        return logName.replace("%global%", group.getName());
    }

    public List<LogReadType> getLogReadTypes() {
        return logReadTypes;
    }
}
