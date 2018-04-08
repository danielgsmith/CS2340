package app.haven.haven.Controller.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.haven.haven.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShelterDetailsActivityFragment extends Fragment {

    /**
     * Details fragment
     */
    public ShelterDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shelter_details, container, false);
    }

}
