package app.haven.haven;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CreateNewShelterActivity extends AppCompatActivity {

    private EditText textShelterName;
    private EditText textShelterCapacity;
    private CheckBox checkMale;
    private CheckBox checkFemale;
    private EditText textLongitude;
    private EditText textLatitude;
    private EditText textAddress;
    private EditText textPhone;
    private Button addShelter;

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

        //Saved Strings
        final String shelterName = textShelterName.getText().toString();
        final String address = textAddress.getText().toString();
        //String phone = textPhone.getText().toString().replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
        int phone = Integer.parseInt(textPhone.getText().toString().
                replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", ""));
        final boolean male = checkMale.isSelected();
        final boolean female = checkFemale.isSelected();
        final double latitiude = Double.parseDouble(textLatitude.getText().toString());
        final double longitude = Double.parseDouble(textLongitude.getText().toString());


        //Checks if all parts are filled and correct
        //boolean valid = validateForm();
        //where the focus is
        View focusView = null;
    }
}
