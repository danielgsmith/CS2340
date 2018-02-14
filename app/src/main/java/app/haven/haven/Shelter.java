package app.haven.haven;

public class Shelter {

    private String shelterName;
    private int capacity;
    private boolean acceptsMale;
    private boolean acceptsFemale;
    private double longitude;
    private double latatude;
    private int phone;
    private String address;



    Shelter(){
        // Default constructor required for calls for database
    }

    Shelter(String name, int capacity, boolean acceptsMale, boolean acceptsFemale,
            double longitude, double latatude, int phone, String address) {
        this.shelterName = name;
        this.capacity = capacity;
        this.acceptsMale = acceptsMale;
        this.acceptsFemale = acceptsFemale;
        this.longitude = longitude;
        this.latatude = latatude;
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

    public double getLatatude(){
        return latatude;
    }

    public void setShelterName(String shelterName){
        this.shelterName = shelterName;
    }
}
