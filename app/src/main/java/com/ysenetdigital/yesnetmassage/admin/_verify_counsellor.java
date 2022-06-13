package com.ysenetdigital.yesnetmassage.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.adapter.admin_counseller_adapter;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.ActivityVerifyCounsellorBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;

public class _verify_counsellor extends AppCompatActivity {
ActivityVerifyCounsellorBinding binding;
    ArrayList<signup_models> list = new ArrayList<>();
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityVerifyCounsellorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
try {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.adminCounssellorRecyclerView.setLayoutManager(linearLayoutManager);

        String key = getIntent().getStringExtra("key");
        if (key.equals("counsellor")){

            admin_counseller_adapter adapters = new admin_counseller_adapter(list, getApplicationContext(),1);
            binding.adminCounssellorRecyclerView.setAdapter(adapters);
            database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        signup_models models = dataSnapshot.getValue(signup_models.class);
                        if (models.getPost()!= null){
                            if (models.getPost().equals("Requested Counsellor")){
                                if (models.getUserID()!=null) {
                                    if (models.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                    } else {
                                        models.setUserID(dataSnapshot.getKey());
                                        list.add(models);
                                    }
                                }

                            }
                        }

                    }
                    adapters.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (key.equals("BanglaCounsellorGroup")){

            admin_counseller_adapter adapters = new admin_counseller_adapter(list, getApplicationContext(),2);
            binding.adminCounssellorRecyclerView.setAdapter(adapters);
            database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        signup_models models = dataSnapshot.getValue(signup_models.class);
                        if (models.getPost()!= null){
                            if (models.getPost().equals("Requested Bangla Counselling Group Admin")){
                                if (models.getUserID()!=null) {
                                    if (models.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                    } else {
                                        models.setUserID(dataSnapshot.getKey());
                                        list.add(models);
                                    }
                                }

                            }
                        }

                    }
                    adapters.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    } catch (Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    }
}