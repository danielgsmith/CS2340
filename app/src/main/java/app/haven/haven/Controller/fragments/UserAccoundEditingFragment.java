package app.haven.haven.Controller.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import app.haven.haven.Controller.activities.LoginActivity;
import app.haven.haven.Controller.activities.MainPageActivity;
import app.haven.haven.Controller.activities.RegisterActivity;
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

    private TextView textUserFirstName;
    private TextView firstNameText;
    private EditText editTextUserFirstName;
    private String oldFirstName;
    private String updatedFirstName;

    private TextView textUserLastName;
    private EditText editTextUserLastName;
    private String oldLastName;
    private String updatedLastName;

    private TextView textTelephoneNumber;
    private EditText editTextUserTelephoneNumber;
    private String oldTelephoneNumber;
    private String updatedTelephoneNumber;


    //buttons:
    private Button sendPasswordResetButton;
    private Button saveInfoButton;
    private Button editInfoButton;
    private Button cancelButton;

    private LinearLayout editAndPassword;
    private LinearLayout saveAndCancel;

    private boolean editing;
    private boolean emailInUse;

    /**
     * Account Editing Fragment
     */
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
        mUser = MainPageActivity.getUser();
        mAuth = FirebaseAuth.getInstance();
        mFireUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDataRef = database.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_accound_editing, container, false);

        //establish View:
        oldFirstName = mUser.getFirstName();
        textUserFirstName = view.findViewById(R.id.updated_first_name);
        textUserFirstName.setText(oldFirstName);

        oldLastName = mUser.getLastName();
        textUserLastName = view.findViewById(R.id.updated_last_name);
        textUserLastName.setText(oldLastName);

        oldEmail = mUser.getEmail();
        textEmail = view.findViewById(R.id.updated_email);
        textEmail.setText(oldEmail);

        oldTelephoneNumber = mUser.getTelephoneNumber();
        textTelephoneNumber = view.findViewById(R.id.updated_telephone_number);
        if (oldTelephoneNumber == null) {
            textTelephoneNumber.setText("No telephone added yet");
        } else {
            textTelephoneNumber.setText(oldTelephoneNumber);
        }

        editAndPassword = (LinearLayout) view.findViewById(R.id.Password_and_Edit);
        saveAndCancel = (LinearLayout) view.findViewById(R.id.Save_and_Cancel);

        //Initialize the editing screen, but still on the view screen
        editTextUserFirstName = view.findViewById(R.id.input_user_first_name);
        editTextUserFirstName.setText(oldFirstName);

        editTextUserLastName = view.findViewById(R.id.input_user_last_name);
        editTextUserLastName.setText(oldLastName);

        editTextUserEmail = view.findViewById(R.id.input_user_email);
        editTextUserEmail.setText(oldEmail);

        editTextUserTelephoneNumber = view.findViewById(R.id.input_user_telephone_number);
        editTextUserTelephoneNumber.setText("No telephone added yet");

        sendPasswordResetButton = (Button) view.findViewById(R.id.change_Password_Button);
        editInfoButton = (Button) view.findViewById(R.id.edit_Info_Button);
        saveInfoButton = (Button) view.findViewById(R.id.save_Info_Button);
        cancelButton = (Button) view.findViewById(R.id.cancel_Button);


        //BUTTON: SEND PASSWORD RESET EMAIL //////////////////////////////////
        sendPasswordResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendResetEmail();
            }
        });

        //BUTTON: EDIT ///////////////////////////////////////////////
        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Edit Info Button", "Clicked");

                editing = true;

                editAndPassword.setVisibility(View.GONE);
                saveAndCancel.setVisibility(View.VISIBLE);
//                editInfoButton.setVisibility(View.GONE);
//                saveInfoButton.setVisibility(View.VISIBLE);
//                cancelButton.setVisibility(View.VISIBLE);
//                sendPasswordResetButton.setVisibility(View.GONE);

                textUserFirstName.setVisibility((View.GONE));
                editTextUserFirstName.setVisibility(View.VISIBLE);
//                firstNameText.setGravity(10); //TODO FIX THIS!


                textUserLastName.setVisibility((View.GONE));
                editTextUserLastName.setVisibility(View.VISIBLE);

                textEmail.setVisibility((View.GONE));
                editTextUserEmail.setVisibility((View.VISIBLE));

                textTelephoneNumber.setVisibility((View.GONE));
                editTextUserTelephoneNumber.setVisibility((View.VISIBLE));
            }
        });

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Save Info Button", "Clicked");

                if (editing == true) {
//                    update everything and switch back to textView

                    editAndPassword.setVisibility(View.VISIBLE);
                    saveAndCancel.setVisibility(View.GONE);

//                    saveInfoButton.setVisibility(View.GONE);
//                    cancelButton.setVisibility(View.GONE);
//                    editInfoButton.setVisibility(View.VISIBLE);

                    updatedFirstName = editTextUserFirstName.getText().toString();
                    updatedLastName = editTextUserLastName.getText().toString();
                    updatedEmail = editTextUserEmail.getText().toString();
                    updatedTelephoneNumber = editTextUserTelephoneNumber.getText().toString();

                    mUser.setFirstName(updatedFirstName);
                    mUser.setLastName(updatedLastName);
                    mUser.setEmail(updatedEmail);
                    mUser.setTelephoneNumber(updatedTelephoneNumber);

                    editTextUserFirstName.setVisibility(View.GONE);
                    textUserFirstName.setVisibility((View.VISIBLE));
//                    editTextUserFirstName.setText(updatedFirstName);
//                    textUserFirstName.setText(updatedFirstName);

                    editTextUserLastName.setVisibility(View.GONE);
                    textUserLastName.setVisibility(View.VISIBLE);
//                    editTextUserLastName.setText(updatedLastName);
//                    textUserLastName.setText(updatedLastName);

                    editTextUserEmail.setVisibility((View.GONE));
                    textEmail.setVisibility((View.VISIBLE));

                    editTextUserTelephoneNumber.setVisibility((View.GONE));
                    textTelephoneNumber.setVisibility((View.VISIBLE));

                    updateInfo();
//                    editing = false;

                } else {
                    Toast.makeText(getActivity(), "Invalid Field Entered", Toast.LENGTH_SHORT).show();
//                    Log.w("TRUEORFALSE", editing + " " + readyToSwitch);
//                    System.out.println(editing + " " + readyToSwitch);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cancel Button", "Clicked");
                editAndPassword.setVisibility(View.VISIBLE);
                saveAndCancel.setVisibility(View.GONE);
//                saveInfoButton.setVisibility(View.GONE);
//                cancelButton.setVisibility(View.GONE);
//                editInfoButton.setVisibility(View.VISIBLE);
//                sendPasswordResetButton.setVisibility(View.VISIBLE);

                editTextUserFirstName.setVisibility(View.GONE);
                textUserFirstName.setVisibility((View.VISIBLE));

                editTextUserLastName.setVisibility(View.GONE);
                textUserLastName.setVisibility(View.VISIBLE);

                editTextUserEmail.setVisibility((View.GONE));
                textEmail.setVisibility((View.VISIBLE));

                editTextUserTelephoneNumber.setVisibility((View.GONE));
                textTelephoneNumber.setVisibility((View.VISIBLE));

                editing = false;
            }
        });


//        mDataRef.child("users").child(mFireUser.getUid()).addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) { //data snapshot is where in the database you are
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        return view;
    }

    /**
     * unused
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

    /**
     * checks if individual fields were actually altered and then changes the view
     */
    public void updateInfo() {
        if (!oldFirstName.equals(updatedFirstName)) {
            textUserFirstName.setText(updatedFirstName);
            editTextUserFirstName.setText(updatedFirstName);
            mUser.setFirstName(updatedFirstName);
            oldFirstName = mUser.getFirstName();
            mDataRef.child("users").child(mFireUser.getUid()).child("firstName").setValue(updatedFirstName);
//            readyToSwitsch = true;
        }

        if (!oldLastName.equals(updatedLastName)) {
            textUserLastName.setText(updatedLastName);
            editTextUserLastName.setText(updatedLastName);
            mUser.setLastName(updatedLastName);
            oldLastName = updatedLastName;
            mDataRef.child("users").child(mFireUser.getUid()).child("lastName").setValue(updatedLastName);
//            readyToSwitsch = true;
        }

        if (!isEmailCorrect(updatedEmail)) {
            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
            textEmail.setText(oldEmail);
            editTextUserEmail.setText(oldEmail);
            mUser.setEmail(oldEmail);
            editing = true;
        } else if (isEmailCorrect(updatedEmail) && !oldEmail.equals((updatedEmail))) {
            checkEmailInUse(updatedEmail);
            if (emailInUse) {
                Log.d("Check Email", emailInUse + " ");
                editing = true;
            } else {
                Log.d("Check Email", emailInUse + " ");
                textEmail.setText(updatedEmail);
                editTextUserEmail.setText(updatedEmail);
                mUser.setEmail(updatedEmail);
                oldEmail = updatedEmail;
                mDataRef.child("users").child(mFireUser.getUid()).child("email").setValue(updatedEmail);
                mFireUser.updateEmail(updatedEmail);
                String emailWithout = updatedEmail.replace(".", "|");
                mDataRef.child("emailtouid").child(emailWithout).setValue(mFireUser.getUid());

                editing = false;
            }

        }


        if (updatedTelephoneNumber == null) {
            textTelephoneNumber.setText("No telephone added yet");
            editTextUserTelephoneNumber.setText("No telephone added yet");
            oldTelephoneNumber = "No telephone added yet";

        } else {
            textTelephoneNumber.setText(updatedTelephoneNumber);
            editTextUserTelephoneNumber.setText(updatedTelephoneNumber);
            oldTelephoneNumber = updatedTelephoneNumber;
        }

    }


    public boolean isEmailCorrect(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email passed in is null");
        }
        return !email.isEmpty() && email.contains("@") && email.contains(".");
    }

    private void checkEmailInUse(String email) {
        mAuth.fetchProvidersForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                        emailInUse = !task.getResult().getProviders().isEmpty();

//                        if(emailInUse) {
//                            wrong = true;
//                            Toast.makeText(getActivity(), "Email already in use.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
////                        else {
////                            Toast.makeText(getActivity(), "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
                    }
                });
    }

    private void sendResetEmail(){
        mAuth.sendPasswordResetEmail(mUser.getEmail());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Password Reset Email Sent!")
                .setMessage("Follow the instructions in your email to reset your password")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).show();

    }
}
