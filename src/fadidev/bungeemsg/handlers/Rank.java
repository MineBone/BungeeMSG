package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.BungeeMSG;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.List;

public class Rank {

    private static BungeeMSG msg;
	private String name;
	private List<String> permissions;
    private List<String> rankPerms;

	public Rank(String name, List<String> permissions, List<String> rankPerms){
        msg = BungeeMSG.getInstance();
        this.name = name;
		this.permissions = permissions;
	    this.rankPerms = rankPerms;
    }
	
	public String getName() {
		return name;
	}

	public List<String> getPermissions() {
		return permissions;
	}

    public void update(){
        for(String rankP : rankPerms){
            Rank rank = getRank(rankP);
            if(rank != null){
                for(String perm : rank.getPermissions()){
                    if(!this.permissions.contains(perm)) {
                        this.permissions.add(perm);
                    }
                }
            }
        }
    }

    public static Rank getRank(String rankName){
        for(Rank rank : msg.getRanks()){
            if(rank.getName().equals(rankName)){
                return rank;
            }
        }
        return null;
    }
}