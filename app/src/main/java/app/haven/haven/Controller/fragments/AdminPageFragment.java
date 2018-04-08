package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import app.haven.haven.Controller.activities.CreateNewShelterActivity;
import app.haven.haven.Model.shelters.Capacity;
import app.haven.haven.Model.shelters.Restrictions;
import app.haven.haven.R;
import app.haven.haven.Model.shelters.Shelter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdminPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdminPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPageFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseDatabase database;

    private OnFragmentInteractionListener mListener;

    /**
     * the admin page fragment
     */
    public AdminPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPageFragment newInstance(String param1, String param2) {
        AdminPageFragment fragment = new AdminPageFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_page, container, false);
        Button createShelter = (Button) view.findViewById(R.id.button_admin_create_shelter);
        createShelter.setOnClickListener(this);
        Button removeShelter = (Button) view.findViewById(R.id.button_admin_remove_shelter);
        removeShelter.setOnClickListener(this);
        Button parseFile = (Button) view.findViewById(R.id.parse_file);
        parseFile.setOnClickListener(this);

        return view;
    }

    /**
     * if button is pressed
     * @param uri unused
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_admin_create_shelter:
                Log.w("CreateShelter:", "Worked");
                Intent i = new Intent(getActivity(), CreateNewShelterActivity.class);
                startActivity(i);
                break;
            case R.id.button_admin_remove_shelter:
                Log.w("RemoveShelter:", "Worked");
                break;
            case R.id.parse_file:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity());
                // Adds Alert when logging out to make sure you want to
                builder.setTitle("Parse File?")
                        .setMessage("Are you sure you want to parse the file?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File("CS2340/app/src/main/res/raw/homeless");
                                //CSVParser parser = new CSVParser(file);
                                //List<Shelter> shelterList = parser.getShelterList();
                                //Log.d("Shelter", shelterList.get(0).getShelterName());
                                CSVParser(file);
                                //Log.d("Shelter", shelterList.get(0).getShelterName());
                                database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference();

                                for (int count = 0; count < shelterList.size(); count++) {
                                    String pushID = reference.child("shelters").push().getKey();
                                    shelterList.get(count).setPushKey(pushID);
                                    reference.child("shelters").child(pushID).setValue(shelterList.get(count));
                                }

                                Toast.makeText(getActivity(), "Shelters added", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                //closeContextMenu();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
        }
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
         * @param uri unused
         */
        void onFragmentInteraction(Uri uri);
    }




    public static int DEFAULT_CAPACITY = -1; //this can be a number if we just want a default instead of allowing them not to specify

    private List<Shelter> shelterList;

    private void CSVParser(File file) {
        shelterList = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.homeless);

            BufferedReader b = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            b.readLine();
            for (String line = ""; (line = b.readLine()) != null;) {
                line = parseLineForCommas(line.replaceAll("~", ""));
                String[] csLine = line.split("~");
                shelterList.add(new Shelter(
                        csLine[1],
                        Capacity.parseFromString(csLine[2]),
                        Restrictions.parseFrom(csLine[3]),
                        Double.parseDouble(csLine[4]),
                        Double.parseDouble(csLine[5]),
                        csLine[8],
                        csLine[6],
                        Integer.parseInt(csLine[0]),
                        csLine[7]
                ));
                //Log.d("Shelter", shelterList.get(0).getShelterName());
            }
            b.close();
        } catch (IOException e) {
            Log.e("An error ", "" + file);
            //e.printStackTrace();
        }
    }

    /**
     * List of shelters
     * @return list of shelters
     */
    public List<Shelter> getShelterList() {
        return shelterList;
    }

    /***
     * Current we do NOT have a way to use an escape character for quotation marks,
     * so any shelter with quotation marks in one of its categories will get messed up
     * @param line the line
     * @return the line parsed to replace commas not in a text field with ~ characters
     */
    private String parseLineForCommas(String line) {
        boolean ignore = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ',' && !ignore) {
                line = line.substring(0,i) + "~" + line.substring(i + 1);
            }
            if (c == '"') ignore = !ignore;
        }
        return line;
    }
}
