package com.ysenetdigital.yesnetmassage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.ysenetdigital.yesnetmassage.databinding.ActivityPhoneBinding;

public class ActivityPhone extends AppCompatActivity {
    ActivityPhoneBinding binding;
    FirebaseAuth auth;
    String fullNumber;
    String countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
        auth = FirebaseAuth.getInstance();

        CountryCodePicker ccp;
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(binding.editTextPhone);
//        String[] countryName = getResources().getStringArray(R.array.contryCode);
//        ArrayAdapter<String> language_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.sninner_lanuage, R.id.simple_language_spenner, countryName);
//        binding.spiner2.setAdapter(language_adapter);
//        binding.spiner2.setFocusable(true);
//        binding.spiner2.setFocusableInTouchMode(true);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = binding.editTextPhone.getText().toString();

                if (number.isEmpty()) {


                    binding.editTextPhone.setError("Number Required !");
                    binding.editTextPhone.requestFocus();

                } else {
                    ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                        @Override
                        public void onValidityChanged(boolean isValidNumber) {
                            if (isValidNumber) {
                                Toast.makeText(ActivityPhone.this, ccp.getFullNumberWithPlus(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), OTP.class);
                                intent.putExtra("number", ccp.getFullNumberWithPlus());
                                startActivity(intent);
                                finish();

                            } else {
                                binding.editTextPhone.setError("Invalid Number");
                                binding.editTextPhone.requestFocus();
                            }
                        }
                    });
                }

//                else if(number.length() == 10){
//
//
//
////                    String selectedid = binding.spiner2.getSelectedItem().toString();
//
////                    switch (selectedid) {
////                        case "+880":
////                            fullNumber = "+880" + number;
////                            Toast.makeText(ActivityPhone.this, fullNumber, Toast.LENGTH_SHORT).show();
////                            Intent intent = new Intent(getApplicationContext(), OTP.class);
////                            intent.putExtra("number", fullNumber);
////                            startActivity(intent);
////                            finish();
////                            break;
////
////                        case "+91":
////                            fullNumber = "+91" + number;
////                            Toast.makeText(ActivityPhone.this, fullNumber, Toast.LENGTH_SHORT).show();
////                            Intent intent1 = new Intent(getApplicationContext(), OTP.class);
////                            intent1.putExtra("number", fullNumber);
////                            startActivity(intent1);
////                            finish();
////                            break;
////
////                        default:
////                            binding.spiner2.requestFocusFromTouch();
////                            binding.spiner2.performClick();
////                            Toast.makeText(ActivityPhone.this, "Not select Country", Toast.LENGTH_SHORT).show();
////                    }
//                    ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
//                        @Override
//                        public void onValidityChanged(boolean isValidNumber) {
//                            // your code
//                        }
//                    });
//                }else {
//                    binding.editTextPhone.setError("Invalid Number !");
//                    binding.editTextPhone.requestFocus();
//                }
            }
        });
    } catch (Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}