package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
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
import app.haven.haven.Controller.activities.ShelterDetailsActivity;
import app.haven.haven.Controller.adapters.MyShelterSearchRecyclerViewAdapter;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShelterSearchFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;


    private static ArrayList<Shelter> sheltersArray;
    private RecyclerView.Adapter rvAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShelterSearchFragment() {
    }

    @SuppressWarnings("unused")
    public static ShelterSearchFragment newInstance(int columnCount) {
        ShelterSearchFragment fragment = new ShelterSearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shelter_search_list, container, false);

        FirebaseUser mFireUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();


        sheltersArray = new ArrayList<>();

        mDataRef.child("shelters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sheltersArray.clear();
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
                //Log.w("Item", ""+ sheltersArray.get(0));
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(rvLayoutManager);

        rvAdapter = new MyShelterSearchRecyclerViewAdapter(sheltersArray, mListener);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(rvAdapter);
        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        /**
         * sends shelter to next screen
         * @param shelter the shelter being used
         */
        void onListFragmentInteraction(Shelter shelter);
    }

    /**
     * get shelter array
     * @return array of shelters
     */
    public static ArrayList<Shelter> getSheltersArray() {
        if (sheltersArray == null || sheltersArray.isEmpty()) {
            sheltersArray = new ArrayList<>();
            DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference();
            mDataRef.child("shelters").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //sheltersArray.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                        Shelter place = child.getValue(Shelter.class);
                        sheltersArray.add(place);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return sheltersArray;
    }

    /**
     * sets array
     * @param array array to set it to
     */
    public static void setShelterArray(ArrayList<Shelter> array) {
        sheltersArray = array;
    }
}
