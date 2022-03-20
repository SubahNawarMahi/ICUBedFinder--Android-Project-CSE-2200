package com.example.myapplicationlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationlab.Model.MyAdapter;
import com.example.myapplicationlab.Model.ProviderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private Button logout;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FirebaseAuth fAuth;
    private Toolbar mtoolbar;
    private TextView total;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    List<ProviderModel> providerlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        searchView=(SearchView) findViewById(R.id.searchOption);


        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Hospital List");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        total = (TextView)findViewById(R.id.totalResult);

        total.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(Home.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("provider");

            if (fAuth.getCurrentUser() !=null) {
                databaseReference1.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String s = (String) snapshot.getValue();
                        if (fAuth.getCurrentUser() != null && !TextUtils.isEmpty(s) && s.equals("provider")) {
                            startActivity(new Intent(Home.this, ProviderHomeActivity.class));
                            finish();
                        } else if (fAuth.getCurrentUser() != null && !TextUtils.isEmpty(s) && s.equals("finder")) {
                            startActivity(new Intent(Home.this, FinderHomeActivity.class));
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            startActivity(new Intent(Home.this,MainActivity.class));
            finish();
        }

        if(item.getItemId()==R.id.refresh)
        {
            LoadData();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        LoadData();

        if(searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<ProviderModel> user = new ArrayList<>();
                    for(ProviderModel object : providerlist)
                    {
                        if(object.getHospiname().toLowerCase().contains(newText.toLowerCase()) || object.getDistrict().toLowerCase().contains(newText.toLowerCase())||object.getHospiadd().toLowerCase().contains(newText.toLowerCase()))
                        {
                            user.add(object);
                        }
                    }
                    String s = "Total ";
                    s+=user.size();
                    s+=" results found";
                    total.setText(s);
                    MyAdapter myAdapter = new MyAdapter(user,Home.this);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
    }

    private void LoadData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                providerlist.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    ProviderModel data = ds.getValue(ProviderModel.class);
                    providerlist.add(data);
                }

                String s = "Total ";
                s+=providerlist.size();
                s+=" results found";
                total.setText(s);

                MyAdapter myAdapter = new MyAdapter (providerlist,Home.this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onBackPressed() {

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("provider");
        if (fAuth.getCurrentUser() !=null) {
            databaseReference1.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String s = (String) snapshot.getValue();
                    if (fAuth.getCurrentUser() != null && !TextUtils.isEmpty(s) && s.equals("provider")) {
                        startActivity(new Intent(Home.this, ProviderHomeActivity.class));
                        finish();
                    } else if (fAuth.getCurrentUser() != null && !TextUtils.isEmpty(s) && s.equals("finder")) {
                        startActivity(new Intent(Home.this, FinderHomeActivity.class));
                        finish();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}