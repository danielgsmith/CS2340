package app.haven.haven.Controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.haven.haven.Controller.fragments.ShelterSearchFragment;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

/**
 * Searched shelters list
 */
public class SearchedSheltersActivity extends AppCompatActivity
        implements
        ShelterSearchFragment.OnListFragmentInteractionListener {

    private static Shelter selectedShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_shelters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Searched Shelters");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ShelterSearchFragment();
        ft.replace(R.id.layoutcontainer, new ShelterSearchFragment());
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onListFragmentInteraction(Shelter shelter) {
        selectedShelter = shelter;
        MainPageActivity.setSelectedShelter(shelter);
        Intent i = new Intent(getApplicationContext(), ShelterDetailsActivity.class);
        startActivity(i);
    }
}
