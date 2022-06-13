package com.ysenetdigital.yesnetmassage.adapter;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.chatdeatails;
import com.ysenetdigital.yesnetmassage.models.friendModel;
import com.ysenetdigital.yesnetmassage.models.signup_models;
import com.ysenetdigital.yesnetmassage.userViewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class userAdapters extends RecyclerView.Adapter<userAdapters.viewHolder> {


    ArrayList<signup_models> list;
    ArrayList<friendModel> list2;
    Context context;
    int ID;
    String nameniz;
    String emailniz;
    String phoneniz;


    public userAdapters(ArrayList<signup_models> list, Context context, int ID) {
        this.list = list;
        this.context = context;
        this.ID = ID;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.simple_user_list, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        signup_models users = list.get(position);
       try {
           if (ID == 1) {

               Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
               holder.userName.setText(users.getUsername());
               holder.Post.setText(users.getPost());

               holder.MemberId.setText(users.getMemberId());
               holder.MemberId.setVisibility(View.GONE);
               holder.Post.setVisibility(View.GONE);
               holder.lastId.setVisibility(View.GONE);
               holder.delete.setVisibility(View.GONE);

               holder.image.setOnClickListener(new View.OnClickListener() {
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
                       context.startActivity(intent);

                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("request_friend").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {
                           Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       } else {
                           String friends = String.valueOf(task.getResult().getValue());
                           if (friends.equals("true")) {
                               holder.itemView.setVisibility(View.GONE);
                               ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                               params.height = 0;
                               params.width = 0;
                               holder.itemView.setLayoutParams(params);

                           } else if (friends.equals("Friends")) {
                               holder.itemView.setVisibility(View.GONE);
                               ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                               params.height = 0;
                               params.width = 0;
                               holder.itemView.setLayoutParams(params);

//                            list.remove(list.get(holder.getAdapterPosition()));
//                         holder.simple_userList.setVisibility(View.GONE);
                           } else {
                               holder.add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       FirebaseDatabase data;
//                                    final String[] nameniz = new String[3];

                                       data = FirebaseDatabase.getInstance();
                                       FirebaseAuth auth = FirebaseAuth.getInstance();
                                       Map<String, String> items = new HashMap<>();
                                       items.put("reciverID", users.getUserID());
                                       items.put("friend", "true");
                                       items.put("request_friend", "Friends");

                                       FirebaseFirestore.getInstance().collection(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid())).document(users.getUserID()).set(items,SetOptions.merge());


                                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   nameniz = String.valueOf(task.getResult().getValue());
                                                   items.put("name", nameniz);
                                                   FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                           if (task.isSuccessful()) {
                                                               emailniz = String.valueOf(task.getResult().getValue());
                                                               items.put("email", emailniz);
                                                               FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("phoneNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           phoneniz= String.valueOf(task.getResult().getValue());
                                                                           items.put("phone", phoneniz);
                                                                           FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                   if (task.isSuccessful()) {
                                                                                       phoneniz= String.valueOf(task.getResult().getValue());
                                                                                       items.put("pic", phoneniz);
                                                                                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("token").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                                           @Override
                                                                                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                               if (task.isSuccessful()) {
                                                                                                   phoneniz= String.valueOf(task.getResult().getValue());
                                                                                                   items.put("token", phoneniz);
                                                                                                   Map<String, Long> itemsdata = new HashMap<>();
                                                                                                   itemsdata.put("lastMassageTime", new Date().getTime());

                                                                                                   FirebaseFirestore.getInstance().collection(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid())).document(users.getUserID()).set(items,SetOptions.merge());
                                                                                                   FirebaseFirestore.getInstance().collection(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid())).document(users.getUserID()).set(itemsdata,SetOptions.merge());

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


                                       Map<String, String> item = new HashMap<>();
                                       item.put("reciverID", FirebaseAuth.getInstance().getUid());
                                       item.put("friend", "false");
                                       item.put("request_friend", "true");
                                       item.put("name", users.getUsername());
                                       item.put("token", users.getToken());
                                       item.put("email", users.getEmail());
                                       item.put("pic", users.getProfilepic());
                                       item.put("userName", users.getUsername());
                                       item.put("phone", users.getPhoneNumber());
                                       item.put("post", users.getPost());
                                       Map<String, Long> itemsdatas = new HashMap<>();
                                       itemsdatas.put("lastMassageTime", new Date().getTime());

                                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(item,SetOptions.merge());
                                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(itemsdatas,SetOptions.merge());
                                       signup_models mods = new signup_models(FirebaseAuth.getInstance().getCurrentUser().getUid(), "false", "true");
                                       signup_models mods2 = new signup_models(users.getUserID(), "true", "Friends");
                                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(mods).addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           String nickname = String.valueOf(task.getResult().getValue());
                                                           FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(users.getUserID()).child("NickName").setValue(nickname);
                                                       } else {

                                                       }
                                                   }
                                               });
                                           }
                                       }).addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friends").child(users.getUserID()).setValue(mods2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           String nickname = String.valueOf(task.getResult().getValue());
//                                                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(users.getUserID()).child("NickName").setValue(nickname);
                                                       } else {

                                                       }
                                                   }
                                               });
                                           }
                                       }).addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                           }
                                       });

                                   }
                               });
                           }
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);
                           }
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + users.getUserID()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.hasChildren()) {
                           for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                               holder.lastId.setText(dataSnapshot.child("massage").getValue().toString());
                               holder.lastId.setVisibility(View.GONE);
                           }

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

           }
           else if (ID == 2) {

               holder.userName.setText(users.getFriend());
               holder.MemberId.setText(users.getRequest_friend());
               holder.lastId.setText(users.getUserID());
               holder.lastId.setVisibility(View.GONE);
               holder.add.setText("Conform");
               holder.delete.setText("delete");
               holder.add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String, String> item = new HashMap<>();
                       item.put("friend", "accept");
                       item.put("request_friend", "Friends");
                       FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(users.getUserID()).set(item, SetOptions.merge());
                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(item, SetOptions.merge());
                       FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("request_friend").setValue("Friends");
                       FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("friend").setValue("accept");
                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("friend").setValue("accept");
                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                               if (task.isSuccessful()) {
                                   String nickname = String.valueOf(task.getResult().getValue());
                                   FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(users.getUserID()).child("NickName").setValue(nickname);
                               } else {

                               }
                           }
                       });
                   }
               });
               holder.delete.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String, String> item = new HashMap<>();
                       item.put("friend", "false");
                       item.put("request_friend", "false");
                       FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(users.getUserID()).set(item,SetOptions.merge());
                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(item,SetOptions.merge());
                       FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("request_friend").setValue("false");
                       FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(users.getUserID()).child("friend").setValue("false");
                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("friend").setValue("false");


                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String profilePic = String.valueOf(task.getResult().getValue());
                           Picasso.get().load(profilePic).placeholder(R.drawable.cicle_logo).into(holder.image);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String username = String.valueOf(task.getResult().getValue());
                           holder.userName.setText(username);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });
           }
           else if (ID == 3) {
               holder.laoppp.setVisibility(View.GONE);
               holder.lastId.setVisibility(View.GONE);
               holder.add.setVisibility(View.GONE);
               holder.delete.setVisibility(View.GONE);

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friend").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {

                           String isfrend = String.valueOf(task.getResult().getValue());
//                        if (isfrend.equals("true")) {
//                            holder.frdlogo.setVisibility(View.VISIBLE);
//                            holder.pending.setVisibility(View.GONE);
//                        } else {
//
//                            holder.frdlogo.setVisibility(View.GONE);
//                            holder.pending.setVisibility(View.VISIBLE);
//                        }/>
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String profilePic = String.valueOf(task.getResult().getValue());
                           Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                       }
                   }
               });

               FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(users.getUserID()).child("NickName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (task.isSuccessful()) {

                           String nickName = String.valueOf(task.getResult().getValue());

                           if (nickName == null) {
                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                                               FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("NickName").child(users.getUserID()).child("NickName").setValue(nickname);
                                           }

                                       }
                                   });


                                   Toast.makeText(context, "find himm", Toast.LENGTH_SHORT).show();
                                   return false;
                               }
                           });
                       } else {
                           FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });


               FirebaseDatabase.getInstance().getReference().child("chats").child(users.getUserID() + FirebaseAuth.getInstance().getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
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
                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("lastmassagetime").setValue(time_lastmassage);
                           }

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
//
//
//
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(context, chatdeatails.class);
                       intent.putExtra("userId", users.getUserID());
                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friend").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                               if (!task.isSuccessful()) {

                               } else {
                                   String friends = String.valueOf(task.getResult().getValue());
                                   if (friends.equals("accept")) {

                                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                                               if (!task.isSuccessful()) {
                                                   Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                               } else {
                                                   String profilePic = String.valueOf(task.getResult().getValue());
                                                   intent.putExtra("profilePic", profilePic);
                                                   FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                           if (!task.isSuccessful()) {
                                                               Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                           } else {
                                                               String username = String.valueOf(task.getResult().getValue());
                                                               intent.putExtra("username", username);

                                                               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("token").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                       if (!task.isSuccessful()) {
                                                                           Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                       } else {
                                                                           String token = String.valueOf(task.getResult().getValue());
                                                                           intent.putExtra("token", token);
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
                                   } else {
                                       Toast.makeText(context, "User Delete Your account \n Please Sent Friend Requiest again", Toast.LENGTH_LONG).show();
                                   }

                               }
                           }
                       });


                   }
               });

           }
           else if (ID == 4) {

               holder.lastId.setVisibility(View.GONE);
               holder.delete.setVisibility(View.GONE);
               holder.add.setText("cancel request");
               holder.add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String, String> item = new HashMap<>();
                       item.put("friend", "false");
                       item.put("request_friend", "false");
                       FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(users.getUserID()).set(item,SetOptions.merge());
                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(item,SetOptions.merge());


                       signup_models mods = new signup_models(FirebaseAuth.getInstance().getCurrentUser().getUid(), "false", "false");
                       signup_models mods2 = new signup_models(users.getUserID(), "false", "false");

                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(mods);
                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friends").child(users.getUserID()).setValue(mods2);
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String profilePic = String.valueOf(task.getResult().getValue());
                           Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                       }
                   }
               });

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });


           }
           else if (ID == 5) {

               holder.lastId.setVisibility(View.GONE);
               holder.delete.setVisibility(View.GONE);
               holder.add.setText("Remove Friend");
               holder.add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String, String> item = new HashMap<>();
                       item.put("friend", "false");
                       item.put("request_friend", "false");
                       FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(users.getUserID()).set(item,SetOptions.merge());
                       FirebaseFirestore.getInstance().collection(users.getUserID()).document(FirebaseAuth.getInstance().getUid()).set(item,SetOptions.merge());


                       signup_models mods = new signup_models(FirebaseAuth.getInstance().getCurrentUser().getUid(), "false", "false");
                       signup_models mods2 = new signup_models(users.getUserID(), "false", "false");

                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(mods);
                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friends").child(users.getUserID()).setValue(mods2);
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String profilePic = String.valueOf(task.getResult().getValue());
                           Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                       }
                   }
               });

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });


           }
           else if (ID == 6) {

               holder.lastId.setVisibility(View.GONE);
               holder.delete.setVisibility(View.GONE);
               holder.add.setText("Remove Friend");
               holder.add.setVisibility(View.GONE);
               holder.add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       signup_models mods = new signup_models(FirebaseAuth.getInstance().getCurrentUser().getUid(), "false", "false");
                       signup_models mods2 = new signup_models(users.getUserID(), "false", "false");

                       FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("Friends").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(mods);
                       FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friends").child(users.getUserID()).setValue(mods2);
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String profilePic = String.valueOf(task.getResult().getValue());
                           Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image);
                       }
                   }
               });

               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String memberId = String.valueOf(task.getResult().getValue());
                           holder.MemberId.setText(memberId);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String post = String.valueOf(task.getResult().getValue());
                           holder.Post.setText(post);
                       }
                   }
               });
               FirebaseDatabase.getInstance().getReference().child("users").child(users.getUserID()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if (!task.isSuccessful()) {

                       } else {
                           String verification = String.valueOf(task.getResult().getValue());
                           if (verification.equals("true")) {
                               holder.verf.setVisibility(View.VISIBLE);
                           } else {
                               holder.verf.setVisibility(View.GONE);

                           }
                       }
                   }
               });


           }
           else  if (ID == 7){
               Toast.makeText(context, "suecessful", Toast.LENGTH_SHORT).show();
           }
       }catch (Exception e){
           Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
       }



    }

    @Override
    public int getItemCount() {
        return list.size();
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
