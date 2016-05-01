package fadidev.bungeemsg.utils.enums;

public enum Cooldown {

    REPORT(600),
    HELPOP(600),
    LAST_MSG(2),
    LAST_GLOBAL(2),
    TOO_FAST_STARTED(5);

    private int cooldown;

    Cooldown(int cooldown){
        this.cooldown = cooldown;
    }

    public long getCooldown(){
        return cooldown * 1000;
    }

    public void setCooldown(int cooldown){
        this.cooldown = cooldown;
    }
}
