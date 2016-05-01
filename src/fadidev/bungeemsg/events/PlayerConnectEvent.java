package fadidev.bungeemsg.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.BungeePlayer;

public class PlayerConnectEvent implements Listener{
    
    private BungeeMSG msg;
    
    @EventHandler
    public void onJoin(ServerConnectEvent e) {
        this.msg = BungeeMSG.getInstance();
        ProxiedPlayer p = e.getPlayer();

        if(!msg.getBungeePlayers().containsKey(p)){
            BungeePlayer bp = new fadidev.bungeemsg.handlers.BungeePlayer(p);
            msg.getBungeePlayers().put(p, bp);
        }
    }
}
