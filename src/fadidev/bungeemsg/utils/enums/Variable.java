package fadidev.bungeemsg.utils.enums;

public enum Variable {

    RECEIVER("%receiver%"),
    TYPE("%type%"),
    CMD("%cmd%"),
    SERVER_SENDER("%server-sender%"),
    SUGGEST_PLAYER("%suggest-player%"),
    SERVER_RECEIVER("%server-receiver%"),
    SENDER("%sender%"),
    MSG("%msg%"),
    MUTED("%muted%"),
    SERVER_NAME("%server-name%"),
    IGNORED("%ignored%"),
    REASON("%reason%"),
    SERVER_REPORTED("%server-reported%"),
    REPORTED("%reported%"),
    SECOND_GRAMMER("%second-grammer%"),
    LEFT("%left%"),
    TOGGLED("%toggled%"),
    RANK("%rank%"),
    AMOUNT("%amount%"),
    REPORTER("%reporter%"),
    DATE("%date%"),
    INDEX("%index%");

    private String stringVariable;

    Variable(String stringVariable){
        this.stringVariable = stringVariable;
    }

    public String getVariable() {
        return stringVariable;
    }
}
