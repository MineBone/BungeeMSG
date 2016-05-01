package fadidev.bungeemsg.events;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.ActionBar;
import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.handlers.SpigotBridge.PlayerVariable;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerDisconnectEvent implements Listener{

    private BungeeMSG msg;

    @EventHandler
    public void onQuit(ServerDisconnectEvent e) {
        this.msg = BungeeMSG.getInstance();
        ProxiedPlayer p = e.getPlayer();

        /* Check if really disconnected (otherwise they switched servers) */
        if(p.getServer() != null && p.getServer().getInfo() == e.getTarget()){
            for(PlayerVariable pVariable : msg.getPlayerVariables().values()){
                if(pVariable.getPlayerValues().containsKey(p.getName())){
                    pVariable.getPlayerValues().remove(p.getName());
                }
            }

            BungeePlayer bp = BungeePlayer.getBungeePlayer(p);
            if(bp.hasActionBar()){
                msg.getCurrentActionbars().remove(p);
            }
        }
    }
}
