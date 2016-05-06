package fadidev.bungeemsg.handlers.spigotbridge;

import fadidev.bungeemsg.BungeeMSG;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class StandardVariable {

    private BungeeMSG msg;
    private String server;
    private String variable;
    private String replacement;

    public StandardVariable(String server, String variable, String replacement){
        msg = BungeeMSG.getInstance();

        this.server = server;
        this.variable = variable;
        this.replacement = replacement.replace("&", "§");
        msg.getStandardVariables().put(variable, this);
    }

    public String getServer() {
        return server;
    }

    public String getVariable() {
        return variable;
    }

    public String getReplacement() {
        return replacement;
    }
}
