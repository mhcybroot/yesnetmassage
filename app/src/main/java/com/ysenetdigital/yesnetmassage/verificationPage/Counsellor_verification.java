package com.ysenetdigital.yesnetmassage.verificationPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ysenetdigital.yesnetmassage.MainActivity;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.databinding.ActivityCounsellorVerificationBinding;

import java.util.Objects;

public class Counsellor_verification extends AppCompatActivity {
ActivityCounsellorVerificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCounsellorVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cousellerVerificationWebview.loadUrl("https://www.bbdonlinetraining.in/en/");
        binding.cousellerVerificationWebview.getSettings().setLoadsImagesAutomatically(true);
        binding.cousellerVerificationWebview.getSettings().setJavaScriptEnabled(true);
        binding.cousellerVerificationWebview.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (Objects.equals(url, "https://www.bbdonlinetraining.in/en/subadmin/counsellor/")) {


                    try {
                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("verification").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        return true;


                    } catch (Exception e) {

                    }
                }


                return false;
            }
        });
    }
}