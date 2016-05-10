package fadidev.bungeemsg.utils;

import fadidev.bungeemsg.NameFetcher;
import fadidev.bungeemsg.UUIDFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class PlayerUtils {

    public static ProxiedPlayer getPlayer(String name){
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
            if(p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }

    public static String getNameOrUUID(UUID uuid){
        String name = null;
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);

        if(p != null) name = p.getName();
        if(name == null) name = getName(uuid);
        if(name == null) name = uuid.toString();

        return name;
    }

    public static UUID getUUID(String playername){
        UUIDFetcher uuidf = new UUIDFetcher(Arrays.asList(playername));
        try{
            return uuidf.call().get(playername);
        }catch(Exception ex){
            return null;
        }
    }
    public static String getName(UUID uuid){
        NameFetcher namef = new NameFetcher(uuid);
        try{
            String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
            String[] nameparts = name.split(" ");
            return nameparts[0];
        }catch(Exception ex){
            return null;
        }
    }
    public static String getNameDate(UUID uuid){
        NameFetcher namef = new NameFetcher(uuid);
        try{
            String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
            String[] nameparts = name.split(" ", 1);
            return nameparts[1];
        }catch(Exception ex){
            return null;
        }
    }
    public static HashMap<String, String> getNames(UUID uuid){
        NameFetcher namef = new NameFetcher(uuid);
        try{
            HashMap<String, String> names = new HashMap<String, String>();
            for(String name : namef.call().get(uuid)){
                String[] nameparts = name.split(" ", 1);
                if(nameparts.length > 1){
                    names.put(nameparts[0], nameparts[1]);
                }
                else{
                    names.put(nameparts[0], null);
                }
            }

            return names;
        }catch(Exception ex){
            return null;
        }
    }
}
