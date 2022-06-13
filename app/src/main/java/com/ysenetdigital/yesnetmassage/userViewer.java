package com.ysenetdigital.yesnetmassage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.adapter.adiitionalPicAdapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityUserViewerBinding;
import com.ysenetdigital.yesnetmassage.models.additional_pic;

import java.util.ArrayList;


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
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ArrayList<additional_pic> list = new ArrayList<>();
        final adiitionalPicAdapter adiitionalPicAdapter = new adiitionalPicAdapter(this, list);
        binding.userImageRecycler.setAdapter(adiitionalPicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.userImageRecycler.setLayoutManager(gridLayoutManager);
        proPic = findViewById(R.id.imageViewer_image);
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String post = getIntent().getStringExtra("post");
        String picUrl = getIntent().getStringExtra("picUrl");
        String userId = getIntent().getStringExtra("userId");
        String memberID = getIntent().getStringExtra("memberID");
        String total_reply = getIntent().getStringExtra("total_reply");
        binding.name.setText("Name:-  "+name);
        binding.email.setText("Email:-  "+email);
        binding.post.setText("Post:-  "+post);
        binding.memberID.setText("Member ID:-  "+memberID);
        binding.totalReply.setText("Total Reply:-  "+total_reply);
        database.getReference().child("users").child(auth.getUid()).child("story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    additional_pic model1 = snapshot1.getValue(additional_pic.class);
                    list.add(model1);
                }
                if (list.size() >0){
                    binding.userImageRecycler.setVisibility(View.VISIBLE);
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