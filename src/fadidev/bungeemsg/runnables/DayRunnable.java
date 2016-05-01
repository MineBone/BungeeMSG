package fadidev.bungeemsg.runnables;

import java.util.Calendar;

import fadidev.bungeemsg.BungeeMSG;

public class DayRunnable implements Runnable {

    private BungeeMSG msg;
    int currentday = -1;

    public DayRunnable() {
        this.msg = BungeeMSG.getInstance();
    }

    @Override
    public void run(){
        Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_YEAR);
        
        if(currentday == -1){
            currentday = day;
        }
        
        if(day != currentday){
            currentday = day;
            msg.getLogManager().loadLogs();
        }
    }
}
