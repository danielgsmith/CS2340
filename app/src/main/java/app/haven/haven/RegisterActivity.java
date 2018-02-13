package app.haven.haven;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    //Makes Firebase parts
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassowrd;
    private Spinner userSpinner;
    //private Button mCreatAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Makes Spinner with Invisible first item so it has a prompt on it
        String[] userTypes = new String[]{"User","Admin"};
        userSpinner = findViewById(R.id.spinner_user_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setPrompt("Choose account type: ");
        userSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        //Grabs Text fields from GUI
        mFirstName = findViewById(R.id.reg_users_first_name);
        mLastName = findViewById(R.id.reg_users_last_name);
        mEmail = findViewById(R.id.reg_user_username);
        mPassword = findViewById(R.id.reg_user_password);
        mConfirmPassowrd = findViewById(R.id.reg_user_confirm_password);

        //Create Account Button
        Button mCreateAccountButton = (Button) findViewById(R.id.button_reg_create_account);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        //Back to sign-in button
        Button mBackToSignIn = (Button) findViewById(R.id.button_back_sign_in);
        mBackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        //Cancel Button
        Button mCancel = (Button) findViewById(R.id.button_reg_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        //Makes Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void createAccount() {
        //spinnerError = (TextView) userSpinner.getSelectedItem();
        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);
        mFirstName.setError(null);
        mLastName.setError(null);
        mConfirmPassowrd.setError(null);
        ((TextView)userSpinner.getSelectedView()).setError(null);

        //Saved Strings
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        //final String firstName = mFirstName.getText().toString();
        //final String lastName = mLastName.getText().toString();

        String confirm = mConfirmPassowrd.toString();

        //Checks if all parts are filled and correct
        boolean valid = validateForm();
        //where the focus is
        View focusView = null;


        if (!valid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
            return;
        } else {
            //Creates a Email/Password user Authentication in FireBase or says failed
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                signIn(email, password);
                                createUser();
                                finish();
                                Intent i = new Intent(getApplicationContext(), SideBar.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    private void createUser(){
        //Creates user in database
        final String email = mEmail.getText().toString().replaceAll("\\s+","");
        final String firseName = mFirstName.getText().toString().replaceAll("\\s+","");
        final String lastName = mLastName.getText().toString().replaceAll("\\s+","");
        //final String email =
        final String type = userSpinner.toString();

        database = FirebaseDatabase.getInstance();
        User user = new User(firseName, lastName, email, type);

        


    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private boolean isEmailValid(String username) {
        //TODO: Replace this with your own logic
        return username.contains("@");
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        /*if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();*/
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        //Checks if email is there and has an @ sign
        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        } else { //Removes errors
            mEmail.setError(null);
        }

        //checks if Password is there and if passwords match
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else if (!mPassword.toString().equals(mConfirmPassowrd.toString())) {
          mConfirmPassowrd.setError("Passwords must match.");
          valid = false;
        } else { //removes errors
            mPassword.setError(null);
            mConfirmPassowrd.setError(null);
        }

        //Checks if First Name field is written to and not just empty or spaces
        String firstName = mFirstName.getText().toString();
        if(firstName.isEmpty() || firstName.replaceAll("\\s+","").isEmpty()){
            mFirstName.setError("Required");
            valid = false;
        } else { //Removes errors
            mFirstName.setError(null);
        }

        //Checks if Last Name field is written to and not just empty or spaces
        String lastName = mLastName.getText().toString();
        if(lastName.isEmpty() || lastName.replaceAll("\\s+","").isEmpty()){
            mLastName.setError("Required");
            valid = false;
        } else { //Removes errors
            mLastName.setError(null);
        }

        //Checks if spinner has been selected
        if (userSpinner.getSelectedItemId() == -1){
            ((TextView)userSpinner.getSelectedView()).setError("Required");
            //Log.v("Spinner", "" + userSpinner.getSelectedItemId());
            valid = false;
        } else { //Removes errors
            ((TextView)userSpinner.getSelectedView()).setError(null);
        }


        return valid;
    }

    /**
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    } */


}
