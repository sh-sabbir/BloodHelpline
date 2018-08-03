package com.isabbir.bloodhelpline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabbir.bloodhelpline.Helpers.DateValidator;
import com.isabbir.bloodhelpline.Models.Donor;

public class ActivityNewDonor extends AppCompatActivity {

    RadioGroup bGroup1,bGroup2;
    RadioButton donBloodGroup;
    Button saveDonorBtn;
    private boolean isChecking = true;
    private int mCheckedId = R.id.ap;
    private DateValidator dateValidator;

    private String isConnected;
    EditText donName,donAge,donPhone,donDept,donRegNo,donSession,donLastDonate;
    CheckBox donEverDonate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nd);

        bGroup1 = findViewById(R.id.bGroup1);
        bGroup2 = findViewById(R.id.bGroup2);
        saveDonorBtn = findViewById(R.id.dSave);

        dateValidator = new DateValidator();

        donName = findViewById(R.id.dName);
        donAge = findViewById(R.id.dAge);
        donPhone = findViewById(R.id.dPhone);
        donDept = findViewById(R.id.dDept);
        donRegNo = findViewById(R.id.dReg);
        donSession = findViewById(R.id.dSession);
        donLastDonate = findViewById(R.id.lastDonate);

        donEverDonate = findViewById(R.id.neverDonate);

        bGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    bGroup2.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });
        bGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    bGroup1.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });


        donLastDonate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String ldDate= donLastDonate.getText().toString().trim();

                    boolean valid = dateValidator.validate(ldDate);
                    System.out.println("Date is valid : " + ldDate + " , " + valid);

                }
            }
        });

        saveDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDonor(mCheckedId);
            }
        });

    }

    private void saveDonor(int mCheckedId){
        String fName,dAge,dPhone,dDept,dRegNo,dSession,dBloodGroup,dLastDonate;
        boolean neverDonated;
        donBloodGroup = (RadioButton) findViewById(mCheckedId);

        fName = donName.getText().toString().trim();
        dAge = donAge.getText().toString().trim();
        dPhone = donPhone.getText().toString().trim();
        dDept = donDept.getText().toString().trim();
        dRegNo = donRegNo.getText().toString().trim();
        dSession = donSession.getText().toString().trim();
        dBloodGroup = donBloodGroup.getText().toString();
        dLastDonate = donLastDonate.getText().toString().trim();

        neverDonated = donEverDonate.isChecked();


        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                    isConnected = "true";
                } else {
                    System.out.println("not connected");
                    isConnected = "false";
                    Toast.makeText(ActivityNewDonor.this, "Data Will Be Saved :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("donors");
        String userId = mDatabase.push().getKey();

        Donor donor = new Donor(fName,dAge,dPhone,dDept,dRegNo,dSession,dBloodGroup,dLastDonate,neverDonated);
        //mDatabase.child(userId).setValue(donor);
        mDatabase.child(userId).setValue(donor, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if(isConnected.equals("true")) {
                    if (error != null) {
                        System.out.println("Data could not be saved. " + error.getMessage());
                    } else {
                        Toast.makeText(ActivityNewDonor.this, "Data Saved :)", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(ActivityNewDonor.this, "Data Will Be Saved :)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearFields();
    }

    private void clearFields(){

        bGroup1.clearCheck();
        bGroup2.clearCheck();

        donName.setText("");
        donAge.setText("");
        donPhone.setText("");
        donDept.setText("");
        donRegNo.setText("");
        donSession.setText("");
        donLastDonate.setText("");

        donEverDonate.setChecked(false);
    }
}
