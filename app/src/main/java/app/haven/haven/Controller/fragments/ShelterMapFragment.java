package app.haven.haven.Controller.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.haven.haven.Controller.activities.MainPageActivity;
import app.haven.haven.Controller.activities.SearchedSheltersActivity;
import app.haven.haven.Controller.activities.ShelterDetailsActivity;
import app.haven.haven.Controller.activities.ShelterListDetailsActivity;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Fragment that shows the google map
 */
public class ShelterMapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    private ArrayList<Shelter> sheltersArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shelter_map, container, false);

        Button editCriteria = rootView.findViewById(R.id.button_edit);
        editCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CriteriaFragment();
                getActivity().setTitle("Criteria");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.commit();
            }
        });

        Button clearSearch = rootView.findViewById(R.id.button_clear_search);
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();
                mDataRef.child("shelters").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sheltersArray.clear();
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            Shelter place = child.getValue(Shelter.class);
                            sheltersArray.add(place);
                        }
                        for (Shelter shelter : sheltersArray) {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(shelter.getLatLng())
                                    .title(shelter.getShelterName())
                                    .snippet(shelter.getPhone()));
                        }
                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker m) {
                                String name = m.getTitle();
                                for (int count = 0; count < sheltersArray.size(); count++) {
                                    if (sheltersArray.get(count).getShelterName().equals(name))
                                        MainPageActivity.setSelectedShelter(sheltersArray.get(count));
                                }
                                Intent i = new Intent(getActivity(), ShelterDetailsActivity.class);
                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                //For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Got here", "Did not complete everything");
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{(Manifest.permission.ACCESS_FINE_LOCATION)}, 0);
                        Log.e("Got here", "inside");
                    if (!(ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                        return;
                    }
                }
                googleMap.setMyLocationEnabled(true);
                Log.e("Got here", "Got to Marker palce");

                FirebaseUser mFireUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();


                sheltersArray = ShelterSearchFragment.getSheltersArray();

                if (sheltersArray.isEmpty()) {
                    mDataRef.child("shelters").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sheltersArray.clear();
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for (DataSnapshot child : children) {
                                Shelter place = child.getValue(Shelter.class);
                                sheltersArray.add(place);
                            }
                            for (Shelter shelter : sheltersArray) {
                                googleMap.addMarker(new MarkerOptions()
                                        .position(shelter.getLatLng())
                                        .title(shelter.getShelterName())
                                        .snippet(shelter.getPhone()));
                            }
                            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker m) {
                                    String name = m.getTitle();
                                    for (int count = 0; count < sheltersArray.size(); count++) {
                                        if (sheltersArray.get(count).getShelterName().equals(name))
                                            MainPageActivity.setSelectedShelter(sheltersArray.get(count));
                                    }
                                    Intent i = new Intent(getActivity(), ShelterDetailsActivity.class);
                                    startActivity(i);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
                        }
                    });
                } else {

                    for (Shelter shelter : sheltersArray) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(shelter.getLatLng())
                                .title(shelter.getShelterName())
                                .snippet(shelter.getPhone())
                                .snippet("Click for details"));
                    }
                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker m) {
                            String name = m.getTitle();
                            for (int count = 0; count < sheltersArray.size(); count++) {
                                if (sheltersArray.get(count).getShelterName().equals(name))
                                    MainPageActivity.setSelectedShelter(sheltersArray.get(count));
                            }
                            Intent i = new Intent(getActivity(), ShelterDetailsActivity.class);
                            startActivity(i);
                        }
                    });
                }


                // For zooming automatically to the location of the marker
                LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        new LatLng(location.getLatitude(), location.getLongitude())
                ).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}