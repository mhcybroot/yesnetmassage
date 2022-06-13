package com.ysenetdigital.yesnetmassage.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.databinding.ActivityPostListBinding;

public class postList extends AppCompatActivity {
ActivityPostListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
try {
        binding.postlistCounseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),_verify_counsellor.class);
                intent.putExtra("key","counsellor");
                startActivity(intent);
            }
        });
        binding.postlistBanglaCounsellerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),_verify_counsellor.class);
                intent.putExtra("key","BanglaCounsellorGroup");
                startActivity(intent);
            }
        });

    } catch (Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
    }
}