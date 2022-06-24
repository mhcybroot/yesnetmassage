package com.ysenetdigital.yesnetmassage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.memberlistadapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityMemberListBinding;
import com.ysenetdigital.yesnetmassage.models.memberlistmodel;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {
    ActivityMemberListBinding binding;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
auth = FirebaseAuth.getInstance();
        ArrayList<memberlistmodel> list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.memberlistrecyclerView.setLayoutManager(linearLayoutManager);
        try {
            binding.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.button4.setVisibility(View.GONE);
                    binding.button3.setVisibility(View.GONE);
                    memberlistadapter memberlistadapter = new memberlistadapter(getApplicationContext(), list, 1);
                    binding.memberlistrecyclerView.setAdapter(memberlistadapter);
                    FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                memberlistmodel datalist = snapshot1.getValue(memberlistmodel.class);

                                if (datalist.getId() != null) {
                                    if (datalist.getId().equals(auth.getUid())) {

                                    } else {

                                        if (datalist.getPost() != null) {
                                            if (datalist.getPost().equals("admin")) {

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
            });
            binding.button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    binding.button4.setVisibility(View.GONE);
//                    binding.button3.setVisibility(View.GONE);
//                    Intent intent = new Intent(getApplicationContext(),memberReject.class);
//                    startActivity(intent);
                    binding.button4.setVisibility(View.GONE);
                    binding.button3.setVisibility(View.GONE);
                    memberlistadapter memberlistadapter = new memberlistadapter(getApplicationContext(), list, 1);
                    binding.memberlistrecyclerView.setAdapter(memberlistadapter);
                    FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                memberlistmodel datalist = snapshot1.getValue(memberlistmodel.class);

                                if (datalist.getId() != null) {
                                    if (datalist.getId().equals(auth.getUid())) {

                                    } else {

                                        if (datalist.getPost() != null) {
                                            if (datalist.getPost().equals("member")) {

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
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}