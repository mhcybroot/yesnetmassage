package com.ysenetdigital.yesnetmassage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.ActivitySignUpBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;
import com.ysenetdigital.yesnetmassage.models.userModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class signUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String username, memberID, email, password;
    private ProgressDialog progressDialog;

    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        phoneNumber = getIntent().getStringExtra("number");
        ArrayList<signup_models> list = new ArrayList<>();
        binding.userSingupReffercode.setVisibility(View.GONE);
        userAdapters adapters = new userAdapters(list, getApplicationContext(), 7);
        try {


            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            progressDialog = new ProgressDialog(signUp.this);
            progressDialog.setTitle("Creating  Account");
            progressDialog.setMessage("Please Wait We are Creating Your Account");
            binding.userSingupAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), signIn.class);
                    startActivity(intent);
                }
            });
            binding.userSingupAlreadyHaveAccount.setVisibility(View.GONE);
            binding.userSingupSignupBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    username = binding.userSingupUsername.getText().toString();


                    email = binding.userSingupEmail.getText().toString();
                    memberID = binding.userSingupMemberId.getText().toString();


                    if (username.isEmpty()) {
                        binding.userSingupUsername.setError("User Name Required!!!");
                        binding.userSingupUsername.requestFocus();
                    } else {
                        if (memberID.isEmpty()) {
                            binding.userSingupMemberId.setError("Member ID Required!!!");
                            binding.userSingupMemberId.requestFocus();
                        } else {
                            if (email.isEmpty()) {
                                binding.userSingupEmail.setError("Email Required!!!");
                                binding.userSingupEmail.requestFocus();
                            } else {

                                String id = Objects.requireNonNull(auth.getUid());
                                signup_models model = new signup_models(username, id, memberID, email, password, "Inactive Member", "false", phoneNumber);
                                long date = new Date().getTime();
                                Date counsellingStopTime = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
                                long counsellingStopTime2 = counsellingStopTime.getTime();
                                userModel model1 = new userModel(email, "false", username, phoneNumber, username, "Inactive Member", "false", "false", auth.getUid(), auth.getUid(), auth.getUid(), "photo", "token", date, counsellingStopTime2, "newUser", auth.getUid());
                                FirebaseFirestore.getInstance().collection(auth.getUid()).document(auth.getUid()).set(model1);
                                database.getReference().child("users").child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                list.clear();
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    signup_models models = dataSnapshot.getValue(signup_models.class);
                                                    if (models.getMemberId() != null) {

                                                        if (models.getMemberId().equals(binding.userSingupReffercode.getText().toString())) {
                                                            if (models.getUserID().equals(auth.getCurrentUser().getUid())) {
                                                            } else {
                                                                models.setUserID(dataSnapshot.getKey());
                                                                list.add(models);
                                                            }
                                                        }
                                                    }

                                                }
                                                adapters.notifyDataSetChanged();
//                                                if (list.size() > 0) {
//                                                    Toast.makeText(getApplicationContext(), "big", Toast.LENGTH_SHORT).show();
//                                                } else {
//                                                    Toast.makeText(getApplicationContext(), "Smoll", Toast.LENGTH_SHORT).show();
//
//                                                }
                                                Intent intent = new Intent(signUp.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(signUp.this, "User Created Successful with reffer", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(signUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(signUp.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(signUp.this, "User Created Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Snackbar.make(view, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
//        if (auth.getCurrentUser()!=null){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (auth.getCurrentUser()!=null){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }
}
