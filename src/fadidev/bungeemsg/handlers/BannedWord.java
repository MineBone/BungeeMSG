package fadidev.bungeemsg.handlers;

public class BannedWord {

	private String bannedWord;
	private String replacement;
	
	public BannedWord(String bannedWord, String replacement){
		this.bannedWord = bannedWord;
		this.replacement = replacement;
	}
	
	public String getBannedWord() {
		return bannedWord;
	}
	
	public String getReplacement() {
		return replacement;
	}
}
