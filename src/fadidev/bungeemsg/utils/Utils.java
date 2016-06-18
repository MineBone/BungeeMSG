package fadidev.bungeemsg.utils;

import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.utils.enums.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public static String cc(String string){
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String checkforColors(ProxiedPlayer p, String message){
        BungeePlayer bp = BungeePlayer.getBungeePlayer(p);

        // Check for offline player: lastMSGTo (BungeePlayer)
        if(bp != null) {
            for (ChatColor cc : ChatColor.values()) {
                if (cc.hasPermission(bp)) {
                    message = message.replace(cc.getToReplace(), cc.getReplacement());
                }
            }
        }

        return message;
    }
}
