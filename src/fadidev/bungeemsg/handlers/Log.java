package fadidev.bungeemsg.handlers;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.log.ConciseFormatter;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.LogManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.LogType;

public class Log {

    private BungeeMSG msg;
    private Logger log;
    private FileHandler fileH;
    private LogManager logManager;
    private LogType type;
    private ServerInfo server;
    private Channel channel;
    private Group group;
    private boolean loaded;
    private String logName;
    private String path;

    public Log(LogManager logManager, LogType type){
        this.msg = BungeeMSG.getInstance();
        this.logManager = logManager;
        this.type = type;
        this.loaded = false;
        this.logName = type.getLogName();
        this.path = msg.getDataFolder().getPath() + "/logs/" + type.getDir();
    }

    public Log(LogManager logManager, LogType type, ServerInfo server){
        this.msg = BungeeMSG.getInstance();
        this.logManager = logManager;
        this.type = type;
        this.server = server;
        this.loaded = false;
        this.logName = type.getLogName(server);
        this.path = msg.getDataFolder().getPath() + "/logs/" + type.getDir() + "/" + server.getName();
    }

    public Log(LogManager logManager, LogType type, Channel channel){
        this.msg = BungeeMSG.getInstance();
        this.logManager = logManager;
        this.type = type;
        this.channel = channel;
        this.loaded = false;
        this.logName = type.getLogName(channel);
        this.path = msg.getDataFolder().getPath() + "/logs/" + type.getDir() + "/" + channel.getName();
    }

    public Log(LogManager logManager, LogType type, Group group){
        this.msg = BungeeMSG.getInstance();
        this.logManager = logManager;
        this.type = type;
        this.group = group;
        this.loaded = false;
        this.logName = type.getLogName(group);
        this.path = msg.getDataFolder().getPath() + "/logs/" + type.getDir() + "/" + group.getName();
    }

    public void createNew(){
        if(log == null){
            if(group != null){
                this.log = Logger.getLogger("BungeeMSG - " + this.type.getLogName() + " (" + group.getName() + ")");
            }
            else if(channel != null){
                this.log = Logger.getLogger("BungeeMSG - " + this.type.getLogName() + " (" + channel.getName() + ")");
            }
            else if(server != null){
                this.log = Logger.getLogger("BungeeMSG - " + this.type.getLogName() + " (" + server.getName() + ")");
            }
            else{
                this.log = Logger.getLogger("BungeeMSG - " + this.type.getLogName());
            }
        }

        File f = new File(msg.getDataFolder().getPath() + "/logs/" + type.getDir());
        if(!f.exists()){
            f.mkdir();
        }

        if(!f.getPath().equals(this.path)){
            File f2 = new File(this.path);
            if(!f2.exists()){
                f2.mkdir();
            }
        }

        if(fileH != null){
            log.removeHandler(fileH);
        }

        try{
            FileHandler fh = new FileHandler(this.path + "/" + Utils.getDayDate() + "_BungeeMSG.log", 1 << 24, 8, true);
            fh.setFormatter(new ConciseFormatter());
            log.addHandler(fh);

            log.setUseParentHandlers(false);

            log.info("Starting BungeeMSG " + logName + "... (" + Utils.getDate() + ")");
            log.info("");
            log.info("BungeeMSG " + msg.getVersion() + " - Developed by O_o_Fadi_o_O.");
            log.info("");

            loaded = true;
            this.fileH = fh;
        }catch(SecurityException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public Logger getLog() {
        return log;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public LogType getType() {
        return type;
    }

    public ServerInfo getServer() {
        return server;
    }

    public Channel getChannel() {
        return channel;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
