package fadidev.bungeemsg.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Utils {

	public static Pattern IP_PATTERN = Pattern.compile("(?:[0-9]{1,3}( ?\\. ?|\\(?dot\\)?)){3}[0-9]{1,3}");
    public static Pattern DOMAIN_PATTERN = Pattern.compile("(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}( ?\\. ?| ?\\(?dot\\)? ?)[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");

	public static void sendConsoleEmpty(){
		ProxyServer.getInstance().getLogger().info("");
	}
	
	public static void sendConsoleMSG(String msg){
		ProxyServer.getInstance().getLogger().info("[BungeeMSG] " + msg);
	}
	
	public static void warnConsole(String msg){
		ProxyServer.getInstance().getLogger().warning("[BungeeMSG] §c" + msg);
	}
	
	public static void successConsole(String msg){
		ProxyServer.getInstance().getLogger().info("[BungeeMSG] §a" + msg);
	}	
	
	public static String getDate(){
		Calendar c = Calendar.getInstance();
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(c.getTimeInMillis())) + " (" + c.getTimeZone().getDisplayName() + ")";
	}

	public static String getDayDate(){
		Calendar c = Calendar.getInstance();
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(c.getTimeInMillis()));
	}
	
	public static List<String> parseStringList(List<UUID> uuidList){
		List<String> stringList = new ArrayList<String>();
		for(UUID uuid : uuidList){
			stringList.add(uuid.toString());
		}
		return stringList;
	}
	
	public static List<ServerInfo> fromString(String serverString){
		List<ServerInfo> servers = new ArrayList<ServerInfo>();
		if(serverString.equals("[ALL]")){
			servers.addAll(ProxyServer.getInstance().getServers().values());
		}
		else{
			String[] serverParts = serverString.split("\\|");
			for(String server : serverParts){
				ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
				
				if(info != null){
					servers.add(info);
				}
				else{
					Utils.warnConsole("Unknown Server: '" + server + "'.");
				}
			}
		}
		
		return servers;
	}
	
	public static String checkforColors(ProxiedPlayer p, String message){
		if(p.hasPermission("BungeeMSG.colors.black") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&0", "§0");
		}
		if(p.hasPermission("BungeeMSG.colors.dark_blue") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&1", "§1");
		}
		if(p.hasPermission("BungeeMSG.colors.green") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&2", "§2");
		}
		if(p.hasPermission("BungeeMSG.colors.cyan") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&3", "§3");
		}
		if(p.hasPermission("BungeeMSG.colors.dark_red") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&4", "§4");
		}
		if(p.hasPermission("BungeeMSG.colors.purple") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&5", "§5");
		}
		if(p.hasPermission("BungeeMSG.colors.orange") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&6", "§6");
		}
		if(p.hasPermission("BungeeMSG.colors.light_gray") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&7", "§7");
		}
		if(p.hasPermission("BungeeMSG.colors.gray") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&8", "§8");
		}
		if(p.hasPermission("BungeeMSG.colors.blue") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&9", "§9");
		}
		if(p.hasPermission("BungeeMSG.colors.lime") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&a", "§a");
		}
		if(p.hasPermission("BungeeMSG.colors.aqua") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&b", "§b");
		}
		if(p.hasPermission("BungeeMSG.colors.red") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&c", "§c");
		}
		if(p.hasPermission("BungeeMSG.colors.pink") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&d", "§d");
		}
		if(p.hasPermission("BungeeMSG.colors.yellow") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&e", "§e");
		}
		if(p.hasPermission("BungeeMSG.colors.white") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&f", "§f");
		}
		
		if(p.hasPermission("BungeeMSG.colors.reset") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&r", "§r");
		}
		if(p.hasPermission("BungeeMSG.colors.italic") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&o", "§o");
		}
		if(p.hasPermission("BungeeMSG.colors.bold") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&l", "§l");
		}
		if(p.hasPermission("BungeeMSG.colors.magic") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&k", "§k");
		}
		if(p.hasPermission("BungeeMSG.colors.strikethrough") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&m", "§m");
		}
		if(p.hasPermission("BungeeMSG.colors.underlined") || p.hasPermission("BungeeMSG.colors.*")){
			message = message.replace("&n", "§n");
		}
		
		return message;
	}
}
