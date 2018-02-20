package app.haven.haven;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ShelterDetailsActivity extends AppCompatActivity {

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

        shelter = SideBar.getSelectedShelter();
        if (shelter.getShelterName().length() < 20)
            setTitle(shelter.getShelterName() + "'s info");
        else
            setTitle("Shelter's info");

        //Toast.makeText(this, "Selected " + shelter.getShelterName(), Toast.LENGTH_SHORT).show();
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
        int available;
        shelterName.setText(shelter.getShelterName());
        shelterLat.setText(String.format("%s", shelter.getLatitude()));
        shelterLong.setText(String.format("%s", shelter.getLongitude()));
        shelterAddress.setText(shelter.getAddress());

        if (shelter.getCapacityType() == 0) {
            available = shelter.getCapacity() - shelter.getOccupancy();
            if (available == 1)
                shelterCapacity.setText(String.format("%d space left", available));
            else if (available == 0)
                shelterCapacity.setText("No spaces left");
            else
                shelterCapacity.setText(String.format("%d spaces left", available));
        } else if (shelter.getCapacityType() == 1) {
            available = shelter.getCapacity() - shelter.getOccupancy();
            if (available == 1)
                shelterCapacity.setText(String.format("%d family room left", available));
            else if (available == 0)
                shelterCapacity.setText("No spaces left");
            else
                shelterCapacity.setText(String.format("%d family rooms left", available));
        } else if (shelter.getCapacityType() == 2) {
            available = shelter.getCapacity() - shelter.getOccupancy();
            if (available == 1)
                shelterCapacity.setText(String.format("%d single room left", available));
            else if (available == 0)
                shelterCapacity.setText("No spaces left");
            else
                shelterCapacity.setText(String.format("%d single rooms left", available));
        } else if (shelter.getCapacityType() == 3) {
            LinearLayout cap = findViewById(R.id.holder_subCapacity);
            cap.setVisibility(View.VISIBLE);
            int sub = shelter.getSubCapacity();
            available = shelter.getCapacity() - shelter.getOccupancy();
            if (available == 1)
                shelterCapacity.setText(String.format("%d single room left", available));
            else if (available == 0)
                shelterCapacity.setText("No single rooms left");
            else
                shelterCapacity.setText(String.format("%d single rooms left", available));

            if (sub == 1)
                shelterSubCapacity.setText(String.format("%d family room left", sub));
            else if (sub == 0)
                shelterSubCapacity.setText("No family rooms left");
            else
                shelterSubCapacity.setText(String.format("%d family rooms left", sub));
        } else if (shelter.getCapacityType() == 4) {
            available = shelter.getCapacity() - shelter.getOccupancy();
            if (available == 1)
                shelterCapacity.setText(String.format("%d apartment left", available));
            else if (available == 0)
                shelterCapacity.setText("No apartments left");
            else
                shelterCapacity.setText(String.format("%d apartments left", available));
        }

        if (shelter.getAcceptsMale() && !shelter.getAcceptsFemale())
            shelterGender.setText("Accepts only Males");
        else if (!shelter.getAcceptsMale() && shelter.getAcceptsFemale())
            shelterGender.setText("Accepts only Females");
        else
            shelterGender.setText("Accepts all genders");

        String phoneNumber = shelter.getPhone();
        phoneNumber = "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6,10);
        shelterPhone.setText(phoneNumber);
    }
}
