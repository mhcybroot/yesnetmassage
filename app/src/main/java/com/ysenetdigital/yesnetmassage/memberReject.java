package com.ysenetdigital.yesnetmassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.memberlistadapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityMemberRejectBinding;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;

import java.util.ArrayList;

public class memberReject extends AppCompatActivity {
ActivityMemberRejectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberRejectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<memberlistmodel> list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.memberlistrecyclerView.setLayoutManager(linearLayoutManager);
        memberlistadapter memberlistadapte = new memberlistadapter(getApplicationContext(), list, 3);
        binding.memberlistrecyclerView.setAdapter(memberlistadapte);
        FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    memberlistmodel datalist = snapshot1.getValue(memberlistmodel.class);
                    if (datalist.getPost() != null) {

                        if (datalist.getPost().equals("member")) {

                            list.add(datalist);
                        }
                    }

                }

                memberlistadapte.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}