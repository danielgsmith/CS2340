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
    private boolean acceptsMale;
    private boolean acceptsFemale;
    private double longitude;
    private double latitude;
    private String phone;
    private String address;
    private boolean acceptsAdults;
    private boolean acceptsNewBorns;
    private boolean acceptsChildUnder5;
    private boolean acceptsFamilies;
    private boolean acceptsChild;
    private boolean acceptsVeterans;
    private int uniqueKey;
    private String notes;
    private int occupancy;



    Shelter(){
        // Default constructor required for calls for database
    }

    Shelter(String name, long capacityType, int capacity, int subCapacity, boolean acceptsMale, boolean acceptsFemale, boolean acceptsAdults, boolean acceptsNewBorns,
            boolean acceptsChildUnder5, boolean acceptsFamilies, boolean acceptsChild, boolean acceptsVeterans, double longitude,
            double latitude, String phone, String address, int uniqueKey, String notes) {
        this.shelterName = name;
        this.capacityType = capacityType;
        this.capacity = capacity;
        this.subCapacity = subCapacity;
        this.acceptsMale = acceptsMale;
        this.acceptsFemale = acceptsFemale;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.address = address;
        this.acceptsAdults = acceptsAdults;
        this.acceptsNewBorns = acceptsNewBorns;
        this.acceptsChildUnder5 = acceptsChildUnder5;
        this.acceptsFamilies = acceptsFamilies;
        this.acceptsChild = acceptsChild;
        this.acceptsVeterans = acceptsVeterans;
        this.uniqueKey = uniqueKey;
        this.notes = notes;
    }

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
        return acceptsMale;
    }

    public boolean getAcceptsFemale(){
        return acceptsFemale;
    }

    public boolean getAcceptsAdults(){
        return acceptsAdults;
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

    public void setAcceptMale(boolean acceptsMale){
        this.acceptsMale = acceptsMale;
    }

    public void setAcceptsFemale(boolean acceptsFemale){
        this.acceptsFemale = acceptsFemale;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public boolean isAcceptsNewBorns() {
        return acceptsNewBorns;
    }

    public void setAcceptsNewBorns(boolean acceptsNewBorns) {
        this.acceptsNewBorns = acceptsNewBorns;
    }

    public boolean isAcceptsChildUnder5() {
        return acceptsChildUnder5;
    }

    public void setAcceptsChildUnder5(boolean acceptsChildUnder5) {
        this.acceptsChildUnder5 = acceptsChildUnder5;
    }

    public boolean isAcceptsFamilies() {
        return acceptsFamilies;
    }

    public void setAcceptsFamilies(boolean acceptsFamilies) {
        this.acceptsFamilies = acceptsFamilies;
    }

    public boolean isAcceptsChild() {
        return acceptsChild;
    }

    public void setAcceptsChild(boolean acceptsChild) {
        this.acceptsChild = acceptsChild;
    }

    public boolean isAcceptsVeterans() {
        return acceptsVeterans;
    }

    public void setAcceptsVeterans(boolean acceptsVeterans) {
        this.acceptsVeterans = acceptsVeterans;
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
