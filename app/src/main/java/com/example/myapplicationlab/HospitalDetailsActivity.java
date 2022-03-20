package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationlab.Model.MyAdapter;
import com.example.myapplicationlab.Model.ProviderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HospitalDetailsActivity extends AppCompatActivity {

    TextView hosName,hospitalAddress,totalICU,CovidGeneralBed,CovidGeneralBedPrice,nonCovidGeneralBed,nonCovidGeneralBedPrice,CovidICUGeneralBed,CovidICUGeneralBedPrice,nonCovidICUGeneralBed,nonCovidGeneralICUBedPrice;
    LinearLayout call,mail,call_email,provider_layout,updateLayout,sUpdateLayout,requestPageLayout,booking_layout,requestBed,finder_layout;
    FirebaseAuth fAuth;
    private Toolbar mtoolbar;
    int position;
    String key,hospiname2,hospiadd2,district2,contact2,totalICUBed2,availICUBed2,CGB2,CGBP2,NCGB2,NCGBP2,CIGB2,CIGBP2,NCIGB2,NCIGBP2,email2,userId;
    List<ProviderModel>providerlist = new ArrayList<>();

    //String fromToHospiDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Hospital Details");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        hosName = (TextView)findViewById(R.id.hospitalName);
        hospitalAddress = (TextView)findViewById(R.id.hospitalAddress);
        totalICU = (TextView)findViewById(R.id.totalICU);
        CovidGeneralBed = (TextView)findViewById(R.id.CovidGeneralBed);
        CovidGeneralBedPrice = (TextView)findViewById(R.id.CovidGeneralBedPrice);
        nonCovidGeneralBed = (TextView)findViewById(R.id.nonCovidGeneralBed);
        nonCovidGeneralBedPrice = (TextView)findViewById(R.id.nonCovidGeneralBedPrice);
        CovidICUGeneralBed = (TextView)findViewById(R.id.CovidICUGeneralBed);
        CovidICUGeneralBedPrice = (TextView)findViewById(R.id.CovidICUGeneralBedPrice);
        nonCovidICUGeneralBed = (TextView)findViewById(R.id.nonCovidICUGeneralBed);
        nonCovidGeneralICUBedPrice = (TextView)findViewById(R.id.nonCovidGeneralICUBedPrice);


        call = (LinearLayout)findViewById(R.id.call);
        mail = (LinearLayout)findViewById(R.id.email);
        call_email = (LinearLayout)findViewById(R.id.call_email);
        updateLayout = (LinearLayout) findViewById(R.id.updateLayout);
        sUpdateLayout = (LinearLayout) findViewById(R.id.sUpdateLayout);
        provider_layout = (LinearLayout) findViewById(R.id.provider_layout);
        requestPageLayout = (LinearLayout) findViewById(R.id.requestPageLayout);
        booking_layout = (LinearLayout) findViewById(R.id.booking_layout);
        requestBed = (LinearLayout) findViewById(R.id.requestBed);
        finder_layout = (LinearLayout) findViewById(R.id.finder_layout);



        fAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        userId = intent.getStringExtra("userId");
        position = intent.getIntExtra("position",0);

        //fromToHospiDet = getIntent().getStringExtra("fromhos");


        //Toast.makeText(HospitalDetailsActivity.this,"userId: "+userId+"\n"+fAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
        LoadData();

        //  Toast.makeText(HospitalDetailsActivity.this,"userId: "+userId+"\n"+fAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();


        if (fAuth.getCurrentUser().getUid().equals(userId)) {

            provider_layout.setVisibility(View.VISIBLE);
            call_email.setVisibility(View.GONE);
            call.setVisibility(View.GONE);
            mail.setVisibility(View.GONE);
            finder_layout.setVisibility(View.GONE);

            //  Toast.makeText(HospitalDetailsActivity.this,"hello",Toast.LENGTH_SHORT).show();

            updateLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent1 = new Intent(HospitalDetailsActivity.this, Provider_registration.class);
                    intent1.putExtra("update", "update");
                    intent1.putExtra("position", position);
                    startActivity(intent1);

                }
            });
            sUpdateLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(HospitalDetailsActivity.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View view2 = layoutInflater.inflate(R.layout.update_info_layout, null);
                    builder.setView(view2);

                    RadioGroup radioGroup2 = (RadioGroup) view2.findViewById(R.id.radioGroup);

                    EditText noOfBed = (EditText) view2.findViewById(R.id.sBnoOfBookingBed);
                    Button addBtn = view2.findViewById(R.id.add);
                    Button subBtn = view2.findViewById(R.id.sub);


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    subBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int select = radioGroup2.getCheckedRadioButtonId();


                            if (select == R.id.sBedType1 && (!noOfBed.getText().toString().isEmpty())) {

                                minusData(position, noOfBed.getText().toString(), "a");
                            }
                            if (select == R.id.sBedType2 && (!noOfBed.getText().toString().isEmpty())) {
                                minusData(position, noOfBed.getText().toString(), "b");
                            }
                            if (select == R.id.sBedType3 && (!noOfBed.getText().toString().isEmpty())) {

                                minusData(position, noOfBed.getText().toString(), "c");
                            }
                            if (select == R.id.sBedType4 && (!noOfBed.getText().toString().isEmpty())) {
                                minusData(position, noOfBed.getText().toString(), "d");
                            }
                            alertDialog.dismiss();
                            Toast.makeText(HospitalDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    });

                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int select = radioGroup2.getCheckedRadioButtonId();

                            if (select == R.id.sBedType1 && (!noOfBed.getText().toString().isEmpty())) {

                                addData(position, noOfBed.getText().toString(), "a");
                            }
                            if (select == R.id.sBedType2 && (!noOfBed.getText().toString().isEmpty())) {
                                addData(position, noOfBed.getText().toString(), "b");
                            }
                            if (select == R.id.sBedType3 && (!noOfBed.getText().toString().isEmpty())) {

                                addData(position, noOfBed.getText().toString(), "c");
                            }
                            if (select == R.id.sBedType4 && (!noOfBed.getText().toString().isEmpty())) {
                                addData(position, noOfBed.getText().toString(), "d");
                            }
                            alertDialog.dismiss();
                            Toast.makeText(HospitalDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }
        else{
            provider_layout.setVisibility(View.GONE);
            call_email.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
            mail.setVisibility(View.VISIBLE);
            finder_layout.setVisibility(View.VISIBLE);
        }



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:"+contact2;
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse(s));
                startActivity(i);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalDetailsActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View view2 = layoutInflater.inflate(R.layout.email_layout,null);
                builder.setView(view2);

                EditText subject = (EditText) view2.findViewById(R.id.subject);
                EditText message = (EditText) view2.findViewById(R.id.message);
                Button sendBtn= (Button) view2.findViewById(R.id.send);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String subject2 = subject.getText().toString();
                        String[]receive = {email2};
                        String message2 = message.getText().toString();


                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.putExtra(Intent.EXTRA_EMAIL,receive);
                        intent1.putExtra(Intent.EXTRA_SUBJECT,subject2);
                        intent1.putExtra(Intent.EXTRA_TEXT,message2);
                        intent1.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent1,"Choose an Email Client"));
                    }
                });

            }
        });

        booking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HospitalDetailsActivity.this,BookingActivity.class);
                intent.putExtra("hosName",hospiname2);
                intent.putExtra("CGBP",CGBP2);
                intent.putExtra("NCGBP",NCGBP2);
                intent.putExtra("CIGBP",CIGBP2);
                intent.putExtra("NCIGBP",NCIGBP2);
                intent.putExtra("uid",userId);
                //  Toast.makeText(HospitalDetailsActivity.this,"hosName: "+hospiname2,Toast.LENGTH_SHORT).show();
                intent.putExtra("position",position);
                startActivity(intent);
                finish();
            }
        });
        requestBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HospitalDetailsActivity.this,FinderRequestActivity.class);
                intent.putExtra("uid",userId);
                intent.putExtra("activity","HospitalDetails");
                intent.putExtra("position",position);
                startActivity(intent);
                finish();
            }
        });

        requestPageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalDetailsActivity.this,AdminRequestActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("position",position);
                intent.putExtra("activity","HospitalDetails");
                startActivity(intent);
                finish();
            }
        });


    }

    private void LoadData() {

        Intent intent = getIntent();


        userId = intent.getStringExtra("userId");
        position = intent.getIntExtra("position",0);




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providerlist.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    ProviderModel data = ds.getValue(ProviderModel.class);
                    providerlist.add(data);
                }

                ProviderModel providerModel = providerlist.get(position);
                hosName.setText(providerModel.getHospiname());
                hospiname2 = providerModel.getHospiname();
                hospitalAddress.setText(providerModel.getHospiadd());
                totalICU.setText(providerModel.getTotalICUBed());
                CovidGeneralBed.setText(providerModel.getCGB());
                CovidGeneralBedPrice.setText(providerModel.getCGBP()+" tk");
                nonCovidGeneralBed.setText(providerModel.getNCGB());
                nonCovidGeneralBedPrice.setText(providerModel.getNCGBP()+" tk");
                CovidICUGeneralBed.setText(providerModel.getCIGB());
                CovidICUGeneralBedPrice.setText(providerModel.getCIGBP()+" tk");
                nonCovidICUGeneralBed.setText(providerModel.getNCIGB());
                nonCovidGeneralICUBedPrice.setText(providerModel.getNCIGBP()+" tk");
                userId = providerModel.getUserId();


                hospiname2 =providerModel.getHospiname();
                hospiadd2 = providerModel.getHospiadd();
                district2 = providerModel.getDistrict();
                contact2 = providerModel.getContact();
                totalICUBed2 = providerModel.getTotalICUBed();
                availICUBed2 = providerModel.getAvailICUBed();
                CGB2 = providerModel.getCGB();
                CGBP2 = providerModel.getCGBP();
                NCGB2 = providerModel.getNCGB();
                NCGBP2 = providerModel.getNCGBP();
                CIGB2 = providerModel.getCIGB();
                CIGBP2 = providerModel.getCIGBP();
                NCIGB2 = providerModel.getNCIGB();
                NCIGBP2 = providerModel.getNCIGBP();
                email2 = providerModel.getEmail();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HospitalDetailsActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void minusData(int position, String bed,String s) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providerlist.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    ProviderModel data = ds.getValue(ProviderModel.class);
                    providerlist.add(data);
                }

                ProviderModel providerModel = providerlist.get(position);
                String id = providerModel.getId();
                String hosName = providerModel.getHospiname();
                String email = providerModel.getEmail();
                String userId = providerModel.getUserId();
                String hospiadd = providerModel.getHospiadd();
                String district = providerModel.getDistrict();
                String totalICUBed = providerModel.getTotalICUBed();
                String contact = providerModel.getContact();
                String availICUBed = providerModel.getAvailICUBed();
                String CGB = providerModel.getCGB();
                String CGBP = providerModel.getCGBP();
                String NCGB = providerModel.getNCGB();
                String NCGBP = providerModel.getNCGBP();
                String CIGB = providerModel.getCIGB();
                String CIGBP = providerModel.getCIGBP();
                String NCIGB = providerModel.getNCIGB();
                String NCIGBP = providerModel.getNCIGBP();

                ProviderModel providerModel1 = null;

                if(s.equals("a") && (Integer.parseInt(availICUBed)>=Integer.parseInt(bed)) && (Integer.parseInt(CGB)>=Integer.parseInt(bed)) && Integer.parseInt(bed)>0)
                {

                    int av = Integer.parseInt(availICUBed)- Integer.parseInt(bed);
                    int cgb = Integer.parseInt(CGB)- Integer.parseInt(bed);
                    providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,""+cgb,CGBP,NCGB,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userId);
                }
                if(s.equals("b") && (Integer.parseInt(availICUBed)>=Integer.parseInt(bed)) && (Integer.parseInt(NCGB)>=Integer.parseInt(bed)) && Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed)- Integer.parseInt(bed);
                    int ncgb = Integer.parseInt(NCGB)- Integer.parseInt(bed);
                    providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,""+ncgb,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userId);
                }
                if(s.equals("c") && (Integer.parseInt(availICUBed)>=Integer.parseInt(bed)) && (Integer.parseInt(CIGB)>=Integer.parseInt(bed)) && Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed)- Integer.parseInt(bed);
                    int cigb = Integer.parseInt(CIGB)- Integer.parseInt(bed);
                    providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,NCGB,NCGBP,""+cigb,CIGBP,NCIGB,NCIGBP,email,userId);
                }
                if(s.equals("d") && (Integer.parseInt(availICUBed)>=Integer.parseInt(bed)) && (Integer.parseInt(NCIGB)>=Integer.parseInt(bed)) && Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed)- Integer.parseInt(bed);
                    int ncigb = Integer.parseInt(NCIGB)- Integer.parseInt(bed);
                    providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,NCGB,NCGBP,CIGB,CIGBP,""+ncigb,NCIGBP,email,userId);
                }

                databaseReference.child(providerModel.getId()).setValue(providerModel1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HospitalDetailsActivity.this,"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void addData(int position, String bed,String s) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providerlist.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    ProviderModel data = ds.getValue(ProviderModel.class);
                    providerlist.add(data);
                }

                ProviderModel providerModel = providerlist.get(position);
                String id = providerModel.getId();
                String hosName = providerModel.getHospiname();
                String email = providerModel.getEmail();
                String userId = providerModel.getUserId();
                String hospiadd = providerModel.getHospiadd();
                String district = providerModel.getDistrict();
                String totalICUBed = providerModel.getTotalICUBed();
                String contact = providerModel.getContact();
                String availICUBed = providerModel.getAvailICUBed();
                String CGB = providerModel.getCGB();
                String CGBP = providerModel.getCGBP();
                String NCGB = providerModel.getNCGB();
                String NCGBP = providerModel.getNCGBP();
                String CIGB = providerModel.getCIGB();
                String CIGBP = providerModel.getCIGBP();
                String NCIGB = providerModel.getNCIGB();
                String NCIGBP = providerModel.getNCIGBP();


                if(s.equals("a") && Integer.parseInt(bed)>0)
                {

                    int av = Integer.parseInt(availICUBed) + Integer.parseInt(bed);
                    int cgb = Integer.parseInt(CGB) + Integer.parseInt(bed);
                    int total = cgb + Integer.parseInt(NCGB) + Integer.parseInt(CIGB) + Integer.parseInt(NCIGB);

                    if(av<=Integer.parseInt(totalICUBed) && total<=Integer.parseInt(totalICUBed))
                    {
                        ProviderModel  providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,""+cgb,CGBP,NCGB,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userId);
                        databaseReference.child(providerModel.getId()).setValue(providerModel1);
                    }
                    else{
                        Toast.makeText(HospitalDetailsActivity.this,"Exceede your total bed",Toast.LENGTH_SHORT).show();
                    }
                }
                if(s.equals("b") &&  Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed) + Integer.parseInt(bed);
                    int ncgb = Integer.parseInt(NCGB) + Integer.parseInt(bed);
                    int total = ncgb + Integer.parseInt(CGB) + Integer.parseInt(CIGB) + Integer.parseInt(NCIGB);

                    if(av<=Integer.parseInt(totalICUBed) && total<=Integer.parseInt(totalICUBed))
                    {
                        ProviderModel  providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,""+ncgb,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userId);
                        databaseReference.child(providerModel.getId()).setValue(providerModel1);
                    }
                    else{
                        Toast.makeText(HospitalDetailsActivity.this,"Exceede your total bed",Toast.LENGTH_SHORT).show();
                    }
                }
                if(s.equals("c") && Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed) + Integer.parseInt(bed);
                    int cigb = Integer.parseInt(CIGB) + Integer.parseInt(bed);
                    int total = cigb + Integer.parseInt(NCGB) + Integer.parseInt(CGB) + Integer.parseInt(NCIGB);

                    if(av<=Integer.parseInt(totalICUBed) && total<=Integer.parseInt(totalICUBed))
                    {
                        ProviderModel providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,NCGB,NCGBP,""+cigb,CIGBP,NCIGB,NCIGBP,email,userId);
                        databaseReference.child(providerModel.getId()).setValue(providerModel1);
                    }
                    else{
                        Toast.makeText(HospitalDetailsActivity.this,"Exceede your total bed",Toast.LENGTH_SHORT).show();
                    }

                }
                if(s.equals("d") && Integer.parseInt(bed)>0)
                {
                    int av = Integer.parseInt(availICUBed) + Integer.parseInt(bed);
                    int ncigb = Integer.parseInt(NCIGB) + Integer.parseInt(bed);
                    int total = ncigb + Integer.parseInt(NCGB) + Integer.parseInt(CIGB) + Integer.parseInt(CGB);

                    if(av<=Integer.parseInt(totalICUBed) && total<=Integer.parseInt(totalICUBed))
                    {
                        ProviderModel providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,""+av,CGB,CGBP,NCGB,NCGBP,CIGB,CIGBP,""+ncigb,NCIGBP,email,userId);
                        databaseReference.child(providerModel.getId()).setValue(providerModel1);
                    }
                    else{
                        Toast.makeText(HospitalDetailsActivity.this,"Exceede your total bed",Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HospitalDetailsActivity.this,"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            //startActivity(new Intent(HospitalDetailsActivity.this,Home.class));
            //finish();
            String fromToHospiDet = getIntent().getStringExtra("fromhos");
            if(fromToHospiDet.equals("Provider"))
            {
                //userId = getIntent().getStringExtra("userId");
                //position = getIntent().getIntExtra("position",0);
                Intent intent = new Intent(HospitalDetailsActivity.this,ProviderHomeActivity.class);
                //Toast.makeText(HospitalDetailsActivity.this, fromToHospiDet,Toast.LENGTH_SHORT).show();
              //intent.putExtra("userId",userId);
                //intent.putExtra("position",position);
                startActivity(intent);
                finish();


            }
            else{
                Intent intent = new Intent(HospitalDetailsActivity.this,Home.class);
                startActivity(intent);

                finish();
            }
        }
        if(item.getItemId() == R.id.refresh)
        {
            refresh();
        }

        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(HospitalDetailsActivity.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providerlist.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    ProviderModel providerModel = ds.getValue(ProviderModel.class);
                    providerlist.add(providerModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ProviderModel providerModel = providerlist.get(position);

        hospiname2 = providerModel.getHospiname();
        hospiadd2 = providerModel.getHospiadd();
        district2 = providerModel.getDistrict();
        contact2 = providerModel.getContact();
        totalICUBed2 = providerModel.getTotalICUBed();;
        availICUBed2 = providerModel.getAvailICUBed();
        CGB2 = providerModel.getCGB();
        CGBP2 = providerModel.getCGBP();
        NCGB2 = providerModel.getNCGB();
        NCGBP2 = providerModel.getNCGBP();
        CIGB2 = providerModel.getCIGB();
        CIGBP2 = providerModel.getCIGBP();
        NCIGB2 = providerModel.getNCIGB();
        NCIGBP2 = providerModel.getNCIGBP();
        email2 = providerModel.getEmail();
        userId = providerModel.getUserId();


        hosName.setText(hospiname2);
        hospitalAddress.setText(hospiadd2);
        totalICU.setText(totalICUBed2);
        CovidGeneralBed.setText(CGB2);
        CovidGeneralBedPrice.setText(CGBP2+" tk");
        nonCovidGeneralBed.setText(NCGB2);
        nonCovidGeneralBedPrice.setText(NCGBP2+" tk");
        CovidICUGeneralBed.setText(CIGB2);
        CovidICUGeneralBedPrice.setText(CIGBP2+" tk");
        nonCovidICUGeneralBed.setText(NCIGB2);
        nonCovidGeneralICUBedPrice.setText(NCIGBP2+" tk");


    }

    @Override
    protected void onStart() {
        super.onStart();

        LoadData();
    }

    @Override
    public void onBackPressed() {

        String fromToHospiDet = getIntent().getStringExtra("fromhos");
        if(fromToHospiDet.equals("Provider"))
        {
            //userId = getIntent().getStringExtra("userId");
            //position = getIntent().getIntExtra("position",0);
            Intent intent = new Intent(HospitalDetailsActivity.this,ProviderHomeActivity.class);
            //Toast.makeText(HospitalDetailsActivity.this, fromToHospiDet,Toast.LENGTH_SHORT).show();
            //intent.putExtra("userId",userId);
            //intent.putExtra("position",position);
            startActivity(intent);
            finish();


        }
        else{
            Intent intent = new Intent(HospitalDetailsActivity.this,Home.class);
            startActivity(intent);

            finish();
        }
    }
}