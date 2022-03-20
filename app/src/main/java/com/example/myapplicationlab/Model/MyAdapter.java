package com.example.myapplicationlab.Model;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationlab.HospitalDetailsActivity;
import com.example.myapplicationlab.R;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ProviderModel> userModelList;
    private Context context;

    public MyAdapter(List<ProviderModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyViewHolder holders = holder;
        ProviderModel providerModel = userModelList.get(position);

        holders.hosName.setText(providerModel.getHospiname());
        holders.available.setText(providerModel.getAvailICUBed());


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hosName,available;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hosName = itemView.findViewById(R.id.hospitalName);
            available = itemView.findViewById(R.id.available);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, HospitalDetailsActivity.class);

            intent.putExtra("userId",userModelList.get(getAdapterPosition()).getUserId());

            intent.putExtra("position",getAdapterPosition());

            intent.putExtra("fromhos","home");

            context.startActivity(intent);

        }
    }
}
