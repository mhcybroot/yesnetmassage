package com.ysenetdigital.yesnetmassage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.ysenetdigital.yesnetmassage.databinding.ActivityOtpBinding;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
ActivityOtpBinding binding;
FirebaseAuth auth;
String verificationId;
    String phoneNumber;

    ProgressDialog dialog;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
//        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
         phoneNumber = getIntent().getStringExtra("number");


            dialog = new ProgressDialog(this);
            dialog.setMessage("Sending OTP...");
            dialog.setCancelable(false);
            dialog.show();

            auth = FirebaseAuth.getInstance();

            getSupportActionBar().hide();



            binding.phoneLbl.setText("Verify " + phoneNumber);

            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(OTP.this)
                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override

                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verifyId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(verifyId, forceResendingToken);
                            dialog.dismiss();
                            verificationId = verifyId;

                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            binding.otpView.requestFocus();
                        }
                    }).build();

            PhoneAuthProvider.verifyPhoneNumber(options);

            binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
                @Override
                public void onOtpCompleted(String otp) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                    auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(OTP.this, signUp.class);
                                intent.putExtra("number",phoneNumber);
                                startActivity(intent);
                                finishAffinity();


                            } else {
                                Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


      

    }}