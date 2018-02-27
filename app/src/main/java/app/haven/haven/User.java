package app.haven.haven;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by benar on 2/12/2018.
 */

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int numLoginAttempts;
    /**
     * -1 form null
     * 0 for user
     * 1 for admin
     */
    private long accountType;

    public User() {
        // Default constructor required for calls for database
        this("test", "test", "test@email.com",12345);
    }

    public User(String firstName, String lastName, String email, long accountType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
    }

    /*public String getFullName(){
        return firstName + " " + lastName;
    }*/

    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
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

    public int getNumLoginAttempts() {
        return numLoginAttempts;
    }
}
