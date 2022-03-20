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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button providerButton, finderButton;
    private Toolbar mtoolbar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = (Toolbar)findViewById(R.id.toolbar1);
        mtoolbar.setTitle("Home");
        setSupportActionBar(mtoolbar);

        providerButton=(Button) findViewById(R.id.providerbutton);
        finderButton=(Button)findViewById(R.id.finderbutton);

        fAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        providerButton.setOnClickListener(this);
        finderButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.providerbutton)
        {
            Toast.makeText(MainActivity.this,"Processing...",Toast.LENGTH_SHORT).show();

            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("provider");

            if (fAuth.getCurrentUser() !=null)
            {
                databaseReference1.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String s = (String) snapshot.getValue();
                        if(fAuth.getCurrentUser()!=null && !TextUtils.isEmpty(s) && s.equals("provider") )
                        {
                            startActivity(new Intent(MainActivity.this,ProviderHomeActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"You are not provider",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else{
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }


        }
        else if(v.getId()==R.id.finderbutton) {

            Toast.makeText(MainActivity.this,"Processing...",Toast.LENGTH_SHORT).show();

            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("provider");

            if (fAuth.getCurrentUser() != null) {
                databaseReference1.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String s = (String) snapshot.getValue();
                        if (!TextUtils.isEmpty(s) && s.equals("finder")) {
                            startActivity(new Intent(MainActivity.this, FinderHomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "You are not finder", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else{
                Intent intent = new Intent(MainActivity.this, Login2Activity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onBackPressed() {
       this.finish();
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
            this.finish();
        }
        if(item.getItemId()==R.id.logOut)
        {
            fAuth.signOut();
            Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

}