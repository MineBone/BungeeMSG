package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fadidev.bungeemsg.handlers.SpigotBridge.PlayerVariable;
import fadidev.bungeemsg.handlers.SpigotBridge.StandardVariable;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.AdvertiseManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.Message;
import fadidev.bungeemsg.utils.enums.Variable;
import fadidev.bungeemsg.utils.enums.WhitelistType;

public class MessageParser {

    private BungeeMSG msg;
    private BungeePlayer bp;
    private boolean cancelled;
    private String message;
    private List<String> messageList;
    private Title title;
    private ActionBar actionBar;

    public MessageParser(BungeePlayer bp, MessageLoader msgL){
        this.msg = BungeeMSG.getInstance();
        this.bp = bp;
        this.cancelled = false;

        this.message = msgL.getMessage();

        if(msgL.getMessageList() != null){
            List<String> messageList = new ArrayList<String>();
            messageList.addAll(msgL.getMessageList());
            this.messageList = messageList;
        }

        if(msgL.getTitle() != null) this.title = msgL.getTitle().copy();
        if(msgL.getActionBar() != null) this.actionBar = msgL.getActionBar().copy();
    }

    public MessageParser(BungeePlayer bp, String message){
        this.msg = BungeeMSG.getInstance();
        this.bp = bp;
        this.cancelled = false;

        this.message = message;
    }

    public void parseVariable(Variable variable, String replacement){
        if(variable == Variable.REASON || variable == Variable.MSG){
            replacement = checkAll(replacement);
        }

        if(!cancelled){
            if(message != null) message = message.replace(variable.getVariable(), replacement);

            if(messageList != null){
                int index = 0;
                for(String s : messageList){
                    messageList.set(index, s.replace(variable.getVariable(), replacement));
                    index++;
                }
            }

            if(title != null){
                title.setTitle(title.getTitle().replace(variable.getVariable(), replacement));
                title.setSubTitle(title.getSubTitle().replace(variable.getVariable(), replacement));
            }

            if(actionBar != null) actionBar.setMessage(actionBar.getMessage().replace(variable.getVariable(), replacement));
        }
    }

    public void parseVariable(String variable, String replacement){
        if(!cancelled){
            if(message != null) message = message.replace(variable, replacement);

            if(messageList != null){
                int index = 0;
                for(String s : messageList){
                    messageList.set(index, s.replace(variable, replacement));
                    index++;
                }
            }

            if(title != null){
                title.setTitle(title.getTitle().replace(variable, replacement));
                title.setSubTitle(title.getSubTitle().replace(variable, replacement));
            }

            if(actionBar != null) actionBar.setMessage(actionBar.getMessage().replace(variable, replacement));
        }
    }

    public void parseCustomVariables(ProxiedPlayer p1, ProxiedPlayer p2){
        parseStandardVariables();
        if(p1 != null){ parsePlayerVariables(p1, 1); }
        if(p2 != null){ parsePlayerVariables(p2, 2); }
    }

    public void parseStandardVariables(){
        for(StandardVariable standardVariable : msg.getStandardVariables().values()){
            parseVariable(standardVariable.getVariable(), standardVariable.getReplacement().replace("&", "§"));
        }
    }

    public void parsePlayerVariables(ProxiedPlayer p, int player){
        for(PlayerVariable playerVariable : msg.getPlayerVariables().values()){
            if(playerVariable.getPlayerValues().containsKey(p.getName())){
                parseVariable(playerVariable.getVariable(player), playerVariable.getPlayerValues().get(p.getName()).replace("&", "§"));
            }
            else{
                parseVariable(playerVariable.getVariable(player), "");
                Utils.warnConsole("Cannot parse " + playerVariable.getVariable() + " for player " + p.getName() + ".");
            }
        }
    }

    public void send(ProxiedPlayer p, boolean updateCustom){
        if(!cancelled){
           if(updateCustom) parseCustomVariables(p, null);

            if(message != null){
                this.message = Utils.checkforColors(p, message);
                bp.sendMessage(message, null);
            }

            if(messageList != null){
                int index = 0;
                for(String s : messageList){
                    messageList.set(index, Utils.checkforColors(p, s));
                    index++;
                    bp.sendMessage(s, null);
                }
            }

            if(title != null){
                title.setTitle(Utils.checkforColors(p, title.getTitle()));
                title.setSubTitle(Utils.checkforColors(p, title.getSubTitle()));
                title.send(p);
            }

            if(actionBar != null){
                actionBar.setPlayer(p);
                actionBar.setMessage(Utils.checkforColors(p, actionBar.getMessage()));
                actionBar.send();
                actionBar.start();
            }
        }
    }

    public void send(ProxiedPlayer p, ProxiedPlayer sender, ProxiedPlayer colorCheck, boolean updateCustom){
        if(!cancelled){
            if(updateCustom) parseCustomVariables(p, sender);

            BungeePlayer bp = msg.getBungeePlayers().get(p);
            if(message != null){
                this.message = Utils.checkforColors(colorCheck, message);
                bp.sendMessage(message, sender);
            }

            if(messageList != null){
                int index = 0;
                for(String s : messageList){
                    messageList.set(index, Utils.checkforColors(colorCheck, s));
                    index++;
                    bp.sendMessage(s, sender);
                }
            }

            if(title != null){
                title.setTitle(Utils.checkforColors(colorCheck, title.getTitle()));
                title.setSubTitle(Utils.checkforColors(colorCheck, title.getSubTitle()));
                title.send(p);
            }

            if(actionBar != null){
                actionBar.setPlayer(p);
                actionBar.setMessage(Utils.checkforColors(colorCheck, actionBar.getMessage()));
                actionBar.send();
                actionBar.start();
            }
        }
    }

    public void checkAdvertising(String replacement){
        ProxiedPlayer p = bp.getPlayer();
        AdvertiseManager aM = msg.getAdvertiseManager();

        if(aM.isUsed()){
            if(aM.canCancelIPs()){
                if(!bp.hasPermission("BungeeMSG.bypass.ips", "BungeeMSG.bypass.*")){
                    String tocheck = replacement;
                    Pattern pattern = Utils.IP_PATTERN;
                    Matcher m = pattern.matcher(tocheck);

                    if (m.find()) {
                        String s = m.group().replace(".", "").replace(" ", "").replace("(dot)", "").replace("dot", "");
                        boolean cancel = true;

                        for (IPWhitelist ipw : msg.getIPWhitelist()) {
                            if (ipw.getType() == WhitelistType.IP && s.endsWith(ipw.getWhitelisted())) {
                                cancel = false;
                            }
                        }

                        if (cancel) {
                            if (aM.canKick()) {
                                MessageParser mP = Message.ADVERTISE_KICK.getParser(bp);
                                mP.parseVariable(Variable.TYPE, Message.IP_VARIABLE.getParser(bp).getMessage());
                                p.disconnect(mP.getMessage());

                                msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send an IP. (Message: " + replacement + ")");
                            } else {
                                MessageParser mP = Message.ADVERTISE_MESSAGE.getParser(bp);
                                mP.parseVariable(Variable.TYPE, Message.IP_VARIABLE.getParser(bp).getMessage());
                                mP.send(p, true);

                                msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send an IP. (Message: " + replacement + ")");
                            }
                            this.cancelled = true;
                            return;
                        }
                    }
                }
            }

            if(aM.canCancelDomains()){
                if(!bp.hasPermission("BungeeMSG.bypass.domainnames", "BungeeMSG.bypass.*")){
                    String tocheck = replacement.toLowerCase();
                    Pattern pattern = Utils.DOMAIN_PATTERN;
                    Matcher m = pattern.matcher(tocheck);

                    if(m.find()) {
                        String s = m.group().replace(".", "").replace(" ", "").replace("(dot)", "").replace("dot", "");
                        boolean cancel = true;

                        for (IPWhitelist ipw : msg.getIPWhitelist()) {
                            if (ipw.getType() == WhitelistType.DOMAIN && s.endsWith(ipw.getWhitelisted())) {
                                cancel = false;
                            }
                        }

                        if(cancel) {
                            if (aM.canKick()) {
                                MessageParser mP = Message.ADVERTISE_KICK.getParser(bp);
                                mP.parseVariable(Variable.TYPE, Message.DOMAIN_VARIABLE.getParser(bp).getMessage());
                                p.disconnect(mP.getMessage());

                                msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send a Website. (Message: " + replacement + ")");
                            } else {
                                MessageParser mP = Message.ADVERTISE_MESSAGE.getParser(bp);
                                mP.parseVariable(Variable.TYPE, Message.DOMAIN_VARIABLE.getParser(bp).getMessage());
                                mP.send(p, true);

                                msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send a Website. (Message: " + replacement + ")");
                            }
                            this.cancelled = true;
                        }
                    }
                }
            }
        }
    }

    private String replaceBannedWords(String message){
        ProxiedPlayer p = bp.getPlayer();

        if(msg.getBannedWords().size() > 0 && !bp.hasPermission("BungeeMSG.bypass.bannedwords", "BungeeMSG.bypass.*")){
            for(String word : message.split(" ")){
                String messagenow = message;

                StringBuilder replacement = new StringBuilder();
                for(int i = 1; i <= word.length(); i++) {
                    replacement.append("*");
                }

                for(BannedWord bw : msg.getBannedWords()){
                    String bannedword = bw.getBannedWord();

                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < bannedword.length(); i++){
                        if(i != 0){
                            sb.append("+");
                        }
                        sb.append(bannedword.charAt(i));
                    }

                    String sbstring = sb.toString();
                    sbstring = sbstring.replace("*", "");

                    Matcher m = Pattern.compile("\\b(?i)" + sbstring.replaceAll("\\$", "[s\\$]").replaceAll("(?i)s", "[s\\$]").replace("a", "[a*]").replace("e", "[e*]").replace("o", "[o*]").replace("u", "[u*]").replace("c+k", "c*k") + "+" + "\\b").matcher(message);

                    if(bw.getReplacement() != null){
                        message = m.replaceAll(bw.getReplacement());
                    }
                    else{
                        message = m.replaceAll(replacement.toString());
                    }
                }

                if(!messagenow.equals(message)){
                    msg.getLogManager().info(LogReadType.BANNED_WORDS, p.getServer().getInfo(), "[BANNED WORDS] " + bp.getPlayer().getName() + " tried to use a banned word: '" + word + "'.");
                }
            }
        }

        return message;
    }

    public String replaceCaps(String message){
        ProxiedPlayer p = bp.getPlayer();

        if(msg.getSpamManager().isUsed() && msg.getSpamManager().canCancelCaps()){
            if(!bp.hasPermission("BungeeMSG.bypass.caps", "BungeeMSG.bypass.*")){
                int caps = 0;
                for(int i = 0; i < message.length(); i++){
                    if(Character.isUpperCase(message.charAt(i))){
                        caps++;
                    }
                }

                if(caps > msg.getSpamManager().getMaxCaps()){
                    message = message.toLowerCase();

                    msg.getLogManager().info(LogReadType.SPAM, p.getServer().getInfo(), "[SPAM] Replaced all uppercases to lowercases in '" + message + "' for " + p.getName() + ". (Caps)");
                }
            }
        }

        return message;
    }

    public String checkAll(String replacement){
        checkAdvertising(replacement);

        if(!cancelled){
            replacement = replaceBannedWords(replacement);
            replacement = replaceCaps(replacement);
        }

        return replacement;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public Title getTitle() {
        return title;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
