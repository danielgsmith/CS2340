package app.haven.haven;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SideBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ValueEventListener mUserlistener;
    private FirebaseUser mFireUser;
    private FirebaseDatabase database;
    private User user;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Not Implemented Yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Makes side drawer and adds functionality
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //The icons/buttons on the sidebar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFireUser = FirebaseAuth.getInstance().getCurrentUser();
        //database = FirebaseDatabase.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference("user").child(mFireUser.getUid()).child("info");
        //DatabaseReference pushRef = mUserRef.push();



        //mUserRef = FirebaseDatabase.getInstance().getReference().child(mFireUser.getUid()).child("info");
        getUser();
    }

    @Override
    public void onBackPressed() {
        //If drawer is open go back to main screen, otherwise do SUPER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Handles logout action
        if (id == R.id.nav_logout) {
            AlertDialog.Builder builder;
            // Depending on Android Version, do one of these
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            // Adds Alert when logging out to make sure you want to
            builder.setTitle("Log out?")
                    .setMessage("Are you sure you would like to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
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
        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_shelters) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_account) {
            if (user.getFirstName() != null) {
                Log.w("User Info:", "" + user.getFirstName());
            }else {
                Log.w("User Info:", "null");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUser() {
        /*mUserlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = (User) dataSnapshot.child(mFireUser.getUid()).child("info").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getUser:", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mUserRef.addValueEventListener(mUserlistener);*/


        /*mUserRef.child("users").child(mFireUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                Log.d("User Error", "" + snapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/
        Log.d("User Error", "" + mUserRef.child("info"));
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("yKlWu19vhqQXfL2tDlBNfMSduMe2");
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.d("getUser", "" + dataSnapshot.child("info").getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getUser:", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }


}
