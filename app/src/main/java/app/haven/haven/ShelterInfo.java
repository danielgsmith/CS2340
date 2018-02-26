package app.haven.haven;

public class ShelterInfo {

    private String shelterName;
    private int capacity;
    private Restrictions restrictions;
    private Location location;
    private String address;
    private String phoneNumber; //If we make a PhoneNumber class we will update this

    public ShelterInfo(String shelterName, int capacity, Restrictions restrictions,
                       Location location, String address, String phoneNumber) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.location = location;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public ShelterInfo(String shelterName, int capacity, Restrictions restrictions,
                       double latitude, double longitude, String address, String phoneNumber) {
        this(shelterName, capacity, restrictions, new Location(latitude, longitude),
                address, phoneNumber);
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Restrictions getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Restrictions restrictions) {
        this.restrictions = restrictions;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ShelterInfo{" +
                "shelterName='" + shelterName + '\'' +
                ", capacity=" + capacity +
                ", restrictions=" + restrictions +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
