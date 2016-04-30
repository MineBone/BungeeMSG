package fadidev.bungeemsg.handlers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Title {

	private String title;
	private String subTitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;
	
	public Title(String title, String subTitle, int fadeIn, int stay, int fadeOut){
		this.title = title;
		this.subTitle = subTitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public int getFadeIn() {
		return fadeIn;
	}
	
	public void setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
	}
	
	public int getStay() {
		return stay;
	}
	
	public void setStay(int stay) {
		this.stay = stay;
	}
	
	public int getFadeOut() {
		return fadeOut;
	}
	
	public void setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
	}
	
	public void send(ProxiedPlayer player){
		net.md_5.bungee.api.Title t = ProxyServer.getInstance().createTitle();
		t.fadeIn(fadeIn * 20);
		t.stay(stay * 20);
		t.fadeOut(fadeOut * 20);
		t.title(new TextComponent(title));
		t.subTitle(new TextComponent(subTitle));
		t.send(player);
	}
	
	public Title copy(){
		return new Title(title, subTitle, fadeIn, stay, fadeOut);
	}
}
