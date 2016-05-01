package fadidev.bungeemsg.events;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.*;
import fadidev.bungeemsg.managers.LogManager;
import fadidev.bungeemsg.utils.PlayerUtils;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerChatEvent implements Listener {
    
    private BungeeMSG msg;
    
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(ChatEvent e){
        this.msg = BungeeMSG.getInstance();
        Connection c = e.getSender();
        
        if(c instanceof ProxiedPlayer){
            String[] a = e.getMessage().split(" ");
            ProxiedPlayer p = (ProxiedPlayer) c;
            BungeePlayer bp = msg.getBungeePlayers().get(p);
            LogManager lM = msg.getLogManager();
            
            Channel ch = null;
            String symb = null;
            
            loop:
            for(Channel channel : msg.getChannels()){
                if(!channel.usePermission() || bp.hasPermission(channel.getPermission())){
                    for(String symbol : channel.getStartSymbols()){
                        if(e.getMessage().toLowerCase().startsWith(symbol.toLowerCase())){
                            ch = channel;
                            symb = symbol;
                            break loop;
                        }
                    }
                }
            }
            
            if(ch != null){
                String message = e.getMessage().substring(symb.length());
                
                if(!bp.isMuted(e)){
                    if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
                        MessageParser mP = ch.getMessage().getParser(bp);
                        mP.parseVariable(Variable.SENDER, p.getName());
                        mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                        mP.parseCustomVariables(p, null);
                        mP.parseVariable(Variable.MSG, message);
                        mP.send(p, false);
                    
                        if(!mP.isCancelled()){
                            for(ProxiedPlayer player : bp.getNotIgnored()){
                                if(!ch.usePermission() || player.hasPermission(ch.getPermission())){
                                    mP.send(player, p, p, false);
                                }
                            }
                            
                            lM.info(ch, "[CHANNEL | " + ch.getName() + "] " + p.getName() + ": '" + message + "'");
                        }
                    }
                }
                else{
                    MessageParser mP = Message.MUTED.getParser(bp);
                    mP.send(p, true);
                }

                e.setCancelled(true);
            }
            else{
                ch = null;
                
                loop:
                for(Channel channel : msg.getChannels()){
                    if(!channel.usePermission() || bp.hasPermission(channel.getPermission())){
                        for(String symbol : channel.getToggleSymbols()){
                            if(e.getMessage().toLowerCase().startsWith(symbol.toLowerCase())){
                                ch = channel;
                                break loop;
                            }
                        }
                    }
                }
                
                if(ch != null){
                    e.setCancelled(true);
                    
                    Channel channel = bp.getToggledChannel();
                    if(channel != null){
                        if(channel == ch){
                            MessageParser mP = channel.getDisabledMessage().getParser(bp);
                            mP.send(p, true);
                            
                            bp.setToggledChannel(null);
                        }
                        else{
                            MessageParser mP = channel.getDisabledMessage().getParser(bp);
                            mP.send(p, true);
                            
                            MessageParser mP2 = ch.getEnabledMessage().getParser(bp);
                            mP2.send(p, true);
                            
                            bp.setToggledChannel(ch);
                        }
                    }
                    else{
                        MessageParser mP = ch.getEnabledMessage().getParser(bp);
                        mP.send(p, true);
                        
                        bp.setToggledChannel(ch);
                    }
                }
                else{
                    if(!a[0].startsWith("/")){
                        String message = e.getMessage();
                        
                        if(bp.getToggledChannel() != null){
                            Channel channel = bp.getToggledChannel();
                            
                            if(!bp.isMuted(e)){
                                if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
                                    MessageParser mP = channel.getMessage().getParser(bp);
                                    mP.parseVariable(Variable.SENDER, p.getName());
                                    mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                    mP.parseCustomVariables(p, null);
                                    mP.parseVariable(Variable.MSG, message);
                                    mP.send(p, false);
                                
                                    if(!mP.isCancelled()){
                                        for(ProxiedPlayer player : bp.getNotIgnored()){
                                            if(!channel.usePermission() || player.hasPermission(channel.getPermission())){
                                                mP.send(player, p, p, false);
                                            }
                                        }
                                        
                                        lM.info(channel, "[CHANNEL | " + channel.getName() + "] " + p.getName() + ": '" + message + "'");
                                    }
                                }
                            }
                            else{
                                MessageParser mP = Message.MUTED.getParser(bp);
                                mP.send(p, true);
                            }

                            e.setCancelled(true);
                        }
                        else{
                            if(msg.useAutoGlobal()){
                                Group group = null;
                                ServerInfo server = p.getServer().getInfo();
                                
                                for(Group g : msg.getGroups()){
                                    if(g.getServers().contains(server)){
                                        group = g;
                                    }
                                }
                                
                                if(group != null){
                                    if(!bp.isMuted(e)){
                                        if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
                                            MessageParser mP = group.getMSGLoader().getParser(bp);
                                            mP.parseVariable(Variable.SENDER, p.getName());
                                            mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                            mP.parseCustomVariables(p, null);
                                            mP.parseVariable(Variable.MSG, message);
                                            mP.send(p, false);
                                        
                                            if(!mP.isCancelled()){
                                                for(ServerInfo info : group.getServers()){
                                                    for(ProxiedPlayer player : bp.getNotIgnored(info.getPlayers())){
                                                        mP.send(player, p, p, false);
                                                    }
                                                }
                                                
                                                lM.info(group, "[GLOBAL-AUTO] " + p.getName() + ": " + message);
                                            }
                                        }
                                    }
                                    else{
                                        MessageParser mP = Message.MUTED.getParser(bp);
                                        mP.send(p, true);
                                    }
                                }
                            }
                        }
                    }
                    else{
                        for(Command cmd : msg.getCommands()){
                            if(cmd.isUsed() && cmd.getCommands().contains(a[0].toLowerCase())){
                                if(!cmd.usePermission() || bp.hasPermission(cmd.getPermission())){
                                    switch(cmd.getType()){
                                        case RELOAD:
                                            {
                                                lM.info(LogReadType.RELOADS, p.getServer().getInfo(), "[RELOAD] " + p.getName() + " started a reload...");
                                                for(Config config : Config.getCorrectOrder()){
                                                    if(config != Config.PLAYERDATA){
                                                        p.sendMessage("§7Reloading §6" + config.getFileName() + "§7...");
                                                    }
                                                    else{
                                                        int players = ProxyServer.getInstance().getPlayers().size();
                                                        if(players == 1){
                                                            p.sendMessage("§7Restoring data for §6" + players + " Player§7...");
                                                        }
                                                        else{
                                                            p.sendMessage("§7Restoring data for §6" + players + " Players§7...");
                                                        }
                                                    }
                                                    msg.getConfigManager().reload(config);
                                                }
                                                msg.loadData(true);
                                                
                                                p.sendMessage("§7Reload §aCompleted§7!");
                                            }
                                            break;
                                        case REPORTLIST:
                                            {
                                                if(a.length == 2){
                                                    try {
                                                        int size = Integer.parseInt(a[1]);
                                                        List<Report> reportList = msg.getReportList();
                                                        if (reportList.size() > 0) {

                                                            int index = 0;
                                                            if (reportList.size() > size) {
                                                                index = reportList.size() - size;
                                                            }

                                                            MessageParser mP = Message.REPORTLIST_LAST.getParser(bp);
                                                            mP.parseVariable(Variable.AMOUNT, size + "");
                                                            mP.send(p, true);

                                                            for (int i = index; i < reportList.size(); i++) {
                                                                Report report = reportList.get(i);

                                                                MessageParser mP2 = Message.REPORTLIST_DISPLAY.getParser(bp);
                                                                mP2.parseVariable(Variable.REPORTER, report.getReportedByName());
                                                                mP2.parseVariable(Variable.REPORTED, report.getReportedName());
                                                                if (report.getServerInfo() != null) {
                                                                    mP2.parseVariable(Variable.SERVER_NAME ,msg.getServerName(report.getServerInfo()));
                                                                } else {
                                                                    mP2.parseVariable(Variable.SERVER_NAME, report.getServer());
                                                                }
                                                                mP2.parseVariable(Variable.DATE, report.getDate());
                                                                mP2.parseVariable(Variable.REASON, report.getReason());
                                                                mP2.send(p, true);
                                                            }
                                                        } else {
                                                            MessageParser mP = Message.REPORTLIST_NONE.getParser(bp);
                                                            mP.send(p, true);
                                                        }
                                                    }catch(NumberFormatException ex){
                                                        MessageParser mP = Message.UNKNOWN_NUMBER.getParser(bp);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else if(a.length == 3){
                                                    try {
                                                        int size = Integer.parseInt(a[2]);
                                                        List<Report> reportList = msg.getReportList();
                                                        if (reportList.size() > 0) {
                                                            UUID reported = PlayerUtils.getUUID(a[1]);
                                                            reportList = Report.getReports(reported);

                                                            if(reported != null && reportList != null && reportList.size() > 0) {
                                                                int index = 0;
                                                                if (reportList.size() > size) {
                                                                    index = reportList.size() - size;
                                                                }

                                                                MessageParser mP = Message.REPORTLIST_PLAYER.getParser(bp);
                                                                mP.parseVariable(Variable.AMOUNT, size + "");
                                                                mP.parseVariable(Variable.REPORTED, a[1]);
                                                                mP.send(p, true);

                                                                for (int i = index; i < reportList.size(); i++) {
                                                                    Report report = reportList.get(i);

                                                                    MessageParser mP2 = Message.REPORTLIST_DISPLAY.getParser(bp);
                                                                    mP2.parseVariable(Variable.REPORTER, report.getReportedByName());
                                                                    if (report.getServerInfo() != null) {
                                                                        mP2.parseVariable(Variable.SERVER_NAME, msg.getServerName(report.getServerInfo()));
                                                                    } else {
                                                                        mP2.parseVariable(Variable.SERVER_NAME, report.getServer());
                                                                    }
                                                                    mP2.parseVariable(Variable.DATE, report.getDate());
                                                                    mP2.parseVariable(Variable.REASON, report.getReason());
                                                                    mP2.send(p, true);
                                                                }
                                                            }
                                                            else{
                                                                MessageParser mP = Message.REPORTLIST_NONE_PLAYER.getParser(bp);
                                                                mP.parseVariable(Variable.REPORTED, a[1]);
                                                                mP.send(p, true);
                                                            }
                                                        } else {
                                                            MessageParser mP = Message.REPORTLIST_NONE.getParser(bp);
                                                            mP.send(p, true);
                                                        }
                                                    }catch(NumberFormatException ex){
                                                        MessageParser mP = Message.UNKNOWN_NUMBER.getParser(bp);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case SETRANK:
                                            {
                                                if(a.length == 3){
                                                    ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);

                                                    if(p2 != null){
                                                        BungeePlayer bp2 = msg.getBungeePlayers().get(p2);

                                                        Rank rank = Rank.getRank(a[2]);
                                                        if(rank != null){
                                                            bp2.setRank(rank);

                                                            MessageParser mP = Message.RANK_SET.getParser(bp);
                                                            mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                            mP.parseVariable(Variable.RANK, a[2]);
                                                            mP.send(p, true);
                                                        }
                                                        else{
                                                            MessageParser mP = Message.UNKNOWN_RANK.getParser(bp);
                                                            mP.parseVariable(Variable.RANK, a[2]);
                                                            mP.send(p, true);
                                                        }

                                                        lM.info(LogReadType.RANK_SET, p.getServer().getInfo(), "[SETRANK] " + p.getName() + " has set " + p2.getName() + "'s Rank to " + a[2] + ".");
                                                    }
                                                    else{
                                                        MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                        mP.parseVariable(Variable.RECEIVER, a[1]);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case SPY:
                                            {
                                                if(bp.isSpy()){
                                                    MessageParser mP = Message.SPY_DISABLE.getParser(bp);
                                                    mP.send(p, true);
                                                    
                                                    lM.info(LogReadType.SPIES, p.getServer().getInfo(), "[SPY] " + p.getName() + " Disabled Spy Mode.");
                                                }
                                                else{
                                                    MessageParser mP = Message.SPY_ENABLE.getParser(bp);
                                                    mP.send(p, true);
                                                    
                                                    lM.info(LogReadType.SPIES, p.getServer().getInfo(), "[SPY] " + p.getName() + " Enabled Spy Mode.");
                                                }
                                                
                                                bp.setSpy(!bp.isSpy());
                                            }
                                            break;
                                        case TOGGLE:
                                            {
                                                if(a.length == 1 || !p.hasPermission(cmd.getPermission() + ".other")){
                                                    if(bp.hasMSGEnabled()){
                                                        MessageParser mP = Message.PM_DISABLED.getParser(bp);
                                                        mP.send(p, true);
                                                        
                                                        lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Disabled receiving Private Messages.");
                                                    }
                                                    else{
                                                        MessageParser mP = Message.PM_ENABLED.getParser(bp);
                                                        mP.send(p, true);
                                                        
                                                        lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Enabled receiving Private Messages.");
                                                    }
                                                    
                                                    bp.setMSGEnabled(!bp.hasMSGEnabled());
                                                }
                                                else{
                                                    if(a.length == 2){
                                                        ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
                                                        
                                                        if(p2 != null){
                                                            BungeePlayer bp2 = msg.getBungeePlayers().get(p2);
                                                            if(bp2.hasMSGEnabled()){
                                                                MessageParser mP = Message.PM_DISABLED_TO_SENDER.getParser(bp);
                                                                mP.parseVariable(Variable.TOGGLED, p2.getName());
                                                                mP.send(p, true);
                                                                
                                                                MessageParser mP2 = Message.PM_DISABLED_TO_PLAYER.getParser(bp);
                                                                mP2.parseVariable(Variable.SENDER, p.getName());
                                                                mP2.send(p2, true);
                                                                
                                                                lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Disabled receiving Private Messages for player " + p2.getName() + ".");
                                                            }
                                                            else{
                                                                MessageParser mP = Message.PM_ENABLED_TO_SENDER.getParser(bp);
                                                                mP.parseVariable(Variable.TOGGLED, p2.getName());
                                                                mP.send(p, true);
                                                                
                                                                MessageParser mP2 = Message.PM_ENABLED_TO_PLAYER.getParser(bp);
                                                                mP2.parseVariable(Variable.SENDER, p.getName());
                                                                mP2.send(p2, true);
                                                                
                                                                lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Enabled receiving Private Messages for player " + p2.getName() + ".");
                                                            }
                                                            
                                                            bp2.setMSGEnabled(!bp2.hasMSGEnabled());
                                                        }
                                                        else{
                                                            MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                            mP.parseVariable(Variable.RECEIVER, a[1]);
                                                            mP.send(p, true);
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                        mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                        mP.send(p, true);
                                                    }
                                                }
                                            }
                                            break;
                                        case MUTE:
                                            {
                                                if(a.length == 2){
                                                    ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
                                                    
                                                    if(p2 != null){
                                                        List<UUID> uuidList = msg.getMutedUUIDs();
                                                        UUID uuid = p2.getUniqueId();
                                                        
                                                        if(uuidList.contains(uuid)){
                                                            uuidList.remove(uuid);
                                                            
                                                            MessageParser mP = Message.UNMUTE_TO_SENDER.getParser(bp);
                                                            mP.parseVariable(Variable.MUTED, p2.getName());
                                                            mP.send(p, true);
                                                            
                                                            MessageParser mP2 = Message.UNMUTE_TO_PLAYER.getParser(bp);
                                                            mP2.parseVariable(Variable.SENDER, p.getName());
                                                            mP2.send(p2, true);
                                                            
                                                            lM.info(LogReadType.MUTES, p.getServer().getInfo(), "[MUTE] " + p.getName() + " unmuted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
                                                        }
                                                        else{
                                                            uuidList.add(uuid);
                                                            
                                                            MessageParser mP = Message.MUTE_TO_SENDER.getParser(bp);
                                                            mP.parseVariable(Variable.MUTED, p2.getName());
                                                            mP.send(p, true);
                                                            
                                                            MessageParser mP2 = Message.MUTE_TO_PLAYER.getParser(bp);
                                                            mP2.parseVariable(Variable.SENDER, p.getName());
                                                            mP2.send(p2, true);
                                                            
                                                            lM.info(LogReadType.MUTES, p.getServer().getInfo(), "[MUTE] " + p.getName() + " muted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
                                                        }
                                                        
                                                        msg.getConfigManager().get(Config.MUTED).set("MutedUUIDs", Utils.parseStringList(uuidList));
                                                        msg.getConfigManager().save(Config.MUTED);
                                                    }
                                                    else{
                                                        MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                        mP.parseVariable(Variable.RECEIVER, a[1]);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case GLOBAL:
                                            {
                                                if(!bp.isMuted(e)){
                                                    ServerInfo server = p.getServer().getInfo();
                                                    Group group = null;
                                                    for(Group g : msg.getGroups()){
                                                        if(g.getServers().contains(server)){
                                                            group = g;
                                                            break;
                                                        }
                                                    }
                                                    
                                                    if(group != null && a.length > 1){
                                                        String message = e.getMessage().substring(a[0].length() + 1);
                                                        
                                                        if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
                                                            MessageParser mP = group.getMSGLoader().getParser(bp);
                                                            mP.parseVariable(Variable.SENDER, p.getName());
                                                            mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                            mP.parseCustomVariables(p, null);
                                                            mP.parseVariable(Variable.MSG, message);
                                                            mP.send(p, false);
                                                        
                                                            if(!mP.isCancelled()){
                                                                for(ServerInfo info : group.getServers()){
                                                                    for(ProxiedPlayer player : bp.getNotIgnored(info.getPlayers())){
                                                                        mP.send(player, p, p, false);
                                                                    }
                                                                }
                                                                
                                                                lM.info(group, "[GLOBAL-CMD] " + p.getName() + ": " + message);
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        p.sendMessage("Hi");
                                                        MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                        mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = Message.MUTED.getParser(bp);
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case MUTE_ALL:
                                            {
                                                if(a.length == 1){
                                                    if(msg.isAllMuted()){
                                                        MessageParser mP = Message.UNMUTE_ALL_TO_SENDER.getParser(bp);
                                                        mP.send(p, true);
                                                        
                                                        MessageParser mP2 = Message.UNMUTE_ALL_TO_PLAYER.getParser(bp);
                                                        mP2.parseVariable(Variable.SENDER, p.getName());
                                                        mP2.parseCustomVariables(p, null);
                                                        
                                                        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                            mP2.send(player, false);
                                                        }
                                                        
                                                        lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Disabled Mute All Mode.");
                                                    }
                                                    else{
                                                        MessageParser mP = Message.MUTE_ALL_TO_SENDER.getParser(bp);
                                                        mP.send(p, true);
                                                        
                                                        MessageParser mP2 = Message.MUTE_ALL_TO_PLAYER.getParser(bp);
                                                        mP2.parseVariable(Variable.SENDER, p.getName());
                                                        mP2.parseCustomVariables(p, null);
                                                        
                                                        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                            mP2.send(player, false);
                                                        }
                                                        
                                                        lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Enabled Mute All Mode.");
                                                    }
                                                    
                                                    msg.setAllMuted(!msg.isAllMuted());
                                                }
                                                else if(a.length == 2){
                                                    ServerInfo server = ProxyServer.getInstance().getServerInfo(a[1]);
                                                    
                                                    if(server != null){
                                                        List<ServerInfo> mutedServers = msg.getServersMuted();
                                                        
                                                        if(mutedServers.contains(server)){
                                                            mutedServers.remove(server);
                                                            
                                                            MessageParser mP = Message.MUTE_SERVER_TO_SENDER.getParser(bp);
                                                            mP.parseVariable(Variable.SERVER_NAME, msg.getServerName(server));
                                                            mP.send(p, true);
                                                            
                                                            MessageParser mP2 = Message.MUTE_ALL_TO_PLAYER.getParser(bp);
                                                            mP2.parseVariable(Variable.SENDER, p.getName());
                                                            mP2.parseCustomVariables(p, null);
                                                        
                                                            for(ProxiedPlayer player : server.getPlayers()){
                                                                mP2.send(player, false);
                                                            }
                                                            
                                                            lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Enabled Mute All Mode for server '" + server.getName() + "'.");
                                                        }
                                                        else{
                                                            mutedServers.add(server);
                                                            
                                                            MessageParser mP = Message.UNMUTE_SERVER_TO_SENDER.getParser(bp);
                                                            mP.parseVariable(Variable.SERVER_NAME, msg.getServerName(server));
                                                            mP.send(p, true);
                                                            
                                                            MessageParser mP2 = Message.UNMUTE_ALL_TO_PLAYER.getParser(bp);
                                                            mP2.parseVariable(Variable.SENDER, p.getName());
                                                            mP2.parseCustomVariables(p, null);
                                                        
                                                            for(ProxiedPlayer player : server.getPlayers()){
                                                                mP2.send(player, false);
                                                            }
                                                            
                                                            lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Disabled Mute All Mode for server '" + server.getName() + "'.");
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                        mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case IGNORE:
                                            {
                                                if(a.length == 2){
                                                    ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
                                                    
                                                    if(p2 != null){
                                                        if(p != p2){
                                                            List<UUID> ignored = bp.getIgnored();
                                                            if(ignored.contains(p2.getUniqueId())){
                                                                ignored.remove(p2.getUniqueId());
                                                                
                                                                MessageParser mP = Message.IGNORE_DISABLE.getParser(bp);
                                                                mP.parseVariable(Variable.IGNORED, p2.getName());
                                                                mP.send(p, true);
                                                            }
                                                            else{
                                                                ignored.add(p2.getUniqueId());
                                                                
                                                                MessageParser mP = Message.IGNORE_ENABLE.getParser(bp);
                                                                mP.parseVariable(Variable.IGNORED, p2.getName());
                                                                mP.send(p, true);
                                                            }
                                                            
                                                            bp.setIgnored(ignored);
                                                        }
                                                        else{
                                                            MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
                                                            mP.send(p, true);
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                        mP.parseVariable(Variable.RECEIVER, a[1]);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case REPORT:
                                            {
                                                if(a.length > 2){
                                                    ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
                                                    
                                                    if(p2 != null){
                                                        if(p != p2){
                                                            if(!bp.onCooldown(Cooldown.REPORT)){
                                                                String reason = e.getMessage().substring(a[0].length() + p2.getName().length() + 2);
                                                                
                                                                MessageParser mP = Message.REPORTED_TO_SENDER.getParser(bp);
                                                                mP.parseVariable(Variable.REPORTED, p2.getName());
                                                                mP.parseVariable(Variable.SERVER_REPORTED, msg.getServerName(p2.getServer().getInfo()));
                                                                mP.parseCustomVariables(p, p2);
                                                                mP.parseVariable(Variable.REASON, reason);
                                                                mP.send(p, false);
                                                                
                                                                MessageParser mP2 = Message.REPORTED_TO_STAFF.getParser(bp);
                                                                mP2.parseVariable(Variable.REPORTED, p2.getName());
                                                                mP2.parseVariable(Variable.SERVER_REPORTED, msg.getServerName(p2.getServer().getInfo()));
                                                                mP2.parseVariable(Variable.SENDER, p.getName());
                                                                mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                mP2.parseCustomVariables(p, p2);
                                                                mP2.parseVariable(Variable.REASON, reason);

                                                                for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                                    if(player != p && player.hasPermission("BungeeMSG.notifyreport")){
                                                                        mP2.send(player, false);
                                                                    }
                                                                }

                                                                String date = new SimpleDateFormat(msg.getDateFormat()).format(new Date(Calendar.getInstance().getTimeInMillis()));
                                                                Report report = new Report(p.getUniqueId(), p2.getUniqueId(), p.getServer().getInfo().getName(), date, reason);
                                                                msg.getReportList().add(report);
                                                                report.generate();
                                                                
                                                                lM.info(LogReadType.REPORTS, p.getServer().getInfo(), "[REPORT] (" + p.getServer().getInfo().getName() + ") " + p.getName() + " > (" + p2.getServer().getInfo().getName() + ") " + p2.getName() + " (Reason: " + reason + ")");
                                                                
                                                                bp.resetCooldown(Cooldown.REPORT);
                                                            }
                                                            else{
                                                                MessageParser mP = Message.REPORT_ON_COOLDOWN.getParser(bp);
                                                                mP.send(p, true);
                                                            }
                                                        }
                                                        else{
                                                            MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
                                                            mP.send(p, true);
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                        mP.parseVariable(Variable.RECEIVER, a[1]);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case HELP_OP:
                                            {
                                                if(a.length > 1){
                                                    if(!bp.onCooldown(Cooldown.HELPOP)){
                                                        String reason = e.getMessage().substring(a[0].length() +1);
                                                        
                                                        MessageParser mP = Message.HELPOP_TO_SENDER.getParser(bp);
                                                        mP.parseCustomVariables(p, null);
                                                        mP.parseVariable(Variable.REASON, reason);
                                                        mP.send(p, false);
                                                        
                                                        MessageParser mP2 = Message.HELPOP_TO_STAFF.getParser(bp);
                                                        mP2.parseVariable(Variable.SENDER, p.getName());
                                                        mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                        mP2.parseCustomVariables(p, null);
                                                        mP2.parseVariable(Variable.REASON, reason);
                                                        
                                                        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                            if(player != p && player.hasPermission("BungeeMSG.notifyhelpop")){
                                                                mP2.send(player, false);
                                                            }
                                                        }
                                                        
                                                        lM.info(LogReadType.HELP_OPS, p.getServer().getInfo(), "[HELPOP] (" + p.getServer().getInfo().getName() + ") " + p.getName() + ": " + reason);
                                                        
                                                        bp.resetCooldown(Cooldown.HELPOP);
                                                    }
                                                    else{
                                                        MessageParser mP = Message.HELPOP_ON_COOLDOWN.getParser(bp);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case BROADCAST:
                                            {
                                                if(a.length > 2){
                                                    boolean all = a[1].equalsIgnoreCase("all");
                                                    ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(a[1]);

                                                    if(all || serverInfo != null){
                                                        String message = e.getMessage().substring(a[0].length() + a[1].length() + 2);

                                                        MessageParser mP = Message.BROADCAST.getParser(bp);
                                                        mP.parseVariable(Variable.SENDER, p.getName());
                                                        mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                        mP.parseCustomVariables(p, null);
                                                        mP.parseVariable(Variable.MSG, message);

                                                        if(all){
                                                            for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                                mP.send(player, false);
                                                            }
                                                        }
                                                        else{
                                                            for(ProxiedPlayer player : serverInfo.getPlayers()){
                                                                mP.send(player, false);
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = Message.UNKNOWN_SERVER.getParser(bp);
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case MESSAGE:
                                            {
                                                if(!bp.isMuted(e)){
                                                    if(a.length > 2){
                                                        ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
                                                    
                                                        if(p2 != null){
                                                            if(p != p2){
                                                                if(bp.hasMSGEnabled()){
                                                                    String message = e.getMessage().substring(a[0].length() + p2.getName().length() + 2);
                                                                
                                                                    if(bp.canMessage(message, Cooldown.LAST_MSG)){
                                                                        BungeePlayer bp2 = msg.getBungeePlayers().get(p2);
                                                                        
                                                                        if(!bp2.hasMSGEnabled()){
                                                                            if(msg.tellPMDisabled()){
                                                                                MessageParser mP = Message.PM_TOGGLED_OTHER.getParser(bp);
                                                                                mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP.parseCustomVariables(p, p2);
                                                                                mP.send(p, false);
                                                                            }
                                                                            else{
                                                                                MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
                                                                                mP.parseVariable(Variable.SENDER, p.getName());
                                                                                mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP.parseCustomVariables(p, p2);
                                                                                mP.parseVariable(Variable.MSG, message);
                                                                                
                                                                                if(!bp.hasIgnored(bp2, mP, message)){
                                                                                    mP.send(p, p2, p, false);
                                                                                }
                                                                            }
                                                                        }
                                                                        else{
                                                                            MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
                                                                            mP.parseVariable(Variable.SENDER, p.getName());
                                                                            mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                            mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                            mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                            mP.parseCustomVariables(p, p2);
                                                                            mP.parseVariable(Variable.MSG, message);
                                                                            
                                                                            if(!bp.hasIgnored(bp2, mP, message) && !mP.isCancelled()){
                                                                                MessageParser mP2 = Message.PM_TO_RECEIVER.getParser(bp);
                                                                                mP2.parseVariable(Variable.SENDER, p.getName());
                                                                                mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP2.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP2.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP2.parseCustomVariables(p, p2);
                                                                                mP2.parseVariable(Variable.MSG, message);
                                                                                mP2.send(p2, p, p, false);
                                                                                mP.send(p, p2, p, false);
                                                                                
                                                                                MessageParser mP3 = Message.SPY_MESSAGE.getParser(bp);
                                                                                mP3.parseVariable(Variable.SENDER, p.getName());
                                                                                mP3.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP3.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP3.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP3.parseCustomVariables(p, p2);
                                                                                mP3.parseVariable(Variable.MSG, message);
                                                                                
                                                                                for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                                                    BungeePlayer bplayer = msg.getBungeePlayers().get(player);
                                                                                    
                                                                                    if(bplayer.isSpy() && player != p && player != p2){
                                                                                        mP3.send(player, false);
                                                                                    }
                                                                                }
                                                                                
                                                                                if(msg.hasReplyInfo() && bp2.getLastMSG() == null){
                                                                                    MessageParser mP4 = Message.REPLY_INFO.getParser(bp);
                                                                                    mP4.send(p2, true);
                                                                                }
                                                                                
                                                                                bp.setLastMSG(message);
                                                                                bp.setLastMSGTo(bp2);
                                                                                bp2.setLastMSGTo(bp);
                                                                                
                                                                                lM.info(LogReadType.PRIVATE_MESSAGES, p.getServer().getInfo(), "[MSG] " + p.getName() + " > " + p2.getName() + ": " + message);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                else{
                                                                    MessageParser mP = Message.PM_TOGGLED.getParser(bp);
                                                                    mP.send(p, true);
                                                                }
                                                            }
                                                            else{
                                                                MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
                                                                mP.send(p, true);
                                                            }
                                                        }
                                                        else{
                                                            MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                            mP.parseVariable(Variable.RECEIVER, a[1]);
                                                            mP.send(p, true);
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                        mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = Message.MUTED.getParser(bp);
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                        case REPLY:
                                            {
                                                if(!bp.isMuted(e)){
                                                    if(a.length > 1){
                                                        if(bp.getLastMSGTo() != null){
                                                            ProxiedPlayer p2 = bp.getLastMSGTo().getPlayer();

                                                            if(p2.isConnected()){
                                                                if(bp.hasMSGEnabled()){
                                                                    String message = e.getMessage().substring(a[0].length() + 1);

                                                                    if(bp.canMessage(message, Cooldown.LAST_MSG)){
                                                                        BungeePlayer bp2 = msg.getBungeePlayers().get(p2);

                                                                        if(!bp2.hasMSGEnabled()){
                                                                            if(msg.tellPMDisabled()){
                                                                                MessageParser mP = Message.PM_TOGGLED_OTHER.getParser(bp);
                                                                                mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP.send(p, true);
                                                                            }
                                                                            else{
                                                                                MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
                                                                                mP.parseVariable(Variable.SENDER, p.getName());
                                                                                mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP.parseCustomVariables(p, p2);
                                                                                mP.parseVariable(Variable.MSG, message);

                                                                                if(!bp.hasIgnored(bp2, mP, message)){
                                                                                    mP.send(p, p2, p, false);
                                                                                }
                                                                            }
                                                                        }
                                                                        else{
                                                                            MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
                                                                            mP.parseVariable(Variable.SENDER, p.getName());
                                                                            mP.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                            mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                                            mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                            mP.parseCustomVariables(p, p2);
                                                                            mP.parseVariable(Variable.MSG, message);

                                                                            if(!bp.hasIgnored(bp2, mP, message) && !mP.isCancelled()){
                                                                                MessageParser mP2 = Message.PM_TO_RECEIVER.getParser(bp);
                                                                                mP2.parseVariable(Variable.SENDER, p.getName());
                                                                                mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP2.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP2.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP2.parseCustomVariables(p, p2);
                                                                                mP2.parseVariable(Variable.MSG, message);
                                                                                mP2.send(p2, p, p, false);
                                                                                mP.send(p, p2, p, false);

                                                                                MessageParser mP3 = Message.SPY_MESSAGE.getParser(bp);
                                                                                mP3.parseVariable(Variable.SENDER, p.getName());
                                                                                mP3.parseVariable(Variable.SERVER_SENDER, msg.getServerName(p.getServer().getInfo()));
                                                                                mP3.parseVariable(Variable.RECEIVER, p2.getName());
                                                                                mP3.parseVariable(Variable.SERVER_RECEIVER, msg.getServerName(p2.getServer().getInfo()));
                                                                                mP3.parseCustomVariables(p, p2);
                                                                                mP3.parseVariable(Variable.MSG, message);

                                                                                for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                                                                                    BungeePlayer bplayer = msg.getBungeePlayers().get(player);

                                                                                    if(bplayer.isSpy() && player != p && player != p2){
                                                                                        mP3.send(player, false);
                                                                                    }
                                                                                }

                                                                                if(msg.hasReplyInfo() && bp2.getLastMSG() == null){
                                                                                    MessageParser mP4 = Message.REPLY_INFO.getParser(bp);
                                                                                    mP4.send(p2, true);
                                                                                }

                                                                                bp.setLastMSG(message);
                                                                                bp.setLastMSGTo(bp2);
                                                                                bp2.setLastMSGTo(bp);

                                                                                lM.info(LogReadType.PRIVATE_MESSAGES, p.getServer().getInfo(), "[MSG] " + p.getName() + " > " + p2.getName() + ": " + message);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                else{
                                                                    MessageParser mP = Message.PM_TOGGLED.getParser(bp);
                                                                    mP.send(p, true);
                                                                }
                                                            }
                                                            else{
                                                                MessageParser mP = Message.NO_RECEIVER.getParser(bp);
                                                                mP.send(p, true);

                                                                bp.setLastMSGTo(null);
                                                            }
                                                        }
                                                        else{
                                                            MessageParser mP = Message.NO_RECEIVER.getParser(bp);
                                                            mP.send(p, true);
                                                        }
                                                    }
                                                    else{
                                                        MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                        mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                        mP.send(p, true);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = Message.MUTED.getParser(bp);
                                                    mP.send(p, true);
                                                }
                                            }
                                            break;
                                    }
                                }
                                else{
                                    MessageParser mP = cmd.getNoPermission().getParser(bp);
                                    mP.send(p, true);
                                }

                                e.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
