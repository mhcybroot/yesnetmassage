package com.ysenetdigital.yesnetmassage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.adapter.adiitionalPicAdapter;
import com.ysenetdigital.yesnetmassage.adapter.videoViewAdapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityUserProfileBinding;
import com.ysenetdigital.yesnetmassage.models.additional_pic;
import com.ysenetdigital.yesnetmassage.models.videoView;
import com.ysenetdigital.yesnetmassage.verificationPage.Counsellor_verification;
import com.ysenetdigital.yesnetmassage.verificationPage.InactiveVerification;
import com.ysenetdigital.yesnetmassage.verificationPage.active_member_verification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    ActivityUserProfileBinding binding;
    Spinner post_spiner;
    String[] post_name;
    Uri selectedImage,selectedVideo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ArrayList<additional_pic> list = new ArrayList<>();
        final adiitionalPicAdapter adiitionalPicAdapter = new adiitionalPicAdapter(this, list,FirebaseAuth.getInstance().getUid());
        binding.addpicProfile.setAdapter(adiitionalPicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        binding.addpicProfile.setLayoutManager(gridLayoutManager);
        ArrayList<videoView> videoViewArrayList =new ArrayList<>();
        final videoViewAdapter videoadapter = new videoViewAdapter(this,videoViewArrayList,FirebaseAuth.getInstance().getUid());
        binding.userImageRecyclerVIdeo.setAdapter(videoadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.userImageRecyclerVIdeo.setLayoutManager(linearLayoutManager);
        FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("storyVideo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoViewArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    videoView videoView = snapshot1.getValue(videoView.class);
                    videoView.setVideoID(snapshot1.getKey());
                    videoViewArrayList.add(videoView);

                }


                if (videoViewArrayList.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "not find any video", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "find video list ", Toast.LENGTH_SHORT).show();
                }
                videoadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.addRecyclerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),45);
            }
        });
        binding.addRecyclerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent,"Select Video"),55);

            }
        });


        database.getReference().child("users").child(auth.getUid()).child("story").addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Storing your Image");
        progressDialog.setMessage("Please Wait we are Storing ");
        progressDialog.setCancelable(false);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        binding.profileProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(UserProfile.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1028)    //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        post_name = getResources().getStringArray(R.array.post_name);
        post_spiner = findViewById(R.id.span_meeting1);
        ArrayAdapter<String> language_adapter = new ArrayAdapter<String>(this, R.layout.sninner_lanuage, R.id.simple_language_spenner, post_name);
        post_spiner.setAdapter(language_adapter);
        database.getReference().child("users").child(auth.getUid()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String post = String.valueOf(task.getResult().getValue());
                    if (post.equals("Inactive Member")) {
                        binding.userProfileApplayPostButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String selectedid = post_spiner.getSelectedItem().toString();
                                switch (selectedid) {
                                    case "SELECT":
                                        Toast.makeText(UserProfile.this, "SELECT", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "Active Member":
                                        Toast.makeText(UserProfile.this, "Active User", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), active_member_verification.class);
                                        startActivity(intent);
                                        break;
                                    case "Counsellor":
                                        Toast.makeText(UserProfile.this, "Counsellor", Toast.LENGTH_SHORT).show();
                                        database.getReference().child("users").child(auth.getUid()).child("post").setValue("Requested Counsellor");
                                        database.getReference().child("users").child(auth.getUid()).child("verification").setValue("false");
                                        break;
                                    case "Teacher":
                                        Toast.makeText(UserProfile.this, "Teacher", Toast.LENGTH_SHORT).show();
                                        database.getReference().child("users").child(auth.getUid()).child("post").setValue("Requested Teacher");
                                        database.getReference().child("users").child(auth.getUid()).child("verification").setValue("false");
                                        break;
                                    case "Team Leader":
                                        Toast.makeText(UserProfile.this, "Team Leader", Toast.LENGTH_SHORT).show();
                                        database.getReference().child("users").child(auth.getUid()).child("post").setValue("Requested Team Leader");
                                        database.getReference().child("users").child(auth.getUid()).child("verification").setValue("false");
                                        break;

                                    case "Bangla Counselling Group Admin":
                                        Toast.makeText(UserProfile.this, "Applied Bangla Counselling Group Admin", Toast.LENGTH_SHORT).show();
                                        database.getReference().child("users").child(auth.getUid()).child("post").setValue("Requested Bangla Counselling Group Admin");
                                        break;
                                    default:
                                        Toast.makeText(UserProfile.this, "sorry something wrong", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    } else {
                        binding.spanMeeting1.setVisibility(View.GONE);
                        binding.userProfileApplayPostButton.setText("Your requested for a post");
                    }
                }
            }
        });


        database.getReference().child("users").child(auth.getUid()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String username = String.valueOf(task.getResult().getValue());
                    binding.profileName.setText(username);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String email = String.valueOf(task.getResult().getValue());
                    binding.profileEmail.setText(email);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("memberId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String memberId = String.valueOf(task.getResult().getValue());
                    binding.profileMemberID.setText(memberId);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String post = String.valueOf(task.getResult().getValue());
                    binding.profilePost.setText(post);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("total_reply").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String total_reply = String.valueOf(task.getResult().getValue());
                    binding.totalReply.setText("Total Reply:-  " + total_reply);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("profilepic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String profilepic = String.valueOf(task.getResult().getValue());
                    Picasso.get().load(profilepic).placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.profileProfileImage);
                }
            }
        });
        database.getReference().child("users").child(auth.getUid()).child("verification").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    String verification = String.valueOf(task.getResult().getValue());
                    if (verification.equals("false")) {
                        database.getReference().child("users").child(auth.getUid()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    String post = String.valueOf(task.getResult().getValue());
                                    if (post.equals("Inactive Member")) {
                                        binding.profileVarificationButtom.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), InactiveVerification.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                    if (post.equals("Counsellor")) {
                                        binding.profileVarificationButtom.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), Counsellor_verification.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(UserProfile.this, "Wait for Admin Approve", Toast.LENGTH_SHORT).show();
                                        binding.profileVarificationButtom.setText("Verification Not Available");
                                    }
                                }
                            }
                        });
                    } else {
                        binding.profileVarificationButtom.setText("Verified");
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45) {
            progressDialog.show();
            if (data != null) {
                if (data.getData() != null) {
                    Uri selectedImages = data.getData();

                    if (selectedImages != null) {

                        Calendar calendar = new GregorianCalendar();
                        String rendomKey = String.valueOf(calendar.getTimeInMillis());
                        StorageReference references = FirebaseStorage.getInstance().getReference().child("story").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(rendomKey);
                        references.putFile(selectedImages).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("picUrl", imageUrl);
                                        map.put("userID",FirebaseAuth.getInstance().getUid());
                                        HashMap<String, Object> pic = new HashMap<>();
                                        map.put("pic", imageUrl);
                                        map.put("userID",FirebaseAuth.getInstance().getUid());
                                        FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(pic, SetOptions.merge());

                                        database.getReference().child("users").child(auth.getCurrentUser().getUid()).child("story").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
//                                              Toast.makeText(UserProfile.this, "45 seccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }else if (requestCode == 55){
            progressDialog.show();
            if (data != null) {
                if (data.getData() != null) {
                    Uri selectedVideo = data.getData();
                    Calendar calendar = new GregorianCalendar();
                    String rendompush = String.valueOf(calendar.getTimeInMillis());

                    if (selectedVideo != null) {
                        StorageReference references = FirebaseStorage.getInstance().getReference().child("storyVideo").child(FirebaseAuth.getInstance().getUid()).child(rendompush);
                        references.putFile(selectedVideo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        HashMap<String, Object> maps = new HashMap<>();
                                        maps.put("VidoeUrl", imageUrl);
                                        maps.put("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        HashMap<String, Object> pic = new HashMap<>();
//                                        map.put("pic", imageUrl);
//                                        map.put("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        database.getReference().child("users").child(auth.getCurrentUser().getUid()).child("storyVideo").push().setValue(maps).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
//                                              Toast.makeText(UserProfile.this, "45 seccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
        else {
            if (data != null) {
                if (data.getData() != null) {
                    binding.profileProfileImage.setImageURI(data.getData());
                    selectedImage = data.getData();
                    if (selectedImage != null) {
                        progressDialog.show();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child("profile").child(auth.getCurrentUser().getUid());
                        reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        database.getReference().child("users").child(auth.getCurrentUser().getUid()).child("profilepic").setValue(imageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
//                                                Toast.makeText(UserProfile.this, "Successful imagePicker", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(UserProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }
        }
    }
}