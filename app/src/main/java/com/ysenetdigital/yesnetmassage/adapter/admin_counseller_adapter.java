package com.ysenetdigital.yesnetmassage.adapter;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;
import com.ysenetdigital.yesnetmassage.models.signup_models;
import com.ysenetdigital.yesnetmassage.userViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class admin_counseller_adapter extends RecyclerView.Adapter<admin_counseller_adapter.viewHolder> {


    ArrayList<signup_models> list;
    Context context;
    int  page ;



    public admin_counseller_adapter(ArrayList<signup_models> list, Context context, int page) {
        this.list = list;
        this.context = context;
        this.page = page;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_admin_verification, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        signup_models users = list.get(position);

        try {
            holder.userName.setText(users.getUsername());
            holder.Post.setText(users.getPost());
            holder.MemberId.setText(users.getMemberId());
            holder.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, userViewer.class);
                    intent.putExtra("name",users.getUsername());
                    intent.putExtra("email",users.getEmail());
                    intent.putExtra("post",users.getPost());
                    intent.putExtra("picUrl",users.getProfilepic());
                    intent.putExtra("userId",users.getUserID());
                    intent.putExtra("memberID",users.getMemberId());
                    intent.putExtra("total_reply",users.getTotal_reply());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });   holder.Post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, userViewer.class);
                    intent.putExtra("name",users.getUsername());
                    intent.putExtra("email",users.getEmail());
                    intent.putExtra("post",users.getPost());
                    intent.putExtra("picUrl",users.getProfilepic());
                    intent.putExtra("userId",users.getUserID());
                    intent.putExtra("memberID",users.getMemberId());
                    intent.putExtra("total_reply",users.getTotal_reply());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            holder.MemberId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, userViewer.class);
                    intent.putExtra("name", users.getUsername());
                    intent.putExtra("email", users.getEmail());
                    intent.putExtra("post", users.getPost());
                    intent.putExtra("picUrl", users.getProfilepic());
                    intent.putExtra("userId", users.getUserID());
                    intent.putExtra("memberID", users.getMemberId());
                    intent.putExtra("total_reply", users.getTotal_reply());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (page){
                        case 1:
                            FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").setValue("Counsellor");
                            break;
                        case 2:
                            memberlistmodel memberlistmodel = new memberlistmodel(users.getUserID(), "admin");
                            FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(users.getUserID()).setValue(memberlistmodel);
                            FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").setValue("BanglaCounsellorGroupAdmin");
                            memberlistmodel memberlistmodl = new memberlistmodel(users.getUserID(), "admin");
                            FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(users.getUserID())).setValue(memberlistmodl);
                            Map<String, String> zmap = new HashMap<>();
                            zmap.put("CounsellingGroupStatus", "admin");
                            FirebaseFirestore.getInstance().collection(users.getUserID()).document(users.getUserID()).set(zmap, SetOptions.merge());

                            break;

                    }


                }
            });
        }catch (Exception e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {


        TextView userName, Post, MemberId;
        Button approve;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.admin_user_name);
            Post = itemView.findViewById(R.id.admin_post);
            MemberId = itemView.findViewById(R.id.admin_member_id);
            approve = itemView.findViewById(R.id.admin_approve);


        }
    }

}
