package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ComponentMessage {
	
	private List<TextComponent> tcs;
	
	public ComponentMessage(){
		tcs = new ArrayList<TextComponent>();
	}
	
	public void addPart(String part, ClickEvent.Action clickaction, String clickevent, HoverEvent.Action hoveraction, String hoverevent){
		TextComponent tc = new TextComponent(part);
		if(clickaction != null){
			tc.setClickEvent(new ClickEvent(clickaction, clickevent));
		}
		if(hoveraction != null){
			tc.setHoverEvent(new HoverEvent(hoveraction, new ComponentBuilder(hoverevent).create()));
		}
		
		tcs.add(tc);
	}
	
	public void send(ProxiedPlayer... players){
		TextComponent[] tcs = new TextComponent[this.tcs.size()];
		int index = 0;
		for(TextComponent tc : this.tcs){
			tcs[index] = tc;
			index++;
		}
		
		TextComponent tc = new TextComponent(tcs);
		for(ProxiedPlayer player : players){
			player.sendMessage(tc);
		}
	}
}
