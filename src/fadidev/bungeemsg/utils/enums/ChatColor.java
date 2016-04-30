package fadidev.bungeemsg.utils.enums;

import fadidev.bungeemsg.handlers.BungeePlayer;

public enum ChatColor {

	BLACK("&0", "§0", "BungeeMSG.colors.black"),
	DARK_BLUE("&1", "§1", "BungeeMSG.colors.dark_blue"),
	GREEN("&2", "§2", "BungeeMSG.colors.green"),
	CYAN("&3", "§3", "BungeeMSG.colors.cyan"),
	DARK_RED("&4", "§4", "BungeeMSG.colors.dark_red"),
	PURPLE("&5", "§5", "BungeeMSG.colors.purple"),
	ORANGE("&6", "§6", "BungeeMSG.colors.orange"),
	LIGHT_GRAY("&7", "§7", "BungeeMSG.colors.light_gray"),
	GRAY("&8", "§8", "BungeeMSG.colors.gray"),
	BLUE("&9", "§9", "BungeeMSG.colors.blue"),
	LIME("&a", "§a", "BungeeMSG.colors.lime"),
	AQUA("&b", "§b", "BungeeMSG.colors.aqua"),
	RED("&c", "§c", "BungeeMSG.colors.red"),
	PINK("&d", "§d", "BungeeMSG.colors.pink"),
	YELLOW("&e", "§e", "BungeeMSG.colors.yellow"),
	WHITE("&f", "§f", "BungeeMSG.colors.white"),
	RESET("&r", "§r", "BungeeMSG.colors.reset"),
	ITALIC("&o", "§o", "BungeeMSG.colors.italic"),
	BOLD("&l", "§l", "BungeeMSG.colors.bold"),
	MAGIC("&k", "§k", "BungeeMSG.colors.magic"),
	STRIKETHROUGH("&m", "§m", "BungeeMSG.colors.strikethrough"),
	UNDERLINED("&n", "§n", "BungeeMSG.colors.underlined");

	private String toReplace;
	private String replacement;
	private String permission;

	ChatColor(String toReplace, String replacement, String permission){
		this.toReplace = toReplace;
		this.replacement = replacement;
		this.permission = permission;
	}

	public String getToReplace() {
		return toReplace;
	}

	public String getReplacement() {
		return replacement;
	}

	public String getPermission() {
		return permission;
	}

	public boolean hasPermission(BungeePlayer bp){
		return bp.hasPermission(getPermission(), "BungeeMSG.colors.*");
	}
}
