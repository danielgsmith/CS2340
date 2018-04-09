package app.haven.haven.Model.shelters;

/**
 * Restrictions Object
 */
public enum Restrictions {

    WOMEN_CHILDREN("women/children", false, true, false, false, false, false, false, true, false),
    MEN("men", true, false, false, false, false, false, false, false, true),
    FAMILIES("families", true, true, true, false, false, false, false, false, false),
    FAMILIES_CHILDREN_UNDER_5("familiesw/childrenunder5", true, true, true, false, true, false, false, true, false),
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
    private boolean allowsChildren;
    private boolean adultsOnly;

    Restrictions(String parseFrom, boolean men, boolean women, boolean familyOnly,
                 boolean newbornsOnly, boolean childrenUnder5Only, boolean youngAdultsOnly,
                 boolean veteransOnly, boolean allowsChildren, boolean adultsOnly) {
        this.parseFrom = parseFrom;
        this.men = men;
        this.women = women;
        this.familyOnly = familyOnly;
        this.newbornsOnly = newbornsOnly;
        this.childrenUnder5Only = childrenUnder5Only;
        this.youngAdultsOnly = youngAdultsOnly;
        this.veteransOnly = veteransOnly;
        this.allowsChildren = allowsChildren;
        this.adultsOnly = adultsOnly;
    }

    /**
     * Parses restrictions from strings
     * @param text the text being parsed
     * @throws IllegalArgumentException if null, empty or not a valid Restriction
     * @return restrictions
     */
    public static Restrictions parseFrom(String text) {
        if (text == null || text.length() == 0) throw new IllegalArgumentException("Empty argument");
        text = text.replace("\n", "")
                .replace(" ", "").toLowerCase();
        for (Restrictions restriction : values()) {
            if (restriction.parseFrom.equals(text)) {
                return restriction;
            }
        }
        throw new IllegalArgumentException("No enum found for: " + text);
    }

    /**
     * are men allowed
     * @return boolean true if man false otherwise
     */
    public boolean isMen() {
        return men;
    }

    /**
     * are women allowed
     * @return boolean true if woman false otherwise
     */
    public boolean isWomen() {
        return women;
    }

    /**
     * are families allowed
     * @return boolean true if true false otherwise
     */
    public boolean isFamilyOnly() {
        return familyOnly;
    }

    /**
     * are newborns allowed
     * @return boolean true if true false otherwise
     */
    public boolean isNewbornsOnly() {
        return newbornsOnly;
    }

    /**
     * are children allowed
     * @return boolean true if true false otherwise
     */
    public boolean isChildrenUnder5Only() {
        return childrenUnder5Only;
    }

    /**
     * are young adults allowed
     * @return boolean true if true false otherwise
     */
    public boolean isYoungAdultsOnly() {
        return youngAdultsOnly;
    }

    /**
     * are veterans allowed
     * @return boolean true if true false otherwise
     */
    public boolean isVeteransOnly() {
        return veteransOnly;
    }

    /**
     * are children allowed
     * @return boolean true if true false otherwise
     */
    public boolean isAllowsChildren() {
        return allowsChildren;
    }

    /**
     * are adults allowed
     * @return boolean true if true false otherwise
     */
    public boolean isAdultsOnly() { return adultsOnly; }
}
