package com.ysenetdigital.yesnetmassage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ysenetdigital.yesnetmassage.adapter.chatAdapter;
import com.ysenetdigital.yesnetmassage.databinding.ActivityChatdeatailsBinding;
import com.ysenetdigital.yesnetmassage.models.massageModels;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class chatdeatails extends AppCompatActivity {
    ActivityChatdeatailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Uri selectedImage;
    ProgressDialog progressDialog;
    String senderId;
    String reciveID;
    String senderroom;
    String receiverroom;
    String rendomkey;
    ValueEventListener seenlistener;
    DatabaseReference referenceMassage;

    NoInternetSnackbar noInternetSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatdeatailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        try {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Storing your Image");
        progressDialog.setMessage("Please Wait we are Sending Your Image");
        progressDialog.setCancelable(false);
        senderId = auth.getUid();
        reciveID = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("username");
        String profilePic = getIntent().getStringExtra("profilePic");
        String token = getIntent().getStringExtra("token");
//        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        final ArrayList<massageModels> MassageModels = new ArrayList<>();
        senderroom = senderId + reciveID;
        receiverroom = reciveID + senderId;
        seenMessage(reciveID);

        final chatAdapter chatAdapters = new chatAdapter(MassageModels, this,1);
        binding.chatdeatlsRecyclerView.setAdapter(chatAdapters);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatdeatlsRecyclerView.setLayoutManager(linearLayoutManager);
        binding.chatdeatlsToolbarUserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.chatdeatlsToolbarUserProfile);
        binding.chatdeatlsToolbarBackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.chatdeeatlsAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(chatdeatails.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(800)    //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });
        final Handler handler = new Handler();
        binding.chatdeeatlsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Typing....");

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(userTypingStop, 1000);

            }

            Runnable userTypingStop = new Runnable() {
                @Override
                public void run() {
                    FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");

                }
            };
        });
        database.getReference().child("presence").child(reciveID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if (!status.isEmpty()) {
                        if (status.equals("Online")) {
                            binding.chatdeatlsToolbarStatusOnline.setVisibility(View.VISIBLE);
                            binding.chatdeatlsToolbarStatusOnline.setText(status);
                        } else if (status.equals("Offline")) {
                            binding.chatdeatlsToolbarStatusOnline.setVisibility(View.VISIBLE);
                            binding.chatdeatlsToolbarStatusOnline.setText(status);

                        } else if (status.equals("Typing....")) {
                            binding.chatdeatlsToolbarStatusOnline.setVisibility(View.VISIBLE);
                            binding.chatdeatlsToolbarStatusOnline.setText(status);
                        } else {
                            binding.chatdeatlsToolbarStatusOnline.setVisibility(View.VISIBLE);
                            binding.chatdeatlsToolbarStatusOnline.setText(status);

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                MassageModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    massageModels model1 = snapshot1.getValue(massageModels.class);
                    model1.setMassageID(snapshot.getKey());
                    MassageModels.add(model1);

                }
                binding.chatdeatlsRecyclerView.smoothScrollToPosition(binding.chatdeatlsRecyclerView.getAdapter().getItemCount());
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
                    final massageModels model = new massageModels(senderId, massage, reciveID, false);
                    model.setTimestamp(new Date().getTime());

                    binding.chatdeeatlsEditText.setText("");
                    rendomkey = database.getReference().push().getKey();

                    database.getReference().child("chats").child(senderroom).child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            database.getReference().child("chats").child(receiverroom).child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sendNotification(userName, massage, token);
                                    long time = new Date().getTime();
                                    String datayime = Long.toString(time);
                                    Map<String, Long> item = new HashMap<>();
                                    item.put("lastMassageTime",new Date().getTime());
                                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(reciveID).set(item, SetOptions.merge());
                                    FirebaseFirestore.getInstance().collection(reciveID).document(FirebaseAuth.getInstance().getUid()).set(item,SetOptions.merge());
                                    FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").child(reciveID).child("timeofmassage").setValue(datayime);
                                    FirebaseDatabase.getInstance().getReference().child("users").child(reciveID).child("Friends").child(FirebaseAuth.getInstance().getUid()).child("timeofmassage").setValue(datayime);

//                                    database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("total_reply").setValue()
//                                    database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("total_reply").setValue("1");
                                    database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("total_reply").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(chatdeatails.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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

    private void seenMessage(final String friendid) {

        referenceMassage = FirebaseDatabase.getInstance().getReference().child("chats").child(receiverroom);


        seenlistener = referenceMassage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    massageModels chats = ds.getValue(massageModels.class);


                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("isseen", true);
                    ds.getRef().updateChildren(hashMap);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void sendNotification(String name, String massage, String token) {
        try {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject data = new JSONObject();
            data.put("title", name);
            data.put("body", massage);
            data.put("sound", "default");
            JSONObject notificationData = new JSONObject();
            notificationData.put("notification", data);
            notificationData.put("to", token);

            JsonObjectRequest request = new JsonObjectRequest(url, notificationData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                    Toast.makeText(chatdeatails.this, "Successfully send", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(chatdeatails.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headerMap = new HashMap<>();
                    String key = "Key=AAAAtp79rkA:APA91bFLLONPLjGRbYkyVcaze0mMHZGwSOnuZDG2wfzLz29Q1Liqwx-HzWjGybssdWpZXK9ZrI2dfE0Mb7u7vh2Vm_VDl-CgjwsS56k1LdznEPeav_gzEtOyuIimTg7F39KosPU5HUzA";
                    headerMap.put("Authorization", key);
                    headerMap.put("Content-Type", "application/json");
                    return headerMap;
                }
            };
            queue.add(request);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");
        // No Internet Snackbar
        NoInternetSnackbar.Builder builder2 = new NoInternetSnackbar.Builder(this, (ViewGroup) findViewById(android.R.id.content));

        builder2.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder2.setIndefinite(true); // Optional
        builder2.setNoInternetConnectionMessage("No active Internet connection!"); // Optional
        builder2.setOnAirplaneModeMessage("You have turned on the airplane mode!"); // Optional
        builder2.setSnackbarActionText("Settings");
        builder2.setShowActionToDismiss(false);
        builder2.setSnackbarDismissActionText("OK");

        noInternetSnackbar = builder2.build();

    }

    @Override
    protected void onPause() {
        referenceMassage.removeEventListener(seenlistener);
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Offline");
        if (noInternetSnackbar != null) {
            noInternetSnackbar.destroy();
        }
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");

    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                        rendomkey = database.getReference().push().getKey();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child("chats").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(rendomkey);
                        reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        Toast.makeText(chatdeatails.this, imageUrl, Toast.LENGTH_SHORT).show();
                                        final massageModels model = new massageModels(senderId, "Photo", reciveID, imageUrl, false);
                                        model.setTimestamp(new Date().getTime());

                                        database.getReference().child("chats").child(senderroom).child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                database.getReference().child("chats").child(receiverroom).child(rendomkey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(chatdeatails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(chatdeatails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(chatdeatails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(chatdeatails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(chatdeatails.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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