package com.ysenetdigital.yesnetmassage.Fregments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.FriendAcivity;
import com.ysenetdigital.yesnetmassage.MainActivity;
import com.ysenetdigital.yesnetmassage.RequestedFriend;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.FragmentAddFriendsBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;


public class addFriends extends Fragment {


    public addFriends() {
        // Required empty public constructor
    }

    FragmentAddFriendsBinding binding;
    ArrayList<signup_models> list = new ArrayList<>();
    ArrayList<signup_models> list2 = new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog, progressDialogs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddFriendsBinding.inflate(inflater, container, false);
        try {
            database = FirebaseDatabase.getInstance();
            auth = FirebaseAuth.getInstance();
            userAdapters adapters = new userAdapters(list, getContext(), 1);
            binding.addFriendsRecyclerView.setAdapter(adapters);
            binding.requestedFriend.setVisibility(View.GONE);
            binding.textView3.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.addFriendsRecyclerView.setLayoutManager(linearLayoutManager);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loding ......");
            binding.requestedFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), RequestedFriend.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
            binding.textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FriendAcivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            binding.addfriendEacrcImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String search = binding.addfriendEacrcEdittext.getText().toString();
                    if (search.isEmpty()) {

                        binding.addfriendEacrcEdittext.requestFocus();
                        database.getReference().child("users").limitToFirst(50).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    signup_models models = dataSnapshot.getValue(signup_models.class);
                                    if (models.getPost() != null) {

                                        if (models.getPost().contains("Member")) {
                                            if (models.getUserID().equals(auth.getCurrentUser().getUid())) {
                                            } else {
                                                models.setUserID(dataSnapshot.getKey());
                                                list.add(models);
                                            }
                                        }
                                    }

                                }
                                adapters.notifyDataSetChanged();
//                            if (list.size()>0){
//                                Toast.makeText(getContext(), "big", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(getContext(), "Smoll", Toast.LENGTH_SHORT).show();
//
//                            }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        database.getReference().child("users").limitToFirst(50).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    signup_models models = dataSnapshot.getValue(signup_models.class);

                                    if (models.getPhoneNumber() != null) {
                                        if (models.getPhoneNumber().contains(search)) {
                                            models.setUserID(dataSnapshot.getKey());
                                            list.add(models);
                                        }

                                    }

                                }
//                            if (list.size()>0){
//                                Toast.makeText(getContext(), "big", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(getContext(), "Smoll", Toast.LENGTH_SHORT).show();
//
//                            }
                                adapters.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            });

            database.getReference().child("users").limitToFirst(80).addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                       try {
                           signup_models models = dataSnapshot.getValue(signup_models.class);
                           if (models.getPost() != null) {
                               if (models.getPost().contains("Member")) {
                                   if (models.getUserID().equals(auth.getCurrentUser().getUid())) {
                                   } else {
                                       models.setUserID(dataSnapshot.getKey());
                                       list.add(models);
                                   }
                               }

                           }

                       }catch (Exception e){
                       }


                    }
//
                    adapters.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        Toast.makeText(getContext() , "pausez", Toast.LENGTH_SHORT).show();
//        getFragmentManager().beginTransaction().detach(addFriends.this).attach(addFriends.this).commit();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        Toast.makeText(getContext(),"stopz",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(addFriends.this).attach(addFriends.this).commit();
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getContext(),"destroyz",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(addFriends.this).attach(addFriends.this).commit();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(),"resumez",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(addFriends.this).attach(addFriends.this).commit();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(getContext(),"startz",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(addFriends.this).attach(addFriends.this).commit();
//
//    }
}