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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

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

        mFirstName = findViewById(R.id.reg_users_first_name);
        mLastName = findViewById(R.id.reg_users_last_name);
        mEmail = findViewById(R.id.reg_user_username);
        mPassword = findViewById(R.id.reg_user_password);
        mConfirmPassowrd = findViewById(R.id.reg_user_confirm_password);
        Button mCreateAccountButton = (Button) findViewById(R.id.button_reg_create_account);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

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

        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        String confirm = mConfirmPassowrd.toString();

        boolean valid = validateForm();
        View focusView = null;


        if (!valid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                signIn(email, password);
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

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else if (mPassword.toString().equals(mConfirmPassowrd.toString())) {
          mPassword.setError("Passwords must match.");
          valid = false;
        } else {
            mPassword.setError(null);
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
