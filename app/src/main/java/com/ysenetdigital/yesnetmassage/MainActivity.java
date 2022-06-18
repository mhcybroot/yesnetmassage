package com.ysenetdigital.yesnetmassage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ysenetdigital.yesnetmassage.adapter.FragmentsAdapters;
import com.ysenetdigital.yesnetmassage.admin.postList;
import com.ysenetdigital.yesnetmassage.databinding.ActivityMainBinding;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    NoInternetDialog noInternetDialog;
    NoInternetSnackbar noInternetSnackbar;
    String be;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {


            SharedPreferences sharedpreferences = getSharedPreferences("pageMainAcvity", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String pageMAin2 = sharedpreferences.getString("position", "");
            be = pageMAin2;
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.fetchAndActivate().addOnSuccessListener(new OnSuccessListener<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    String toolbarColor = mFirebaseRemoteConfig.getString("toolbarColor");
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(toolbarColor)));
                }
            });
            auth = FirebaseAuth.getInstance();

            binding.viewPage.setAdapter(new FragmentsAdapters(getSupportFragmentManager()));
            binding.tabLayout.setupWithViewPager(binding.viewPage);

//        binding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                String language = sharedpreferences.getString("position", "");
//                Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
//
//                switch (position){
//
//                    case 0:
//                        editor.putString("position", "1");
//                        editor.apply();
//
//                        break;
//                    case 1:
//                        editor.putString("position", be);
//                        editor.apply();
//                        break;
//                    case 2:
//                        editor.putString("position", "1");
//                        editor.apply();
//                        break;
//                    default:
//                        editor.putString("position", "1");
//                        editor.apply();
//                        break;
//
//
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    Map<String, String> item = new HashMap<>();
                    item.put("token", s);
                    FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).document(FirebaseAuth.getInstance().getUid()).set(item, SetOptions.merge());
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("token").setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(auth.getCurrentUser().getUid())).setValue("Online");
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        // No Internet Dialog
        NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);

        builder1.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder1.setCancelable(false); // Optional
        builder1.setNoInternetConnectionTitle("No Internet"); // Optional
        builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        builder1.setShowInternetOnButtons(true); // Optional
        builder1.setPleaseTurnOnText("Please turn on"); // Optional
        builder1.setWifiOnButtonText("Wifi"); // Optional
        builder1.setMobileDataOnButtonText("Mobile data"); // Optional

        builder1.setOnAirplaneModeTitle("No Internet"); // Optional
        builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        builder1.setPleaseTurnOffText("Please turn off"); // Optional
        builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        builder1.setShowAirplaneModeOffButtons(true); // Optional

        noInternetDialog = builder1.build();


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

//    @Override
//    protected void onStop() {
//        super.onStop();
//        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Offline");
//
//
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //    @Override
//    protected void onDestroy() {
//           super.onDestroy();
//        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Offline");
//
//
//    }

    @Override
    protected void onStart() {
        FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String username = String.valueOf(task.getResult().getValue());
                    if (username.equals("null")){
                        Intent intent = new Intent(MainActivity.this, signUp.class);
                        intent.putExtra("number",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        try {
                            FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Intent intent = new Intent(MainActivity.this, signUp.class);
                    intent.putExtra("number",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });


        super.onStart();
    }

    @Override
    protected void onPause() {
        try {
            if (FirebaseAuth.getInstance().getUid() != null) {
                FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(auth.getUid())).setValue("Offline");
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        // No Internet Dialog
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }

        // No Internet Snackbar
        if (noInternetSnackbar != null) {
            noInternetSnackbar.destroy();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings_main:
                FirebaseDatabase.getInstance().getReference().child("users").child(auth.getUid()).child("post").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {

                        } else {
                            String post = String.valueOf(task.getResult().getValue());
                            if (post.equals("admin")) {
                                Intent intent2 = new Intent(getApplicationContext(), postList.class);
                                startActivity(intent2);
                            } else {
                                Toast.makeText(MainActivity.this, "Sorry your not Admin", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;

            case R.id.logout_main:
                try {
                    FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(auth.getUid())).setValue("Offline");
                    auth.signOut();
                    Intent intent = new Intent(getApplicationContext(), ActivityPhone.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Profile:
                Intent intent1 = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent1);
                break;
        }


        return true;

    }

}