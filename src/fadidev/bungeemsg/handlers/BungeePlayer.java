package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.SpamManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BungeePlayer {

    private static BungeeMSG msg;
    private ProxiedPlayer player;

    private BungeePlayer lastMSGTo;
    private String lastMSG;

    private List<UUID> ignored;
    private Map<Cooldown, Long> cooldowns;
    private int tooFastAmount;
    private Channel toggledChannel;
    private boolean spy;
    private boolean msgEnabled;

    private Rank rank;

    private boolean hasActionBar;

    public BungeePlayer(ProxiedPlayer player){
        this.msg = BungeeMSG.getInstance();
        this.player = player;
        this.lastMSGTo = null;
        this.lastMSG = null;

        this.ignored = new ArrayList<UUID>();
        this.cooldowns = new HashMap<Cooldown, Long>();
        this.tooFastAmount = 0;
        this.toggledChannel = null;
        this.hasActionBar = false;

        String uuid = player.getUniqueId().toString();
        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
        if(c.get("players." + uuid + ".Ignored") != null){
            String[] ignoredUUIDs = c.getString("players." + uuid + ".Ignored").split("\\|");

            for(String ignoredUUID : ignoredUUIDs){
                if(ignoredUUID.length() > 30) {
                    try {
                        this.ignored.add(UUID.fromString(ignoredUUID));
                    } catch (IllegalArgumentException ex) {
                        Utils.warnConsole("Error while parsing '" + ignoredUUID + "' to a UUID.");
                    }
                }
            }
        }

        this.msgEnabled = true;
        if(c.get("players." + uuid + ".Toggle") != null){
            this.msgEnabled = c.getBoolean("players." + uuid + ".Toggle");
        }

        if(c.get("players." + uuid + ".Rank") != null){
            rank = Rank.getRank(c.getString("players." + uuid + ".Rank"));
        }
        else{
            rank = Rank.getRank("Default");
            c.set("players." + uuid + ".Rank", "Default");
            msg.getConfigManager().save(Config.PLAYERDATA);
        }

        this.spy = hasPermission("BungeeMSG.spy.on");
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public BungeePlayer getLastMSGTo() {
        return lastMSGTo;
    }
    public void setLastMSGTo(BungeePlayer lastMSGTo) {
        this.lastMSGTo = lastMSGTo;
    }

    public String getLastMSG() {
        return lastMSG;
    }
    public void setLastMSG(String lastMSG) {
        this.lastMSG = lastMSG;
    }

    public List<UUID> getIgnored() {
        return ignored;
    }
    public void setIgnored(List<UUID> ignored) {
        this.ignored = ignored;

        String ignoredString = "";
        for(UUID uuid : ignored){
            if(!ignoredString.equals("")){
                ignoredString += "|";
            }
            ignoredString += uuid.toString();
        }

        msg.getConfigManager().get(Config.PLAYERDATA).set("players." + getPlayer().getUniqueId().toString() + ".Ignored", ignoredString);
        msg.getConfigManager().save(Config.PLAYERDATA);
    }
    public List<ProxiedPlayer> getNotIgnored() {
        List<ProxiedPlayer> notIgnored = new ArrayList<ProxiedPlayer>();
        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(!getIgnored().contains(player.getUniqueId()) && player != getPlayer()){
                notIgnored.add(player);
            }
        }
        return notIgnored;
    }
    public List<ProxiedPlayer> getNotIgnored(Collection<ProxiedPlayer> collection) {
        List<ProxiedPlayer> notIgnored = new ArrayList<ProxiedPlayer>();
        for(ProxiedPlayer player : collection){
            if(!getIgnored().contains(player.getUniqueId()) && player != getPlayer()){
                notIgnored.add(player);
            }
        }
        return notIgnored;
    }

    public Map<Cooldown, Long> getCooldowns() {
        return cooldowns;
    }
    public long getCooldown(Cooldown cooldown) {
        if(this.cooldowns.containsKey(cooldown)){
            return cooldowns.get(cooldown);
        }
        return -1;
    }
    public void resetCooldown(Cooldown cooldown){
        this.cooldowns.put(cooldown, System.currentTimeMillis());
    }
    public void removeCooldown(Cooldown cooldown){
        this.cooldowns.remove(cooldown);
    }
    public boolean onCooldown(Cooldown cooldown){
        if(this.cooldowns.containsKey(cooldown)){
            if(System.currentTimeMillis() - this.cooldowns.get(cooldown) >= cooldown.getCooldown()){
                return false;
            }
            return true;
        }
        return false;
    }

    public Channel getToggledChannel() {
        return toggledChannel;
    }
    public void setToggledChannel(Channel toggledChannel) {
        this.toggledChannel = toggledChannel;
    }

    public boolean isSpy() {
        return spy;
    }
    public void setSpy(boolean spy) {
        this.spy = spy;
    }

    public boolean hasMSGEnabled() {
        return msgEnabled;
    }
    public void setMSGEnabled(boolean msgEnabled) {
        this.msgEnabled = msgEnabled;

        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
        c.set("players." + player.getUniqueId().toString() + ".Toggle", msgEnabled);
        msg.getConfigManager().save(Config.PLAYERDATA);
    }

    public boolean isMuted(){
        return !hasPermission("BungeeMSG.bypass.mute", "BungeeMSG.bypass.*") && (msg.getMutedUUIDs().contains(getPlayer().getUniqueId()) || msg.isAllMuted() || msg.getServersMuted().contains(getPlayer().getServer().getInfo()));
    }

    // Maybe 'String... permissions' in a future update.
    public boolean hasPermission(String permission){
        List<String> perms = getBungeePerms();
        if(getRank() != null){
            perms.addAll(getRank().getPermissions());
        }
        perms = lowercase(perms);

        return perms.contains("*") || perms.contains(permission.toLowerCase());
    }

    public boolean hasPermission(String permission, String otherPermission){
        List<String> perms = getBungeePerms();
        if(getRank() != null){
            perms.addAll(getRank().getPermissions());
        }
        perms = lowercase(perms);

        return perms.contains("*") || perms.contains(permission.toLowerCase()) || perms.contains(otherPermission.toLowerCase());
    }

    private List<String> lowercase(List<String> list){
        List<String> lowerList = new ArrayList<>();
        for(String s : list){
            lowerList.add(s.toLowerCase());
        }
        return lowerList;
    }

    private List<String> getBungeePerms(){
        List<String> perms = new ArrayList<>();
        perms.addAll(getPlayer().getPermissions());
        if(msg.bungeePermsUsed()){
            perms.addAll(msg.getBungeePermsApi().getPerms(getPlayer().getName()));
            perms.addAll(msg.getBungeePermsApi().getGroupPerms(getPlayer().getName()));
        }
        return perms;
    }

    public Rank getRank() {
        return rank;
    }
    public void setRank(Rank rank) {
        this.rank = rank;

        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
        c.set("players." + player.getUniqueId().toString() + ".Rank", rank.getName());
        msg.getConfigManager().save(Config.PLAYERDATA);
    }

    public boolean hasActionBar() {
        return hasActionBar;
    }

    public void setActionBar(boolean hasActionBar) {
        this.hasActionBar = hasActionBar;
    }

    public void sendMessage(String message, ProxiedPlayer sender){
        if(sender != null && message.contains("%suggest-player%")) {
            String before = message.substring(0, message.indexOf("%suggest-player%"));
            String after = message.substring(message.indexOf("%suggest-player%") + 16);

            MessageParser mp1 = Message.SUGGESTED_PLAYER.getParser(this);
            mp1.parseVariable(Variable.RECEIVER, sender.getName());

            MessageParser mp2 = Message.SUGGESTED_COMMAND.getParser(this);
            mp2.parseVariable(Variable.RECEIVER, sender.getName());

            MessageParser mp3 = Message.HOVER_MESSAGE.getParser(this);
            mp3.parseVariable(Variable.RECEIVER, sender.getName());

            ComponentMessage cm = new ComponentMessage();
            cm.addPart(before, null, null, null, null);
            cm.addPart(mp1.getMessage(), ClickEvent.Action.SUGGEST_COMMAND, mp2.getMessage(), HoverEvent.Action.SHOW_TEXT, mp3.getMessage());
            checkComponentColors(message, after, cm);
            cm.send(getPlayer());
        }
        else if(sender == null && message.contains("%suggest-server-")){
            String s1 = message.substring(message.indexOf("%suggest-server-") + 16);
            String servername = s1.substring(0, s1.indexOf("%"));
            String variable = "%suggest-server-" + servername + "%";
            ServerInfo info = ProxyServer.getInstance().getServerInfo(servername);

            if(info != null){
                String before = message.substring(0, message.indexOf(variable));
                String after = message.substring(message.indexOf(variable) + variable.length());
                String name = msg.getServerName(info);

                MessageParser mp = Message.SERVER_HOVER.getParser(this);
                mp.parseVariable(Variable.SERVER_NAME, name);

                ComponentMessage cm = new ComponentMessage();
                cm.addPart(before, null, null, null, null);
                cm.addPart(name, ClickEvent.Action.RUN_COMMAND, "/server " + servername, HoverEvent.Action.SHOW_TEXT, mp.getMessage());
                checkComponentColors(message, after, cm);
                cm.send(getPlayer());
            }
            else{
                getPlayer().sendMessage(message.replace(variable, "Unknown Server"));
            }
        }
        else{
            getPlayer().sendMessage(message);
        }
    }

    private void checkComponentColors(String message, String after, ComponentMessage cm){
        String color = message.substring(message.lastIndexOf("§"), message.lastIndexOf("§") +2);

        String newAfter = "";
        for(String part : after.split(" ")){

            if(!newAfter.equals("") && newAfter.contains("§")){
                color = getStringColors(color, newAfter);
            }
            else{
                if(part.contains("§")){
                    color = getStringColors(color, part);
                }
            }
            newAfter += color + part + " ";
        }

        Pattern pattern = Utils.DOMAIN_PATTERN;
        Matcher m = pattern.matcher(newAfter);

        if(m.find()) {
            // Remove color & add http:// to make it clickable.
            String url = m.group().substring(1);
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                if(!url.startsWith("ttps://")) {
                    url = "http://" + url;
                }
                else{
                    // if there was no color
                    url = "h" + url;
                }
            }

            cm.addPart(newAfter, ClickEvent.Action.OPEN_URL, url, null, null);
        }
        else{
            cm.addPart(newAfter, null, null, null, null);
        }
    }

    private String getStringColors(String color, String string){
        int lastIndex = string.lastIndexOf("§");
        color = string.substring(lastIndex, lastIndex +2);

        // Check if there are any additional color codes (§k, §l, §m, §n, §o, §r)
        boolean done = false;
        while (!done) {
            if(lastIndex > 1 && lastIndex - 2 > 1) {
                String nextColor = string.substring(lastIndex - 2, lastIndex);
                if (nextColor.startsWith("§") && !color.contains(nextColor)) {
                    if(isSpecColor(nextColor)) {
                        color = nextColor + color;
                    }
                    else{
                        if(containsSpecColor(color)){
                           color = nextColor + color;
                        }
                    }
                    string = string.substring(0, lastIndex - 1) + string.substring(lastIndex +2);
                    lastIndex -= 2;
                }
                else {
                    done = true;
                }
            }
            else {
                done = true;
            }
        }

        return color;
    }

    private boolean isSpecColor(String nextColor){
        String c = nextColor.substring(1);
        return c.equals("k") || c.equals("l") || c.equals("m") || c.equals("n") || c.equals("o") || c.equals("r");
    }

    private boolean containsSpecColor(String string){
        return string.contains("k") || string.contains("l") || string.contains("m") || string.contains("n") || string.contains("o") || string.contains("r");
    }

    public boolean canMessage(String message, Cooldown cooldown){
        return !msgOnCooldown(message, cooldown) && !isDuplicated(message) && !isTooFast(message);
    }

    public boolean msgOnCooldown(String message, Cooldown msgCooldown){
        SpamManager sM = msg.getSpamManager();

        if(sM.isUsed() && sM.useCooldown() && !hasPermission("BungeeMSG.bypass.cooldown") && !hasPermission("BungeeMSG.bypass.*")){
            if(!onCooldown(msgCooldown)){
                resetCooldown(msgCooldown);
            }
            else{
                int timeleft = (int) ((getCooldown(msgCooldown) / 1000) - ((System.currentTimeMillis() - msgCooldown.getCooldown()) / 1000));
                MessageParser mP = Message.SPAM_COOLDOWN.getParser(this);
                mP.parseVariable(Variable.LEFT, "" + timeleft);

                if(timeleft == 1){
                    mP.parseVariable(Variable.SECOND_GRAMMER, Message.SECOND_SINGLE.getParser(this).getMessage());
                }
                else{
                    mP.parseVariable(Variable.SECOND_GRAMMER, Message.SECOND_MULTIPLE.getParser(this).getMessage());
                }
                mP.send(getPlayer(), true);

                msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (Cooldown)");

                return true;
            }
        }
        return false;
    }

    public boolean isDuplicated(String message){
        SpamManager sM = msg.getSpamManager();

        if(sM.isUsed() && sM.canCancelDuplicate() && !hasPermission("BungeeMSG.bypass.duplicate") && !hasPermission("BungeeMSG.bypass.*")){
            if(getLastMSG() != null){
                String lastmessage = getLastMSG();
                int dif = lastmessage.length() - message.length();

                if(dif < 0) dif *= -1;

                if(lastmessage.length() > 2){
                    String newstring = lastmessage.substring(0, lastmessage.length() - sM.getDuplicateSensitivity());
                    if(newstring.length() > 1){
                        lastmessage = newstring;
                    }
                }

                if(sM.getDuplicateSensitivity() != 0 && dif <= sM.getDuplicateSensitivity() && message.toLowerCase().startsWith(lastmessage.toLowerCase()) || message.toLowerCase().equals(lastmessage.toLowerCase())){
                    MessageParser mP = Message.SPAM_DUPLICATE.getParser(this);
                    mP.send(getPlayer(), true);

                    msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (Duplicate)");

                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTooFast(String message){
        SpamManager sM = msg.getSpamManager();

        if(sM.isUsed() && sM.canCancelTooFast() && !hasPermission("BungeeMSG.bypass.toofast") && !hasPermission("BungeeMSG.bypass.*")){
            if(tooFastAmount != 0){
                if(!onCooldown(Cooldown.TOO_FAST_STARTED)){
                    removeCooldown(Cooldown.TOO_FAST_STARTED);
                    tooFastAmount = 0;
                }
                else{
                    if(tooFastAmount >= sM.getTooFastMax()){
                        MessageParser mP = Message.SPAM_TOO_FAST.getParser(this);
                        mP.send(getPlayer(), true);

                        msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (TooFast)");

                        return true;
                    }
                    else{
                        tooFastAmount++;
                    }
                }
            }
            else{
                resetCooldown(Cooldown.TOO_FAST_STARTED);
                tooFastAmount = 1;
            }
        }
        return false;
    }

    public boolean hasIgnored(BungeePlayer bp2, MessageParser tosend, String message){
        if(!hasPermission("BungeeMSG.bypass.ignore") && !hasPermission("BungeeMSG.bypass.*")){
            if(getIgnored().contains(bp2.getPlayer().getUniqueId())){
                MessageParser mP = Message.IGNORED.getParser(this);
                mP.parseVariable(Variable.RECEIVER, bp2.getPlayer().getName());
                mP.send(getPlayer(), true);

                return true;
            }
            else{
                if(bp2.getIgnored().contains(getPlayer().getUniqueId())){
                    if(msg.tellIgnored()){
                        MessageParser mP = Message.IGNORED_YOU.getParser(this);
                        mP.parseVariable(Variable.RECEIVER, bp2.getPlayer().getName());
                        mP.send(getPlayer(), true);
                    }
                    else{
                        tosend.send(getPlayer(), true);
                    }

                    msg.getLogManager().info(LogReadType.IGNORES, getPlayer().getServer().getInfo(), "[IGNORED] " + getPlayer().getName() + " > " + bp2.getPlayer().getName() + ": " + message);

                    return true;
                }
            }
        }
        return false;
    }

    public static BungeePlayer getBungeePlayer(ProxiedPlayer p){
        return msg.getBungeePlayers().get(p);
    }
}
