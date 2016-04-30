package fadidev.old_plugin;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.ConfigManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.Config;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 23-4-2016.
 */
public class OldConfigChecker {

    private BungeeMSG msg;

    private Map<String, Configuration> configs;
    private Map<String, File> files;

    private boolean hasStarted;

    public OldConfigChecker(){
        msg = BungeeMSG.getInstance();
        configs = new HashMap<String, Configuration>();
        files = new HashMap<String, File>();
        hasStarted = false;
    }

    private void hasOldConfig(){
        if(!hasStarted) {
            Utils.sendConsoleEmpty();
            Utils.sendConsoleEmpty();
            Utils.sendConsoleMSG("§aBungeeMSG has found an old configuration file.");
            Utils.sendConsoleMSG("§aAll stored data from your config will be transfered to the new plugin.");
            Utils.sendConsoleMSG("§aThis may cause lag on slower servers.");
            Utils.sendConsoleEmpty();
            Utils.sendConsoleEmpty();
            hasStarted = true;
        }
    }

    public void copyPlayerData(){
        File fD = new File(msg.getDataFolder(), "playerdata.yml");
        if(fD.exists()){
            hasOldConfig();
            File f = new File(msg.getDataFolder() + "/configs", Config.PLAYERDATA.getFileName());

            if(!f.exists()){
                try{
                    Files.copy(fD.toPath(), f.toPath());
                    fD.renameTo(new File(msg.getDataFolder(), "old_playerdata.yml"));
                    Utils.sendConsoleMSG("playerdata.yml copied.");
                }catch(IOException ex){
                    Utils.warnConsole("Error while copying playerdata.yml");
                    ex.printStackTrace();
                }
            }
        }
    }

    public void setup(){
        File bannedWords = new File(msg.getDataFolder(), "banned-words.yml");
        File config = new File(msg.getDataFolder(), "config.yml");
        File domainWhitelist = new File(msg.getDataFolder(), "domain-whitelist.yml");
        File muted = new File(msg.getDataFolder(), "muted.yml");
        File[] files = { bannedWords, config, domainWhitelist, muted, };
        List<String> filesToRestore = new ArrayList<>();

        copyPlayerData();

        ConfigManager c = msg.getConfigManager();

        for(File f : files){
            if(f.exists()){
                hasOldConfig();
                this.files.put(f.getName(), f);
                filesToRestore.add(f.getName());

                try{
                    this.configs.put(f.getName(), YamlConfiguration.getProvider(YamlConfiguration.class).load(f));
                }catch(IOException e){
                    Utils.warnConsole("Error while loading " + f.getName());
                    e.printStackTrace();
                }
            }
        }

        if(filesToRestore.size() > 0) {
            for(OldConfigPath path : OldConfigPath.values()){
                if(filesToRestore.contains(path.getOldFileName())){
                    Config cfg = Config.getConfig(path.getNewFileName().substring(8));
                    Configuration c_new = c.get(cfg);
                    Configuration c_old = get(path.getOldFileName());
                    String old_path = path.getOldPath();
                    String new_path = path.getNewPath();

                    Utils.sendConsoleMSG("Restoring " + old_path + " (" + path.getOldFileName() + ") to " + new_path + " (" + path.getNewFileName() + ")");

                    try{
                        switch(path.getType()){
                            case ANNOUNCERS:
                                for(String announcer : c_old.getSection(old_path).getKeys()){
                                    c_new.set(new_path + "." + announcer + ".Servers", c_old.getString(old_path + "." + announcer + ".Servers"));
                                    c_new.set(new_path + "." + announcer + ".Delay", c_old.getInt(old_path + "." + announcer + ".Delay"));
                                    for(String i : c_old.getSection(old_path + "." + announcer + ".Messages").getKeys()){
                                        c_new.set(new_path + "." + announcer + ".Messages." + i + ".MessageList", c_old.getStringList(old_path + "." + announcer + ".Messages." + i));
                                    }
                                }
                                break;
                            case  BOOLEAN:
                                c_new.set(new_path, c_old.getBoolean(old_path));
                                break;
                            case CHANNELS:
                                for(String channel : c_old.getSection(old_path).getKeys()){
                                    c_new.set(new_path + "." + channel + ".Permission.Permission", c_old.getString(old_path + "." + channel + ".Permission"));
                                    c_new.set(new_path + "." + channel + ".Symbol.StartWith", c_old.getStringList(old_path + "." + channel + ".StartingSymbol"));
                                    c_new.set(new_path + "." + channel + ".Symbol.Toggle", c_old.getStringList(old_path + "." + channel + ".ToggleSymbol"));
                                    c_new.set(new_path + "." + channel + ".Messages.Message.Message", c_old.getString(old_path + "." + channel + ".Message"));
                                    c_new.set(new_path + "." + channel + ".Messages.Enable.Message", c_old.getString(old_path + "." + channel + ".ToggleEnabled"));
                                    c_new.set(new_path + "." + channel + ".Messages.Disable.Message", c_old.getString(old_path + "." + channel + ".ToggleDisabled"));
                                }
                                break;
                            case GROUPS:
                                for(String group : c_old.getSection(old_path).getKeys()){
                                    c_new.set(new_path + "." + group + ".Servers", c_old.getString(old_path + "." + group + ".Servers"));
                                    c_new.set(new_path + "." + group + ".Message.Message", c_old.getString(old_path + "." + group + ".Message"));
                                }
                                break;
                            case INTEGER:
                                c_new.set(new_path, c_old.getInt(old_path));
                                break;
                            case STRING:
                                c_new.set(new_path, c_old.getString(old_path));
                                break;
                            case STRINGLIST:
                                c_new.set(new_path, c_old.getStringList(old_path));
                                break;
                        }

                        c.save(cfg);
                    }catch(ClassCastException ex){
                        // Try catch, as BungeeMSG v1.7.6 has an error in its standard config file. Server as List, not a String.
                    }
                    Utils.sendConsoleMSG("Done.");
                }
            }

            for (String file : filesToRestore) {
                File f = this.files.get(file);
                f.renameTo(new File(msg.getDataFolder(), "old_" + file));
            }
        }
    }

    public Configuration get(String string){
        return configs.get(string);
    }
}
