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
    private String telephoneNumber;

    /**
     * default constructor
     */
    public User() {
        // Default constructor required for calls for database
    }

    /**
     * Constructor for user
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @param accountType the account type
     */
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

    /**
     * Setter for firstname
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for lastname
     * @param lastName the last name
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Setter for email
     * @param email the new email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Setter for accountType
     * @param accountType the accountType
     */
    public void setAccountType(long accountType){
        this.accountType = accountType;
    }

    /**
     * incrases numLoginAttemps by 1
     */
    public void increaseNumLoginAttempts() {
        numLoginAttempts++;
    }

    /**
     * Setter for numLoginAttampts
     * @param numLoginAttempts the number of login attempts
     */
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

    /**
     * Setter for locked out
     * @param lockedOut the boolean describing locked out
     */
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

    /**
     * Setter for curentShelterPushID
     * @param currentShelterPushID the new ID
     */
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

    /**
     * Setter for taken spaces
     * @param takenSpaces the number of taken spaces
     */
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

    /**
     * Setter for taken rooms
     * @param takenRooms the number of taken rooms
     */
    public void setTakenRooms(int takenRooms) {
        this.takenRooms = takenRooms;
    }

    /**
     * gets the telephone number
     * @return telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * sets the old telephoneNumber to the new telephoneNumber
     * @param telephoneNumber the new telephoneNumber
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

}
