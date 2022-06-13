package com.ysenetdigital.yesnetmassage.Fregments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ysenetdigital.yesnetmassage.adapter.userListAdpater;
import com.ysenetdigital.yesnetmassage.databinding.FragmentCounsellingBinding;
import com.ysenetdigital.yesnetmassage.models.userModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Counselling extends Fragment {
    FragmentCounsellingBinding binding;
    ArrayList<userModel> datalist = new ArrayList<>();
    FirebaseDatabase database;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    public Counselling() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCounsellingBinding.inflate(inflater, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        userListAdpater userListAdpater = new userListAdpater(getContext(), datalist);
        binding.chatsRecyclerView.setAdapter(userListAdpater);
        binding.icBaselineAnnouncement24Text.setVisibility(View.GONE);
        binding.icBaselineAnnouncement24.setVisibility(View.GONE);
        binding.chatsRecyclerView.setVisibility(View.VISIBLE);

        binding.chatsRecyclerView.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Finding  Chats ");
        progressDialog.setMessage("Please Wait We are Finding Chats ......");
        firestore = FirebaseFirestore.getInstance();
//        final Handler handler = new Handler();
//        handler.removeCallbacksAndMessages(null);
//        Runnable userTypingStop = null;
//        handler.postDelayed(userTypingStop, 3000);
//
//         userTypingStop = new Runnable() {
//            @Override
//            public void run() {
//                firestore.collection(FirebaseAuth.getInstance().getUid()).orderBy("lastMassageTime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        datalist.clear();
//                        for (DocumentSnapshot d : list) {
//
//                            userModel obj = d.toObject(userModel.class);
//                            if (obj != null) {
//
//                                datalist.add(obj);
//                            }
//
//                        }
//                        userListAdpater.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        };
try {
    firestore.collection(FirebaseAuth.getInstance().getUid()).orderBy("lastMassageTime").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            datalist.clear();
            for (DocumentSnapshot d : list) {

                userModel obj = d.toObject(userModel.class);

                if (obj != null) {
                    if(obj.getFriend() !=null){
                        if (obj.getFriend().equals("accept")){
                            datalist.add(obj);

                        }
                    }

                }

            }

            if (list.isEmpty()){
                datalist.add( new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }else if (list.size()==0){
                datalist.add( new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }else if (list.size()==1){
                datalist.add( new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }else if (list.size()==2){
                datalist.add( new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }else if (list.size()>2&&!list.isEmpty()){
                datalist.add(list.size()-1, new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }else {
                datalist.add(list.size()-1, new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));

            }
//            if (list.size() < 2){
//                datalist.add(list.size(), new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));
//            }else {
//                datalist.add( new userModel("yesnet@gmail.com"," বাংলা কাউন্সিলিং গ্রুপ"," বাংলা কাউন্সিলিং গ্রুপ","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43","yP6RgpxWbVPHrnkLNv1hdNlxVC43"));
//
//            }

//                if (obj.getCounsellingGroupStatus()!=null){
//                    if (obj.getCounsellingGroupStatus().equals("newUser")){
//                        long time = new Date().getTime();
//                        if (obj.getCounsellingGroupStopTime() > time){
//
//
//                        }
//                    }
//
//                }



            userListAdpater.notifyDataSetChanged();


        }
    });

}catch (Exception e){
    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
}


//        database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("Friends").orderByChild("lastmassagetim").addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    signup_models models = dataSnapshot.getValue(signup_models.class);
//                    if (models != null && models.getFriend().equals("accept")) {
//                        models.setUserID(dataSnapshot.getKey());
//                        list.add(models);
//                    }
//                    if (list.isEmpty()) {
//
//                    } else {
//
//                        binding.chatsRecyclerView.setVisibility(View.VISIBLE);
//                        binding.icBaselineAnnouncement24.setVisibility(View.GONE);
//                        binding.icBaselineAnnouncement24Text.setVisibility(View.GONE);
//                    }
//
//                }
//                adapters.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
try {



        FirebaseDatabase.getInstance().getReference().child("presence").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue("Online");
    }catch (Exception e){
        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
    }


}