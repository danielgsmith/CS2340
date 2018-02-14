package app.haven.haven;

public class Shelter {

    private String shelterName;
    private int capacity;
    private boolean acceptsMale;
    private boolean acceptsFemale;
    private double longitude;
    private double latitude;
    private int phone;
    private String address;



    Shelter(){
        // Default constructor required for calls for database
    }

    Shelter(String name, int capacity, boolean acceptsMale, boolean acceptsFemale,
            double longitude, double latitude, int phone, String address) {
        this.shelterName = name;
        this.capacity = capacity;
        this.acceptsMale = acceptsMale;
        this.acceptsFemale = acceptsFemale;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.address = address;
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

    public int getPhone(){
        return phone;
    }

    public boolean getAcceptsMale(){
        return acceptsMale;
    }

    public boolean getAcceptsFemale(){
        return acceptsFemale;
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

    public void setPhone(int phone) {
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
}
