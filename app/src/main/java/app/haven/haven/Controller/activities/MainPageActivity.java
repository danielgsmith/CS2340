package app.haven.haven.Controller.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.haven.haven.Controller.fragments.AdminPageFragment;
import app.haven.haven.Controller.fragments.CriteriaFragment;
import app.haven.haven.Controller.fragments.ShelterListFragment;
import app.haven.haven.Controller.fragments.ShelterMapFragment;
import app.haven.haven.Controller.fragments.UserAccoundEditingFragment;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.Model.User;
import app.haven.haven.R;

/**
 * The main page
 */
public class MainPageActivity extends AppCompatActivity
        implements


        AdminPageFragment.OnFragmentInteractionListener,
        ShelterListFragment.OnListFragmentInteractionListener,
        CriteriaFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        UserAccoundEditingFragment.OnFragmentInteractionListener {

    private ValueEventListener mUserlistener;
    private FirebaseUser mFireUser;
    private FirebaseDatabase database;
    private static User user = new User();
    private NavigationView navigationView;
    private static Shelter selectedShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Not Implemented Yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Makes side drawer and adds functionality
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // The icons/buttons on the sidebar
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFireUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();
        //Gets user out of the database
        if (!mFireUser.isAnonymous() && mFireUser != null) {
            mDataRef.child("users").child(mFireUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Grabs saved user class from database
                    user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    } else if (user.isLockedOut()) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Toast.makeText(MainPageActivity.this, "Error, please re-login", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }else if (!mFireUser.isAnonymous() && user.getAccountType() == 1)
                        unhiddenAdminMenu();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        //Log.w("User Info", "" + user.getEmail());


        // Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.nav_map);
        setTitle("Map");

        // Open ShelterMap Fragment initially.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new ShelterMapFragment());
        ft.commit();

    }

    // Sets admin menu to visible
    private void unhiddenAdminMenu()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.admin_menu).setVisible(true);
    }

    @Override
    public void onBackPressed() {
        // If drawer is open go back to main screen, otherwise do SUPER
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

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        // Handles logout action
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
            fragment = new ShelterMapFragment();
            setTitle("Map");
        } else if (id == R.id.nav_search) {
            fragment = new CriteriaFragment();
            setTitle("Criteria");
        } else if (id == R.id.nav_shelters) {
            fragment = new ShelterListFragment();
            setTitle("Shelters");
        } else if (id == R.id.nav_admin_page) {
            if (!mFireUser.isAnonymous() && user.getAccountType() == 1) {
                fragment = new AdminPageFragment();
                setTitle("Admin Page");
                /*Toast.makeText(this, "Permission Granted",
                        Toast.LENGTH_SHORT).show();*/
            }
            else {
                Toast.makeText(this, "Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_account) {

            if (mFireUser.isAnonymous() && mFireUser != null) {
                Toast.makeText(this, "Must be logged in.",
                        Toast.LENGTH_SHORT).show();
            } else {
                fragment = new UserAccoundEditingFragment();
                setTitle("Account Settings");
                Log.w("Logged in:", "Implement account page");
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onListFragmentInteraction(Shelter shelter) {
        //Toast.makeText(this, "Item Clicked " + shelter.getShelterName(), Toast.LENGTH_SHORT).show();
        selectedShelter = shelter;
        Intent i = new Intent(getApplicationContext(), ShelterDetailsActivity.class);
        startActivity(i);


    }

    /**
     * get selected shelter
     * @return Shelter
     */
    public static Shelter getSelectedShelter(){
        return selectedShelter;
    }

    /**
     * set selected shelter
     * @param shelter shelter to be selected
     */
    public static void setSelectedShelter(Shelter shelter) {
        selectedShelter = shelter;
    }

    /**
     * gets user
     * @return user
     */
    public static User getUser() {
        return user;
    }

    /**
     * sets user
     * @param here user to set
     */
    public static void setUser(User here) {
        user = here;
    }
}
