package fadidev.bungeemsg.utils;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.enums.Config;
import net.md_5.bungee.config.Configuration;

/**
 * Created by Fadi on 30-4-2016.
 */
public enum NewConfigPath {

    SUGGEST_SERVER_HOVER("v2.1.0", Config.CONFIG, "ServerSuggest.HoverMessage", "&7Click to connect to %server-name%");

    private BungeeMSG msg;
    private String version;
    private Config cfg;
    private String path;
    private Object value;

    NewConfigPath(String version, Config cfg, String path, Object value){
        this.msg = BungeeMSG.getInstance();
        this.version = version;
        this.cfg = cfg;
        this.path = path;
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public Config getCfg() {
        return cfg;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    public void check(){
        Configuration c = msg.getConfigManager().get(getCfg());
        if(c.get(getPath()) == null){
            c.set(getPath(), getValue());
            msg.getConfigManager().save(getCfg());

            Utils.sendConsoleMSG("§e" + getVersion() + " | New config path | " + getCfg().getFileName() + " | " + getPath());
        }
    }
}
