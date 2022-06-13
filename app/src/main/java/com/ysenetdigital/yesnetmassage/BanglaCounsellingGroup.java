package com.ysenetdigital.yesnetmassage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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
import com.ysenetdigital.yesnetmassage.adapter.chatAdapter;
import com.ysenetdigital.yesnetmassage.admin.postList;
import com.ysenetdigital.yesnetmassage.databinding.ActivityBanglaCounsellingGroupBinding;
import com.ysenetdigital.yesnetmassage.models.massageModels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BanglaCounsellingGroup extends AppCompatActivity {
ActivityBanglaCounsellingGroupBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBanglaCounsellingGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
        setTitle("Bangle Counselling Group");
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Storing your Image");
        progressDialog.setMessage("Please Wait we are Sending Your Image");
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        final ArrayList<massageModels> MassageModels = new ArrayList<>();
        final chatAdapter chatAdapters = new chatAdapter(MassageModels, this,2);
        binding.recyclerView.setAdapter(chatAdapters);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.chatdeeatlsAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(BanglaCounsellingGroup.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(800)    //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });
        database.getReference().child("group").child("BanglaCounsellingGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                MassageModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    massageModels model1 = snapshot1.getValue(massageModels.class);
                    model1.setMassageID(snapshot.getKey());
                    MassageModels.add(model1);

                }
                binding.recyclerView.smoothScrollToPosition(binding.recyclerView.getAdapter().getItemCount());
                chatAdapters.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.chatdeeatlsSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String massage = binding.chatdeeatlsEditText.getText().toString();

                if (massage.isEmpty()) {
                    binding.chatdeeatlsEditText.setError("No Massage");
                    binding.chatdeeatlsEditText.requestFocus();

                } else {
                    final massageModels model = new massageModels(FirebaseAuth.getInstance().getUid(), massage,  false);
                    model.setTimestamp(new Date().getTime());

                    binding.chatdeeatlsEditText.setText("");
                   String  rendomkey = database.getReference().push().getKey();

                    database.getReference().child("group").child("BanglaCounsellingGroup").child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            long time = new Date().getTime();
                            String datayime = Long.toString(time);
                            Map<String, Long> item = new HashMap<>();
                            item.put("lastMassageTime",new Date().getTime());
                            database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("total_reply").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(BanglaCounsellingGroup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        try {
                                            int totalreply = task.getResult().getValue(Integer.class);

                                            int final_value = totalreply + 1;
                                            database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("total_reply").setValue(final_value);


                                        }catch (Exception e){
                                            database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("total_reply").setValue(1);

                                        }



                                    }
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    } catch (Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.banglacounsellingmenu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Member:
                FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            String post = String.valueOf(task.getResult().getValue());
                            if (post.equals("admin")){
                                Intent intent = new Intent(getApplicationContext(),MemberList.class);
                                startActivity(intent);
                                finish();
                                
                            }else {
                                Toast.makeText(BanglaCounsellingGroup.this, "Your are not admin", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(BanglaCounsellingGroup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.Request:
                FirebaseDatabase.getInstance().getReference().child("group").child("BanglaCounsellingGroup").child("GroupInfo").child("memberlist").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            String post = String.valueOf(task.getResult().getValue());
                            if (post.equals("admin")){
                                Intent intent = new Intent(getApplicationContext(),requesteduseradd.class);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(BanglaCounsellingGroup.this, "Your are not admin", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(BanglaCounsellingGroup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

        }


        return true;
    }

    @Override
    public void onBackPressed() {
      Intent intent= new Intent(getApplicationContext(),MainActivity.class);
      startActivity(intent);
      finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            progressDialog.show();
            if (data != null) {
                if (data.getData() != null) {
                    selectedImage = data.getData();
//                    Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();
                    if (selectedImage != null) {
                        Calendar calenda = Calendar.getInstance();
                        String rendomkey = database.getReference().push().getKey();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child("chats").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(rendomkey);
                        reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        Toast.makeText(BanglaCounsellingGroup.this, imageUrl, Toast.LENGTH_SHORT).show();
                                        final massageModels model = new massageModels(FirebaseAuth.getInstance().getUid(),imageUrl,"Photo",1, false);


                                        model.setTimestamp(new Date().getTime());

                                        database.getReference().child("group").child("BanglaCounsellingGroup").child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                database.getReference().child("group").child("BanglaCounsellingGroup").child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(BanglaCounsellingGroup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(BanglaCounsellingGroup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(BanglaCounsellingGroup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(BanglaCounsellingGroup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(BanglaCounsellingGroup.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "task canseled", Toast.LENGTH_SHORT).show();
        }


    }
}