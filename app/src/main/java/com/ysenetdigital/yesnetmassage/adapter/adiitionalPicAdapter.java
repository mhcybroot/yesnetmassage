package com.ysenetdigital.yesnetmassage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.ImageViewer;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.GetReactModel;
import com.ysenetdigital.yesnetmassage.models.additional_pic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adiitionalPicAdapter extends RecyclerView.Adapter<adiitionalPicAdapter.viewholder> {


    Context context;
    ArrayList<additional_pic> list;
    String userKey;

    public adiitionalPicAdapter(Context context, ArrayList<additional_pic> list, String userKey) {
        this.context = context;
        this.list = list;
        this.userKey = userKey;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_addition_pic, parent, false);
        return new adiitionalPicAdapter.viewholder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        additional_pic model = list.get(position);
        Toast.makeText(context, userKey, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, FirebaseAuth.getInstance().getUid(), Toast.LENGTH_SHORT).show();
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(new int[]{
                        R.drawable.like,
                        R.drawable.laughing,
                        R.drawable.angry,
                        R.drawable.dislike,

                })
                .build();

        @SuppressLint("SetTextI18n") ReactionPopup popup = new ReactionPopup(context, config, (positio) -> {
            Map<String, Integer> setReact = new HashMap<>();
            setReact.put("Position", positio);

            FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("totalReact").child(FirebaseAuth.getInstance().getUid()).setValue(setReact);
            return true; // true is closing popup, false is requesting a new selection
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("totalReact").child(FirebaseAuth.getInstance().getUid()).child("Position").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String react = String.valueOf(task.getResult().getValue());
                    if (react.equals("1") || react.equals("2") || react.equals("3") || react.equals("0")) {
                        int reactPos = Integer.parseInt(react);
                        switch (reactPos) {
                            case 0:
                                holder.imageView7.setImageResource(R.drawable.like);
                                break;
                            case 1:
                                holder.imageView7.setImageResource(R.drawable.laughing);
                                break;
                            case 2:
                                holder.imageView7.setImageResource(R.drawable.angry);

                                break;
                            case 3:
                                holder.imageView7.setImageResource(R.drawable.dislike);
                                break;
                            default:
                                holder.imageView7.setImageResource(R.drawable.like);
                                break;

                        }
                    } else {
                        holder.imageView7.setImageResource(R.drawable.like);
                    }

                }
            }
        });
        ArrayList<GetReactModel> rect = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("totalReact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rect.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    GetReactModel model1 = snapshot1.getValue(GetReactModel.class);
                    rect.add(model1);
                }
                if (rect.isEmpty()) {
                    if (userKey.equals(FirebaseAuth.getInstance().getUid())) {
                        holder.imageView6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (model.getPicId() != null) {

                                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("story").child(model.getPicId()).removeValue();

                                }
                            }
                        });
                    } else {
                        holder.imageView6.setVisibility(View.GONE);
                    }
//
                }
                else {
                    if (userKey.equals(FirebaseAuth.getInstance().getUid())) {
                        holder.imageView6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (model.getPicId() != null) {

                                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("story").child(model.getPicId()).child("totalReact").removeValue();
                                    Toast.makeText(context, "Image React deleted .Try again To delete Your Image", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        holder.imageView6.setVisibility(View.GONE);
                    }
                    FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("TotalReaction").setValue(rect.size());

//                    Toast.makeText(context, "Find React", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("TotalReaction").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String TotalReaction = String.valueOf(task.getResult().getValue());
                    if (TotalReaction.equals("null")){

                    }else {
                        holder.countlike.setText(TotalReaction);
                    }

                }
            }
        });

        holder.imageView7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popup.onTouch(view, motionEvent);
                return true;
            }
        });
//        holder.imageView7.se { v, event ->
//                // Avoid scroll view to consume events
//                scrollView.requestDisallowInterceptTouchEvent(true);
//            // Resolve reactions selection
//            popup.onTouch(v, event);
//        }
        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ImageViewer.class);
            intent.putExtra("url", model.getPicUrl());
            context.startActivity(intent);
        });

        FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("story").child(model.getPicId()).child("picUrl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                } else {
                    String profilePic = String.valueOf(task.getResult().getValue());
                    Picasso.get().load(profilePic).placeholder(R.drawable.cicle_logo).into(holder.imageView);
                }
            }
        });


//        if (model.getPicId() != null) {
//            holder.imageView6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (model.getPicId() != null) {
//
//                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("story").child(model.getPicId()).removeValue();
//
//                    }
//                }
//            });
//        } else {
//            holder.imageView6.setVisibility(View.GONE);
//        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView imageView, imageView6, imageView7, imageView8, imageView9;
        TextView countlike;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_additional);
            imageView6 = itemView.findViewById(R.id.imageView6);
            imageView7 = itemView.findViewById(R.id.imageView7);
            countlike = itemView.findViewById(R.id.textView9);

        }
    }
}
