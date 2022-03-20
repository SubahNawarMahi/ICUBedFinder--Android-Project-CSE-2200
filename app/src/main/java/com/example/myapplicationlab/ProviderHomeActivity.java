package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplicationlab.Model.ProviderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderHomeActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private Button hosList,reqList,myHosId;
    FirebaseAuth fAuth;
    String userId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);

        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Provider");
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();

        hosList = (Button)findViewById(R.id.hosListId);
        reqList = (Button)findViewById(R.id.requestListId);
        myHosId = (Button)findViewById(R.id.myHosId);

        hosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProviderHomeActivity.this,Home.class));
                finish();
            }
        });

        reqList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderHomeActivity.this,AdminRequestActivity.class);
                intent.putExtra("activity","ProviderHome");
                startActivity(intent);
                finish();
            }
        });

        myHosId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int count=0;
                        for(DataSnapshot ds:snapshot.getChildren())
                        {
                            ProviderModel providerModel = ds.getValue(ProviderModel.class);


                            if(fAuth.getCurrentUser().getUid().equals(providerModel.getUserId()))
                            {
                                // Toast.makeText(ProviderHomeActivity.this,"hi",Toast.LENGTH_SHORT).show();
                                //ProviderModel  providerModel2 = new ProviderModel(providerModel.getId(),providerModel.getHospiname(),providerModel.getHospiadd(),providerModel.getDistrict(),providerModel.getTotalICUBed(),providerModel.getContact(),providerModel.getAvailICUBed(),providerModel.getCGB(),providerModel.getCGBP(),providerModel.getNCGB(),providerModel.getNCGBP(),providerModel.getCIGB(),providerModel.getCIGBP(),providerModel.getNCIGB(),providerModel.getNCIGBP(),providerModel.getEmail(),providerModel.getUserId());
                                Intent intent = new Intent(ProviderHomeActivity.this, HospitalDetailsActivity.class);

                                intent.putExtra("hosName",providerModel.getHospiname());
                                intent.putExtra("hosAdd",providerModel.getHospiadd());
                                intent.putExtra("district",providerModel.getDistrict());


                                intent.putExtra("totalICUBed",providerModel.getTotalICUBed());
                                intent.putExtra("conNo",providerModel.getContact());
                                intent.putExtra("availICUBed",providerModel.getAvailICUBed());
                                intent.putExtra("CGB",providerModel.getCGB());
                                intent.putExtra("CGBP",providerModel.getCGBP());
                                intent.putExtra("NCGB",providerModel.getNCGB());
                                intent.putExtra("NCGBP",providerModel.getNCGBP());
                                intent.putExtra("CIGB",providerModel.getCIGB());
                                intent.putExtra("CIGBP",providerModel.getCIGBP());

                                intent.putExtra("NCIGB",providerModel.getNCIGB());
                                intent.putExtra("NCIGBP",providerModel.getNCIGBP());
                                intent.putExtra("email",providerModel.getEmail());

                                intent.putExtra("userId",providerModel.getUserId());


                                intent.putExtra("position",count);

                                intent.putExtra("fromhos","Provider");

                                startActivity(intent);
                                finish();

                            }
                            else{
                                count++;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            startActivity(new Intent(ProviderHomeActivity.this,MainActivity.class));
            finish();
        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(ProviderHomeActivity.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProviderHomeActivity.this,MainActivity.class));
        finish();
    }
}