package com.example.myapplicationlab.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationlab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FinderRequestAdapter extends RecyclerView.Adapter<FinderRequestAdapter.MyViewHolder> {

    private List<RequestModel> requestModelList;
    private Context context;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("request");
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("admin");

    public FinderRequestAdapter(List<RequestModel> requestModelList, Context context) {
        this.requestModelList = requestModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FinderRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinderRequestAdapter.MyViewHolder holder, int position) {
        MyViewHolder holders = holder;

        RequestModel requestModel = requestModelList.get(position);

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

        TextView bedType,noOfBed,status,price;
        Button cancel,delete,payment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bedType = itemView.findViewById(R.id.bedTypeId);
            noOfBed = itemView.findViewById(R.id.noOfBed);
            status = itemView.findViewById(R.id.status);
            price = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.delete);
            cancel = itemView.findViewById(R.id.cancel);
            payment = itemView.findViewById(R.id.payment);
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
            cancel.setOnClickListener(this);
            payment.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.delete)
            {
                RequestModel requestModel = requestModelList.get(getAdapterPosition());
                String key = requestModel.getId();
                String  uid = firebaseAuth.getCurrentUser().getUid();
                String hosId = requestModel.getHosId();
                databaseReference.child(uid).child(requestModel.getHosId()).child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       databaseReference = FirebaseDatabase.getInstance().getReference("allRequest");
                        databaseReference.child(uid).child(key).removeValue();
                        Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
           if(v.getId() == R.id.cancel)
           {
               String  uid = firebaseAuth.getCurrentUser().getUid();

               RequestModel requestModel = requestModelList.get(getAdapterPosition());


               RequestModel requestModel1 = new RequestModel(requestModel.getId(),uid,requestModel.getHosName(),requestModel.getPatient(),requestModel.getAddress(),requestModel.getEmail(),requestModel.getPhone(),requestModel.getBedType(),requestModel.getNoBed(),"Cancelled",requestModel.getPrice(),requestModel.getHosId());
               String key = requestModel.getId();
               String hosId = requestModel.getHosId();

               databaseReference.child(uid).child(hosId).child(key).setValue(requestModel1);
               databaseReference2.child(hosId).child(key).setValue(requestModel1);

               Toast.makeText(context,"Request Cancelled",Toast.LENGTH_SHORT).show();
           }
            if(v.getId() == R.id.payment)
            {
                Toast.makeText(context,"Contact authority for payment",Toast.LENGTH_SHORT).show();

            }

        }
    }
}
