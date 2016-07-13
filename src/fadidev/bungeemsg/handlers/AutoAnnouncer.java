package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.enums.Variable;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class AutoAnnouncer {

    private static BungeeMSG msg;
    private String name;
    private List<ServerInfo> servers;
    private int delay;
    private List<MessageLoader> messages;
    private int timer;
    private int index;

    public AutoAnnouncer(String name, List<ServerInfo> servers, int delay, List<MessageLoader> messages){
        msg = BungeeMSG.getInstance();
        this.name = name;
        this.servers = servers;
        this.delay = delay;
        this.messages = messages;
        this.timer = 0;
        this.index = 0;
    }

    public String getName() {
        return name;
    }

    public List<ServerInfo> getServers() {
        return servers;
    }

    public int getDelay() {
        return delay;
    }

    public List<MessageLoader> getMessages() {
        return messages;
    }

    public int getTimer() {
        return timer;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public void announce(MessageLoader msgL){
        for(ServerInfo info : getServers()){
            for(ProxiedPlayer player : info.getPlayers()){
                BungeePlayer bp = BungeePlayer.getBungeePlayer(player);
                ServerInfo server = player.getServer().getInfo();

                MessageParser mP = new MessageParser(bp, msgL);
                mP.parseVariable(Variable.RECEIVER, player.getName());
                mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(server));

                mP.send(player, true);
            }
        }
    }

    public static List<AutoAnnouncer> getAutoAnnouncers(ServerInfo info){
        List<AutoAnnouncer> autoAnnouncers = new ArrayList<>();

        for(AutoAnnouncer aa : msg.getAutoAnnouncers()){
            if(aa.getServers().contains(info)){
                autoAnnouncers.add(aa);
            }
        }

        return autoAnnouncers;
    }
}
