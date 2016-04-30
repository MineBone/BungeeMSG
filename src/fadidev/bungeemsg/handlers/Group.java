package fadidev.bungeemsg.handlers;

import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;

public class Group {

	private String name;
	private List<ServerInfo> servers;
	private MessageLoader msgL;
	
	public Group(String name, List<ServerInfo> servers, MessageLoader msgL){
		this.name = name;
		this.servers = servers;
		this.msgL = msgL;
	}
	
	public String getName() {
		return name;
	}
	
	public List<ServerInfo> getServers() {
		return servers;
	}
	
	public MessageLoader getMSGLoader() {
		return msgL;
	}
}
