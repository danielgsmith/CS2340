package app.haven.haven;

/**
 * Created by Matt on 2/26/2018.
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

        public String getIndividualSuffix() {
            return individualSuffix;
        }

        public String getGroupSuffix() {
            return groupSuffix;
        }

        public boolean hasIndividualRooms() {
            return individualRooms;
        }

        public boolean hasGroupRooms() {
            return groupRooms;
        }

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

    public void setIndividualCapacity(int individualCapacity) {
        this.individualCapacity = individualCapacity;
    }

    public void setGroupCapacity(int groupCapacity) {
        this.groupCapacity = groupCapacity;
    }

    private int individualCapacity;
    private int groupCapacity;

    public Capacity(){
//        this(CapacityType.SPACES, 0);
    }

    public Capacity(CapacityType capacityType, int individualRoom, int groupRoom) {
        this.capacityType = capacityType;
        this.individualCapacity = individualRoom;
        this.groupCapacity = groupRoom;
    }

    public Capacity(CapacityType capacityType, int slots) {
        this(capacityType, capacityType.individualRooms ? slots : 0, capacityType.groupRooms ? slots : 0);
    }

    public Capacity(CapacityType capacityType) {
        this(capacityType, 0, 0);
    }

    public CapacityType getCapacityType() {
        return capacityType;
    }

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

    public int getIndividualCapacity() {
        return individualCapacity;
    }

    public int getGroupCapacity() {
        return groupCapacity;
    }

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

    @Override
    public String toString() {
        return "Capacity{" +
                "capacityType=" + capacityType +
                (capacityType.individualRooms ? ", individualCapacity=" + individualCapacity : "") +
                (capacityType.groupRooms ? ", groupCapacity=" + groupCapacity : "") +
                '}';
    }

    public String toDetailedString() {
        return (capacityType.individualRooms ? individualCapacity + capacityType.individualSuffix : "") +
                (capacityType.groupRooms && capacityType.individualRooms ? "\n" : "") +
                (capacityType.groupRooms ? groupCapacity + capacityType.groupSuffix : "");
    }

}