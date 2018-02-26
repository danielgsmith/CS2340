package app.haven.haven;

public class Shelter {

    private String shelterName;
    // 0 - Spaces
    // 1 - family rooms
    // 2 - single rooms
    // 3 - family and single
    // 4 Apartments
    private long capacityType;
    private int capacity;
    private int subCapacity; // Used if place has family rooms and singles
    private double longitude;
    private double latitude;
    private String phone;
    private String address;
    private int uniqueKey;
    private String notes;
    private int occupancy;
    private Restrictions restrictions;



    Shelter(){
        // Default constructor required for calls for database
    }

    Shelter(String name, long capacityType, int capacity, int subCapacity, Restrictions restrictions,
            double longitude, double latitude, String phone, String address, int uniqueKey, String notes) {
        this.shelterName = name;
        this.capacityType = capacityType;
        this.capacity = capacity;
        this.subCapacity = subCapacity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.address = address;
        this.uniqueKey = uniqueKey;
        this.notes = notes;
        this.occupancy = 0;
        this.restrictions = restrictions;
    }

    public Restrictions getRestrictions() { return restrictions; }

    public void setRestrictions(Restrictions restrictions) { this.restrictions = restrictions; }

    public String getShelterName() {
        return shelterName;
    }

    public String getAddress(){
        return address;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getPhone(){
        return phone;
    }

    public boolean getAcceptsMale(){
        return restrictions.isMen();
    }

    public boolean getAcceptsFemale(){
        return restrictions.isWomen();
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setShelterName(String shelterName){
        this.shelterName = shelterName;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(long capacityType) {
        this.capacityType = capacityType;
    }

    public int getSubCapacity() {
        return subCapacity;
    }

    public void setSubCapacity(int subCapacity) {
        this.subCapacity = subCapacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }
}
