package fadidev.bungeemsg.managers;

public class AdvertiseManager {

	private boolean use;
	private boolean cancelIPs;
	private boolean cancelDomains;
	private boolean kick;
	
	public AdvertiseManager(boolean use, boolean cancelIPs, boolean cancelDomains, boolean kick){
		this.use = use;
		this.cancelIPs = cancelIPs;
		this.cancelDomains = cancelDomains;
		this.kick = kick;
	}
	
	public boolean isUsed() {
		return use;
	}
	
	public boolean canCancelIPs() {
		return cancelIPs;
	}
	
	public boolean canCancelDomains() {
		return cancelDomains;
	}
	
	public boolean canKick() {
		return kick;
	}
}
