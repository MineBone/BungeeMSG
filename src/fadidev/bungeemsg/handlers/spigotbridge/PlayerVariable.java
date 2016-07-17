package fadidev.bungeemsg.handlers.spigotbridge;

import fadidev.bungeemsg.BungeeMSG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class PlayerVariable {

    private BungeeMSG msg;
    private String server;
    private String variable;
    private Map<String, String> playerValues;

    public PlayerVariable(String server, String variable, List<String> playerValues){
        msg = BungeeMSG.getInstance();

        this.server = server;

        if(!msg.getPlayerVariables().containsKey(variable)){
            this.variable = variable;
            this.playerValues = new HashMap<>();
            for(String playerValue : playerValues){
                String[] valueParts = playerValue.split("\\|");
                if(valueParts.length == 1){
                    this.playerValues.put(valueParts[0], "");
                }
                else{
                    this.playerValues.put(valueParts[0], valueParts[1]);
                }
            }

            msg.getPlayerVariables().put(variable, this);
        }
        else{
            PlayerVariable pVariable = msg.getPlayerVariables().get(variable);

            for(String playerValue : playerValues){
                String[] valueParts = playerValue.split("\\|");
                if(valueParts.length == 1){
                    pVariable.getPlayerValues().put(valueParts[0], "");
                }
                else{
                    pVariable.getPlayerValues().put(valueParts[0], valueParts[1]);
                }
            }
        }
    }

    public String getServer() {
        return server;
    }

    public String getVariable() {
        return variable;
    }

    public String getVariable(int player){
        return getVariable().substring(0, getVariable().length() - 1) + "-p" + player + "%";
    }

    public Map<String, String> getPlayerValues() {
        return playerValues;
    }
}
