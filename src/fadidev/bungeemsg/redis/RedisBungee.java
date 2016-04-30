package fadidev.bungeemsg.redis;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.redis.events.SubMessageEvent;

/**
 * Created by Fadi on 30-4-2016.
 */
public class RedisBungee {

    private BungeeMSG msg;

    public RedisBungee(){
        msg = BungeeMSG.getInstance();

        msg.getProxy().getPluginManager().registerListener(msg, new SubMessageEvent());
    }
}
