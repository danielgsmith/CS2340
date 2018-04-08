package app.haven.haven.Model.shelters;

import com.google.android.gms.maps.model.LatLng;

/**
 * Shelter object
 */
public class Shelter {

    private String shelterName;
    private Capacity capacity;
    private double longitude;
    private double latitude;
    private String phone;
    private String address;
    private int uniqueKey;
    private String pushKey;
    private String notes;
    private int singleOccupancy;
    private int groupOccupancy;
    private Restrictions restrictions;


    /**
     * Default constructor, needed for firebase
     */
    public Shelter(){
    }

    /**
     * Makes a Shelter
     * @param name name of the shelter
     * @param capacity what they can hold and how many
     * @param restrictions who they take
     * @param longitude the longitude of the shelter
     * @param latitude the latitude of the shelter
     * @param phone the phone number
     * @param address their address
     * @param uniqueKey a unique key saved for them
     * @param notes any other notes about it
     */
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
        this.restrictions = restrictions;
    }

    /**
     * gets the restrictions for the shelter
     * @return the restrictions
     */
    public Restrictions getRestrictions() { return restrictions; }

    /**
     * sets the restrictions for the shelter
     * @param restrictions who they allow
     */
    public void setRestrictions(Restrictions restrictions) { this.restrictions = restrictions; }

    /**
     * gets shelters name
     * @return shelters name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * gets address
     * @return the address
     */
    public CharSequence getAddress(){
        return address;
    }

    /**
     * returns capacity
     * @return the capacity
     */
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

    /**
     * returns phone
     * @return phone number
     */
    public String getPhone(){
        return phone;
    }

    /**
     * tells if they accept males
     * @return boolean true if accepts false if not
     */
    public boolean getAcceptsMale(){
        return restrictions.isMen();
    }

    /**
     * tells if they accept females
     * @return boolean true if accepts false if not
     */
    public boolean getAcceptsFemale(){
        return restrictions.isWomen();
    }

    /**
     * gets longitude
     * @return double longitude
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * get latitude
     * @return double latitude
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * set shelter name
     * @param shelterName String shelterName
     */
    public void setShelterName(String shelterName){
        this.shelterName = shelterName;
    }

    /**
     * sets address
     * @param address String address
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * sets phone number
     * @param phone String phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * sets longitude
     * @param longitude double longitude
     */
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * sets latitude
     * @param latitude double latitude
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    /**
     * gets a LatLng for use of google map
     * @return new LatLng
     */
    public LatLng getLatLng() {return new LatLng(latitude, longitude); }

    /**
     * gets shelters unique key
     * @return int unique key
     */
    public int getUniqueKey() {
        return uniqueKey;
    }

    /**
     * sets a new key
     * @param uniqueKey int unique key
     */
    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    /**
     * gets shelters notes
     * @return String notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * sets shelters notes
     * @param notes String notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * gets the shelters single occupancy
     * @return int occupancy
     */
    public int getSingleOccupancy() {
        return singleOccupancy;
    }

    /**
     * sets shelters single occupancy
     * @param singleOccupancy int occupancy
     */
    public void setSingleOccupancy(int singleOccupancy) {
        this.singleOccupancy = singleOccupancy;
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
                ", singleOccupancy=" + singleOccupancy +
                ", restrictions=" + restrictions +
                '}';
    }

    /**
     * gets the shelters pushkey, used for firebase
     * @return String pushkey
     */
    public String getPushKey() {
        return pushKey;
    }

    /**
     * sets shelters pushkey, for firebase
     * @param pushKey String pushkey
     */
    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
