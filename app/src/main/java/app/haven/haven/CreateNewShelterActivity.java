package app.haven.haven;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    private Spinner capacitySpinner;
    private EditText selectedCapacity;
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
    private int subCapacity = 0;
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

        String[] capacityTypes = new String[]{"Spaces", "Family Rooms", "Single Rooms", "Family and Single", "Apartments"};
        capacitySpinner = findViewById(R.id.shelter_capacity_spinner);
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


        capacitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = capacitySpinner.getSelectedItemId();
                Log.w("Spinner ID:", "" + selected);
                if (selected != -1) {
                    findViewById(R.id.text_shelter_holder_capacity).setVisibility(View.VISIBLE);
                    findViewById(R.id.space_spinner).setVisibility(View.VISIBLE);
                }

                if(selected == 0) {
                    findViewById(R.id.holer_spaces).setVisibility(View.VISIBLE);
                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
                    selectedCapacity = findViewById(R.id.shelter_capacity);
                } else if(selected == 1) {
                    findViewById(R.id.holer_spaces).setVisibility(View.GONE);
                    findViewById(R.id.holder_family_rooms).setVisibility(View.VISIBLE);
                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
                    selectedCapacity = findViewById(R.id.shelter_capacity_family_rooms);
                } else if(selected == 2){
                    findViewById(R.id.holer_spaces).setVisibility(View.GONE);
                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_single_rooms).setVisibility(View.VISIBLE);
                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
                    selectedCapacity = findViewById(R.id.shelter_capacity_single_rooms);
                } else if(selected == 3){
                    findViewById(R.id.holer_spaces).setVisibility(View.GONE);
                    findViewById(R.id.holder_family_rooms).setVisibility(View.VISIBLE);
                    findViewById(R.id.holder_single_rooms).setVisibility(View.VISIBLE);
                    findViewById(R.id.holder_apartments).setVisibility(View.GONE);
                    selectedCapacity = findViewById(R.id.shelter_capacity_family_rooms);
                } else if(selected == 4) {
                    findViewById(R.id.holer_spaces).setVisibility(View.GONE);
                    findViewById(R.id.holder_family_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_single_rooms).setVisibility(View.GONE);
                    findViewById(R.id.holder_apartments).setVisibility(View.VISIBLE);
                    selectedCapacity = findViewById(R.id.shelter_capacity_apartments);
                }
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
        if (selected != -1)
            capacity = Integer.parseInt(selectedCapacity.getText().toString());
        if(selected == 3){
            EditText sub = findViewById(R.id.shelter_capacity_single_rooms);
            subCapacity = Integer.parseInt(sub.getText().toString());
        }
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

            Shelter shelter = new Shelter(shelterName, selected, capacity, subCapacity, male, female, adults, newBorns, childUnder5,
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
            textShelterName.setError(null);
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

        if(selected == -1){
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
