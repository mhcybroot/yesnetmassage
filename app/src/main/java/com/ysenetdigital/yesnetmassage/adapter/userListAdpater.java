package com.ysenetdigital.yesnetmassage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.BanglaCounsellingGroup;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.chatdeatails;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;
import com.ysenetdigital.yesnetmassage.models.userModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class userListAdpater extends RecyclerView.Adapter<userListAdpater.viewHolder> {

    Context context;
    ArrayList<userModel> datalist;

    public userListAdpater(Context context, ArrayList<userModel> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_user_list, parent, false);

        return new userListAdpater.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        userModel model = datalist.get(position);


//if (position==0){
//
////    long time = new Date().getTime();
////  if (model.getCounsellingGroupStopTime() )
//
////    if (model.getCounsellingGroupStatus()!=null){
////        if (model.getCounsellingGroupStatus().equals("newUser")){
////            long time = new Date().getTime();
////            if (model.getCounsellingGroupStopTime() < time){
////                holder.userName.setText("Counselling Meeting");
////            }
////        }
//
//    }
//}
//else{
        try {
            if (model.getUserID() != null) {
                if (model.getUserID().equals("yP6RgpxWbVPHrnkLNv1hdNlxVC43")) {

                    DocumentReference docRef = FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    String cousellingStatus = document.getString("counsellingGroupStatus");
                                    Long cousellingtime = document.getLong("counsellingGroupStopTime");

                                    if (cousellingStatus != null) {
                                        if (cousellingStatus.equals("FristJoin")) {
                                            long time = new Date().getTime();
                                            if (cousellingtime > time) {
                                                holder.add.setText("Open");
                                                holder.lastId.setVisibility(View.GONE);
                                                holder.MemberId.setVisibility(View.GONE);
                                                holder.Post.setVisibility(View.GONE);
                                                holder.userName.setText(model.getName());
                                                holder.add.setVisibility(View.GONE);
                                                holder.delete.setVisibility(View.GONE);
                                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(context, BanglaCounsellingGroup.class);
                                                        context.startActivity(intent);
                                                    }
                                                });
                                            } else {
                                                holder.add.setText("Request To Join");
                                                holder.lastId.setVisibility(View.GONE);
                                                holder.MemberId.setVisibility(View.GONE);
                                                holder.Post.setVisibility(View.GONE);
                                                holder.userName.setText(model.getName());
                                                holder.add.setVisibility(View.VISIBLE);
                                                holder.delete.setVisibility(View.GONE);
                                                holder.add.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Map<String, String> zmap = new HashMap<>();
                                                        zmap.put("counsellingGroupStatus", "RequestToJoin");
                                                        memberlistmodel memberlistmodel = new memberlistmodel(FirebaseAuth.getInstance().getUid(), "Requested Permanent User");
                                                        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(FirebaseAuth.getInstance().getUid()).setValue(memberlistmodel);

                                                        FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(zmap, SetOptions.merge());

                                                    }
                                                });
                                            }
                                        }
                                        if (cousellingStatus.equals("newUser")) {

                                            holder.add.setText("Join");
                                            holder.lastId.setVisibility(View.GONE);
                                            holder.MemberId.setVisibility(View.GONE);
                                            holder.Post.setVisibility(View.GONE);
                                            holder.userName.setText(model.getName());
                                            holder.add.setVisibility(View.VISIBLE);
                                            holder.delete.setVisibility(View.GONE);
                                            holder.add.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Date counsellingStopTime = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
                                                    long counsellingStopTime2 = counsellingStopTime.getTime();
                                                    Map<String, Long> map = new HashMap<>();
                                                    map.put("counsellingGroupStopTime", counsellingStopTime2);
                                                    Map<String, String> zmap = new HashMap<>();
                                                    zmap.put("counsellingGroupStatus", "FristJoin");
                                                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(map, SetOptions.merge());
                                                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(zmap, SetOptions.merge());
                                                    Intent intent = new Intent(context, BanglaCounsellingGroup.class);
                                                    ((Activity) context).finish();
                                                    context.startActivity(intent);
                                                }
                                            });
                                        }
                                        if (cousellingStatus.equals("Block")) {
                                            holder.itemView.setVisibility(View.GONE);
                                            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                                            params.height = 0;
                                            params.width = 0;
                                            holder.itemView.setLayoutParams(params);

                                        }
                                        if (cousellingStatus.equals("RequestToJoin")) {
                                            holder.add.setText("Requested");
                                            holder.lastId.setVisibility(View.GONE);
                                            holder.MemberId.setVisibility(View.GONE);
                                            holder.Post.setVisibility(View.GONE);
                                            holder.userName.setText(model.getName());
                                            holder.add.setVisibility(View.VISIBLE);
                                            holder.delete.setVisibility(View.GONE);
                                            holder.add.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Map<String, String> zmap = new HashMap<>();
                                                    zmap.put("CounsellingGroupStatus", "RequestToJoin");
                                                    memberlistmodel memberlistmodel = new memberlistmodel(FirebaseAuth.getInstance().getUid(), "Requested Permanent User");
                                                    FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(FirebaseAuth.getInstance().getUid()).setValue(memberlistmodel);

                                                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(zmap, SetOptions.merge());

                                                }
                                            });
                                        }
                                        if (cousellingStatus.equals("admin")){
                                            holder.lastId.setVisibility(View.GONE);
                                            holder.MemberId.setVisibility(View.GONE);
                                            holder.Post.setVisibility(View.GONE);
                                            holder.userName.setText(model.getName());
                                            holder.add.setVisibility(View.GONE);
                                            holder.delete.setVisibility(View.GONE);
                                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(context, BanglaCounsellingGroup.class);
                                                    context.startActivity(intent);
                                                    ((Activity) context).finish();
                                                }
                                            });
                                        }
                                        if (cousellingStatus.equals("member")) {
                                            holder.lastId.setVisibility(View.GONE);
                                            holder.MemberId.setVisibility(View.GONE);
                                            holder.Post.setVisibility(View.GONE);
                                            holder.userName.setText(model.getName());
                                            holder.add.setVisibility(View.GONE);
                                            holder.delete.setVisibility(View.GONE);
                                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(context, BanglaCounsellingGroup.class);
                                                    context.startActivity(intent);
                                                    ((Activity) context).finish();
                                                }
                                            });

                                        }
                                    } else {
                                        Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


//                if (model.getCounsellingGroupStatus() != null) {
//                    if (model.getCounsellingGroupStatus().equals("newUser") || model.getCounsellingGroupStatus().equals("FristJoin")) {
//                        long time = new Date().getTime();
//                        if (model.getCounsellingGroupStopTime() < time) {
//
//
//                        } else {
//
//                            holder.add.setText("Request To Join");
//                            holder.lastId.setVisibility(View.GONE);
//                            holder.MemberId.setVisibility(View.GONE);
//                            holder.Post.setVisibility(View.GONE);
//                            holder.userName.setText(model.getName());
//                            holder.add.setVisibility(View.VISIBLE);
//                            holder.delete.setVisibility(View.GONE);
//                            holder.add.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Map<String, String> zmap = new HashMap<>();
//                                    zmap.put("CounsellingGroupStatus", "RequestToJoin");
//                                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(zmap, SetOptions.merge());
//
//                                }
//                            });
//                        }
//                    } else if (model.getCounsellingGroupStatus().equals("RequestToJoin")) {
//                        holder.add.setText("Requested");
//                        holder.lastId.setVisibility(View.GONE);
//                        holder.MemberId.setVisibility(View.GONE);
//                        holder.Post.setVisibility(View.GONE);
//                        holder.userName.setText(model.getName());
//                        holder.add.setVisibility(View.VISIBLE);
//                        holder.delete.setVisibility(View.GONE);
//                        holder.add.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Map<String, String> zmap = new HashMap<>();
//                                zmap.put("CounsellingGroupStatus", "RequestToJoin");
//                                FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(zmap, SetOptions.merge());
//
//                            }
//                        });
//
//                    } else {
//                        holder.add.setText("You Have no Join Access");
//                        holder.lastId.setVisibility(View.GONE);
//                        holder.MemberId.setVisibility(View.GONE);
//                        holder.Post.setVisibility(View.GONE);
//                        holder.userName.setText(model.getName());
//                        holder.add.setVisibility(View.VISIBLE);
//                        holder.delete.setVisibility(View.GONE);
//
//                    }
//                }
//                else {
//                    holder.itemView.setVisibility(View.GONE);
//                }

                } else {
                    holder.userName.setText(model.getName());
                    holder.MemberId.setText(model.getPhone());
                    holder.add.setVisibility(View.GONE);
                    holder.delete.setVisibility(View.GONE);
                    holder.laoppp.setVisibility(View.GONE);
                    holder.lastId.setVisibility(View.GONE);
                    holder.MemberId.setVisibility(View.GONE);
                    holder.Post.setVisibility(View.GONE);


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, chatdeatails.class);
                            intent.putExtra("userId", model.getReciverID());
                            FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {

                                    } else {
                                        String profilepic = String.valueOf(task.getResult().getValue());
                                        intent.putExtra("profilePic", profilepic);
                                        FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                if (!task.isSuccessful()) {

                                                } else {
                                                    String username = String.valueOf(task.getResult().getValue());
                                                    intent.putExtra("username", username);
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("token").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                            if (!task.isSuccessful()) {

                                                            } else {
                                                                String token = String.valueOf(task.getResult().getValue());
                                                                intent.putExtra("token", token);

                                                                context.startActivity(intent);
                                                                ((Activity) context).finish();
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


                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(model.getReciverID()).child("NickName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {

                                String nickName = String.valueOf(task.getResult().getValue());

                                if (nickName == null) {
                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                String username = String.valueOf(task.getResult().getValue());
                                                holder.userName.setText(username);
                                            } else {
                                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else if (nickName.equals("null")) {
                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                String username = String.valueOf(task.getResult().getValue());
                                                holder.userName.setText(username);
                                            } else {
                                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    holder.userName.setText(nickName);

                                }

                                holder.userName.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {

                                        holder.simple_edit_user_name.setVisibility(View.VISIBLE);
                                        holder.laoppp.setVisibility(View.VISIBLE);
                                        holder.userName.setVisibility(View.GONE);
                                        holder.lastId.setVisibility(View.GONE);
                                        holder.MemberId.setVisibility(View.GONE);
                                        holder.delete.setVisibility(View.GONE);
                                        holder.Post.setVisibility(View.GONE);
                                        holder.add.setVisibility(View.VISIBLE);
                                        holder.add.setText("submit");
                                        holder.add.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String nickname = holder.simple_edit_user_name.getText().toString();

                                                if (nickname.isEmpty()) {
                                                    holder.simple_edit_user_name.setError("Nick Name required");
                                                    holder.simple_edit_user_name.requestFocus();
                                                } else {
                                                    holder.simple_edit_user_name.setVisibility(View.GONE);
                                                    holder.simple_edit_user_name.setText("");
                                                    holder.laoppp.setVisibility(View.GONE);
                                                    holder.userName.setVisibility(View.VISIBLE);
                                                    holder.lastId.setVisibility(View.VISIBLE);
                                                    holder.MemberId.setVisibility(View.VISIBLE);
                                                    holder.delete.setVisibility(View.VISIBLE);
                                                    holder.Post.setVisibility(View.VISIBLE);
                                                    holder.add.setVisibility(View.GONE);
                                                    holder.add.setText("submit");
                                                    holder.userName.setText(nickname);
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(model.getSenderID()).child("NickName").setValue(nickname);
                                                }

                                            }
                                        });


                                        return false;
                                    }
                                });
                            } else {
                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String username = String.valueOf(task.getResult().getValue());
                                            holder.userName.setText(username);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    FirebaseDatabase.getInstance().getReference().child("chats").child(model.getReciverID() + FirebaseAuth.getInstance().getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    holder.lastId.setVisibility(View.VISIBLE);
                                    holder.lastId.setText(dataSnapshot.child("massage").getValue().toString());
                                    long lastMsgTime = Objects.requireNonNull(dataSnapshot.child("timestamp").getValue(Long.class));
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                    holder.simple_last_massage_time.setText(dateFormat.format(new Date(lastMsgTime)));
                                    holder.simple_last_massage_time.setVisibility(View.VISIBLE);
                                    Long time_lastmassage = dataSnapshot.child("timestamp").getValue(Long.class);
//                            FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("lastmassagetime").setValue(time_lastmassage);
//                      FirebaseDatabase.getInstance().getReference().child("users").child(model.getSenderID()).child("lastmassagetime").setValue(time_lastmassage);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                String profilePic = String.valueOf(task.getResult().getValue());
                                Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                            }
                        }
                    });

                }
            } else {
                holder.userName.setText(model.getName());
                holder.MemberId.setText(model.getPhone());
                holder.add.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.laoppp.setVisibility(View.GONE);
                holder.lastId.setVisibility(View.GONE);
                holder.MemberId.setVisibility(View.GONE);
                holder.Post.setVisibility(View.GONE);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, chatdeatails.class);
                        intent.putExtra("userId", model.getReciverID());
                        FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {

                                } else {
                                    String profilepic = String.valueOf(task.getResult().getValue());
                                    intent.putExtra("profilePic", profilepic);
                                    FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()) {

                                            } else {
                                                String username = String.valueOf(task.getResult().getValue());
                                                intent.putExtra("username", username);
                                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("token").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        if (!task.isSuccessful()) {

                                                        } else {
                                                            String token = String.valueOf(task.getResult().getValue());
                                                            intent.putExtra("token", token);

                                                            context.startActivity(intent);
                                                            ((Activity) context).finish();
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


                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(model.getReciverID()).child("NickName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {

                            String nickName = String.valueOf(task.getResult().getValue());

                            if (nickName == null) {
                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String username = String.valueOf(task.getResult().getValue());
                                            holder.userName.setText(username);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else if (nickName.equals("null")) {
                                FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String username = String.valueOf(task.getResult().getValue());
                                            holder.userName.setText(username);
                                        } else {
                                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                holder.userName.setText(nickName);

                            }

                            holder.userName.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {

                                    holder.simple_edit_user_name.setVisibility(View.VISIBLE);
                                    holder.laoppp.setVisibility(View.VISIBLE);
                                    holder.userName.setVisibility(View.GONE);
                                    holder.lastId.setVisibility(View.GONE);
                                    holder.MemberId.setVisibility(View.GONE);
                                    holder.delete.setVisibility(View.GONE);
                                    holder.Post.setVisibility(View.GONE);
                                    holder.add.setVisibility(View.VISIBLE);
                                    holder.add.setText("submit");
                                    holder.add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String nickname = holder.simple_edit_user_name.getText().toString();

                                            if (nickname.isEmpty()) {
                                                holder.simple_edit_user_name.setError("Nick Name required");
                                                holder.simple_edit_user_name.requestFocus();
                                            } else {
                                                holder.simple_edit_user_name.setVisibility(View.GONE);
                                                holder.simple_edit_user_name.setText("");
                                                holder.laoppp.setVisibility(View.GONE);
                                                holder.userName.setVisibility(View.VISIBLE);
                                                holder.lastId.setVisibility(View.VISIBLE);
                                                holder.MemberId.setVisibility(View.VISIBLE);
                                                holder.delete.setVisibility(View.VISIBLE);
                                                holder.Post.setVisibility(View.VISIBLE);
                                                holder.add.setVisibility(View.GONE);
                                                holder.add.setText("submit");
                                                holder.userName.setText(nickname);
                                                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(model.getReciverID()).child("NickName").setValue(nickname);
                                            }

                                        }
                                    });


                                    return false;
                                }
                            });
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        String username = String.valueOf(task.getResult().getValue());
                                        holder.userName.setText(username);
                                    } else {
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                FirebaseDatabase.getInstance().getReference().child("chats").child(model.getReciverID() + FirebaseAuth.getInstance().getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                holder.lastId.setVisibility(View.VISIBLE);
                                holder.lastId.setText(dataSnapshot.child("massage").getValue().toString());
                                long lastMsgTime = Objects.requireNonNull(dataSnapshot.child("timestamp").getValue(Long.class));
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                holder.simple_last_massage_time.setText(dateFormat.format(new Date(lastMsgTime)));
                                holder.simple_last_massage_time.setVisibility(View.VISIBLE);
                                Long time_lastmassage = dataSnapshot.child("timestamp").getValue(Long.class);
//                            FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("lastmassagetime").setValue(time_lastmassage);
//                      FirebaseDatabase.getInstance().getReference().child("users").child(model.getSenderID()).child("lastmassagetime").setValue(time_lastmassage);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseDatabase.getInstance().getReference().child("users").child(model.getReciverID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            String profilePic = String.valueOf(task.getResult().getValue());
                            Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                        }
                    }
                });

            }

        }catch (Exception e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }


//    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView image, verf, pending, frdlogo;
        TextView userName, Post, MemberId, lastId, simple_last_massage_time, simple_edit_user_name;
        Button add, delete;
        LinearLayout laoppp, simple_userList;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.simple_profile_image);
            pending = itemView.findViewById(R.id.imageView2);
            simple_edit_user_name = itemView.findViewById(R.id.simple_edit_user_name);
            frdlogo = itemView.findViewById(R.id.imageView3);
            userName = itemView.findViewById(R.id.simple_user_name);
            Post = itemView.findViewById(R.id.simple_post);
            MemberId = itemView.findViewById(R.id.simple_member_id);
            lastId = itemView.findViewById(R.id.simple_last_massage);
            simple_last_massage_time = itemView.findViewById(R.id.simple_last_massage_time);
            verf = itemView.findViewById(R.id.verification);
            add = itemView.findViewById(R.id.simple_add);
            delete = itemView.findViewById(R.id.simple_delete);
            laoppp = itemView.findViewById(R.id.laoppp);
            simple_userList = itemView.findViewById(R.id.simple_userList);
        }
    }

}
