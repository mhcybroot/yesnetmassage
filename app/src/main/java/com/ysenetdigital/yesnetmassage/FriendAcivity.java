package com.ysenetdigital.yesnetmassage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.ActivityFriendAcivityBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;

public class FriendAcivity extends AppCompatActivity {
ActivityFriendAcivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendAcivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<signup_models> list = new ArrayList<>();
        userAdapters adapters = new userAdapters(list, getApplicationContext(), 5);
        binding.requesterRecycler.setAdapter(adapters);
        try {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.requesterRecycler.setLayoutManager(linearLayoutManager);
        FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    signup_models models = dataSnapshot.getValue(signup_models.class);


                    if (models.getFriend() != null) {
                        if (models.getFriend().equals("accept")) {
                            models.setUserID(dataSnapshot.getKey());
                            list.add(models);
                        }
                    }


                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });
    } catch (Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}