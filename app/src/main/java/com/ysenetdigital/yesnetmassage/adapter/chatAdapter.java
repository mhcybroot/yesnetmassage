package com.ysenetdigital.yesnetmassage.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.ImageViewer;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.massageModels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class chatAdapter extends RecyclerView.Adapter {

    ArrayList<massageModels> massageModels;
    Context context;
    int senderViewType = 1;
    int receverViewType = 2;
    int page;

    public chatAdapter(ArrayList<com.ysenetdigital.yesnetmassage.models.massageModels> massageModels, Context context, int page) {
        this.massageModels = massageModels;
        this.context = context;
        this.page = page;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == senderViewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.simple_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.simple_recever, parent, false);
            return new ReciverViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        massageModels massageModel = massageModels.get(position);
try {
    if (page==1){
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMsg.setText(massageModel.getMassage());
            ((SenderViewHolder) holder).senderMsg.setMovementMethod(LinkMovementMethod.getInstance());


            if (!massageModel.getMassage().equals("Photo")) {
                ((SenderViewHolder) holder).sender_image.setVisibility(View.GONE);
            } else {
                ((SenderViewHolder) holder).sender_image.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent senderIntent = new Intent(context, ImageViewer.class);
                        senderIntent.putExtra("url", massageModel.getImage_url());
                        context.startActivity(senderIntent);
                    }
                });
                ((SenderViewHolder) holder).senderMsg.setVisibility(View.GONE);


            }
            Picasso.get().load(massageModel.getImage_url()).placeholder(R.drawable.ic_baseline_image_24).into(((SenderViewHolder) holder).sender_image);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            ((SenderViewHolder) holder).senderTime.setText(dateFormat.format(new Date(massageModel.getTimestamp())));

//
//            if (position == massageModels.size() -5) {

            if (massageModel.isIsseen()) {

                ((SenderViewHolder) holder).sender_seen.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_seen.setBackgroundResource(R.drawable.seen);

            } else {
                ((SenderViewHolder) holder).sender_seen.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_seen.setBackgroundResource(R.drawable.delivary);

            }

//            } else {
//
//            }
        } else {
            ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage());
            ((ReciverViewHolder) holder).receverMsg.setMovementMethod(LinkMovementMethod.getInstance());
            if (!massageModel.getMassage().equals("Photo")) {
                ((ReciverViewHolder) holder).receiver_image.setVisibility(View.GONE);
            } else {
                ((ReciverViewHolder) holder).receiver_image.setVisibility(View.VISIBLE);
                ((ReciverViewHolder) holder).receiver_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent reciverIntent = new Intent(context, ImageViewer.class);
                        reciverIntent.putExtra("url", massageModel.getImage_url());
                        context.startActivity(reciverIntent);
                    }
                });
                ((ReciverViewHolder) holder).receverMsg.setVisibility(View.GONE);

            }
            Picasso.get().load(massageModel.getImage_url()).placeholder(R.drawable.ic_baseline_image_24).into(((ReciverViewHolder) holder).receiver_image);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            ((ReciverViewHolder) holder).receverTime.setText(dateFormat.format(new Date(massageModel.getTimestamp())));
//            if (position == massageModels.size() -1) {
//
//                if (massageModel.isIsseen()) {
//
//
//                    ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage()+"seen");
//
//                } else {
//                    ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage()+"delivery");
//                }
//
//            } else {
//                ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage());
//            }
        }
    }else if (page==2){
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMsg.setText(massageModel.getMassage());
            ((SenderViewHolder) holder).senderMsg.setMovementMethod(LinkMovementMethod.getInstance());
            ((SenderViewHolder) holder).senderMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (massageModel.getMassage().startsWith("http")){

                    }
                }
            });
            if (!massageModel.getMassage().equals("Photo")) {
                ((SenderViewHolder) holder).sender_image.setVisibility(View.GONE);
            } else {
                ((SenderViewHolder) holder).sender_image.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent senderIntent = new Intent(context, ImageViewer.class);
                        senderIntent.putExtra("url", massageModel.getImage_url());
                        context.startActivity(senderIntent);
                    }
                });
                ((SenderViewHolder) holder).senderMsg.setVisibility(View.GONE);


            }
            Picasso.get().load(massageModel.getImage_url()).placeholder(R.drawable.ic_baseline_image_24).into(((SenderViewHolder) holder).sender_image);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            ((SenderViewHolder) holder).senderTime.setText(dateFormat.format(new Date(massageModel.getTimestamp())));

//
//            if (position == massageModels.size() -5) {

            if (massageModel.isIsseen()) {

                ((SenderViewHolder) holder).sender_seen.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_seen.setBackgroundResource(R.drawable.seen);

            } else {
                ((SenderViewHolder) holder).sender_seen.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).sender_seen.setBackgroundResource(R.drawable.delivary);

            }

//            } else {
//
//            }
        } else {
            ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage());
            ((ReciverViewHolder) holder).receverMsg.setMovementMethod(LinkMovementMethod.getInstance());
            if (massageModel.getuId()!=null){


                FirebaseDatabase.getInstance().getReference().child("users").child(massageModel.getuId()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            String post = String.valueOf(task.getResult().getValue());
                            if (post.equals("BanglaCounsellorGroupAdmin")){
                                FirebaseDatabase.getInstance().getReference().child("users").child(massageModel.getuId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String username = String.valueOf(task.getResult().getValue());
                                            ((ReciverViewHolder) holder).reciverName.setVisibility(View.VISIBLE);
                                            ((ReciverViewHolder) holder).reciverName.setText("@Admin "+username);

                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("users").child(massageModel.getuId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String username = String.valueOf(task.getResult().getValue());
                                            ((ReciverViewHolder) holder).reciverName.setVisibility(View.VISIBLE);
                                            ((ReciverViewHolder) holder).reciverName.setText("@"+username);

                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
            if (!massageModel.getMassage().equals("Photo")) {
                ((ReciverViewHolder) holder).receiver_image.setVisibility(View.GONE);
            } else {
                ((ReciverViewHolder) holder).receiver_image.setVisibility(View.VISIBLE);
                ((ReciverViewHolder) holder).receiver_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent reciverIntent = new Intent(context, ImageViewer.class);
                        reciverIntent.putExtra("url", massageModel.getImage_url());
                        context.startActivity(reciverIntent);
                    }
                });
                ((ReciverViewHolder) holder).receverMsg.setVisibility(View.GONE);

            }
            Picasso.get().load(massageModel.getImage_url()).placeholder(R.drawable.ic_baseline_image_24).into(((ReciverViewHolder) holder).receiver_image);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            ((ReciverViewHolder) holder).receverTime.setText(dateFormat.format(new Date(massageModel.getTimestamp())));
//            if (position == massageModels.size() -1) {
//
//                if (massageModel.isIsseen()) {
//
//
//                    ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage()+"seen");
//
//                } else {
//                    ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage()+"delivery");
//                }
//
//            } else {
//                ((ReciverViewHolder) holder).receverMsg.setText(massageModel.getMassage());
//            }
        }
    }
}catch (Exception e){
    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
}






    }

    @Override
    public int getItemViewType(int position) {
        if (massageModels.get(position).getuId() != null) {
            if (massageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
                return senderViewType;
            } else {
                return receverViewType;
            }
        } else {
            return 3;
        }

    }

    @Override
    public int getItemCount() {
        return massageModels.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        TextView receverMsg, receverTime,reciverName;
        ImageView receiver_image;


        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            receverMsg = itemView.findViewById(R.id.reciver_sms);
            receverTime = itemView.findViewById(R.id.reciver_time);
            receiver_image = itemView.findViewById(R.id.reciver_image);
            reciverName = itemView.findViewById(R.id.textView5);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime, sender_seen,senderName;
        ImageView sender_image;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.sender_sms);
            sender_seen = itemView.findViewById(R.id.sender_seen);
            senderTime = itemView.findViewById(R.id.sender_time);
            sender_image = itemView.findViewById(R.id.sender_image);
            senderName = itemView.findViewById(R.id.textView6);

        }
    }
}
