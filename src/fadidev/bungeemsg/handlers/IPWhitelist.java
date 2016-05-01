package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.utils.enums.WhitelistType;

public class IPWhitelist {

    private WhitelistType type;
    private String whitelisted;

    public IPWhitelist(WhitelistType type, String whitelisted){
        this.type = type;
        this.whitelisted = whitelisted.replace(".", "");
    }

    public WhitelistType getType() {
        return type;
    }

    public String getWhitelisted() {
        return whitelisted;
    }
}
