package fadidev.bungeemsg.redis.events;

import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Fadi on 30-4-2016.
 */
public class SubMessageEvent implements Listener {

    @EventHandler
    public void onSubMessage(PubSubMessageEvent e){
        String channel = e.getChannel();
    }
}
