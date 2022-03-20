package com.example.myapplicationlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.myapplicationlab.Model.MyAdapter;
import com.example.myapplicationlab.Model.ProviderModel;
import com.example.myapplicationlab.Model.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Provider_registration extends AppCompatActivity implements View.OnClickListener {

    private EditText hospiname,hospiadd,district,contact,totalICUBed,availICUBed,CGB,CGBP,NCGB,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,password;
    private TextView login;
    private Button register,update;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    List<ProviderModel> providerlist = new ArrayList<>();
    private Toolbar mtoolbar;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);
        mtoolbar = (Toolbar)findViewById(R.id.toolbar4);

        String s = getIntent().getStringExtra("update");

        if(!TextUtils.isEmpty(s) && s.equals("update"))
        {
            mtoolbar.setTitle("Update");
        }

        else{
            mtoolbar.setTitle("Sign Up");

        }
        setSupportActionBar(mtoolbar);

        hospiname = (EditText) findViewById(R.id.hospitalName);
        hospiadd=(EditText)findViewById(R.id.addresshospital);
        district=(EditText)findViewById(R.id.districtname);
        contact=(EditText)findViewById(R.id.contact);
        totalICUBed = (EditText)findViewById(R.id.Totalbed);
        availICUBed = (EditText)findViewById(R.id.Availablebed);
        CGB = (EditText)findViewById(R.id.covidGeneralBed);
        CGBP = (EditText)findViewById(R.id.covidGeneralBedPrice);
        NCGB = (EditText)findViewById(R.id.nonCovidGeneralBed);
        NCGBP = (EditText)findViewById(R.id.nonCovidGeneralBedPrice);
        CIGB = (EditText)findViewById(R.id.covidICUGeneralBed);
        CIGBP = (EditText)findViewById(R.id.covidICUGeneralBedPrice);
        NCIGB = (EditText)findViewById(R.id.nonCovidICUGeneralBed);
        NCIGBP = (EditText)findViewById(R.id.nonCovidICUGeneralBedPrice);
        email=(EditText)findViewById(R.id.emailid);
        password=(EditText)findViewById(R.id.password);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login=(TextView)findViewById(R.id.loginacc);
        register=(Button)findViewById(R.id.registerb);
        update = (Button)findViewById(R.id.updateBtnId);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();



        if(fAuth.getCurrentUser()!=null)
        {
            if(s.equals("update"))
            {
                email.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                hospiname.setVisibility(View.GONE);


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hospiadd2,district2,contact2,totalICUBed2,availICUBed2,CGB2,CGBP2,NCGB2,NCGBP2,CIGB2,CIGBP2,NCIGB2,NCIGBP2;

                        position = getIntent().getIntExtra("position",0);



                        hospiadd2=hospiadd.getText().toString();
                        district2=district.getText().toString();
                        contact2=contact.getText().toString();
                        totalICUBed2=totalICUBed.getText().toString();
                        availICUBed2=availICUBed.getText().toString();
                        CGB2=CGB.getText().toString();
                        CGBP2=CGBP.getText().toString();
                        NCGB2=NCGB.getText().toString();
                        NCGBP2=NCGBP.getText().toString();
                        CIGB2=CIGB.getText().toString();
                        CIGBP2=CIGBP.getText().toString();
                        NCIGB2=NCIGB.getText().toString();
                        NCIGBP2=NCIGBP.getText().toString();


                        if(TextUtils.isEmpty(hospiadd2))
                        {
                            hospiadd.setError("Required");
                            return;
                        }

                        if(TextUtils.isEmpty(district2))
                        {
                            district.setError("Required");
                            return;
                        }


                        if(TextUtils.isEmpty(totalICUBed2))
                        {
                            totalICUBed.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(contact2))
                        {
                            contact.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(availICUBed2))
                        {
                            availICUBed.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(CGB2))
                        {
                            CGB.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(CGBP2))
                        {
                            CGBP.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(NCGB2))
                        {
                            NCGB.setError("Required");
                            return;
                        }

                        if(TextUtils.isEmpty(NCGBP2))
                        {
                            NCGBP.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(CIGB2))
                        {
                            CIGB.setError("Required");
                            return;
                        }


                        if(TextUtils.isEmpty(CIGBP2))
                        {
                            CIGBP.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(NCIGB2))
                        {
                            NCIGB.setError("Required");
                            return;
                        }
                        if(TextUtils.isEmpty(NCIGBP2))
                        {
                            NCIGBP.setError("Required");
                            return;
                        }
                        updateData(hospiadd2,district2,totalICUBed2,contact2,availICUBed2,CGB2,CGBP2,NCGB2,NCGBP2,CIGB2,CIGBP2,NCIGB2,NCIGBP2);
                    }
                });


            }
            else{
                startActivity(new Intent(Provider_registration.this,Home.class));
                finish();
            }


        }
        else{
            email.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            hospiname.setVisibility(View.VISIBLE);
        }
        
    }

    private void updateData(String hospiadd,String district,String totalICUBed,String contact,String availICUBed,String CGB,String CGBP,String NCGB,String NCGBP,String CIGB,String CIGBP,String NCIGB,String NCIGBP) {



        databaseReference = FirebaseDatabase.getInstance().getReference("users");

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

                ProviderModel providerModel1 = new ProviderModel(id,hosName,hospiadd,district,totalICUBed,contact,availICUBed,CGB,CGBP,NCGB,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userId);
                databaseReference.child(providerModel.getId()).setValue(providerModel1);
                startActivity(new Intent(Provider_registration.this,Home.class));
                Toast.makeText(Provider_registration.this,"Updated",Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Provider_registration.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginacc)
        {
            Intent intent = new Intent(Provider_registration.this,Login.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId()==R.id.registerb)
        {
            String hospiname2,hospiadd2,district2,contact2,totalICUBed2,availICUBed2,CGB2,CGBP2,NCGB2,NCGBP2,CIGB2,CIGBP2,NCIGB2,NCIGBP2,email2,password2;

            hospiname2= hospiname.getText().toString();
            hospiadd2=hospiadd.getText().toString();
            district2=district.getText().toString();
            contact2=contact.getText().toString();
            totalICUBed2=totalICUBed.getText().toString();
            availICUBed2=availICUBed.getText().toString();
            CGB2=CGB.getText().toString();
            CGBP2=CGBP.getText().toString();
            NCGB2=NCGB.getText().toString();
            NCGBP2=NCGBP.getText().toString();
            CIGB2=CIGB.getText().toString();
            CIGBP2=CIGBP.getText().toString();
            NCIGB2=NCIGB.getText().toString();
            NCIGBP2=NCIGBP.getText().toString();
            email2=email.getText().toString();
            password2=password.getText().toString();


            if(TextUtils.isEmpty(hospiname2))
            {
                hospiname.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(hospiadd2))
            {
                hospiadd.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(district2))
            {
               district.setError("Required");
                return;
            }


            if(TextUtils.isEmpty(totalICUBed2))
            {
                totalICUBed.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(contact2))
            {
                contact.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(availICUBed2))
            {
                availICUBed.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(CGB2))
            {
                CGB.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(CGBP2))
            {
                CGBP.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(NCGB2))
            {
                NCGB.setError("Required");
                return;
            }

            if(TextUtils.isEmpty(NCGBP2))
            {
                NCGBP.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(CIGB2))
            {
                CIGB.setError("Required");
                return;
            }


            if(TextUtils.isEmpty(CIGBP2))
            {
                CIGBP.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(NCIGB2))
            {
                NCIGB.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(NCIGBP2))
            {
                NCIGBP.setError("Required");
                return;
            }


            if(TextUtils.isEmpty(email2))
            {
                email.setError("Required");
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email2).matches())
            {
                email.setError("Email address is invalid");
                return;
            }
            if(TextUtils.isEmpty(password2))
            {
                password.setError("Required");
                return;
            }
            if(password2.length()<6)
            {
                password.setError("Password must contain at least 6 characters");
                return;

            }
            Toast.makeText(Provider_registration.this,"Processing...",Toast.LENGTH_SHORT).show();
            fAuth.createUserWithEmailAndPassword(email2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        String userID = fAuth.getCurrentUser().getUid();
                        saveData(hospiname2,hospiadd2,district2,totalICUBed2,contact2,availICUBed2,CGB2,CGBP2,NCGB2,NCGBP2,CIGB2,CIGBP2,NCIGB2,NCIGBP2,email2,userID);

                        databaseReference = FirebaseDatabase.getInstance().getReference("accounts2");


                        String okMail = "";

                        for(int i=0;i<email.length();i++)
                        {
                            if(email2.charAt(i) == '.') okMail+='@';
                            else{
                                okMail+=email2.charAt(i);
                            }
                        }

                        Status status = new Status(okMail,"provider");

                        databaseReference.child(okMail).setValue(status);

                        databaseReference = FirebaseDatabase.getInstance().getReference("provider");
                        databaseReference.child(fAuth.getCurrentUser().getUid()).setValue("provider");

                        Toast.makeText(Provider_registration.this,"Account Created Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (Provider_registration.this,ProviderHomeActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Provider_registration.this,"Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }

    }
    private void saveData(String hospiname,String hospiadd,String district,String totalICUBed,String contact,String availICUBed,String CGB,String CGBP,String NCGB,String NCGBP,String CIGB,String CIGBP,String NCIGB,String NCIGBP,String email,String userID)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String key = databaseReference.push().getKey();
        ProviderModel provider= new ProviderModel(key,hospiname,hospiadd,district,totalICUBed,contact,availICUBed,CGB,CGBP,NCGB,NCGBP,CIGB,CIGBP,NCIGB,NCIGBP,email,userID);
        databaseReference.child(key).setValue(provider);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}