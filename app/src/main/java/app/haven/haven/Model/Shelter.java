package app.haven.haven.Model;

import app.haven.haven.Model.Capacity;
import app.haven.haven.Model.Restrictions;

public class Shelter {

    private String shelterName;
    private Capacity capacity;
    private double longitude;
    private double latitude;
    private String phone;
    private String address;
    private int uniqueKey;
    private String notes;
    private int occupancy;
    private Restrictions restrictions;



    public Shelter(){
        // Default constructor required for calls from database
    }

    public Shelter(String name, Capacity capacity, Restrictions restrictions, double longitude,
            double latitude, String phone, String address, int uniqueKey, String notes) {
        this.shelterName = name;
        this.capacity = capacity;
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

    public Capacity getCapacity() { return capacity; }

//    public Capacity.CapacityType getCapacityType() { return capacity.getCapacityType(); }
//
//    public int getCapacity(){
//        return capacity.getCapacity();
//    }
//
//    public int getIndividualCapacity() { return capacity.getIndividualCapacity(); }
//
//    public int getGroupCapacity() { return capacity.getGroupCapacity(); }

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

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }


    @Override
    public String toString() {
        return "Shelter{" +
                "shelterName='" + shelterName + '\'' +
                ", capacity=" + capacity +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", uniqueKey=" + uniqueKey +
                ", notes='" + notes + '\'' +
                ", occupancy=" + occupancy +
                ", restrictions=" + restrictions +
                '}';
    }
}