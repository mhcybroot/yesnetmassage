package com.ysenetdigital.yesnetmassage.Fregments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.FirebaseDatabase;
import com.ysenetdigital.yesnetmassage.adapter.MemberFregmentAdapter;
import com.ysenetdigital.yesnetmassage.databinding.FragmentMembersBinding;
import com.ysenetdigital.yesnetmassage.models.signup_models;

import java.util.ArrayList;


public class Members extends Fragment {


    public Members() {

    }

    FragmentMembersBinding binding;
    ArrayList<signup_models> list = new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("pageMember", Context.MODE_PRIVATE);


        // Inflate the layout for this fragment

    try {
        binding = FragmentMembersBinding.inflate(inflater, container, false);
        binding.viewPageMember.setAdapter(new MemberFregmentAdapter(getActivity().getSupportFragmentManager()));
        binding.tabLayoutMember.setupWithViewPager(binding.viewPageMember);
        SharedPreferences.Editor editor = sharedpreferences.edit();
    }catch (Exception e ){
        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

//       SharedPreferences.Editor editor2 = sharedmain.edit();

//        Toast.makeText(getActivity(), pageMAin, Toast.LENGTH_SHORT).show();
//        if (pageMAin.equals("1")){
//            binding.viewPageMember.setCurrentItem(0);
//        }
//        SharedPreferences sharedmain = getContext().getSharedPreferences("pageMainAcvity", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor2 = sharedmain.edit();
//
//        String language = sharedpreferences.getString("position", "");
//        Toast.makeText(getActivity(), language, Toast.LENGTH_SHORT).show();
//
////
//        String pageMAin = sharedmain.getString("position", "");
//        Toast.makeText(getActivity(), pageMAin, Toast.LENGTH_SHORT).show();
//        String pageMAin2 = sharedmain.getString("position", "");
//        Toast.makeText(getActivity(), pageMAin2, Toast.LENGTH_SHORT).show();
//        if (pageMAin2.equals("1")){
//            editor2.putString("position", "123");
//            editor2.apply();
//            binding.viewPageMember.setCurrentItem(0);
//        }


//        binding.viewPageMember.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                String pageMAin = sharedmain.getString("position", "");
////                Toast.makeText(getActivity(), pageMAin, Toast.LENGTH_SHORT).show();
////                if (pageMAin.equals("1")){
////                    binding.viewPageMember.setCurrentItem(0);
////                }
////                if (pageMAin2.equals("1")){
////                    editor2.putString("position", "235");
////                    editor2.apply();
////                    binding.viewPageMember.setCurrentItem(0);
////                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String pageMAin2 = sharedmain.getString("position", "");
//                Toast.makeText(getActivity(), pageMAin2, Toast.LENGTH_SHORT).show();
//
//
//                switch (position) {
//
//                    case 0:
//
//                        binding.viewPageMember.setCurrentItem(0);
//
//                        break;
//
//                    case 1:
//
//                        binding.viewPageMember.setCurrentItem(1);
//
//                        break;
//
//                    case 2:
//
//                        binding.viewPageMember.setCurrentItem(2);
//                        break;
//                    default:
//                      pageMAin2 = sharedmain.getString("position", "");
//
//                        if (pageMAin2.equals("1")){
//                            binding.viewPageMember.setCurrentItem(2);
//                        }
//
//                        break;
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
//        database = FirebaseDatabase.getInstance();
//        userAdapters adapters = new userAdapters(list, getContext(), 1);
//        binding.membersRecyclerView.setAdapter(adapters);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.membersRecyclerView.setLayoutManager(linearLayoutManager);
//        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    signup_models models = dataSnapshot.getValue(signup_models.class);
//                    if (models.getPost().contains("Member")) {
//                        if (models.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                        } else {
//                            models.setUserID(dataSnapshot.getKey());
//                            list.add(models);
//                        }
//                    }
//
//                }
//                adapters.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        binding.button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                userAdapters adapters = new userAdapters(list, getContext(), 1);
//                binding.membersRecyclerView.setAdapter(adapters);
//                database.getReference().child("users").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            signup_models models = dataSnapshot.getValue(signup_models.class);
//                            if (models.getPost().contains("Member")) {
//                                if (models.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                                } else {
//                                    models.setUserID(dataSnapshot.getKey());
//                                    list.add(models);
//                                }
//                            }
//
//                        }
//                        adapters.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//        binding.button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userAdapters adapters = new userAdapters(list, getContext(), 2);
//                binding.membersRecyclerView.setAdapter(adapters);
//                database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                            signup_models models = dataSnapshot.getValue(signup_models.class);
//                            if (models.getFriend().equals("false")) {
//                                if (models.getRequest_friend().equals("true")) {
//                                    models.setUserID(dataSnapshot.getKey());
//                                    list.add(models);
//                                }
//                            }
//
//
//                        }
//                        adapters.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//        binding.button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return binding.getRoot();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        Toast.makeText(getContext() , "pauseL", Toast.LENGTH_SHORT).show();
//        getFragmentManager().beginTransaction().detach(Members.this).attach(Members.this).commit();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        Toast.makeText(getContext(),"stopL",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(Members.this).attach(Members.this).commit();
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getContext(),"destroyL",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(Members.this).attach(Members.this).commit();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(),"resumeL",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(Members.this).attach(Members.this).commit();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(getContext(),"startL",Toast.LENGTH_LONG).show();
//        getFragmentManager().beginTransaction().detach(Members.this).attach(Members.this).commit();
//
//    }
}