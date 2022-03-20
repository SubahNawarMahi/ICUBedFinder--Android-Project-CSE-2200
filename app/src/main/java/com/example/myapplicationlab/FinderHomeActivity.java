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
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplicationlab.Model.RequestModel;
import com.example.myapplicationlab.Model.Sample;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FinderHomeActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private Button hosList,reqList;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder_home);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Finder");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        hosList = (Button)findViewById(R.id.hosListId);
        reqList = (Button)findViewById(R.id.requestListId);

        hosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FinderHomeActivity.this,Home.class));
            }
        });
        reqList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(FinderHomeActivity.this,"Please request some bed",Toast.LENGTH_SHORT).show();

                List<RequestModel>requestModelList = new ArrayList<>();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("allRequest");

                databaseReference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        requestModelList.clear();

                        for(DataSnapshot ds:snapshot.getChildren())
                        {
                            RequestModel requestModel = ds.getValue(RequestModel.class);
                            requestModelList.add(requestModel);
                        }

                        if(requestModelList.size() == 0)
                        {
                            Toast.makeText(FinderHomeActivity.this,"No Request found.Please request some bed",Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(FinderHomeActivity.this,FinderRequestPageActivity.class));
                            finish();
                           // Toast.makeText(FinderHomeActivity.this,"Please request some bed",Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(FinderHomeActivity.this,MainActivity.class));
            finish();
        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(FinderHomeActivity.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FinderHomeActivity.this,MainActivity.class));
        finish();
    }
}