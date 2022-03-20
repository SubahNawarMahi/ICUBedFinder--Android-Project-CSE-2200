package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationlab.Model.FinderRequestAdapter;
import com.example.myapplicationlab.Model.ProviderModel;
import com.example.myapplicationlab.Model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FinderRequestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<RequestModel>requestModelList = new ArrayList<>();
    DatabaseReference databaseReference;
    private LinearLayout notFound;
    private Toolbar mtoolbar;
    FirebaseAuth fAuth;
    String hosId;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder_request);

        recyclerView = (RecyclerView)findViewById(R.id.requestList);
        notFound = (LinearLayout)findViewById(R.id.notFound);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Request Bed List");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FinderRequestActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fAuth = FirebaseAuth.getInstance();

        hosId = getIntent().getStringExtra("uid");
        position = getIntent().getIntExtra("position",0);
     //   Toast.makeText(FinderRequestActivity.this,"positon: "+position,Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase.getInstance().getReference("request");
        LoadData(hosId);




    }

    private void LoadData(String uid) {

        String key = fAuth.getCurrentUser().getUid();

        databaseReference.child(key).child(hosId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                requestModelList.clear();

                for(DataSnapshot ds : snapshot.getChildren())
                {
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModelList.add(requestModel);
                }

            //    Toast.makeText(FinderRequestActivity.this,"size: "+requestModelList.size(),Toast.LENGTH_SHORT).show();

                FinderRequestAdapter finderRequestAdapter = new FinderRequestAdapter(requestModelList,FinderRequestActivity.this);
                recyclerView.setAdapter(finderRequestAdapter);
                finderRequestAdapter.notifyDataSetChanged();

                if(requestModelList.size() == 0)
                {
                    notFound.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FinderRequestActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_SHORT).show();
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

            String s = getIntent().getStringExtra("activity");

          //  Toast.makeText(FinderRequestActivity.this,""+s,Toast.LENGTH_SHORT).show();

            if(!TextUtils.isEmpty(s) && s.equals("FinderHome"))
            {
                startActivity(new Intent(FinderRequestActivity.this,FinderHomeActivity.class));
                finish();
            }
            else if(!TextUtils.isEmpty(s) && s.equals("HospitalDetails"))
            {
                Intent intent = new Intent(FinderRequestActivity.this,HospitalDetailsActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                finish();
            }

        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(FinderRequestActivity.this,MainActivity.class));
            finish();
        }
        if(item.getItemId()==R.id.refresh)
        {
            LoadData(hosId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        hosId = getIntent().getStringExtra("uid");
        LoadData(hosId);
    }

    @Override
    public void onBackPressed() {
        String s = getIntent().getStringExtra("activity");

        //  Toast.makeText(FinderRequestActivity.this,""+s,Toast.LENGTH_SHORT).show();

        if(!TextUtils.isEmpty(s) && s.equals("FinderHome"))
        {
            startActivity(new Intent(FinderRequestActivity.this,FinderHomeActivity.class));
            finish();
        }
        else if(!TextUtils.isEmpty(s) && s.equals("HospitalDetails"))
        {
            Intent intent = new Intent(FinderRequestActivity.this,HospitalDetailsActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
            finish();
        }
    }
}