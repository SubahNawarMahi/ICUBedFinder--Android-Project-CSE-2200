package com.example.myapplicationlab.Model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationlab.AdminRequestActivity;
import com.example.myapplicationlab.HospitalDetailsActivity;
import com.example.myapplicationlab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRequestAdapter extends RecyclerView.Adapter<AdminRequestAdapter.MyViewHolder> {

    private List<RequestModel> requestModelList;
    private Context context;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

     DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("request");
     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("admin");
     DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference("allRequest");

    public AdminRequestAdapter(List<RequestModel> requestModelList, Context context) {
        this.requestModelList = requestModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_request_layout,parent,false);
        return new AdminRequestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequestAdapter.MyViewHolder holder, int position) {
        AdminRequestAdapter.MyViewHolder holders = holder;

        RequestModel requestModel = requestModelList.get(position);

        holders.pName.setText("Patient Name: "+requestModel.getPatient());
        holders.pAddress.setText("Address: "+requestModel.getAddress());
        holders.pEmail.setText("Email: "+requestModel.getEmail());
        holders.phone.setText("Phone: "+requestModel.getPhone());
        holders.bedType.setText("Bed Type: "+requestModel.getBedType());
        holders.noOfBed.setText("No of Bed: "+requestModel.getNoBed());
        holders.status.setText("Status: "+requestModel.getStatus());
        holders.price.setText("Price: "+requestModel.getPrice());


    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pName,pAddress,pEmail,phone,bedType,noOfBed,status,price;
        Button accept,reject,delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            pName = itemView.findViewById(R.id.patientName);
            pAddress = itemView.findViewById(R.id.patientAddress2);
            pEmail = itemView.findViewById(R.id.patientEmail);
            phone = itemView.findViewById(R.id.patientPhone);
            bedType = itemView.findViewById(R.id.bedTypeId);
            noOfBed = itemView.findViewById(R.id.noOfBed);
            status = itemView.findViewById(R.id.status);
            price = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.delete);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);

            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
            accept.setOnClickListener(this);
            reject.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.delete) {
                RequestModel requestModel = requestModelList.get(getAdapterPosition());
                String key = requestModel.getId();
                String uid = firebaseAuth.getCurrentUser().getUid();
                databaseReference.child(uid).child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
            if (v.getId() == R.id.reject) {

                RequestModel requestModel = requestModelList.get(getAdapterPosition());
                String uid = requestModel.getUid();

                RequestModel requestModel1 = new RequestModel(requestModel.getId(), uid, requestModel.getHosName(), requestModel.getPatient(), requestModel.getAddress(), requestModel.getEmail(), requestModel.getPhone(), requestModel.getBedType(), requestModel.getNoBed(), "Rejected", requestModel.getPrice(), requestModel.getHosId());
                String key = requestModel.getId();
                String hosId = requestModel.getHosId();


                databaseReference.child(hosId).child(key).setValue(requestModel1);
                databaseReference2.child(uid).child(hosId).child(key).setValue(requestModel1);
                databaseReference4.child(uid).child(key).setValue(requestModel1);

                Toast.makeText(context, "Request Rejected", Toast.LENGTH_SHORT).show();
            }
            if (v.getId() == R.id.accept) {
                 RequestModel requestModel = requestModelList.get(getAdapterPosition());


                String uid = requestModel.getUid();
                String bed = requestModel.getNoBed();
                String bedType = requestModel.getBedType();


                List<ProviderModel> providerlist = new ArrayList<>();


                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("users");

                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        providerlist.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ProviderModel data = ds.getValue(ProviderModel.class);
                            if (!TextUtils.isEmpty(data.getUserId()) && data.getUserId().equals(requestModel.getHosId())) {
                                providerlist.add(data);
                            }
                        }


                        ProviderModel providerModel = providerlist.get(0);


                        String id = providerModel.getId();
                        String hosName = providerModel.getHospiname();
                        String email = providerModel.getEmail();
                        String userId = providerModel.getUserId();
                        String hospiadd = providerModel.getHospiadd();
                        String district = providerModel.getDistrict();
                        String totalICUBed = providerModel.getTotalICUBed();
                        String contact = providerModel.getContact();
                        String availICUBed = providerModel.getAvailICUBed();
                        String CGB = providerModel.getCGB();
                        String CGBP = providerModel.getCGBP();
                        String NCGB = providerModel.getNCGB();
                        String NCGBP = providerModel.getNCGBP();
                        String CIGB = providerModel.getCIGB();
                        String CIGBP = providerModel.getCIGBP();
                        String NCIGB = providerModel.getNCIGB();
                        String NCIGBP = providerModel.getNCIGBP();


                        ProviderModel providerModel1 = null;

                        if (bedType.equals("Covid General Bed") && (Integer.parseInt(availICUBed) >= Integer.parseInt(bed)) && (Integer.parseInt(CGB) >= Integer.parseInt(bed)) && Integer.parseInt(bed) > 0) {

                            int av = Integer.parseInt(availICUBed) - Integer.parseInt(bed);
                            int cgb = Integer.parseInt(CGB) - Integer.parseInt(bed);
                            providerModel1 = new ProviderModel(id, hosName, hospiadd, district, totalICUBed, contact, "" + av, "" + cgb, CGBP, NCGB, NCGBP, CIGB, CIGBP, NCIGB, NCIGBP, email, userId);
                        }
                        if (bedType.equals("Non Covid General Bed") && (Integer.parseInt(availICUBed) >= Integer.parseInt(bed)) && (Integer.parseInt(NCGB) >= Integer.parseInt(bed)) && Integer.parseInt(bed) > 0) {
                            int av = Integer.parseInt(availICUBed) - Integer.parseInt(bed);
                            int ncgb = Integer.parseInt(NCGB) - Integer.parseInt(bed);
                            providerModel1 = new ProviderModel(id, hosName, hospiadd, district, totalICUBed, contact, "" + av, CGB, CGBP, "" + ncgb, NCGBP, CIGB, CIGBP, NCIGB, NCIGBP, email, userId);
                        }
                        if (bedType.equals("Covid ICU General Bed") && (Integer.parseInt(availICUBed) >= Integer.parseInt(bed)) && (Integer.parseInt(CIGB) >= Integer.parseInt(bed)) && Integer.parseInt(bed) > 0) {
                            int av = Integer.parseInt(availICUBed) - Integer.parseInt(bed);
                            int cigb = Integer.parseInt(CIGB) - Integer.parseInt(bed);
                            providerModel1 = new ProviderModel(id, hosName, hospiadd, district, totalICUBed, contact, "" + av, CGB, CGBP, NCGB, NCGBP, "" + cigb, CIGBP, NCIGB, NCIGBP, email, userId);
                        }
                        if (bedType.equals("Non Covid ICU General Bed") && (Integer.parseInt(availICUBed) >= Integer.parseInt(bed)) && (Integer.parseInt(NCIGB) >= Integer.parseInt(bed)) && Integer.parseInt(bed) > 0) {
                            int av = Integer.parseInt(availICUBed) - Integer.parseInt(bed);
                            int ncigb = Integer.parseInt(NCIGB) - Integer.parseInt(bed);


                            providerModel1 = new ProviderModel(id, hosName, hospiadd, district, totalICUBed, contact, "" + av, CGB, CGBP, NCGB, NCGBP, CIGB, CIGBP, "" + ncigb, NCIGBP, email, userId);
                        }

                        databaseReference3.child(providerModel.getId()).setValue(providerModel1);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }



                });


                RequestModel requestModel1 = new RequestModel(requestModel.getId(), uid, requestModel.getHosName(), requestModel.getPatient(), requestModel.getAddress(), requestModel.getEmail(), requestModel.getPhone(), requestModel.getBedType(), requestModel.getNoBed(), "Accepted", requestModel.getPrice(), requestModel.getHosId());
                String key = requestModel.getId();
                String hosId = requestModel.getHosId();

                databaseReference.child(hosId).child(key).setValue(requestModel1);
                databaseReference2.child(uid).child(hosId).child(key).setValue(requestModel1);
                databaseReference4.child(uid).child(key).setValue(requestModel1);

                Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();

              //  accept.setClickable(false);

            }


        }
    }

}
