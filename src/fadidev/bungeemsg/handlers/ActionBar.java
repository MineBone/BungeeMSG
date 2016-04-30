package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.BungeeMSG;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ActionBar {

    private BungeeMSG msg;
	private String message;
    private ProxiedPlayer player;
    private BungeePlayer bp;
	private int stay;
    private int current;
	
	public ActionBar(ProxiedPlayer player, String message, int stay){
        this.msg = BungeeMSG.getInstance();
		this.player = player;
        if(player != null) this.bp = BungeePlayer.getBungeePlayer(player);
        this.message = message;
		this.stay = stay;
        this.current = 0;
	}
	
	public String getMessage() {
		return message;
	}

    public void setPlayer(ProxiedPlayer player) {
        this.player = player;
        this.bp = BungeePlayer.getBungeePlayer(player);
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void setMessage(String message) {
		this.message = message;
	}
	
	public void send(){
		TextComponent tc1 = new TextComponent(message);
		player.sendMessage(ChatMessageType.ACTION_BAR, tc1);
	}
	
	public ActionBar copy(){
		return new ActionBar(player, message, stay);
	}

    public void check(){
        if(current == stay){
            stop();
            return;
        }

        send();
        current++;
    }

    public void start(){
        msg.getCurrentActionbars().put(player, this);
        bp.setActionBar(true);
    }

    public void stop(){
        msg.getCurrentActionbars().remove(player);
        bp.setActionBar(false);
    }
}
