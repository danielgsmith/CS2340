package app.haven.haven.Model.shelters;

/**
 * Capacity object
 */
public class Capacity {
    public enum CapacityType {
        SPACES(true, false, " spaces", null),
        FAMILY_ROOMS(false, true, null, " family rooms"),
        FAMILY_AND_SINGLE_ROOMS(true, true, " single rooms", " family rooms"),
        APARTMENTS(false, true, null, " apartments"),
        UNLISTED(false, false, null, null);

        private static String[] stringValues;
        private boolean individualRooms;
        private boolean groupRooms;
        private String individualSuffix;
        private String groupSuffix;

        CapacityType(boolean individualRooms, boolean groupRooms, String individualSuffix, String groupSuffix) {
            this.individualRooms = individualRooms;
            this.groupRooms = groupRooms;
            this.individualSuffix = individualSuffix;
            this.groupSuffix = groupSuffix;
        }

        /**
         * get individual string suffix
         * @return individualSuffix
         */
        public String getIndividualSuffix() {
            return individualSuffix;
        }

        /**
         * gets group suffix string
         * @return groupSuffix
         */
        public String getGroupSuffix() {
            return groupSuffix;
        }

        /**
         * gets boolean for individual rooms
         * @return individualRooms
         */
        public boolean hasIndividualRooms() {
            return individualRooms;
        }

        /**
         * gets boolean for groupRooms
         * @return groupRooms
         */
        public boolean hasGroupRooms() {
            return groupRooms;
        }

        /**
         * converts values into a string array
         * @return a string array with uppercase first letter of values
         */
        public static String[] stringValues() {
            if (stringValues == null) {
                stringValues = new String[values().length];
                for (int i = 0; i < values().length; i++) {
                    stringValues[i] = values()[i].toString().toLowerCase();
                    stringValues[i] = Character.toUpperCase(stringValues[i].charAt(0))
                            + stringValues[i].substring(1);
                }
            }
            return stringValues;
        }
    }

    private CapacityType capacityType;
    private int individualCapacity;
    private int groupCapacity;
    private int individualOccupancy;
    private int groupOccupancy;

    /**
     * gets an int for a user's individual occupancy
     * @return individualOccupancy
     */
    public int getIndividualOccupancy() {
        return individualOccupancy;
    }

    /**
     * sets the value of individualOccupancy to new individualOccupancy
     * @param individualOccupancy the new occupancy
     */
    public void setIndividualOccupancy(int individualOccupancy) {
        this.individualOccupancy = individualOccupancy;
    }

    /**
     * gets the int for group occupancy
     * @return groupOccupancy
     */
    public int getGroupOccupancy() {
        return groupOccupancy;
    }

    /**
     * sets the value of groupOccupancy to new groupOccupancy
     * @param groupOccupancy the new groupOccupancy
     */
    public void setGroupOccupancy(int groupOccupancy) {
        this.groupOccupancy = groupOccupancy;
    }

    /**
     * sets the value of individualCapacity to new individualCapacity
     * @param individualCapacity the new individualCapacity
     */
    public void setIndividualCapacity(int individualCapacity) {
        this.individualCapacity = individualCapacity;
    }

    /**
     * sets the value of groupCapacity to new groupCapacity
     * @param groupCapacity the new groupCapacity
     */
    public void setGroupCapacity(int groupCapacity) {
        this.groupCapacity = groupCapacity;
    }

    /**
     * Needed for firebase, default capacity
     */
    public Capacity(){
//        this(CapacityType.SPACES, 0);
    }

    private Capacity(CapacityType capacityType, int individualRoom, int groupRoom) {
        this.capacityType = capacityType;
        this.individualCapacity = individualRoom;
        this.groupCapacity = groupRoom;
    }

    /**
     * constructs a new Capacity Object
     * @param capacityType the type of capacity
     * @param slots the number of slots
     */
    public Capacity(CapacityType capacityType, int slots) {
        this(capacityType, capacityType.individualRooms ? slots : 0, capacityType.groupRooms ? slots : 0);
    }

    /**
     * creates a new capacity object
     * @param capacityType the type of capacity
     */
    public Capacity(CapacityType capacityType) {
        this(capacityType, 0, 0);
    }

    /**
     * gets a Capacity type
     * @return capacity type
     */
    public CapacityType getCapacityType() {
        return capacityType;
    }

    /**
     * sets the capacity type to the new capacityType
     * @param capacityType the new capacityType
     */
    public void setCapacityType(CapacityType capacityType) {
        this.capacityType = capacityType;
    }

//    /***
//     *  Sets the correct capacity to slots
//     *  If the capacityType allows for individual and group slots
//     *  then it prioritizes individual slots
//     * @param slots the number to set
//     */
//    public void setCapacity(int slots) {
//        if (capacityType.individualRooms) {
//            setCapacity(individualCapacity, slots);
//        } else if (capacityType.groupRooms) {
//            setCapacity(slots, groupCapacity);
//        }
//    }

//    /***
//     * Sets the capacity for individual and group slots
//     * @param individualSlots the individual slots to be set
//     * @param groupSlots the group slots to be set
//     */
//    public void setCapacity(int individualSlots, int groupSlots) {
//        this.individualCapacity = individualSlots;
//        this.groupCapacity = groupSlots;
//    }

//    /***
//     * also prioritizes individual room if not specifying
//     * @return the capacity
//     */
//    public int getCapacity() {
//        return capacityType.individualRooms ? individualCapacity : groupCapacity;
//    }

    /**
     * gets the int individualCapacity
     * @return individualCapacity
     */
    public int getIndividualCapacity() {
        return individualCapacity;
    }

    /**
     * gets the int groupCapacity
     * @return groupCapacity
     */
    public int getGroupCapacity() {
        return groupCapacity;
    }

    /**
     * parses a text file
     * @param s a string to parse
     * @return Capacity representing shelter information
     */
    public static Capacity parseFromString(String s) {
        s = s.toLowerCase().replaceAll("[., ]*", "")
                .replaceAll("\n", "").replaceAll("\"","");
        if (s == null || s.isEmpty()) {
            return new Capacity(CapacityType.UNLISTED, -1, -1);
        }
        try {
            return new Capacity(CapacityType.SPACES, Integer.parseInt(s));
        } catch (NumberFormatException e) {
            if (s.contains("apartment")) {
                s = s.replaceAll("[a-z_]*", "");
                return s.isEmpty() ? new Capacity(CapacityType.UNLISTED, -1, -1)
                        : new Capacity(CapacityType.APARTMENTS, Integer.parseInt(s));
            }
            s = s.replaceAll("[a-z_]*fam[a-z_]*","f")
                    .replaceAll("[a-z_]*sing[a-z_]*", "s");
            if (s.contains("f") && s.contains("s")) {
                //this could be smarter but im sick of this.
                String[] split = s.replace("s", "").split("f");
                return new Capacity(CapacityType.FAMILY_AND_SINGLE_ROOMS,
                        Integer.parseInt(split[1]), Integer.parseInt(split[0]));
            } else if (s.contains("f")) {
                return new Capacity(CapacityType.FAMILY_ROOMS, Integer.parseInt(s.replace("f", "")));
            } else if (s.contains("s")) {
                return new Capacity(CapacityType.SPACES, Integer.parseInt(s.replace("s", "")));
            } else if (s.isEmpty()){
                return new Capacity(CapacityType.UNLISTED, -1, -1);
            } else {
                return new Capacity(CapacityType.SPACES, Integer.parseInt(s));
            }
        }
    }

    /**
     * converts Capacity object to a meaningful string
     * @return String
     */
    @Override
    public String toString() {
        return "Capacity{" +
                "capacityType=" + capacityType +
                (capacityType.individualRooms ? ", individualCapacity=" + individualCapacity : "") +
                (capacityType.groupRooms ? ", groupCapacity=" + groupCapacity : "") +
                '}';
    }

    /**
     * converts to a meaningful string
     * @return charSequence
     */
    public CharSequence toDetailedString() {
        return (capacityType.individualRooms ? individualCapacity - individualOccupancy + capacityType.individualSuffix : "") +
                (capacityType.groupRooms && capacityType.individualRooms ? "\n" : "") +
                (capacityType.groupRooms ? groupCapacity - groupOccupancy + capacityType.groupSuffix : "");
    }

    /**
     * creates a new string without space and room
     * @param space the space
     * @param room the room
     * @return a string containing the new information
     */
    public String subtractDetailedString(int space, int room) {
        return (capacityType.individualRooms ? individualCapacity - individualOccupancy - space + capacityType.individualSuffix : "") +
                (capacityType.groupRooms && capacityType.individualRooms ? "\n" : "") +
                (capacityType.groupRooms ? groupCapacity - groupOccupancy - room + capacityType.groupSuffix : "");
    }

}