package com.thereza.sqlitemcc.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thereza.sqlitemcc.R;
import com.thereza.sqlitemcc.data.FormDataModel;

import java.util.List;

/**
 * Created by theReza on 1/11/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {
    List<FormDataModel> dataModelArrayList;

    public RecycleAdapter(List<FormDataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView name,email,age,phone;
        ImageView propic;

        public Myholder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name1);
            email = (TextView) itemView.findViewById(R.id.email);
            age = (TextView) itemView.findViewById(R.id.age);
            phone = (TextView) itemView.findViewById(R.id.phone);
            propic =itemView.findViewById(R.id.profile_image);
        }
    }


    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        FormDataModel dataModel=dataModelArrayList.get(position);
        holder.name.setText("Name "+dataModel.getName());
        holder.age.setText("Age "+dataModel.getAge());
        holder.email.setText(dataModel.getEmail());
        holder.phone.setText(dataModel.getPhone());
        //holder.propic.setImageBitmap(dataModel.getImageData(byte[]imageData));

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}
