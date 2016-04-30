package fadidev.bungeemsg.handlers;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.PlayerUtils;
import fadidev.bungeemsg.utils.enums.Config;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fadi-Laptop on 7-4-2016.
 */
public class Report {

    private static BungeeMSG msg;

    private UUID reported;
    private UUID reportedBy;
    private String reportedName;
    private String reportedByName;
    private String server;
    private ServerInfo serverInfo;
    private String date;
    private String reason;

    public Report(UUID reported, UUID reportedBy, String server, String date, String reason){
        msg = BungeeMSG.getInstance();
        this.reported = reported;
        this.reportedBy = reportedBy;
        this.server = server;
        this.serverInfo = ProxyServer.getInstance().getServerInfo(server);
        this.date = date;
        this.reason = reason;
    }

    public UUID getReported() {
        return reported;
    }

    public UUID getReportedBy() {
        return reportedBy;
    }

    public String getReportedName() {
        if(reportedName != null)
          return reportedName;

        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reported);
        String name = null;
        if(p != null) name = p.getName();
        if(name == null) name = PlayerUtils.getName(reported);
        if(name == null) name = reported.toString();

        this.reportedName = name;
        return name;
    }

    public String getReportedByName() {
        if(reportedByName != null)
            return reportedByName;

        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(reportedBy);
        String name = null;
        if(p != null) name = p.getName();
        if(name == null) name = PlayerUtils.getName(reportedBy);
        if(name == null) name = reportedBy.toString();

        this.reportedByName = name;
        return name;
    }

    public String getServer() {
        return server;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public String getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public void generate(){
        Configuration c = msg.getConfigManager().get(Config.REPORTS);
        c.set("reports." + getReported().toString() + "|" + getReportedBy().toString() + "|" + getServer() + "|" + getDate(), getReason());
        msg.getConfigManager().save(Config.REPORTS);
    }

    public static List<Report> getReports(UUID uuid){
        if(uuid == null) return null;

        List<Report> reportList = new ArrayList<>();

        for(Report report : msg.getReportList()){
            if(report.getReportedBy().toString().equals(uuid.toString())){
                reportList.add(report);
            }
        }

        return reportList;
    }
}
