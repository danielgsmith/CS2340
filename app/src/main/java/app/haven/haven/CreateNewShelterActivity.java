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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewShelterActivity extends AppCompatActivity {

    FirebaseDatabase database;

    private EditText textShelterName;
    private EditText textShelterCapacity;
    private CheckBox checkMale;
    private CheckBox checkFemale;
    private CheckBox checkYoung;
    private CheckBox checkNew;
    private CheckBox checkFamily;
    private CheckBox checkChildren;
    private CheckBox checkAdult;
    private CheckBox checkVeteran;
    private EditText textLongitude;
    private EditText textLatitude;
    private EditText textAddress;
    private EditText textPhone;
    private EditText textKey;
    private EditText textNotes;
    private Button addShelter;
    private String shelterName;
    private String address;
    private String notes;
    private String phone;
    private int key;
    private boolean male;
    private boolean female;
    private boolean child;
    private boolean childUnder5;
    private boolean newBorns;
    private boolean adults;
    private boolean families;
    private boolean veterans;
    private double latitude;
    private double longitude;
    private int capacity;
    private TextView acceptedText;

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
        checkYoung = findViewById(R.id.check_young_children);
        checkNew = findViewById(R.id.check_newborn);
        checkAdult = findViewById(R.id.check_adult);
        checkFamily = findViewById(R.id.check_family);
        checkChildren = findViewById(R.id.check_children);
        checkVeteran = findViewById(R.id.check_vets);
        textKey = findViewById(R.id.shelter_key);
        acceptedText = findViewById(R.id.text_shelter_criteria);
        textNotes = findViewById(R.id.shelter_notes);

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
        textLongitude.setError(null);
        textLatitude.setError(null);
        textAddress.setError(null);
        textPhone.setError(null);
        textShelterCapacity.setError(null);
        acceptedText.setError(null);
        textKey.setError(null);

        // Saved Strings
        shelterName = textShelterName.getText().toString();
        address = textAddress.getText().toString();
        if (!TextUtils.isEmpty(textPhone.getText()))
            phone =textPhone.getText().toString().replaceAll("-", "").replaceAll("\\(", "")
                    .replaceAll("\\)", "").replaceAll(" ", "");
        male = checkMale.isSelected();
        female = checkFemale.isChecked();
        adults = checkAdult.isChecked();
        newBorns = checkNew.isChecked();
        childUnder5 = checkYoung.isChecked();
        families = checkFamily.isChecked();
        child = checkChildren.isChecked();
        veterans = checkVeteran.isChecked();
        if (!TextUtils.isEmpty(textLatitude.getText().toString()))
            latitude = Double.parseDouble(textLatitude.getText().toString());
        if (!TextUtils.isEmpty(textLongitude.getText().toString()))
            longitude = Double.parseDouble(textLongitude.getText().toString());
        if (!TextUtils.isEmpty(textShelterCapacity.getText().toString()))
            capacity = Integer.parseInt(textShelterCapacity.getText().toString());
        if(!TextUtils.isEmpty(textKey.getText().toString()))
            key = Integer.parseInt(textKey.getText().toString());
        notes = textNotes.getText().toString();

        // Checks if all parts are filled and correct
        boolean valid = validateForm();
        // where the focus is
        View focusView = null;

        if(valid) {
            database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();

            Shelter shelter = new Shelter(shelterName, capacity, male, female, adults, newBorns, childUnder5,
                    families, child, veterans, longitude, latitude, phone, address, key, notes);

            reference.child("shelters").push().setValue(shelter);

            Toast.makeText(this, "Shelter added", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(getApplicationContext(), SideBar.class);
            startActivity(i);
        }
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(shelterName)){
            textShelterName.setError("Required");
            valid = false;
        } else {
            textShelterCapacity.setError(null);
        }

        if (TextUtils.isEmpty(address)){
            textAddress.setError("Required");
            valid = false;
        }else {
            textAddress.setError(null);
        }

        if(TextUtils.isEmpty(phone)){
            textPhone.setError("Required");
            valid = false;
        } else if ((phone.length() < 10 && phone.length() != 11) || (phone.length() > 10 && phone.length() != 11))
        {
            textPhone.setError("Must be a valid phone number.");
            valid = false;
        } else {
            textPhone.setError(null);
        }

        if ((!male && !female && !adults && !newBorns && !childUnder5 && !families && !child && !veterans)) {
            acceptedText.setError("At least one must be selected");
            valid = false;
        } else {
            acceptedText.setError(null);
        }

        if (TextUtils.isEmpty(textLatitude.getText().toString())){
            textLatitude.setError("Required");
            valid = false;
        }else {
            textLatitude.setError(null);
        }

        if(TextUtils.isEmpty(textLongitude.getText().toString())){
            textLongitude.setError("Required");
            valid = false;
        }else{
            textLongitude.setError(null);
        }

        if(TextUtils.isEmpty(textShelterCapacity.getText().toString())){
            textShelterCapacity.setError("Required");
            valid = false;
        }else{
            textShelterCapacity.setError(null);
        }

        if(TextUtils.isEmpty(textKey.getText().toString())){
            textKey.setError("Required");
            valid = false;
        }else{
            textKey.setError(null);
        }

        return valid;
    }
}
