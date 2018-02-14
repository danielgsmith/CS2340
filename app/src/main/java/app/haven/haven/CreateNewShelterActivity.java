package app.haven.haven;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewShelterActivity extends AppCompatActivity {

    FirebaseDatabase database;

    private EditText textShelterName;
    private EditText textShelterCapacity;
    private CheckBox checkMale;
    private CheckBox checkFemale;
    private EditText textLongitude;
    private EditText textLatitude;
    private EditText textAddress;
    private EditText textPhone;
    private Button addShelter;
    private String shelterName;
    private String address;
    private int phone;
    private boolean male;
    private boolean female;
    private double latitiude;
    private double longitude;
    private int capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shelter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add New Shelter");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textShelterName = findViewById(R.id.shelter_name);
        textShelterCapacity = findViewById(R.id.shelter_capacity);
        checkMale = findViewById(R.id.check_male);
        checkFemale = findViewById(R.id.check_female);
        textLongitude = findViewById(R.id.shelter_longitude);
        textLatitude = findViewById(R.id.shelter_latitude);
        textAddress = findViewById(R.id.shelter_address);
        textPhone = findViewById(R.id.shelter_phone);

        addShelter = (Button) findViewById(R.id.button_create_shelter);
        addShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShelter();
            }
        });

    }

    private void createShelter(){
        textShelterName.setError(null);
        checkMale.setError(null);
        checkFemale.setError(null);
        textLongitude.setError(null);
        textLatitude.setError(null);
        textAddress.setError(null);
        textPhone.setError(null);
        textShelterCapacity.setError(null);

        // Saved Strings
        shelterName = textShelterName.getText().toString();
        address = textAddress.getText().toString();
        if (!TextUtils.isEmpty(textPhone.getText()))
            phone = Integer.parseInt(textPhone.getText().toString().replaceAll("-", "")
                    .replaceAll("\\(", "").replaceAll("\\)", ""));
        male = checkMale.isSelected();
        female = checkFemale.isSelected();
        if (!TextUtils.isEmpty(textLatitude.getText().toString()))
            latitiude = Double.parseDouble(textLatitude.getText().toString());
        if (!TextUtils.isEmpty(textLongitude.getText().toString()))
            longitude = Double.parseDouble(textLongitude.getText().toString());
        if (!TextUtils.isEmpty(textShelterCapacity.getText().toString()))
            capacity = Integer.parseInt(textShelterCapacity.getText().toString());


        // Checks if all parts are filled and correct
        boolean valid = validateForm();
        // where the focus is
        View focusView = null;

        if(valid) {
            database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();

            Shelter shelter = new Shelter(shelterName, capacity, male, female, longitude, latitiude, phone, address);

            reference.child("shelters").child(shelterName).setValue(shelter);
        }
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(shelterName)){
            textShelterName.setError("Required");
            valid = false;
        }

        if (TextUtils.isEmpty(address)){
            textAddress.setError("Required");
            valid = false;
        }

        String stringphone = textPhone.getText().toString().replaceAll("-", "").
                replaceAll("\\(", "").replaceAll("\\)", "");
        if(TextUtils.isEmpty(textPhone.getText().toString())){
            textPhone.setError("Required");
            valid = false;
        } else if ((stringphone.length() < 10 && stringphone.length() != 11) || (stringphone.length() > 10 && stringphone.length() != 11))
        {
            textPhone.setError("Must be a valid phone number.");
            valid = false;
        }

        if(!male && !female) {
            checkMale.setError("At least one must be selected");
            valid = false;
        }

        if (TextUtils.isEmpty(textLatitude.getText().toString())){
            textLatitude.setError("Required");
            valid = false;
        }

        if(TextUtils.isEmpty(textLongitude.getText().toString())){
            textLatitude.setError("Required");
            valid = false;
        }

        if(TextUtils.isEmpty(textShelterCapacity.getText().toString())){
            textShelterCapacity.setError("Required");
            valid = false;
        }

        return valid;
    }
}
