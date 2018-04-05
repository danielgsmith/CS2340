package app.haven.haven.Model;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int numLoginAttempts;
    private boolean lockedOut;
    private String currentShelterPushID;
    private int takenSpaces;
    private int takenRooms;
    /**
     * -1 form null
     * 0 for user
     * 1 for admin
     */
    private long accountType;

    public User() {
        // Default constructor required for calls for database
    }

    public User(String firstName, String lastName, String email, long accountType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
        this.numLoginAttempts = 0;
        this.lockedOut = false;
    }

    /*public String getFullName(){
        return firstName + " " + lastName;
    }*/

    /**
     * Getter for firstName
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName
     * @return lastName
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Getter for accountType
     * @return accountType
     */
    public long getAccountType() {
        return accountType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAccountType(long accountType){
        this.accountType = accountType;
    }

    public void increaseNumLoginAttempts() {
        numLoginAttempts++;
    }

    public void setNumLoginAttempts(int numLoginAttempts) {
        this.numLoginAttempts = numLoginAttempts;
    }

    /**
     * Getter for numLoginAttempts
     * @return numLoginAttempts
     */
    public int getNumLoginAttempts() {
        return numLoginAttempts;
    }

    public void setLockedOut(boolean lockedOut){
        this.lockedOut = lockedOut;
    }

    /**
     * Getter for lockedOut
     * @return lockedOut
     */
    public boolean isLockedOut() {
        return lockedOut;
    }

    /**
     * Getter for currentShelterPushID
     * @return  currentShelterPushID
     */
    public String getCurrentShelterPushID() {
        return currentShelterPushID;
    }

    public void setCurrentShelterPushID(String currentShelterPushID) {
        this.currentShelterPushID = currentShelterPushID;
    }

    /**
     * Getter for takenSpace
     * @return takenSpace
     */
    public int getTakenSpace() {
        return takenSpaces;
    }

    public void setTakenSpaces(int takenSpaces) {
        this.takenSpaces = takenSpaces;
    }

    /**
     * Getter for takenRooms
     * @return takenRooms
     */
    public int getTakenRooms() {
        return takenRooms;
    }

    public void setTakenRooms(int takenRooms) {
        this.takenRooms = takenRooms;
    }
}
