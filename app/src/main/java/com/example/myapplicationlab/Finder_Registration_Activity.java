package com.example.myapplicationlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplicationlab.Model.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Finder_Registration_Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText myemail,mypassword;
    private TextView login;
    private Button register;
    private FirebaseAuth fAuth;
    private Toolbar mtoolbar;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finder_registration);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar5);
        mtoolbar.setTitle("Sign Up");
        setSupportActionBar(mtoolbar);
        myemail = (EditText)findViewById(R.id.email);
        mypassword = (EditText)findViewById(R.id.password);
        login = (TextView)findViewById(R.id.createacc);
        register = (Button)findViewById(R.id.loginb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("accounts2");

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(Finder_Registration_Activity.this,Home.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.createacc)
        {
            Intent intent = new Intent(Finder_Registration_Activity.this,Login.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId()==R.id.loginb)
        {
            String email,password;
            email = myemail.getText().toString();
            password = mypassword.getText().toString();
            if (TextUtils.isEmpty((email))) {
                myemail.setError("Please enter your email address");
                return;

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                myemail.setError("Email address is invalid");
                return;

            }
            if (TextUtils.isEmpty((password))) {
                mypassword.setError("Please enter a password");
                return;

            }

            Toast.makeText(Finder_Registration_Activity.this,"Processing...",Toast.LENGTH_SHORT).show();

           fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String okMail = "";

                        for(int i=0;i<email.length();i++)
                        {
                            if(email.charAt(i) == '.') okMail+='@';
                            else{
                                okMail+=email.charAt(i);
                            }
                        }

                        Status status = new Status(okMail,"finder");



                        databaseReference.child(okMail).setValue(status);

                        databaseReference = FirebaseDatabase.getInstance().getReference("provider");
                        databaseReference.child(fAuth.getCurrentUser().getUid()).setValue("finder");

                        Toast.makeText(Finder_Registration_Activity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Finder_Registration_Activity.this, FinderHomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(Finder_Registration_Activity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            startActivity(new Intent(Finder_Registration_Activity.this,MainActivity.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}