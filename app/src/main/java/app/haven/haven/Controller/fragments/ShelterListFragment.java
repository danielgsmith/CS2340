package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import app.haven.haven.Controller.adapters.MyShelterRecyclerViewAdapter;
import app.haven.haven.Model.shelters.Shelter;
import app.haven.haven.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShelterListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;


    private ArrayList<Shelter> sheltersArray;
    private RecyclerView.Adapter rvAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShelterListFragment() {
    }

    @SuppressWarnings("unused")
    public static ShelterListFragment newInstance(int columnCount) {
        ShelterListFragment fragment = new ShelterListFragment();
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
        View view = inflater.inflate(R.layout.fragment_shelter_list, container, false);

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
                    sheltersArray.add(place);
                    //Log.e("Capacity", "" + place.getCapacity());
                    //Log.w("Item", ""+ sheltersArray.get(0));
                }
                //Log.w("Item", ""+ sheltersArray.get(0));
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        });



        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyShelterRecyclerViewAdapter(sheltersArray, mListener));
        }*/
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(rvLayoutManager);

        rvAdapter = new MyShelterRecyclerViewAdapter(sheltersArray, mListener);
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
        //Log.w("something", "something");
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
         * lets sub access info
         * @param shelter the shelter being used
         */
        void onListFragmentInteraction(Shelter shelter);
    }
}
