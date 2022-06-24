package com.ysenetdigital.yesnetmassage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.adapter.adiitionalPicAdapter;
import com.ysenetdigital.yesnetmassage.adapter.videoViewAdapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityUserViewerBinding;
import com.ysenetdigital.yesnetmassage.models.additional_pic;
import com.ysenetdigital.yesnetmassage.models.videoView;

import java.util.ArrayList;
import java.util.Objects;


public class userViewer extends AppCompatActivity {
ActivityUserViewerBinding binding;
ImageView proPic;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String post = getIntent().getStringExtra("post");
        String picUrl = getIntent().getStringExtra("picUrl");
        String userId = getIntent().getStringExtra("userId");
        String memberID = getIntent().getStringExtra("memberID");
        String total_reply = getIntent().getStringExtra("total_reply");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ArrayList<additional_pic> list = new ArrayList<>();
        ArrayList<videoView> videoViewArrayList =new ArrayList<>();
        final videoViewAdapter videoadapter = new videoViewAdapter(this,videoViewArrayList,userId);
        binding.userVideoRecycler.setAdapter(videoadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.userVideoRecycler.setLayoutManager(linearLayoutManager);
        database.getReference().child("users").child(Objects.requireNonNull(userId)).child("storyVideo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoViewArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    videoView videoView = snapshot1.getValue(videoView.class);
                    videoViewArrayList.add(videoView);
                    videoView.setVideoID(snapshot1.getKey());
                }


//                if (videoViewArrayList.isEmpty() ){
//                    Toast.makeText(getApplicationContext(), "not find any video", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "find video list ", Toast.LENGTH_SHORT).show();
//                }
                videoadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        ArrayList<videoView> videoViewArrayList =new ArrayList<>();
//        final videoViewAdapter videoadapter = new videoViewAdapter(this,videoViewArrayList);
//        binding.userImageRecyclerVIdeo.setAdapter(videoadapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        binding.userImageRecyclerVIdeo.setLayoutManager(linearLayoutManager);
//        database.getReference().child("users").child(userId).child("storyVideo").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                videoViewArrayList.clear();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    videoView videoView = snapshot1.getValue(videoView.class);
//                    videoViewArrayList.add(videoView);
//                }
//
//
//                if (videoViewArrayList.isEmpty() ){
//                    Toast.makeText(userViewer.this, "not find any video", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(userViewer.this, "find video list ", Toast.LENGTH_SHORT).show();
//                }
//                videoadapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(userViewer.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        final adiitionalPicAdapter adiitionalPicAdapter = new adiitionalPicAdapter(this, list,userId);
        binding.userImageRecycler.setAdapter(adiitionalPicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        binding.userImageRecycler.setLayoutManager(gridLayoutManager);
        proPic = findViewById(R.id.imageViewer_image);

        binding.name.setText("Name:-  "+name);
        binding.email.setText("Email:-  "+email);
        binding.post.setText("Post:-  "+post);
        binding.memberID.setText("Member ID:-  "+memberID);
        binding.totalReply.setText("Total Reply:-  "+total_reply);
        database.getReference().child("users").child(userId).child("story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    additional_pic model1 = snapshot1.getValue(additional_pic.class);
                    list.add(model1);
                    model1.setPicId(snapshot1.getKey());
                }

                adiitionalPicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(userViewer.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageViewerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ImageViewer.class);
                intent.putExtra("url",picUrl);
                startActivity(intent);

            }
        });
        Picasso.get().load(picUrl).placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.imageViewerImage);

    }
}