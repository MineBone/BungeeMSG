package fadidev.bungeemsg.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import fadidev.old_plugin.OldConfigChecker;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.Config;
 
public class ConfigManager {
 
    private BungeeMSG msg;

    private Map<Config, Configuration> configs;
    private Map<Config, File> files;

    public ConfigManager(){
        msg = BungeeMSG.getInstance();
        configs = new HashMap<Config, Configuration>();
        files = new HashMap<Config, File>();
    }

    public void setup(Config... configs){
        if(!msg.getDataFolder().exists()){
            msg.getDataFolder().mkdir();
        }

        File fD = new File(msg.getDataFolder() + "/configs");
        if(!fD.exists()){
            fD.mkdir();
        }

        for(Config config : configs){
            File f = new File(msg.getDataFolder() + "/configs", config.getFileName());
            files.put(config, f);

            if(!f.exists()){
                copyFile(config.getFileName(), f.toPath());
            }

            try{
                this.configs.put(config, YamlConfiguration.getProvider(YamlConfiguration.class).load(f));
            }catch(IOException e){
                Utils.warnConsole("Error while loading " + config.getFileName());
                e.printStackTrace();
            }

            if(config == Config.RANKS){
                new OldConfigChecker().setup();
            }
        }
    }

    public Configuration get(Config config){
        return configs.get(config);
    }

    public void save(Config config){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(get(config), new File(msg.getDataFolder() + "/configs", config.getFileName()));
        }
        catch(IOException ex){
            Utils.warnConsole("Error while saving " + config.getFileName());
            ex.printStackTrace();
        }
    }

    public void reload(Config config){
        try{
            this.configs.put(config, YamlConfiguration.getProvider(YamlConfiguration.class).load(this.files.get(config)));
        }catch(IOException ex){
            Utils.warnConsole("Error while reloading " + config.getFileName());
            ex.printStackTrace();
        }
    }

    private void copyFile(String filename, Path path){
        try{
            Files.copy(msg.getResourceAsStream(filename), path);
        }catch(IOException ex){
            Utils.warnConsole("Error while creating " + filename);
            ex.printStackTrace();
        }
    }
}
