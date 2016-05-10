package fadidev.bungeemsg.runnables;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.AutoAnnouncer;
import fadidev.bungeemsg.handlers.MessageLoader;

import java.util.List;

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

                    aa.announce(msgL);

                    aa.setIndex(index +1);
                    aa.setTimer(0);
                }
            }
        }
    }
}
