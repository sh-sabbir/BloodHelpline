package com.isabbir.bloodhelpline;

import android.net.Uri;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.Manifest;

public class donorDetails extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_details);

        String donorID = getIntent().getStringExtra("DonorID");

        Button call_now =  (Button) findViewById(R.id.dd_callNow);

        TextView donorName     = (TextView)findViewById(R.id.dDetails_name);
        TextView donorAge      = (TextView)findViewById(R.id.dDetails_age);
        TextView donorAddress  = (TextView)findViewById(R.id.dDetails_address);
        TextView donorPhone    = (TextView)findViewById(R.id.dDetails_phone);
        //TextView donorGroup    = (TextView)findViewById(R.id.dD);
        TextView donorDonated  = (TextView)findViewById(R.id.dDetails_lastDonate);
        TextView donorNxtDnt   = (TextView)findViewById(R.id.dDetails_nextDonate);
        TextView NxtDntDayLeft = (TextView)findViewById(R.id.dDetails_dayLeft);


        DBhandler db = new DBhandler(this);
        Cursor cursor = db.getDonorInfo(donorID);

        if (cursor == null)
            return;

        try{
            if (cursor.moveToFirst()) { // Here we try to move to the first record
                //edittext.setText(cursor_2.getString(2)); // Only assign string value if we moved to first record
                String dname = cursor.getString(cursor.getColumnIndex("name"));
                String dage = cursor.getString(cursor.getColumnIndex("age"));
                String daddress = cursor.getString(cursor.getColumnIndex("address"));
                final String dphone = cursor.getString(cursor.getColumnIndex("phone"));
                String dbGroup = cursor.getString(cursor.getColumnIndex("blood_group"));
                String dlDonated = cursor.getString(cursor.getColumnIndex("last_donated"));

                donorName.setText(dname);
                donorAge.setText(dage);
                donorAddress.setText(daddress);
                donorPhone.setText(dphone);
                //donorGroup.setText(dbGroup);
                donorDonated.setText(dlDonated);
                donorNxtDnt.setText("");
                NxtDntDayLeft.setText("");

                call_now.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                       final Integer pnumber = Integer.parseInt(dphone);
                        onCall(pnumber);
                    }
                });
            }
        }finally {
            cursor.close();
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void onCall(Integer pNum) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+ pNum)));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //onCall(123);
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}
