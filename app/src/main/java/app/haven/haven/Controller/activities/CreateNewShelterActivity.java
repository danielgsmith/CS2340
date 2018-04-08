package app.haven.haven.Controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;

import app.haven.haven.Model.shelters.Capacity;
import app.haven.haven.Controller.adapters.NothingSelectedSpinnerAdapter;
import app.haven.haven.Model.shelters.Restrictions;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.Controller.adapters.UsPhoneNumberFormatter;
import app.haven.haven.R;

/**
 * Creates a new shelter
 */
public class CreateNewShelterActivity extends AppCompatActivity {

    private EditText textShelterName;
    private EditText textShelterCapacity;
    private EditText textLongitude;
    private EditText textLatitude;
    private EditText textAddress;
    private EditText textPhone;
    private EditText textKey;
    private EditText textNotes;
    private Spinner capacitySpinner;
    private EditText selectedCapacity;
    private int key;

    private String shelterName;
    private Capacity capacity;
    private double longitude;
    private double latitude;
    private String phone;
    private String address;
    private int uniqueKey;
    private int occupancy;
    private Restrictions restrictions;

    private TextView acceptedText;
    private long selected;

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

        String[] capacityTypes = Capacity.CapacityType.stringValues();
        capacitySpinner = findViewById(R.id.shelter_capacity_spinner); //Keep solving errors starting here
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, capacityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacitySpinner.setPrompt("Choose Capacity Type: ");
        capacitySpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.capacity_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        textShelterName = findViewById(R.id.shelter_name);
//        textShelterCapacity = findViewById(R.id.shelter_capacity);
        CheckBox checkMale = findViewById(R.id.check_male);
        CheckBox checkFemale = findViewById(R.id.check_female);
        textLongitude = findViewById(R.id.shelter_longitude);
        textLatitude = findViewById(R.id.shelter_latitude);
        textAddress = findViewById(R.id.shelter_address);
        textPhone = findViewById(R.id.shelter_phone);
        CheckBox checkYoung = findViewById(R.id.check_young_children);
        CheckBox checkNew = findViewById(R.id.check_newborn);
        CheckBox checkAdult = findViewById(R.id.check_adult);
        CheckBox checkFamily = findViewById(R.id.check_family);
        CheckBox checkChildren = findViewById(R.id.check_children);
        CheckBox checkVeteran = findViewById(R.id.check_vets);
        textKey = findViewById(R.id.shelter_key);
        acceptedText = findViewById(R.id.text_shelter_criteria);
        textNotes = findViewById(R.id.shelter_notes);

        TextWatcher addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(textPhone));
        textPhone.addTextChangedListener(addLineNumberFormatter);

        Button addShelter = (Button) findViewById(R.id.button_create_shelter);
        addShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShelter();
            }
        });


        capacitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = capacitySpinner.getSelectedItemId();
                Log.w("Spinner ID:", "" + selected);

//                if (selected != -1) {
//                    findViewById(R.id.text_shelter_holder_capacity).setVisibility(View.VISIBLE);
//                    findViewById(R.id.space_spinner).setVisibility(View.VISIBLE);
//                }
//
//                if(selected == 0) {
//                    findViewById(R.id.holder_spaces).setVisibility(View.VISIBLE);
//                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
//                    selectedCapacity = findViewById(R.id.shelter_capacity);
//                } else if(selected == 1) {
//                    findViewById(R.id.holder_spaces).setVisibility(View.GONE);
//                    findViewById(R.id.holder_family_rooms).setVisibility(View.VISIBLE);
//                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
//                    selectedCapacity = findViewById(R.id.shelter_capacity_family_rooms);
//                } else if(selected == 2){
//                    findViewById(R.id.holder_spaces).setVisibility(View.GONE);
//                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_single_rooms).setVisibility(View.VISIBLE);
//                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
//                    selectedCapacity = findViewById(R.id.shelter_capacity_single_rooms);
//                } else if(selected == 3){
//                    findViewById(R.id.holder_spaces).setVisibility(View.GONE);
//                    findViewById(R.id.holder_family_rooms).setVisibility(View.VISIBLE);
//                    findViewById(R.id.holder_single_rooms).setVisibility(View.VISIBLE);
//                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
//                    selectedCapacity = findViewById(R.id.shelter_capacity_family_rooms);
//                } else if(selected == 4) {
//                    findViewById(R.id.holder_spaces).setVisibility(View.GONE);
//                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
//                    findViewById(R.id.holder_apartments).setVisibility(View.VISIBLE);
//                    selectedCapacity = findViewById(R.id.shelter_capacity_apartments);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        if (!TextUtils.isEmpty(textLatitude.getText().toString()))
            latitude = Double.parseDouble(textLatitude.getText().toString());
        if (!TextUtils.isEmpty(textLongitude.getText().toString()))
            longitude = Double.parseDouble(textLongitude.getText().toString());
        if (selected != -1) {
            if(!TextUtils.isEmpty(selectedCapacity.getText().toString()))
                capacity = new Capacity(Capacity.CapacityType.SPACES, Integer.parseInt(selectedCapacity.getText().toString())); //TODO: capacity
        }
        if(selected == 3){
//            EditText sub = findViewById(R.id.shelter_capacity_single_rooms);
            //subCapacity = Integer.parseInt(sub.getText().toString()); NO LONGER USING SUBCAPACITY
        }
        if(!TextUtils.isEmpty(textKey.getText().toString()))
            key = Integer.parseInt(textKey.getText().toString());
        String notes = textNotes.getText().toString();

        // Checks if all parts are filled and correct
        boolean valid = validateForm();
        // where the focus is
        View focusView = null;

        if(valid) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
//            Shelter(String name, Capacity capacity, Restrictions restrictions, double longitude,
//            double latitude, String phone, String address, int uniqueKey, String notes) {


            //TODO: This is the biggest error currently
            Shelter shelter = new Shelter(shelterName, capacity, restrictions, longitude, latitude, phone, address, key, notes);





            reference.child("shelters").push().setValue(shelter);

            Toast.makeText(this, "Shelter added", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
            startActivity(i);
        }
    }

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(shelterName)){
            textShelterName.setError("Required");
            valid = false;
        } else {
            textShelterName.setError(null);
        }

        if (TextUtils.isEmpty(address)){
            textAddress.setError("Required");
            valid = false;
        }else {
            textAddress.setError(null);
        }

        int MAX_PHONE_LENGTH = 11;
        if(TextUtils.isEmpty(phone)){
            textPhone.setError("Required");
            valid = false;
        } else if ((phone.length() < 10 && phone.length() != MAX_PHONE_LENGTH) || (phone.length() > 10 && phone.length() != MAX_PHONE_LENGTH))
        {
            textPhone.setError("Must be a valid phone number.");
            valid = false;
        } else {
            textPhone.setError(null);
        }

//        if ((!male && !female && !adults && !newBorns && !childUnder5 && !families && !child && !veterans)) {
//            acceptedText.setError("At least one must be selected");
//            valid = false;
//        } else {
//            acceptedText.setError(null);
//        }

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

        if(selected == -1){
            textShelterCapacity.setError("Required");
            valid = false;
        }else if(TextUtils.isEmpty(selectedCapacity.getText().toString())){
            selectedCapacity.setError("Required");
        } else{
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
