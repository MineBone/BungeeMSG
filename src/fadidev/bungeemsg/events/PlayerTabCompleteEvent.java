package fadidev.bungeemsg.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.handlers.Channel;
import fadidev.bungeemsg.handlers.Command;
import fadidev.bungeemsg.utils.enums.CommandType;

public class PlayerTabCompleteEvent implements Listener {

	private BungeeMSG msg;
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTab(TabCompleteEvent  e){
		if(!e.getSuggestions().isEmpty()){
            return;
		}
		this.msg = BungeeMSG.getInstance();
        String[] a = e.getCursor().split(" ");
        
	    if(a.length != 0 && e.getCursor().startsWith("/")){
	    	for(Command cmd : msg.getPlayerTabCommands()){
		        if(cmd.getCommands().contains(a[0].toLowerCase())){
			        if(a.length > 1){
			        	final String checked = (a.length > 0 ? a[a.length - 1] : e.getCursor()).toLowerCase();
				        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
				            if(player.getName().toLowerCase().startsWith(checked) && !msg.hideTab(player)){
				                e.getSuggestions().add(player.getName());
				            }
				        }
			        }
			        else{
			        	if(e.getCursor().substring(a[0].length()).startsWith(" ")){
			        		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
								if(!msg.hideTab(player)) {
									e.getSuggestions().add(player.getName());
								}
				        	}
			        	}
			        }
			        return;
		        }
	    	}
	        
	        if(Command.getCommand(CommandType.MUTE_ALL).getCommands().contains(a[0].toLowerCase())){
		        if(a.length > 1){
		        	final String checked = (a.length > 0 ? a[a.length - 1] : e.getCursor()).toLowerCase();
			        for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
			            if(server.getName().toLowerCase().startsWith(checked)){
			                e.getSuggestions().add(server.getName());
			            }
			        }
		        }
		        else{
		        	if(e.getCursor().substring(a[0].length()).startsWith(" ")){
				        for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
			                e.getSuggestions().add(server.getName());
			        	}
		        	}
		        }
		        return;
	        }
        }
	    
        if(e.getSender() instanceof ProxiedPlayer){ 
        	ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        	BungeePlayer bp = msg.getBungeePlayers().get(p);
        	
	        if(bp.hasMSGEnabled()){
				boolean found = false;
				
				loop:
				for(Channel channel : msg.getChannels()){
					if(!channel.usePermission() || p.hasPermission(channel.getPermission())){
						for(String symbol : channel.getStartSymbols()){
							if(e.getCursor().toLowerCase().startsWith(symbol.toLowerCase())){
								found = true;
								break loop;
							}
						}
					}
				}
				
				if(found){
			        if(a.length > 1){
			        	final String checked = (a.length > 0 ? a[a.length - 1] : e.getCursor()).toLowerCase();
				        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
				            if(player.getName().toLowerCase().startsWith(checked) && !msg.hideTab(player)){
				                e.getSuggestions().add(player.getName());
				            }
				        }
			        }
			        else{
			        	if(e.getCursor().substring(a[0].length()).startsWith(" ")){
			        		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
								if(!msg.hideTab(player)) {
									e.getSuggestions().add(player.getName());
								}
				        	}
			        	}
			        }
				}
        	}
        }
	}
}
