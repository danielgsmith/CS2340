package app.haven.haven;

public enum Restrictions {

    WOMEN_CHILDREN("women/children", false, true, false, false, false, false, false, false, false),
    MEN("men", true, false, false, false, false, false, false, false, true),
    FAMILIES("families", true, true, true, false, false, false, false, false, false),
    FAMILIES_CHILDREN_UNDER_5("familiesw/childrenunder5", true, true, true, false, true, false, false, false, false),
    FAMILIES_NEWBORNS("familiesw/newborns", true, true, true, true, false, false, false, false, false),
    CHILDRENS_YOUNG_ADULTS("childrens/youngadults", true, true, false, false, false, true, false, true, false),
    ANYONE("anyone", true, true, false, false, false, false, false, false, false),
    YOUNG_ADULTS("youngadults", true, true, false, false, false, true, false, false, false),
    VETERANS("veterans", true, true, false, false, false, false, true, false, true),
    WOMEN("women", false, true, false, false, false, false, false, false, true);

    private String parseFrom;
    private boolean men;
    private boolean women;
    private boolean familyOnly;
    private boolean newbornsOnly;
    private boolean childrenUnder5Only;
    private boolean youngAdultsOnly;
    private boolean veteransOnly;
    private boolean childrenOnly;
    private boolean adultsOnly;

    Restrictions(String parseFrom, boolean men, boolean women, boolean familyOnly,
                 boolean newbornsOnly, boolean childrenUnder5Only, boolean youngAdultsOnly,
                 boolean veteransOnly, boolean childrenOnly, boolean adultsOnly) {
        this.parseFrom = parseFrom;
        this.men = men;
        this.women = women;
        this.familyOnly = familyOnly;
        this.newbornsOnly = newbornsOnly;
        this.childrenUnder5Only = childrenUnder5Only;
        this.youngAdultsOnly = youngAdultsOnly;
        this.veteransOnly = veteransOnly;
        this.childrenOnly = childrenOnly;
    }

    public static Restrictions parseFrom(String text) {
        text = text.replace("\n", "")
                .replace(" ", "").toLowerCase();
        for (Restrictions restriction : values()) {
            if (restriction.parseFrom.equals(text)) {
                return restriction;
            }
        }
        throw new IllegalArgumentException("No enum found for: " + text);
    }

    public boolean isMen() {
        return men;
    }

    public boolean isWomen() {
        return women;
    }

    public boolean isFamilyOnly() {
        return familyOnly;
    }

    public boolean isNewbornsOnly() {
        return newbornsOnly;
    }

    public boolean isChildrenUnder5Only() {
        return childrenUnder5Only;
    }

    public boolean isYoungAdultsOnly() {
        return youngAdultsOnly;
    }

    public boolean isVeteransOnly() {
        return veteransOnly;
    }

    public boolean isChildrenOnly() {
        return childrenOnly;
    }

    public boolean isAdultsOnly() { return adultsOnly; }
}
