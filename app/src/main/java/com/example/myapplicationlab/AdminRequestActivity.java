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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationlab.Model.AdminRequestAdapter;
import com.example.myapplicationlab.Model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRequestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Toolbar mtoolbar;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    LinearLayout notFound;
    FirebaseAuth fAuth;
    List<RequestModel> requestModelList = new ArrayList<>();
    String userId;
    int position;
    String s;
    private TextView total2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Request Bed List");
        setSupportActionBar(mtoolbar);
        recyclerView = (RecyclerView)findViewById(R.id.adminRecyclerView);

        notFound = (LinearLayout)findViewById(R.id.notFound);

        total2 = (TextView)findViewById(R.id.totalResult2);


        fAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        userId = getIntent().getStringExtra("userId");
        position = getIntent().getIntExtra("position",0);
        s = intent.getStringExtra("activity");

        //Toast.makeText(AdminRequestActivity.this,"userId: "+userId,Toast.LENGTH_SHORT).show();

        layoutManager = new LinearLayoutManager(AdminRequestActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadData();



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



            if(s.equals("HospitalDetails"))
            {
                userId = getIntent().getStringExtra("userId");
                position = getIntent().getIntExtra("position",0);
                Intent intent = new Intent(AdminRequestActivity.this,HospitalDetailsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("position",position);
                startActivity(intent);
                finish();

            }
            else{
                Intent intent = new Intent(AdminRequestActivity.this,ProviderHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(AdminRequestActivity.this,MainActivity.class));
            finish();
        }

        if(item.getItemId()==R.id.refresh)
        {
            LoadData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {


        userId = getIntent().getStringExtra("userId");
        position = getIntent().getIntExtra("position",0);


        String key = fAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin");
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestModelList.clear();

                for(DataSnapshot ds : snapshot.getChildren())
                {
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModelList.add(requestModel);
                }

                AdminRequestAdapter adminRequestAdapter = new AdminRequestAdapter(requestModelList,AdminRequestActivity.this);
                recyclerView.setAdapter(adminRequestAdapter);
                adminRequestAdapter.notifyDataSetChanged();

                if(requestModelList.size() == 0)
                {
                    notFound.setVisibility(View.VISIBLE);
                }
                else
                {
                    total2.setVisibility(View.VISIBLE);
                    String received_request = "Total ";
                    received_request+=requestModelList.size();
                    received_request+=" requests found";
                    total2.setText(received_request);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadData();
    }


    @Override
    public void onBackPressed() {


        if(s.equals("HospitalDetails"))
        {
            userId = getIntent().getStringExtra("userId");
            position = getIntent().getIntExtra("position",0);
            Intent intent = new Intent(AdminRequestActivity.this,HospitalDetailsActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("position",position);
            startActivity(intent);
            finish();


        }
        else{
            Intent intent = new Intent(AdminRequestActivity.this,ProviderHomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}