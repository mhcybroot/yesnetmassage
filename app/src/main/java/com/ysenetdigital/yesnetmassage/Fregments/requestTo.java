package com.ysenetdigital.yesnetmassage.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.MainActivity;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.FragmentAddFriendsBinding;
import com.ysenetdigital.yesnetmassage.databinding.FragmentRequestToBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;


public class requestTo extends Fragment {

    public requestTo() {
        // Required empty public constructor
    }
FragmentRequestToBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestToBinding.inflate(inflater, container, false);

try {
    ArrayList<signup_models> list = new ArrayList<>();
    userAdapters adapters = new userAdapters(list, getContext(), 4);
    binding.requesterRecycler.setAdapter(adapters);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    binding.requesterRecycler.setLayoutManager(linearLayoutManager);
    FirebaseDatabase.getInstance().getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

            list.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                signup_models models = dataSnapshot.getValue(signup_models.class);


                if (models.getRequest_friend() != null) {
                    if (models.getRequest_friend().equals("Friends")) {
                        models.setUserID(dataSnapshot.getKey());
                        list.add(models);
                    }
                }


            }
            adapters.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

        }
    });

}catch (Exception e){
    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
}

        return  binding.getRoot();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        Toast.makeText(getContext() , "pauseP", Toast.LENGTH_SHORT).show();
//        getFragmentManager().beginTransaction().detach(requestTo.this).attach(requestTo.this).commit();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        Toast.makeText(getContext(),"stopP",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(requestTo.this).attach(requestTo.this).commit();
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getContext(),"destroyP",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(requestTo.this).attach(requestTo.this).commit();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(),"resumeP",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(requestTo.this).attach(requestTo.this).commit();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(getContext(),"startP",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(requestTo.this).attach(requestTo.this).commit();
//
//    }
}