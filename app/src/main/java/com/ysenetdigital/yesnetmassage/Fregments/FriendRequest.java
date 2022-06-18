package com.ysenetdigital.yesnetmassage.Fregments;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.adapter.userAdapters;
import com.ysenetdigital.yesnetmassage.databinding.FragmentFriendRequestBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;
import java.util.Objects;

public class FriendRequest extends Fragment {


    public FriendRequest() {
        // Required empty public constructor
    }

    FragmentFriendRequestBinding binding;
    ArrayList<signup_models> list = new ArrayList<>();
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFriendRequestBinding.inflate(inflater, container, false);
        try {


            database = FirebaseDatabase.getInstance();
            userAdapters adapters = new userAdapters(list, getContext(), 2);
            binding.requestFriendRecyclerView.setAdapter(adapters);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.requestFriendRecyclerView.setLayoutManager(linearLayoutManager);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loding ......");

            database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        signup_models models = dataSnapshot.getValue(signup_models.class);
                        if (models.getFriend() != null) {
                            if (models.getFriend().equals("false")) {
                                if (models.getRequest_friend().equals("true")) {
                                    models.setUserID(dataSnapshot.getKey());
                                    list.add(models);
                                }
                            }
                        }

                        if (list.isEmpty()) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("TotalRequestFriendFrom").setValue(0);

                        } else {
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("TotalRequestFriendFrom").setValue(list.size());
                        }
                    }
                    adapters.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();


    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        Toast.makeText(getContext() , "pauseR", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//
//        getFragmentManager().beginTransaction().detach(FriendRequest.this).attach(FriendRequest.this).commit();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//        Toast.makeText(getContext(),"stopR",Toast.LENGTH_LONG).show();
//         getFragmentManager().beginTransaction().detach(FriendRequest.this).attach(FriendRequest.this).commit();
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getContext(),"destroyR",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(FriendRequest.this).attach(FriendRequest.this).commit();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(),"resumeR",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(FriendRequest.this).attach(FriendRequest.this).commit();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(getContext(),"startR",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(FriendRequest.this).attach(FriendRequest.this).commit();
//
//    }
}