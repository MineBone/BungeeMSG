package fadidev.bungeemsg.utils.enums;

public enum Config {

	ADVERTISE("advertise-cfg.yml"),
	ANNOUNCER("announcer-cfg.yml"),
	BANNEDWORDS("bannedwords-cfg.yml"),
	CONFIG("cfg.yml"),
	CHANNEL("channel-cfg.yml"),
	COMMAND("command-cfg.yml"),
	GROUP("group-cfg.yml"),
	LOG("log-cfg.yml"),
	MUTED("muted.yml"),
	RANKS("ranks.yml"),
	PLAYERDATA("playerdata.yml"),
	SPAM("spam-cfg.yml"),
	REPORTS("reports.yml");
	
	private static Config[] correctOrder = { LOG, CONFIG, COMMAND, GROUP, CHANNEL, ANNOUNCER, ADVERTISE, SPAM, BANNEDWORDS, MUTED, RANKS, PLAYERDATA, REPORTS };
	private String fileName;
	
	Config(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public static Config[] getCorrectOrder() {
		return correctOrder;
	}

	public static Config getConfig(String fileName){
		for(Config config : correctOrder){
			if(config.getFileName().equals(fileName)){
				return config;
			}
		}
		return null;
	}
}
