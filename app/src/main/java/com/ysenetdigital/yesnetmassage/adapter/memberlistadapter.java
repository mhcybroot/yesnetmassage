package com.ysenetdigital.yesnetmassage.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;
import com.ysenetdigital.yesnetmassage.userViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class memberlistadapter extends RecyclerView.Adapter<memberlistadapter.viewholder> {

    Context context;
    ArrayList<memberlistmodel> list;
    int page;

    public memberlistadapter(Context context, ArrayList<memberlistmodel> list, int page) {
        this.context = context;
        this.list = list;
        this.page = page;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simplemeberlist, parent, false);

        return new memberlistadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        memberlistmodel model = list.get(position);

        if (page == 1||page==4) {
            try {
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String profilepic = String.valueOf(task.getResult().getValue());
                            Picasso.get().load(profilepic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.imageView);
                        }
                    }
                });
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, userViewer.class);
                        FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {

                                } else {
                                    String profilepic = String.valueOf(task.getResult().getValue());
                                    intent.putExtra("picUrl", profilepic);
                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()) {

                                            } else {
                                                String username = String.valueOf(task.getResult().getValue());
                                                intent.putExtra("name", username);
                                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        if (!task.isSuccessful()) {

                                                        } else {
                                                            String post = String.valueOf(task.getResult().getValue());
                                                            intent.putExtra("post", post);
                                                            FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                    if (!task.isSuccessful()) {

                                                                    } else {
                                                                        String email = String.valueOf(task.getResult().getValue());
                                                                        intent.putExtra("email", post);
                                                                        FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("userID").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                if (!task.isSuccessful()) {

                                                                                } else {
                                                                                    String userId = String.valueOf(task.getResult().getValue());
                                                                                    intent.putExtra("userId", userId);
                                                                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                            if (!task.isSuccessful()) {

                                                                                            } else {
                                                                                                String memberID = String.valueOf(task.getResult().getValue());
                                                                                                intent.putExtra("memberID", memberID);

                                                                                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("total_reply").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                                        if (!task.isSuccessful()) {

                                                                                                        } else {
                                                                                                            String total_reply = String.valueOf(task.getResult().getValue());
                                                                                                            intent.putExtra("total_reply", total_reply);
                                                                                                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                                                                                            context.startActivity(intent);

                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        }
                                                                                    });

                                                                                }
                                                                            }
                                                                        });

                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String username = String.valueOf(task.getResult().getValue());
                            holder.name.setText(username);
                        }
                    }
                });
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (page==1){
                            memberlistmodel memberlistmodel = new memberlistmodel(model.getId(), "Requested Permanent User");
                            FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(model.getId()).setValue(memberlistmodel);
                            Map<String, String> zmap = new HashMap<>();
                            zmap.put("counsellingGroupStatus", "RequestToJoin");
                            FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());

                        }if (page==4){
                            memberlistmodel memberlistmodel = new memberlistmodel(FirebaseAuth.getInstance().getUid(), "Requested Permanent User");
                            FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(model.getId())).setValue(memberlistmodel);
                            Map<String, String> zmap = new HashMap<>();
                            zmap.put("CounsellingGroupStatus", "RequestToJoin");
                            FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());

                        }

                    }
                });
//        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child(model.getId()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    String namelist = String.valueOf(task.getResult().getValue());
//                    if (namelist.equals("Block")) {
//                        holder.button.setText("UnBlock");
//                        memberlistmodel memberlistmodel = new memberlistmodel(model.getId(), "newUser");
//                        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(model.getId()).setValue(memberlistmodel);
//                        Map<String, String> zmap = new HashMap<>();
//                        zmap.put("counsellingGroupStatus", "newUser");
//                        FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());
//
//                    } else {
//                        holder.button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                memberlistmodel memberlistmodel = new memberlistmodel(model.getId(), "Block");
//                                holder.button.setText("UnBlock");
//                                FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(model.getId()).setValue(memberlistmodel);
//                                Map<String, String> zmap = new HashMap<>();
//                                zmap.put("counsellingGroupStatus", "Block");
//                                FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());
//
//                            }
//                        });
//                    }
//                } else {
//
//                }
//
//            }
//        });

//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                memberlistmodel memberlistmodel = new memberlistmodel(model.getId(),"Block");
//                FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(model.getId()).setValue(memberlistmodel);
//                Map<String, String> zmap = new HashMap<>();
//                zmap.put("counsellingGroupStatus", "Block");
//                FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());
//
//            }
//        });

            } catch (Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        try {
            if (page == 3) {
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String profilepic = String.valueOf(task.getResult().getValue());
                            Picasso.get().load(profilepic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.imageView);
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String namr = String.valueOf(task.getResult().getValue());
                            holder.name.setText(namr);
                        }
                    }
                });
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memberlistmodel memberlistmodel = new memberlistmodel(model.getId(), "Requested Permanent User");
                        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(model.getId())).setValue(memberlistmodel);
                        Map<String, String> zmap = new HashMap<>();
                        zmap.put("CounsellingGroupStatus", "RequestToJoin");
                        FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());

                    }
                });

            }
        } catch (Exception e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            if (page == 2) {
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String profilepic = String.valueOf(task.getResult().getValue());
                            Picasso.get().load(profilepic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.imageView);
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getId()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String namr = String.valueOf(task.getResult().getValue());
                            holder.name.setText(namr);
                        }
                    }
                });
                holder.button.setText("Accept");
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memberlistmodel memberlistmodel = new memberlistmodel(model.getId(), "member");
                        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(model.getId())).setValue(memberlistmodel);
                        Map<String, String> zmap = new HashMap<>();
                        zmap.put("CounsellingGroupStatus", "member");
                        FirebaseFirestore.getInstance().collection(model.getId()).document(model.getId()).set(zmap, SetOptions.merge());

                    }
                });

            }
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;
        Button button;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView5);
            button = itemView.findViewById(R.id.button2);


        }
    }
}
