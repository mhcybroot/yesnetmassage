package com.ysenetdigital.yesnetmassage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.friendModel;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;

public class friendAdapter extends RecyclerView.Adapter<friendAdapter.viewHolder>{
    ArrayList<friendModel> list2;
    Context context;

    public friendAdapter(ArrayList<friendModel> list2, Context context) {
        this.list2 = list2;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_user_list, parent, false);

        return new friendAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        friendModel users = list2.get(position);
        holder.userName.setText(users.getFriend());
        holder.MemberId.setText(users.getRequest_friend());
        holder.lastId.setText(users.getUserId());
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView image, verf;
        TextView userName, Post, MemberId, lastId;
        Button add;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.simple_profile_image);
            userName = itemView.findViewById(R.id.simple_user_name);
            Post = itemView.findViewById(R.id.simple_post);
            MemberId = itemView.findViewById(R.id.simple_member_id);
            lastId = itemView.findViewById(R.id.simple_last_massage);
            verf = itemView.findViewById(R.id.verification);
            add = itemView.findViewById(R.id.simple_add);
        }
    }}
