package com.ysenetdigital.yesnetmassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.memberlistadapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityRequesteduseraddBinding;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;

import java.util.ArrayList;

public class requesteduseradd extends AppCompatActivity {
ActivityRequesteduseraddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequesteduseraddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<memberlistmodel> list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        memberlistadapter memberlistadapter = new memberlistadapter(getApplicationContext(), list,2);
        binding.requesetuseraddrecycler.setAdapter(memberlistadapter);
        binding.requesetuseraddrecycler.setLayoutManager(linearLayoutManager);
//        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(FirebaseAuth.getInstance().getUid())
        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    memberlistmodel datalist = snapshot1.getValue(memberlistmodel.class);

                    if (datalist.getId() != null) {
                        if (datalist.getId().equals(FirebaseAuth.getInstance().getUid())) {

                        } else {

                            if (datalist.getPost() != null) {
                                if (datalist.getPost().equals("Requested Permanent User")){
                                    list.add(datalist);
                                }
                            }

                        }
                    }

                }

                memberlistadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}