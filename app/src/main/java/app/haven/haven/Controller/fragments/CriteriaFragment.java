package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.haven.haven.Controller.activities.SearchedSheltersActivity;
import app.haven.haven.Controller.activities.ShelterDetailsActivity;
import app.haven.haven.Controller.adapters.NothingSelectedSpinnerAdapter;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CriteriaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CriteriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CriteriaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private Spinner genderSpinner;
    public static long genderselected;
    private Spinner rangeSpinner;
    public static long rangeSelected;
    public static String searchedName;
    private EditText textName;

    /**
     * Fragment with criteria
     */
    public CriteriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CriteriaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CriteriaFragment newInstance(String param1, String param2) {
        CriteriaFragment fragment = new CriteriaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criteria, container, false);

        textName = view.findViewById(R.id.input_shelter_name);


        final TextView text = (TextView) view.findViewById(R.id.textView_gender);
        String[] genders = new String[]{"Male", "Female", "Either"};
        genderSpinner = view.findViewById(R.id.genderspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setPrompt("Choose Gender: ");
        genderSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.gender_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this.getActivity()));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderselected = genderSpinner.getSelectedItemId();
                if (genderselected != -1) {
                    text.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        final TextView ageRange = (TextView) view.findViewById(R.id.textView_age_range);
        String[] age = new String[]{"Families with newborns", "Children", "Young Adults", "Anyone"};
        rangeSpinner = view.findViewById(R.id.age_range_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, age);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeSpinner.setPrompt("Age Range: ");
        rangeSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter1,
                        R.layout.age_range_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this.getActivity()));
        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rangeSelected = rangeSpinner.getSelectedItemId();
                if (rangeSelected != -1) {
                    ageRange.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button searchButton = (Button) view.findViewById(R.id.search_shelters);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedName = textName.getText().toString();
                searchShelters();
            }
        });

        Button seeMap = view.findViewById(R.id.button_see_on_map);
        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedName = textName.getText().toString();
                //searchShelters();
                getLocations();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Unused
     * @param uri unused button press
     */
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        /**
         * unused
         * @param uri unused button press
         */
        void onFragmentInteraction(Uri uri);
    }

    private void searchShelters(){
        //Toast.makeText(this.getActivity(), "No Shelters Found", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), SearchedSheltersActivity.class);
        startActivity(i);
    }

    private void getLocations(){
        final ArrayList<Shelter> sheltersArray = new ArrayList<>();
        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.child("shelters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //sheltersArray.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {

                    Shelter place = child.getValue(Shelter.class);
                    //Log.w("String", "" + place.getAcceptsFemale());
                    //Log.w("String", "" + CriteriaFragment.genderselected);
                    boolean add = true;
                    String name = CriteriaFragment.searchedName;
                    //Log.w("Name", name);

                    if (name.length() == 0 || name.isEmpty()) {
                        if (CriteriaFragment.genderselected == 0) {
                            if (!place.getAcceptsMale())
                                add = false;
                            else if (CriteriaFragment.genderselected == 1)
                                if (!place.getAcceptsFemale())
                                    add = false;
                        }

                        if (CriteriaFragment.rangeSelected == 0) {
                            if (place.getRestrictions().isAdultsOnly())
                                add = false;
                        }
                        else if (CriteriaFragment.rangeSelected == 1) {
                            if (!place.getRestrictions().isAllowsChildren())
                                add = false;
                        }
                        else if (CriteriaFragment.rangeSelected == 2) {
                            if (!place.getRestrictions().isYoungAdultsOnly())
                                add = false;
                        }
                    } else if (!place.getShelterName().equals(name))
                        add = false;


                    if (add)
                        sheltersArray.add(place);
                    //Log.w("Item", ""+ sheltersArray.get(0));
                }
                if (sheltersArray.isEmpty()) {
                    Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
                }

                ShelterSearchFragment.setShelterArray(sheltersArray);
                //Log.w("Item", ""+ sheltersArray.get(0));
                Fragment fragment = new ShelterMapFragment();
                getActivity().setTitle("Map");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        });
    }
}
