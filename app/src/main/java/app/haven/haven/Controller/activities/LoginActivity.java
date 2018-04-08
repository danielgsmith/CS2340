package app.haven.haven.Controller.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.UserRecord;
//import com.google.firebase.auth.UserRecord.CreateRequest;
//import com.google.firebase.auth.UserRecord.UpdateRequest;

import java.util.ArrayList;
import java.util.List;

import app.haven.haven.Model.User;
import app.haven.haven.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String TAG = "EmailPassword";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private FirebaseAuth mAuth;

    private FirebaseUser mFireUser;

    FirebaseDatabase database;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mCancelButtonView;
    private boolean canceledLogin;
    public boolean signedIn;
    private String emailAddress = "";
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");
    //private FirebaseAuth mAuth;

    private int numLoginAttempts; //tracks number of login attempts

    private boolean addLoginAttempt = false;

    private final boolean[] locked = new boolean[1];
    private boolean noAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Checks if device is still logged into Firebase, if so skip login page and go to Sidebar
        if(FirebaseAuth.getInstance().getCurrentUser() != null && !FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
            finish();
            Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
            startActivity(i);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        //Password enter
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Sets up Sign in button and adds listener to login
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addLoginAttempt = true;
                attemptLogin();
            }
        });

        //Gets views
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //Button that comes up when you try to log in, if clicked cancels and moves back to login screen
        mCancelButtonView = (Button) findViewById(R.id.cancel_action_button);
        mCancelButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelLogin();
            }
        });

        //Button that moves to create account screen
        View mRegisterButtonView = (Button) findViewById(R.id.create_account_button);
        mRegisterButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        // Reset password Button
        Button mReset = (Button) findViewById(R.id.forgot_password_button);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        //Logs in anonymously
        Button mGuestButtonView = (Button) findViewById(R.id.continue_as_guest_button);
        mGuestButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAnonymously();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    //If the cancel button is clicked return to main screen and don't give an error
    private void cancelLogin() {
        showProgress(false);
        Thread.interrupted();
        canceledLogin = true;
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS) || shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS, ACCESS_FINE_LOCATION}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS, ACCESS_FINE_LOCATION}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (!TextUtils.isEmpty(mEmailView.getText().toString()))
            getLocked(email);
        Log.d("Email" , email);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Required");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            //FirebaseAuth.getInstance().signOut();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            showProgress(true);
        }
    }

    //Checks if Email is valid by having an @
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    //Checks if password is >= 6 characters long
    private boolean isPasswordValid(CharSequence password) {
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mCancelButtonView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Nickname.NAME,
                //ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private DatabaseReference mDataRef;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                signIn(mEmail, mPassword);
                if (signedIn) {
                    return true;
                }
                // Simulate network access.
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                return false;
            }

            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if(canceledLogin) { //if login was canceled set focus to password and try again
                mPasswordView.requestFocus();
                canceledLogin = false;
            } else if ((success || signedIn) && !locked[0]) { //if user is signed in, move to MainPageActivity class
                Log.d("About", "" + locked[0]);
                final String[] firebaseID = new String[1];
                //final User[] lockOutUser = new User[1];
                final String emailWithout = mEmail.replace(".", "|");
                signIn(mEmail, mPassword);
                addLoginAttempt = true;
                mDataRef = FirebaseDatabase.getInstance().getReference();
                mDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (addLoginAttempt) {
                            firebaseID[0] = dataSnapshot.child("emailtouid").child(emailWithout).getValue(String.class);
                            //Log.w("String", firebaseID[0]);
                            mDataRef.child("users").child(firebaseID[0]).child("numLoginAttempts").setValue(0);
                        }
                        addLoginAttempt = false;
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                finish();
                Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
                startActivity(i);
            } else { //Tell them incorrect and put focus on password
                if (!noAccount) {
                    addLoginAttempt = true;
                    mDataRef = FirebaseDatabase.getInstance().getReference();
                    final String[] firebaseID = new String[1];
                    final User[] lockOutUser = new User[1];
                    final String emailWithout = mEmail.replace(".", "|");
                    mDataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (addLoginAttempt) {
                                firebaseID[0] = dataSnapshot.child("emailtouid").child(emailWithout).getValue(String.class);
                                //Log.w("String", firebaseID[0]);

                                lockOutUser[0] = dataSnapshot.child("users").child(firebaseID[0]).getValue(User.class);

                                lockOutUser[0].increaseNumLoginAttempts();

                                mDataRef.child("users").child(firebaseID[0]).child("numLoginAttempts").setValue(lockOutUser[0].getNumLoginAttempts());
                                signIn("1@gmail.com", "nullll");
                                if (lockOutUser[0].getAccountType() != 1) {
                                    if (lockOutUser[0].getNumLoginAttempts() >= 3) {
                                        addLoginAttempt = false;
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle("Account Disabled")
                                            .setMessage("Your account has been disabled, please email us to reset your account")
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                    closeContextMenu();
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();*/
                                        Toast.makeText(LoginActivity.this, "Account Disabled",
                                                Toast.LENGTH_LONG).show();

                                        mDataRef.child("users").child(firebaseID[0]).child("lockedOut").setValue(true);

                                    } else if (lockOutUser[0].getNumLoginAttempts() == 2) {
                                        addLoginAttempt = false;

                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("Reset Password")
                                                .setMessage("One more incorrect password will disable your account, check your email and reset your password")
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        closeContextMenu();
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();

                                        mAuth.sendPasswordResetEmail(lockOutUser[0].getEmail());
                                    } else {
                                        mPasswordView.setError("Invalid Email or Password");
                                        //Log.w("FAIL", "SUC");
                                        addLoginAttempt = false;
                                    }
                                    //lockOutUser[0] = null;
                                } else {
                                    mPasswordView.setError("Invalid Email or Password");
                                    //Log.w("FAIL", "SUC");
                                    addLoginAttempt = false;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                //locked[0] = false;
                //mPasswordView.setError("Incorrect email"); //or password
                mPasswordView.requestFocus();
                //mAuth.signOut();

                Log.v(TAG, "Failed");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * signed into firebase
     * @param email the email
     * @param password the password
     */
    public void signIn(final String email, String password) {
        Log.d(TAG, "signIn:" + email);
        //getLocked(email);
        Log.w(TAG, "Locked" + locked[0]);
            /*if (!validateForm()) {
                return;
            }*/

        //showProgressDialog();

        // [START sign_in_with_email]
        checkEmailUse();
        if (!noAccount) {
            if (!locked[0]) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    signedIn = true;
                                    Log.d("Locked", "" + locked[0]);
//                                    if (locked[0]){
//                                        mAuth.signOut();
//                                        Log.d("Locked", "Signed Out");
//                                    }
                                    //updateUI(user);
                                } else {
                                    checkEmailUse();
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
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
        }
    }

    //Signs in to FireBase Authentication Anonymously
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
                            startActivity(i);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void checkEmailUse() {
        mAuth.fetchProvidersForEmail(mEmailView.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                        boolean check = !task.getResult().getProviders().isEmpty();

                        if(!check) {
                            Toast.makeText(LoginActivity.this, "No account associated with this email.",
                                    Toast.LENGTH_SHORT).show();
                            noAccount = true;
                        } else {
                            //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            noAccount = false;
                        }
                    }
                });
    }

    private void resetPassword(){

        Log.w("Forgot Button:", "Clicked");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password? ");

        //final EditText input = new EditText(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.popup_reset_password, (ViewGroup) findViewById(android.R.id.input), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(viewInflated);

        //builder.setMessage("Would you like to reset you password?");
        // Set up the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(input.getText().toString())) {
                    emailAddress = input.getText().toString();
                    sendResetEmail();
                } else {
                    Toast.makeText(LoginActivity.this, "No email detected.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendResetEmail(){
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Reset Email sent.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                        } else {
                            Toast.makeText(LoginActivity.this, "Reset Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void getLocked(String mEmail){
        final DatabaseReference mDataRef;
        final String emailWithout = mEmail.replace(".", "|");
        final String[] firebaseID = new String[1];
        //final User[] lockOutUser = new User[1];
        addLoginAttempt = true;
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (addLoginAttempt) {
                    firebaseID[0] = dataSnapshot.child("emailtouid").child(emailWithout).getValue(String.class);
                    if (firebaseID[0] != null) {
                        Log.w("String", firebaseID[0]);
                        locked[0] = dataSnapshot.child("users").child(firebaseID[0]).child("lockedOut").getValue(Boolean.class);
                        Log.w("Locked", emailWithout + locked[0]);
                    /*if (!locked[0]) {
                        lockOutUser[0] = dataSnapshot.child("users").child(firebaseID[0]).getValue(User.class);
                        mDataRef.child("users").child(firebaseID[0]).child("numLoginAttempts").setValue(0);
                    }*/
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        /*if (locked[0]) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Account Disabled")
                    .setMessage("Your account has been disabled, please email us to reset your account")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            closeContextMenu();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }*/
    }
}

