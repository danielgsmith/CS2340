package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.haven.haven.Controller.activities.LoginActivity;
import app.haven.haven.Controller.activities.MainPageActivity;
import app.haven.haven.Model.User;
import app.haven.haven.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAccoundEditingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccoundEditingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccoundEditingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseUser mFireUser;
    private DatabaseReference mDataRef;
    private FirebaseAuth mAuth;
    private User mUser;

    private OnFragmentInteractionListener mListener;

    private View view;

    private TextView textEmail;
    private EditText editTextUserEmail;
    private String oldEmail;
    private String updatedEmail;

    private TextView textUserName;
    private EditText editTextUserName;
    private String oldName;
    private String updatedName;

    private TextView textTelephoneNumber;
    private EditText editTextUserTelephoneNumber;
    private String oldTelephoneNumber;
    private String updatedTelephoneNumber;


    //buttons:
    private Button saveInfoButton;
    private Button editInfoButton;

    private boolean editing;



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
        mFireUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDataRef = database.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_accound_editing, container, false);
        editTextUserName = view.findViewById(R.id.input_user_name);
        editTextUserEmail = view.findViewById(R.id.input_user_email);
        editTextUserTelephoneNumber = view.findViewById(R.id.input_user_telephone_number);
        textUserName = view.findViewById(R.id.textUserName);
        textEmail = view.findViewById(R.id.textUserEmail);
        textTelephoneNumber = view.findViewById(R.id.textUserTelephoneNumber);

        textUserName.setText(mUser.getFirstName() + " " + mUser.getLastName());
        textEmail.setText(mUser.getEmail());
        textTelephoneNumber.setText("No telephone number added");

        editTextUserName.setText(mUser.getFirstName() + " " + mUser.getLastName());
        editTextUserEmail.setText(mUser.getEmail());
        editTextUserTelephoneNumber.setText("No telephone number added");

        editInfoButton = (Button) view.findViewById(R.id.edit_Info_Button);
        saveInfoButton = (Button) view.findViewById(R.id.save_Info_Button);

        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editing = true;

                textUserName.setVisibility((View.GONE));
                editTextUserName.setVisibility(View.VISIBLE);

                textEmail.setVisibility((View.GONE));
                editTextUserEmail.setVisibility((View.VISIBLE));

                textTelephoneNumber.setVisibility((View.GONE));
                editTextUserTelephoneNumber.setVisibility((View.VISIBLE));
            }
        });

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editing == true) {
//                    update everything and switch back to textView

                    updatedName = editTextUserName.getText().toString();
                    updatedEmail = editTextUserEmail.getText().toString();
                    updatedTelephoneNumber = editTextUserTelephoneNumber.getText().toString();

                    editing = false;

                    editTextUserName.setVisibility(View.GONE);
                    textUserName.setVisibility((View.VISIBLE));

                    editTextUserEmail.setVisibility((View.GONE));
                    textEmail.setVisibility((View.VISIBLE));

                    editTextUserTelephoneNumber.setVisibility((View.GONE));
                    textTelephoneNumber.setVisibility((View.VISIBLE));

                    updateInfo();

                } else {
                    //throw an exception TOAST?
                }

            }
        });


        mDataRef.child("users").child(mFireUser.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //data snapchot is where in the database you are
                mUser = dataSnapshot.getValue(User.class);
                textUserName.setText(mUser.getFirstName() + " " + mUser.getLastName());
                oldName = mUser.getFirstName() + " " + mUser.getLastName();
                textEmail.setText(mUser.getEmail());
                oldEmail = mUser.getEmail();
                String tNum = mUser.getTelephoneNumber();
                if (tNum == null) {
                    textTelephoneNumber.setText("No telephone added yet");
                } else {
                    textTelephoneNumber.setText(mUser.getTelephoneNumber());
                }
                oldTelephoneNumber = textTelephoneNumber.getText().toString();


                //update Info
                //anything that uses the updated info
                //set text boxes to equal current info
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
//        switch (view.getId()) {
//            case R.id.update_button:
//                Log.w("Update :", "Worked");
////                Intent i = new Intent(getActivity(), ShowUserAccountActivity.class);
////                startActivity(i);
//                break;
////            case R.id.button_admin_remove_shelter:
////                Log.w("RemoveShelter:", "Worked");
//        }
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

        if (!oldName.equals(updatedName)) {
            textUserName.setText(updatedName);
            mUser.setFirstName("JOE"); //TODO fix this first and last Name
        }

        if (!oldEmail.equals(updatedEmail)) {
            editTextUserEmail.setText(updatedEmail);
            mUser.setEmail(updatedEmail);
        }
        //if old is not equal to the new, then update User and setText
//
//        mUser.setFirstName(newName);
//                mUser.setLastName(newLastName);
//                mUser.setEmail(newEmail);
//                mUser.setPassword(newPasswod);
//                mUser.setAccountType(newAccountType);
////                //same for fireuser?
//////                update the screen!

        /**
         * TODO:is this needed? what if they only edit one thing
         * TODO:have these as private variables at the top and update when the user actually changes them
         */

//        String updatedName = EditTextUserName.getText().toString();
//        String updatedEmail = EditTextUserEmail.getText().toString();
//        String updatedPassword = EditTextUserPassword.getText().toString();
//        String updatedAccountType = EditTextUserAccountType.getText().toString();
////        EditTextUserName.setSelection(updatedName);

//        EditTextUserEmail.setSelection(newEmail);
//        EditTextUserPassword.setSelection(newPassword);
//        EditTextUserAccountType.setSelection(newAccountType);

    }

//    private void sendResetEmail(){
//        mAuth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Reset Email sent.",
//                                    Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "Email sent.");
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Reset Failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}
