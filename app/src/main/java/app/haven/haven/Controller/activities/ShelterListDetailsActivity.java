package app.haven.haven.Controller.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.haven.haven.Model.User;
import app.haven.haven.Model.shelters.Capacity;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

/**
 * Gives shelter details
 */
public class ShelterListDetailsActivity extends AppCompatActivity {

    private Shelter shelter;
    private TextView shelterName;
    private TextView shelterCapacity;
    private TextView shelterGender;
    private TextView shelterLong;
    private TextView shelterLat;
    private TextView shelterAddress;
    private TextView shelterPhone;
    private Button claimBedButton;
    private Spinner numberSpinner;
    private Button claimRoomButton;
    private Spinner roomSpinner;
    private Button releaseSpacesButton;
    private Button releaseRoomsButton;

    private User user;
    private FirebaseUser mFireUser;
    private FirebaseDatabase database;
    private boolean guest;
    private boolean notEnoughSpace;

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

        shelter = MainPageActivity.getSelectedShelter();
        if (shelter == null)
            return;
        Log.w("Shelter Details", shelter.getShelterName());
        mFireUser = FirebaseAuth.getInstance().getCurrentUser();
        user = MainPageActivity.getUser();

        int MAX_NAME_LENGTH = 20;
        if (shelter.getShelterName().length() < MAX_NAME_LENGTH)
            setTitle(shelter.getShelterName() + "'s info");
        else
            setTitle("Shelter's info");

        setViews();

        setText();

        setSpinners();

        hideInfo();


        claimBedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFireUser.isAnonymous()) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to claim a bed", Toast.LENGTH_SHORT).show();
                } else {
                    if (user.getCurrentShelterPushID() != null) {
                        String string = user.getCurrentShelterPushID();
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ShelterListDetailsActivity.this);
                        // Adds Alert when logging out to make sure you want to
                        builder.setTitle("Already Claimed")
                                .setMessage("You already claimed a bed. " +
                                        "\nWould you like to leave that shelter and claim this new one?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        removeOccupancy();
                                        claimBed();
                                        if (!notEnoughSpace)
                                            hideInfo();
                                        notEnoughSpace = false;
                                        closeContextMenu();
//                                        finish();
                                        //onBackPressed();
                                    }
                                })

                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                        closeContextMenu();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        claimBed();
                        if (!notEnoughSpace)
                            hideInfo();
                        notEnoughSpace = false;
                    }
                }

            }
        });

        claimRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFireUser.isAnonymous()) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to claim a bed", Toast.LENGTH_SHORT).show();
                } else {
                    if (user.getCurrentShelterPushID() != null) {
                        String string = user.getCurrentShelterPushID();
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ShelterListDetailsActivity.this);
                        // Adds Alert when logging out to make sure you want to
                        builder.setTitle("Already Claimed")
                                .setMessage("You already claimed a bed." +
                                        "\nWould you like to leave that shelter and claim this new one?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        removeOccupancy();
                                        claimBed();
                                        if (!notEnoughSpace)
                                            hideInfo();
                                        notEnoughSpace = false;
                                        closeContextMenu();
//                                        finish();
                                        //onBackPressed();
                                    }
                                })

                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                        closeContextMenu();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        claimBed();
                        if (!notEnoughSpace)
                            hideInfo();
                        notEnoughSpace = false;
                    }
                }

            }
        });

        releaseRoomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ShelterListDetailsActivity.this);
                // Adds Alert when logging out to make sure you want to
                builder.setTitle("Release space?")
                        .setMessage("Are you sure you want to release your spot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeOccupancy();
                                hideInfo();
                                closeContextMenu();
                                //finish();
                                //onBackPressed();
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                closeContextMenu();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        releaseSpacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ShelterListDetailsActivity.this);
                // Adds Alert when logging out to make sure you want to
                builder.setTitle("Release space?")
                        .setMessage("Are you sure you want to release your spot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeOccupancy();
                                hideInfo();
                                closeContextMenu();
                                //finish();
                                //onBackPressed();
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                closeContextMenu();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void removeOccupancy(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        String pushKey = shelter.getPushKey();
        String userPushID = user.getCurrentShelterPushID();
        int spaces = user.getTakenSpace();
        int rooms = user.getTakenRooms();
        user.setCurrentShelterPushID(null);
        reference.child("users").child(mFireUser.getUid()).child("currentShelterPushID").setValue(null);
        reference.child("users").child(mFireUser.getUid()).child("takenRooms").setValue(0);
        reference.child("users").child(mFireUser.getUid()).child("takenSpaces").setValue(0);

        spaces = (shelter.getCapacity().getIndividualOccupancy() - spaces);
        Log.w("Remove spaces", "" + spaces);
        shelter.getCapacity().setIndividualOccupancy(spaces);
        reference.child("shelters").child(userPushID).child("capacity").child("individualOccupancy").setValue(spaces);

        rooms = (shelter.getCapacity().getGroupOccupancy() - rooms);
        Log.w("Remove rooms", "" + rooms);
        shelter.getCapacity().setGroupOccupancy(rooms);
        reference.child("shelters").child(userPushID).child("capacity").child("groupOccupancy").setValue(rooms);

        shelterCapacity.setText(shelter.getCapacity().toDetailedString());
        Toast.makeText(this, "Beds removed", Toast.LENGTH_SHORT).show();
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

        String phoneNumber = shelter.getPhone();
        //phoneNumber = "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6,10);
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

    private void claimBed(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        String pushKey = shelter.getPushKey();
        int spaces = 0;
        int rooms = 0;
        switch (shelter.getCapacity().getCapacityType()) {

            case SPACES:
                spaces = Integer.parseInt(numberSpinner.getSelectedItem().toString());
                rooms = 0;
                break;

            case FAMILY_ROOMS:
                rooms = Integer.parseInt(roomSpinner.getSelectedItem().toString());
                spaces = 0;
                break;

            case APARTMENTS:
                rooms = Integer.parseInt(roomSpinner.getSelectedItem().toString());
                spaces = 0;
                break;

            case FAMILY_AND_SINGLE_ROOMS:
                spaces = Integer.parseInt(numberSpinner.getSelectedItem().toString());
                rooms = Integer.parseInt(roomSpinner.getSelectedItem().toString());
                break;

            case UNLISTED:
                spaces = 0;
                rooms = 0;
                break;
        }

        if (spaces != 0 || rooms != 0) {
            Capacity capacity = shelter.getCapacity();
            int shelterSpace = capacity.getIndividualCapacity() - capacity.getIndividualOccupancy();
            int shelterRoom = capacity.getGroupCapacity() - capacity.getIndividualOccupancy();
            if (spaces > shelterSpace || rooms > shelterRoom) {
                Toast.makeText(getApplicationContext(), "Not enough space", Toast.LENGTH_SHORT).show();
                notEnoughSpace = true;
            } else {
                user.setCurrentShelterPushID(pushKey);
                user.setTakenSpaces(spaces);
                user.setTakenRooms(rooms);

                reference.child("users").child(mFireUser.getUid()).child("currentShelterPushID").setValue(pushKey);
                reference.child("users").child(mFireUser.getUid()).child("takenSpaces").setValue(spaces);
                reference.child("users").child(mFireUser.getUid()).child("takenRooms").setValue(rooms);

                spaces = spaces + capacity.getIndividualOccupancy();
                Log.w("Spaces", "" + spaces);
                capacity.setIndividualOccupancy(spaces);
                reference.child("shelters").child(pushKey).child("capacity").child("individualOccupancy").setValue(spaces);

                rooms = rooms + shelter.getCapacity().getGroupOccupancy();
                Log.w("Rooms", "" + rooms);
                capacity.setGroupOccupancy(rooms);
                reference.child("shelters").child(pushKey).child("capacity").child("groupOccupancy").setValue(rooms);

                //claimBedButton.setText("RELEASE");

                //claimRoomButton.setText("RELEASE");
                //shelterCapacity.setText(shelter.getCapacity().subtractDetailedString(spaces, rooms));
                shelterCapacity.setText(shelter.getCapacity().toDetailedString());

                Toast.makeText(getApplicationContext(), "Bed Claimed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Must select amount of beds", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideInfo(){
        boolean claimed = false;
        if (shelter.getPushKey() == null) {

        } else if (shelter.getPushKey().equals(user.getCurrentShelterPushID())) {
            claimed = true;
            Log.w("Claimed", "TRUE");
        }
        switch (shelter.getCapacity().getCapacityType()) {

            case SPACES:
                findViewById(R.id.bed_space_holder).setVisibility(View.VISIBLE);
                findViewById(R.id.bed_room_holder).setVisibility(View.GONE);
                claimBedButton.setText("CLAIM BEDS");
                numberSpinner.setVisibility(View.VISIBLE);
                claimRoomButton.setVisibility(View.GONE);
                claimBedButton.setVisibility(View.VISIBLE);
                releaseSpacesButton.setVisibility(View.GONE);
                if (claimed) {
                    claimRoomButton.setVisibility(View.GONE);
                    claimBedButton.setVisibility(View.GONE);
                    numberSpinner.setVisibility(View.GONE);
                    releaseSpacesButton.setVisibility(View.VISIBLE);
                    releaseSpacesButton.setText("RELEASE BEDS");
                }
                break;

            case FAMILY_ROOMS:
                findViewById(R.id.bed_space_holder).setVisibility(View.GONE);
                findViewById(R.id.bed_room_holder).setVisibility(View.VISIBLE);
                claimRoomButton.setText("CLAIM ROOMS");
                roomSpinner.setVisibility(View.VISIBLE);
                claimRoomButton.setVisibility(View.VISIBLE);
                claimBedButton.setVisibility(View.GONE);
                releaseSpacesButton.setVisibility(View.GONE);
                if (claimed) {
                    claimRoomButton.setVisibility(View.GONE);
                    claimBedButton.setVisibility(View.GONE);
                    roomSpinner.setVisibility(View.GONE);
                    releaseRoomsButton.setVisibility(View.VISIBLE);
                    releaseRoomsButton.setText("RELEASE ROOMS");
                }
                break;

            case APARTMENTS:
                findViewById(R.id.bed_space_holder).setVisibility(View.GONE);
                findViewById(R.id.bed_room_holder).setVisibility(View.VISIBLE);
                claimRoomButton.setText("CLAIM ROOMS");
                roomSpinner.setVisibility(View.VISIBLE);
                claimRoomButton.setVisibility(View.VISIBLE);
                claimBedButton.setVisibility(View.GONE);
                releaseSpacesButton.setVisibility(View.GONE);
                if (claimed) {
                    claimRoomButton.setVisibility(View.GONE);
                    claimBedButton.setVisibility(View.GONE);
                    roomSpinner.setVisibility(View.GONE);
                    releaseRoomsButton.setVisibility(View.VISIBLE);
                    releaseRoomsButton.setText("RELEASE ROOMS");
                }
                break;

            case FAMILY_AND_SINGLE_ROOMS:
                findViewById(R.id.bed_space_holder).setVisibility(View.VISIBLE);
                findViewById(R.id.bed_room_holder).setVisibility(View.VISIBLE);
                claimRoomButton.setText("CLAIM ROOMS");
                claimBedButton.setText("CLAIM BEDS");
                roomSpinner.setVisibility(View.VISIBLE);
                numberSpinner.setVisibility(View.VISIBLE);
                claimRoomButton.setVisibility(View.VISIBLE);
                claimBedButton.setVisibility(View.VISIBLE);
                releaseSpacesButton.setVisibility(View.GONE);
                releaseRoomsButton.setVisibility(View.GONE);
                boolean bothButtons = true;
                if (claimed) {
                    claimRoomButton.setVisibility(View.GONE);
                    claimBedButton.setVisibility(View.GONE);
                    releaseRoomsButton.setVisibility(View.VISIBLE);
                    releaseSpacesButton.setVisibility(View.VISIBLE);
                    roomSpinner.setVisibility(View.GONE);
                    numberSpinner.setVisibility(View.GONE);
                    releaseRoomsButton.setText("RELEASE ROOMS");
                    releaseSpacesButton.setText("RELEASE BEDS");
                }
                break;

            case UNLISTED:
                findViewById(R.id.bed_space_holder).setVisibility(View.GONE);
                findViewById(R.id.bed_room_holder).setVisibility(View.GONE);
                releaseRoomsButton.setVisibility(View.GONE);
                releaseSpacesButton.setVisibility(View.GONE);
                break;
        }

    }

    private void setViews(){
        shelterName = findViewById(R.id.info_shelter_name);
        shelterCapacity = findViewById(R.id.info_shelter_capacity);
        shelterGender = findViewById(R.id.info_shelter_gender);
        shelterLong = findViewById(R.id.info_shelter_longitude);
        shelterLat = findViewById(R.id.info_shelter_latitude);
        shelterAddress = findViewById(R.id.info_shelter_address);
        shelterPhone = findViewById(R.id.info_shelter_phone);
        TextView shelterSubCapacity = findViewById(R.id.info_shelter_subcapacity);
        claimBedButton = findViewById(R.id.button_claim_bed);
        claimRoomButton = findViewById(R.id.button_claim_room);
        roomSpinner = findViewById(R.id.number_spinner_room);
        numberSpinner = findViewById(R.id.number_spinner_space);
        releaseSpacesButton = findViewById(R.id.button_release_bed);
        releaseRoomsButton = findViewById(R.id.button_release_room);
    }

    private void setSpinners() {
        String[] bedInts = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        //numberSpinner = findViewById(R.id.number_spinner_space); //Keep solving errors starting here
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bedInts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(adapter);

        String[] roomInts = {"0", "1", "2", "3", "4"};
        //numberSpinner = findViewById(R.id.number_spinner_room); //Keep solving errors starting here
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roomInts);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter1);
    }
}
