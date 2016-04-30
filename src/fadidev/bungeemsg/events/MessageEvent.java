package fadidev.bungeemsg.events;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.SpigotBridge.PlayerVariable;
import fadidev.bungeemsg.handlers.SpigotBridge.StandardVariable;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.VariableType;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class MessageEvent implements Listener {

    private BungeeMSG msg;
    private List<String> serverVersions = new ArrayList<>();

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e){
        if(!e.getTag().equals("SpigotBridge") || !(e.getSender() instanceof Server)){
            return;
        }
        msg = BungeeMSG.getInstance();
        ByteArrayInputStream stream = new ByteArrayInputStream(e.getData());
        DataInputStream in = new DataInputStream(stream);

        try{
            String version = in.readUTF();
            String server = in.readUTF();
            if(!serverVersions.contains(server)){
                Utils.sendConsoleMSG("BungeeMSG is recording a SpigotBrigde...");
                Utils.sendConsoleMSG("SpigotBrigde on server " + server + " has connected to BungeeMSG.");
                Utils.sendConsoleMSG(server + " is running SpigotBrigde " + version + ".");

                serverVersions.add(server);
            }

            boolean done = false;
            while(!done){
                String type = in.readUTF();

                if(!type.equals("done")){
                    if(!type.equals("CLEAR")){
                        VariableType vType = VariableType.valueOf(type);
                        String variable = in.readUTF();

                        switch(vType){
                            case STANDERD:
                                String replacement = in.readUTF();

                                new StandardVariable(server, variable, replacement);
                                break;
                            case PLAYERDATA:
                                List<String> playerValues = new ArrayList<>();

                                boolean next = false;
                                while (!next) {
                                    String value = in.readUTF();

                                    if(!value.equals("next")){
                                        playerValues.add(value);
                                    }
                                    else{
                                        next = true;
                                    }
                                }

                                new PlayerVariable(server, variable, playerValues);
                                break;
                        }
                    }
                    else{
                        String player = in.readUTF();

                        if(player.equals("done")){
                            Utils.sendConsoleMSG("Clearing SpigotBridge Data for server " + server + "...");
                            List<StandardVariable> standardToRemove = new ArrayList<>();
                            for(StandardVariable standardVariable : msg.getStandardVariables().values()){
                                if(standardVariable.getServer().equals(server)){
                                    standardToRemove.add(standardVariable);
                                }
                            }

                            for(StandardVariable standardVariable : standardToRemove){
                                msg.getStandardVariables().remove(standardVariable.getVariable());
                            }

                            List<PlayerVariable> playersToRemove = new ArrayList<>();
                            for(PlayerVariable playerVariable : msg.getPlayerVariables().values()){
                                if(playerVariable.getServer().equals(server)){
                                    playersToRemove.add(playerVariable);
                                }
                            }

                            for(PlayerVariable playerVariable : playersToRemove){
                                msg.getPlayerVariables().remove(playerVariable.getVariable());
                            }
                        }
                        else{
                            Utils.sendConsoleMSG("Clearing SpigotBridge Data for player " + player + "...");
                            for(PlayerVariable playerVariable : msg.getPlayerVariables().values()){
                                if(playerVariable.getPlayerValues().keySet().contains(player)){
                                    playerVariable.getPlayerValues().remove(player);
                                }
                            }
                        }

                        Utils.successConsole("Done!");
                        done = true;
                    }
                }
                else{
                    done = true;
                }
            }
        }catch(IOException ex){
            Utils.warnConsole("Error while receiving data from SpigotBridge:");
            ex.printStackTrace();
        }
    }
}
