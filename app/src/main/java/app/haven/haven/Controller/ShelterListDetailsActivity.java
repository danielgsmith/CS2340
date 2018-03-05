package app.haven.haven.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import app.haven.haven.Model.Shelter;
import app.haven.haven.R;

public class ShelterListDetailsActivity extends AppCompatActivity {

    private Shelter shelter;
    private TextView shelterName;
    private TextView shelterCapacity;
    private TextView shelterGender;
    private TextView shelterLong;
    private TextView shelterLat;
    private TextView shelterAddress;
    private TextView shelterPhone;
    private TextView shelterSubCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        shelter = SearchedSheltersActivity.getSelectedShelter();

        if (shelter.getShelterName().length() < 20)
            setTitle(shelter.getShelterName() + "'s info");
        else
            setTitle("Shelter's info");

        shelterName = findViewById(R.id.info_shelter_name);
        shelterCapacity = findViewById(R.id.info_shelter_capacity);
        shelterGender = findViewById(R.id.info_shelter_gender);
        shelterLong = findViewById(R.id.info_shelter_longitude);
        shelterLat = findViewById(R.id.info_shelter_latitude);
        shelterAddress = findViewById(R.id.info_shelter_address);
        shelterPhone = findViewById(R.id.info_shelter_phone);
        shelterSubCapacity = findViewById(R.id.info_shelter_subcapacity);

        setText();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setText(){
        shelterName.setText(shelter.getShelterName());
        shelterLat.setText(String.format("%s", shelter.getLatitude()));
        shelterLong.setText(String.format("%s", shelter.getLongitude()));
        shelterAddress.setText(shelter.getAddress());

        String phoneNumber = shelter.getPhone();
        shelterPhone.setText(phoneNumber);

        if (shelter.getRestrictions().isMen() && shelter.getRestrictions().isWomen()){
            shelterGender.setText("Accepts all genders");
        } else if (shelter.getRestrictions().isMen() && !shelter.getRestrictions().isWomen())
            shelterGender.setText("Accepts only Males");
        else if (!shelter.getRestrictions().isMen() && shelter.getRestrictions().isWomen())
            shelterGender.setText("Accepts only Females");

        shelterCapacity.setText(shelter.getCapacity().toDetailedString());
        //Log.e("Capacity", "" + shelter.getCapacity().getIndividualCapacity());


    }
}
