package fadidev.bungeemsg.runnables;

import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.AutoAnnouncer;
import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.handlers.MessageLoader;
import fadidev.bungeemsg.handlers.MessageParser;
import fadidev.bungeemsg.utils.enums.Variable;

public class AutoAnnouncerRunnable implements Runnable {

    private BungeeMSG msg;

    public AutoAnnouncerRunnable() {
        this.msg = BungeeMSG.getInstance();
    }

    @Override
    public void run(){
        if(msg.getAutoAnnouncers().size() > 0){
            for(AutoAnnouncer aa : msg.getAutoAnnouncers()){
                aa.setTimer(aa.getTimer() +1);

                if(aa.getTimer() > aa.getDelay()){
                    int index = aa.getIndex();

                    List<MessageLoader> messageslist = aa.getMessages();
                    if(index >= messageslist.size()) index = 0;

                    MessageLoader msgL = messageslist.get(index);

                    for(ServerInfo info : aa.getServers()){
                        for(ProxiedPlayer player : info.getPlayers()){
                            BungeePlayer bp = msg.getBungeePlayers().get(player);
                            ServerInfo server = player.getServer().getInfo();

                            MessageParser mP = new MessageParser(bp, msgL);
                            mP.parseVariable(Variable.RECEIVER, player.getName());
                            mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(server));

                            mP.send(player, true);
                        }
                    }

                    aa.setIndex(index +1);
                    aa.setTimer(0);
                }
            }
        }
    }
}
