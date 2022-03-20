package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationlab.Model.ProviderModel;
import com.example.myapplicationlab.Model.RequestModel;
import com.example.myapplicationlab.Model.RequestPageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FinderRequestPageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout notFound;
    private Toolbar mtoolbar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder_request_page);

        recyclerView = (RecyclerView)findViewById(R.id.requestList2);
        notFound = (LinearLayout)findViewById(R.id.notFound);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("My Request Bed List");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FinderRequestPageActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fAuth = FirebaseAuth.getInstance();


    }

    private void LoadData() {

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

                RequestPageAdapter requestPageAdapter = new RequestPageAdapter(requestModelList,FinderRequestPageActivity.this);
                recyclerView.setAdapter(requestPageAdapter);
                requestPageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            startActivity(new Intent(FinderRequestPageActivity.this,FinderHomeActivity.class));
            finish();
        }
        if(item.getItemId() == R.id.refresh)
        {
            refresh();
        }

        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(FinderRequestPageActivity.this,FinderHomeActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void refresh() {
        LoadData();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FinderRequestPageActivity.this,FinderHomeActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadData();
    }
}