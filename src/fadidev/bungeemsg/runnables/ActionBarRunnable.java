package fadidev.bungeemsg.runnables;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.*;
import fadidev.bungeemsg.utils.enums.Variable;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class ActionBarRunnable implements Runnable {

	private BungeeMSG msg;

	public ActionBarRunnable() {
		this.msg = BungeeMSG.getInstance();
	}
	
	@Override
	public void run(){
		List<ActionBar> actionBars = new ArrayList<>();
		for(ActionBar ab : msg.getCurrentActionbars().values()){
			actionBars.add(ab);
		}
		for(ActionBar ab : actionBars){
            ab.check();
		}
	}
}
