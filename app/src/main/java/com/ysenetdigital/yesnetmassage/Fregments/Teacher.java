package com.ysenetdigital.yesnetmassage.Fregments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.FragmentTeacherBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;

public class Teacher extends Fragment {


    public Teacher() {
        // Required empty public constructor
    }

    FragmentTeacherBinding binding;
    ArrayList<signup_models> list = new ArrayList<>();
    ArrayList<signup_models> molist = new ArrayList<>();
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeacherBinding.inflate(inflater, container, false);
        try {
            userAdapters adapters = new userAdapters(list, getContext(), 5);
            binding.allfriends.setAdapter(adapters);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.allfriends.setLayoutManager(linearLayoutManager);
            database = FirebaseDatabase.getInstance();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Finding  Chats ");
            progressDialog.setMessage("Please Wait We are Finding Chats ......");

            database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        signup_models models = dataSnapshot.getValue(signup_models.class);
                        if (models.getFriend() != null && models.getFriend().equals("accept")) {
                            models.setUserID(dataSnapshot.getKey());
                            list.add(models);
                        }
                        if (list.isEmpty()) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("TotalFriend").setValue(0);

                        } else {
                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("TotalFriend").setValue(list.size());
                        }

                    }
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
}