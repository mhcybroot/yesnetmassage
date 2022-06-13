package com.ysenetdigital.yesnetmassage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.ActivityRequestedFriendBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;

public class RequestedFriend extends AppCompatActivity {
    ActivityRequestedFriendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestedFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       ArrayList<signup_models> list = new ArrayList<>();
        userAdapters adapters = new userAdapters(list, getApplicationContext(), 4);
        binding.requesterRecycler.setAdapter(adapters);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.requesterRecycler.setLayoutManager(linearLayoutManager);


           FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                   list.clear();
                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                       signup_models models = dataSnapshot.getValue(signup_models.class);


                       if (models.getRequest_friend() != null) {
                           if (models.getRequest_friend().equals("Friends")) {
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



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}