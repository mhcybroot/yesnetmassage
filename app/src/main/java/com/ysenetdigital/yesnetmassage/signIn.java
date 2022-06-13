package com.ysenetdigital.yesnetmassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ysenetdigital.yesnetmassage.databinding.ActivitySignInBinding;
import com.ysenetdigital.yesnetmassage.databinding.ActivitySignUpBinding;

public class signIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String username, memberID, email, password;
    private   ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(signIn.this);
        progressDialog.setTitle("Sign In.....");
        progressDialog.setMessage("Please We are Sign in Your Account");
        binding.userSigninSigninBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                password = binding.userSigninPassword.getText().toString();
                email = binding.userSigninEmail.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        Snackbar.make(view,"Successful", BaseTransientBottomBar.LENGTH_LONG).show();
                        Intent  intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(view,e.getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });


            }
        });
        binding.userSinginClickToSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),signUp.class);
                startActivity(intent);
            }
        });

    }
}