package fadidev.bungeemsg.managers;

public class SpamManager {

    private boolean use;

    private boolean cancelDuplicate;
    private int duplicateSensitivity;

    private boolean cancelTooFast;
    private int tooFastCheck;
    private int tooFastMax;

    private boolean useCooldown;

    private boolean cancelCaps;
    private int maxCaps;

    public SpamManager(boolean use, boolean cancelDuplicate, int duplicateSensitivity, boolean cancelTooFast, int tooFastCheck, int tooFastMax, boolean useCooldown, boolean cancelCaps, int maxCaps){
        this.use = use;
        this.cancelDuplicate = cancelDuplicate;
        this.duplicateSensitivity = duplicateSensitivity;
        this.cancelTooFast = cancelTooFast;
        this.tooFastCheck = tooFastCheck;
        this.tooFastMax = tooFastMax;
        this.useCooldown = useCooldown;
        this.cancelCaps = cancelCaps;
        this.maxCaps = maxCaps;
    }

    public boolean isUsed() {
        return use;
    }

    public boolean canCancelDuplicate() {
        return cancelDuplicate;
    }

    public int getDuplicateSensitivity() {
        return duplicateSensitivity;
    }

    public boolean canCancelTooFast() {
        return cancelTooFast;
    }

    public int getTooFastCheck() {
        return tooFastCheck;
    }

    public int getTooFastMax() {
        return tooFastMax;
    }

    public boolean useCooldown() {
        return useCooldown;
    }

    public boolean canCancelCaps() {
        return cancelCaps;
    }

    public int getMaxCaps() {
        return maxCaps;
    }
}
