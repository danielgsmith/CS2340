package app.haven.haven;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAccoundEditingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccoundEditingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccoundEditingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseUser mFireUser;
    private DatabaseReference mDataRef;
    private User mUser;

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText EditTextUserName;
    private String updatedName;
    private EditText EditTextUserEmail;
    private String updatedEmail;
    private EditText EditTextUserPassword;
    private String updatedPassword;
    private EditText EditTextUserAccountType;
    private String updatedAccountType;
    private Button updateUserInfoButton;


    public UserAccoundEditingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccoundEditingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccoundEditingFragment newInstance(String param1, String param2) {
        UserAccoundEditingFragment fragment = new UserAccoundEditingFragment();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_accound_editing, container, false);
        EditTextUserName = view.findViewById(R.id.input_user_name);
        EditTextUserEmail = view.findViewById(R.id.input_user_email);
        EditTextUserPassword = view.findViewById(R.id.input_user_password); //make this numericPassword inputType
        EditTextUserAccountType = view.findViewById(R.id.input_user_account_type);

        updateUserInfoButton = (Button) view.findViewById(R.id.update_button);
        updateUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo();

//              searchedName = textName.getText().toString();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_button:
                Log.w("Update :", "Worked");
//                Intent i = new Intent(getActivity(), ShowUserAccountActivity.class);
//                startActivity(i);
                break;
//            case R.id.button_admin_remove_shelter:
//                Log.w("RemoveShelter:", "Worked");
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateInfo() {
//        mUser.setFirstName(newName);
//                mUser.setLastName(newLastName);
//                mUser.setEmail(newEmail);
////                mUser.setPassword(newPasswod);
//                mUser.setAccountType(newAccountType);
//                //same for fireuser?
////                update the screen!

        /**
         * TODO:is this needed? what if they only edit one thing
         * TODO:have these as private variables at the top and update when the user actually changes them
         */

        String updatedName = EditTextUserName.getText().toString();
        String updatedEmail = EditTextUserEmail.getText().toString();
        String updatedPassword = EditTextUserPassword.getText().toString();
        String updatedAccountType = EditTextUserAccountType.getText().toString();
//        EditTextUserName.setSelection(updatedName);

//        EditTextUserEmail.setSelection(newEmail);
//        EditTextUserPassword.setSelection(newPassword);
//        EditTextUserAccountType.setSelection(newAccountType);

    }
}
