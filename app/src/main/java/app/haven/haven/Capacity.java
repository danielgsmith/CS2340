package app.haven.haven;

/**
 * Created by Matt on 2/26/2018.
 */

public class Capacity {

    public enum CapacityType {
        SPACES(true, false),
        FAMILY_ROOMS(false, true),
        FAMILY_AND_SINGLE_ROOMS(true, true),
        APARTMENTS(false, true),
        UNLISTED(false, false);

        private boolean individualRooms;
        private boolean groupRooms;
        private CapacityType(boolean individualRooms, boolean groupRooms) {
            this.individualRooms = individualRooms;
            this.groupRooms = groupRooms;
        }

        public boolean hasIndividualRooms() {
            return individualRooms;
        }

        public boolean hasGroupRooms() {
            return groupRooms;
        }
    }

    private CapacityType capacityType;
    int individualRoom;
    int groupRoom;

    public Capacity(CapacityType capacityType, int individualRoom, int groupRoom) {
        this.capacityType = capacityType;
        this.individualRoom = individualRoom;
        this.groupRoom = groupRoom;
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

    /***
     *  Sets the correct capacity to slots
     *  If the capacityType allows for individual and group slots
     *  then it prioritizes individual slots
     * @param slots the number to set
     */
    public void setCapacity(int slots) {
        if (capacityType.individualRooms) {
            setCapacity(individualRoom, slots);
        } else if (capacityType.groupRooms) {
            setCapacity(slots, groupRoom);
        }
    }

    /***
     * Sets the capacity for individual and group slots
     * @param individualSlots the individual slots to be set
     * @param groupSlots the group slots to be set
     */
    public void setCapacity(int individualSlots, int groupSlots) {
        this.individualRoom = individualSlots;
        this.groupRoom = groupSlots;
    }

    /***
     * also prioritizes individual room if not specifying
     * @return the capacity
     */
    public int getCapacity() {
        return capacityType.individualRooms ? individualRoom : groupRoom;
    }

    public int getIndividualCapacity() {
        return individualRoom;
    }

    public int getGroupCapacity() {
        return groupRoom;
    }

    public static Capacity parseFromString(String s) {
        s = s.toLowerCase().replace("^[., ]*", "")
                .replace("\n", "").replace("\"","");
        if (s == null || s.isEmpty()) {
            return new Capacity(CapacityType.UNLISTED, -1, -1);
        }
        try {
            int num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            if (s.contains("apartment")) {
                s = s.replace("^[a-z_]*", "");
                return s.isEmpty() ? new Capacity(CapacityType.UNLISTED, -1, -1)
                        : new Capacity(CapacityType.APARTMENTS, Integer.parseInt(s));
            }
            s = s.replace("^[a-z_]*fam[a-z_]*","f")
                    .replace("^[a-z_]*sing[a-z_]*", "s");
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
        return null;
    }
}
