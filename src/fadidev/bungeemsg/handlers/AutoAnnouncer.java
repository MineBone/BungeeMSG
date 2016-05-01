package fadidev.bungeemsg.handlers;

import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;

public class AutoAnnouncer {

    private String name;
    private List<ServerInfo> servers;
    private int delay;
    private List<MessageLoader> messages;
    private int timer;
    private int index;

    public AutoAnnouncer(String name, List<ServerInfo> servers, int delay, List<MessageLoader> messages){
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
}
