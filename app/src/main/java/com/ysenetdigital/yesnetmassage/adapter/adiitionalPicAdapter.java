package com.ysenetdigital.yesnetmassage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.ImageViewer;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.additional_pic;

import java.util.ArrayList;

public class adiitionalPicAdapter extends RecyclerView.Adapter<adiitionalPicAdapter.viewholder> {


    Context context;
    ArrayList<additional_pic> list;

    public adiitionalPicAdapter(Context context, ArrayList<additional_pic> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_addition_pic, parent, false);
        return new adiitionalPicAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        additional_pic model = list.get(position);
        Picasso.get().load(model.getPicUrl()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageViewer.class);
                intent.putExtra("url",model.getPicUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_additional);
        }
    }
}
