package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplicationlab.Model.ProviderModel;
import com.example.myapplicationlab.Model.RequestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText noBed,Bemail,pName,phoneNo,address;
    private LinearLayout request;
    private Toolbar mtoolbar;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;
    String hosName,CGBP,NCGBP,CIGBP,NCIGBP,uid2;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Booking your Bed");
        setSupportActionBar(mtoolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroup = (RadioGroup)findViewById(R.id.bedType);
        noBed = (EditText)findViewById(R.id.noOfBookingBed);
        Bemail = (EditText)findViewById(R.id.email);
        pName = (EditText)findViewById(R.id.patient);
        phoneNo = (EditText)findViewById(R.id.phoneNo);
        address = (EditText)findViewById(R.id.patientAddress);

        hosName = getIntent().getStringExtra("hosName");
        CGBP = getIntent().getStringExtra("CGBP");
        NCGBP = getIntent().getStringExtra("NCGBP");
        CIGBP = getIntent().getStringExtra("CIGBP");
        NCIGBP = getIntent().getStringExtra("NCIGBP");
        uid2 = getIntent().getStringExtra("uid");
        position = getIntent().getIntExtra("position",0);
        request = (LinearLayout)findViewById(R.id.requestBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("request");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("admin");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("allRequest");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int select = radioGroup.getCheckedRadioButtonId();
                String noBed2 = noBed.getText().toString();
                String email2 = Bemail.getText().toString();
                String pName2 = pName.getText().toString();
                String phoneNo2 = phoneNo.getText().toString();
                String address2 = address.getText().toString();



                if(TextUtils.isEmpty(pName2))
                {
                    pName.setError("Required");
                    return;

                }
                if(TextUtils.isEmpty(address2))
                {
                    address.setError("Required");
                    return;

                }
                if(TextUtils.isEmpty(email2))
                {
                    Bemail.setError("Required");
                    return;

                }
                if(TextUtils.isEmpty(phoneNo2))
                {
                    phoneNo.setError("Required");
                    return;

                }
                if(TextUtils.isEmpty(noBed2))
                {
                    noBed.setError("Required");
                    return;

                }


                String requestStatus = "Pending";


                String type = "";
                int price = 1;


                Toast.makeText(BookingActivity.this,"requesting...",Toast.LENGTH_SHORT).show();

                if(select == R.id.bedType1)
                {
                    type +="Covid General Bed";

                    if(!TextUtils.isEmpty(CGBP))
                    {
                        price*= Integer.parseInt(CGBP);


                    }
                }
                if(select == R.id.bedType2)
                {
                    type +="Non Covid General Bed";
                    if(!TextUtils.isEmpty(NCGBP))
                    {
                        price*= Integer.parseInt(NCGBP);
                    }
                }
                if(select == R.id.bedType3)
                {
                    type +="Covid ICU General Bed";
                    if(!TextUtils.isEmpty(CIGBP))
                    {
                        price*= Integer.parseInt(CIGBP);
                    }

                }
                if(select == R.id.bedType4) {
                    type += "Non Covid ICU General Bed";
                    if (!TextUtils.isEmpty(NCIGBP)) {
                        price *= Integer.parseInt(NCIGBP);
                    }

                }

                saveData(hosName,pName2,address2,email2,phoneNo2,type,noBed2,requestStatus,price,uid2);

                Intent intent = new Intent(BookingActivity.this,HospitalDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("userId",uid2);
                startActivity(intent);
                finish();



            }
        });
    }


    private void saveData(String hosName,String pName,String address, String email,String phoneNo, String type,String noBed,String status,int price,String hosId) {

       String uid = fAuth.getCurrentUser().getUid();
       String key = databaseReference.push().getKey();

        price*=Integer.parseInt(noBed);

        RequestModel requestModel = new RequestModel(key,uid,hosName,pName,address,email,phoneNo,type,noBed,status,""+price,hosId);


        databaseReference.child(uid).child(hosId).child(key).setValue(requestModel);

        databaseReference2.child(hosId).child(key).setValue(requestModel);
        databaseReference3.child(uid).child(key).setValue(requestModel);

        Toast.makeText(BookingActivity.this,"Request has been sent",Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(BookingActivity.this,HospitalDetailsActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        finish();



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(BookingActivity.this,HospitalDetailsActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


   @Override
    public void onBackPressed() {

        Intent intent = new Intent(BookingActivity.this,HospitalDetailsActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        finish();


    }

}