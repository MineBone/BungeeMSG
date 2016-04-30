package fadidev.bungeemsg.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fadidev.bungeemsg.utils.Utils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.Channel;
import fadidev.bungeemsg.handlers.Group;
import fadidev.bungeemsg.handlers.Log;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.LogType;

public class LogManager {

	private BungeeMSG msg;
	private boolean use;
    private boolean bungeeAsDefault;
	private List<LogReadType> read;
	private List<Log> logs;
	private List<LogType> logTypes;
	
	public LogManager(boolean use, boolean bungeeAsDefault, List<LogReadType> read, boolean defaultLog, boolean perServerLog, boolean perChannelLog, boolean perGlobalLog, boolean allChannelsLog, boolean allGlobalsLog, boolean pmLog){
		this.msg = BungeeMSG.getInstance();
		this.use = use;
        this.bungeeAsDefault = bungeeAsDefault;
		this.read = read;
		this.logs = new ArrayList<Log>();
		this.logTypes = new ArrayList<LogType>();
		
		updateLogTypes(defaultLog, perServerLog, perChannelLog, perGlobalLog, allChannelsLog, allGlobalsLog, pmLog);

		File f = new File(msg.getDataFolder().getPath() + "/logs");
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	public void updateLogTypes(boolean defaultLog, boolean perServerLog, boolean perChannelLog, boolean perGlobalLog, boolean allChannelsLog, boolean allGlobalsLog, boolean pmLog){
		this.logTypes = new ArrayList<LogType>();
		
		if(defaultLog) logTypes.add(LogType.DEFAULT);
		if(perServerLog) logTypes.add(LogType.SERVER);
		if(perChannelLog) logTypes.add(LogType.CHANNEL);
		if(perGlobalLog) logTypes.add(LogType.GLOBAL);
		if(allChannelsLog) logTypes.add(LogType.ALL_CHANNELS);
		if(allGlobalsLog) logTypes.add(LogType.ALL_GLOBALS);
		if(pmLog) logTypes.add(LogType.PRIVATE_MESSAGES);
	}
	
	public void loadLogs(){
		this.logs.clear();
		
		for(LogType type : logTypes){
			loadLog(type, true);
		}
	}
	
	public void loadLog(LogType type, boolean forceNew){
		List<Log> logs = new ArrayList<Log>();
		
		if(isUsed()){
			if(logTypes.contains(type)){
				switch(type){
					case CHANNEL:
						for(Channel channel : msg.getChannels()){
							Log log = getLog(channel);
							if(forceNew || log == null){
								log = new Log(this, type, channel);
								log.createNew();
							}
							else{
								this.logs.remove(log);
							}
							logs.add(log);
						}
						break;
					case GLOBAL:
						for(Group group : msg.getGroups()){
							Log log = getLog(group);
							if(forceNew || log == null){
								log = new Log(this, type, group);
								log.createNew();
							}
							else{
								this.logs.remove(log);
							}
							logs.add(log);
						}
						break;
					case SERVER:
						for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
							Log log = getLog(server);
							if(forceNew || log == null){
								log = new Log(this, type, server);
								log.createNew();
							}
							else{
								this.logs.remove(log);
							}
							logs.add(log);
						}
						break;
					default:
						Log log = getLog(type);
						if(forceNew || log == null){
							log = new Log(this, type);
							log.createNew();
						}
						else{
							this.logs.remove(log);
						}
						logs.add(log);
						break;
				}
			}
		}
		
		this.logs.addAll(logs);
	}
	
	public boolean isUsed() {
		return use;
	}

    public boolean isBungeeAsDefault() {
        return bungeeAsDefault;
    }

    public List<LogReadType> getRead() {
		return read;
	}
	
	public boolean isRead(LogReadType type) {
		return read.contains(type);
	}
	
	public Log getLog(Channel channel){
		for(Log log : this.logs){
			if(log.getChannel() != null && log.getChannel().getName().equals(channel.getName())){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(Group group){
		for(Log log : this.logs){
			if(log.getGroup() != null && log.getGroup().getName().equals(group.getName())){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(ServerInfo server){
		for(Log log : this.logs){
			if(log.getServer() != null && log.getServer().getName().equals(server.getName())){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(LogType type){
		for(Log log : this.logs){
			if(log.getType() != null && log.getType() == type){
				return log;
			}
		}
		return null;
	}
	
	public void info(LogReadType type, ServerInfo server, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType().getLogReadTypes().contains(type) || log.getServer() == server){
					if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
                        log.getLog().info(s);
                    }
                    else{
						Utils.sendConsoleMSG(s);
                    }
				}
			}
		}
	}
	
	public void info(Channel channel, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType() == LogType.ALL_CHANNELS || log.getChannel() == channel){
                    if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
                        log.getLog().info(s);
                    }
                    else{
						Utils.sendConsoleMSG(s);
                    }
				}
			}
		}
	}
	
	public void info(Group group, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType() == LogType.ALL_GLOBALS || log.getGroup() == group){
                    if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
                        log.getLog().info(s);
                    }
                    else{
						Utils.sendConsoleMSG(s);
                    }
				}
			}
		}
	}
	
	public void warning(LogReadType type, ServerInfo server, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType().getLogReadTypes().contains(type) || log.getServer() == server){
                    if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
                        log.getLog().warning(s);
                    }
                    else{
                        Utils.warnConsole(s);
                    }
				}
			}
		}
	}
	
	public void warning(Channel channel, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType() == LogType.ALL_CHANNELS || log.getChannel() == channel){
					if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
						log.getLog().warning(s);
					}
					else{
						Utils.warnConsole(s);
					}
				}
			}
		}
	}
	
	public void warning(Group group, String s){
		if(isUsed()){
			for(Log log : this.logs){
				if(log.getType() == LogType.DEFAULT || log.getType() == LogType.ALL_GLOBALS || log.getGroup() == group){
					if(!(isBungeeAsDefault() && log.getType() == LogType.DEFAULT)){
						log.getLog().warning(s);
					}
					else{
						Utils.warnConsole(s);
					}
				}
			}
		}
	}
}
