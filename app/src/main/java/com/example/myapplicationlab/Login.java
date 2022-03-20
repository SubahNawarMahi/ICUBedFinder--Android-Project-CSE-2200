package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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

import com.example.myapplicationlab.Model.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password;
    private TextView forgotPassword, createAccount;
    private Button logIn;
    private String text;
    private FirebaseAuth fAuth;
    private Toolbar mtoolbar;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar2);
        mtoolbar.setTitle("Log in");
        setSupportActionBar(mtoolbar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgotpass);
        createAccount = (TextView) findViewById(R.id.createacc);
        logIn = (Button) findViewById(R.id.loginb);


        databaseReference = FirebaseDatabase.getInstance().getReference("accounts2");

        createAccount.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        logIn.setOnClickListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createacc) {
            Intent intent = new Intent(Login.this, Provider_registration.class);
            startActivity(intent);
            finish();
        }
        if (v.getId() == R.id.loginb) {

            fAuth.signOut();

            String myemail, mypassword;
            myemail = email.getText().toString();
            mypassword = password.getText().toString();
            if (TextUtils.isEmpty((myemail))) {
                email.setError("Please enter your email address");
                return;

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(myemail).matches()) {
                email.setError("Email address is invalid");
                return;

            }
            if (TextUtils.isEmpty((mypassword))) {
                password.setError("Please enter a password");
                return;

            }
            Toast.makeText(Login.this,"Processing...",Toast.LENGTH_SHORT).show();

            fAuth.signInWithEmailAndPassword(myemail, mypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String okMail = "";

                        for (int i = 0; i < myemail.length(); i++) {
                            if (myemail.charAt(i) == '.') okMail += '@';
                            else {
                                okMail += myemail.charAt(i);
                            }
                        }


                        databaseReference.child(okMail).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                Status status1 = snapshot.getValue(Status.class);


                                if (status1.getStatus().equals("provider")) {
                                    startActivity(new Intent(Login.this, ProviderHomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Error!" + task.getException(), Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            });
        }

        if (v.getId() == R.id.forgotpass) {
            EditText resetMail = new EditText(v.getContext());
            AlertDialog.Builder passwordRestDialog = new AlertDialog.Builder(v.getContext());
            passwordRestDialog.setTitle("Reset Password?");
            passwordRestDialog.setMessage("Enter your email to receive reset password link");
            passwordRestDialog.setView(resetMail);
            passwordRestDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login.this, "Reset link has been sent to email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Reset link was not sent\nError: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            passwordRestDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            passwordRestDialog.create().show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}