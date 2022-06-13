package com.ysenetdigital.yesnetmassage.Fregments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ysenetdigital.yesnetmassage.databinding.FragmentChatsBinding;
import com.ysenetdigital.yesnetmassage.models.userModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class chats extends Fragment {

    public chats() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    ArrayList<userModel> datalist = new ArrayList<>();
    FirebaseDatabase database;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatsRecyclerView.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Finding  Chats ");
        progressDialog.setMessage("Please Wait We are Finding Chats ......");
        firestore = FirebaseFirestore.getInstance();
//        firestore.collection(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                for
//            }
//        });

//        binding.seeMoreChats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//
//                            signup_models models = dataSnapshot.getValue(signup_models.class);
//
//                            if (models != null && models.getFriend().equals("true")) {
//                                models.setUserID(dataSnapshot.getKey());
//                                list.add(models);
//
//                            }
//
//
//                        }
//                        adapters.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//
//            }
//        });
//        database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends")
//       database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").orderByChild("lastmassagetime").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    signup_models models = dataSnapshot.getValue(signup_models.class);
//                    if (models != null && models.getFriend().equals("true")) {
//                        models.setUserID(dataSnapshot.getKey());
//                        list.add(models);
//
//                    }
//                    if (list.isEmpty()) {
//
//                    } else {
//
//                        binding.chatsRecyclerView.setVisibility(View.VISIBLE);
////                        binding.seeMoreChats.setVisibility(View.VISIBLE);
//                        binding.icBaselineAnnouncement24.setVisibility(View.GONE);
//                        binding.icBaselineAnnouncement24Text.setVisibility(View.GONE);
//                    }
//
//                }
//                adapters.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
//
//            }
//        });

//        database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").orderByChild("timeofmassage").limitToLast(1).addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    signup_models models = dataSnapshot.getValue(signup_models.class);
//                    if (models != null && models.getFriend().equals("true")) {
//                        models.setUserID(dataSnapshot.getKey());
//                        list.add(models);
//
//
//                    }
//                    if (list.isEmpty()) {
//
//                    } else {
//                        binding.chatsRecyclerView.setVisibility(View.VISIBLE);
////                        binding.seeMoreChats.setVisibility(View.VISIBLE);
//                        binding.icBaselineAnnouncement24.setVisibility(View.GONE);
//                        binding.icBaselineAnnouncement24Text.setVisibility(View.GONE);
//                    }
//
//                }
//                adapters.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");

    }

}