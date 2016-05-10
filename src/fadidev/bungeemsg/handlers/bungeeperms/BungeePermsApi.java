package fadidev.bungeemsg.handlers.bungeeperms;

import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.PermissionsManager;
import net.alpenblock.bungeeperms.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 6-5-2016.
 */
public class BungeePermsApi {

    private PermissionsManager permissionsManager;

    public BungeePermsApi(){
        permissionsManager = BungeePerms.getInstance().getPermissionsManager();
    }

    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    public User getUser(String usernameoruuid){
        return permissionsManager.getUser(usernameoruuid);
    }

    public boolean hasPerm(String usernameoruuid, String perm){
        return getUser(usernameoruuid).hasPerm(perm);
    }

    public List<String> getPerms(String usernameoruuid){
        return getUser(usernameoruuid).getPerms();
    }

    public List<String> getGroupPerms(String usernameoruuid){
        List<String> perms = new ArrayList<>();
        List<Group> groups = getUser(usernameoruuid).getGroups();

        for(Group group : groups){
            perms.addAll(group.getPerms());
        }

        return perms;
    }
}
