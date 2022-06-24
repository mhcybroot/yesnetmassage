package com.ysenetdigital.yesnetmassage.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.GetReactModel;
import com.ysenetdigital.yesnetmassage.models.additional_pic;
import com.ysenetdigital.yesnetmassage.models.videoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class videoViewAdapter extends RecyclerView.Adapter<videoViewAdapter.viewholder> {
    Context context;
    ArrayList<videoView> list = new ArrayList<>();
    String userKey;
FirebaseDatabase database;
FirebaseAuth auth;
    public videoViewAdapter(Context context, ArrayList<videoView> list, String userKey) {
        this.context = context;
        this.list = list;
        this.userKey = userKey;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videoview, parent, false);
        return new videoViewAdapter.viewholder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        videoView model = list.get(position);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
//        Toast.makeText(context, model.getVideoID(), Toast.LENGTH_SHORT).show();
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(new int[]{
                        R.drawable.like,
                        R.drawable.laughing,
                        R.drawable.angry,
                        R.drawable.dislike,

                })
                .build();

//if (model.getUserID()==null){
//
//}else {
//    if (model.getVideoID()==null){
//
//    }else {
//        database.getReference().child("users").child(Objects.requireNonNull(model.getUserID())).child("storyVideo").child(Objects.requireNonNull(model.getVideoID())).child("Total_Like").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    String like = String.valueOf(task.getResult().getValue());
//                    if (like.equals("null")) {
//
//                    } else {
//                        total_like = Integer.parseInt(like);
//                    }
//
//
//                }
//            }
//        });
//        database.getReference().child("users").child(Objects.requireNonNull(model.getUserID())).child("storyVideo").child(Objects.requireNonNull(model.getVideoID())).child("total_angry").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    String angry = String.valueOf(task.getResult().getValue());
//                    if (angry.equals("null")) {
//
//                    } else {
//                        total_angry = Integer.parseInt(angry);
//                    }
//
//                }
//            }
//        });
//        database.getReference().child("users").child(Objects.requireNonNull(model.getUserID())).child("storyVideo").child(Objects.requireNonNull(model.getVideoID())).child("total_laughing").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    String laughing = String.valueOf(task.getResult().getValue());
//                    if (laughing.equals("null")) {
//
//                    } else {
//                        total_laughing = Integer.parseInt(laughing);
//                    }
//
//                }
//            }
//        });
//        database.getReference().child("users").child(Objects.requireNonNull(model.getUserID())).child("storyVideo").child(Objects.requireNonNull(model.getVideoID())).child("total_dislike").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    String dislike = String.valueOf(task.getResult().getValue());
//                    if (dislike.equals("null")) {
//
//                    } else {
//                        total_dislike = Integer.parseInt(dislike);
//                    }
//
//                }
//            }
//        });
//        switch (positio) {
//                case 0:
//
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("Total_Like").setValue(total_like + 1);
////                    Map<String, String> maptotal_like = new HashMap<>();
////                    maptotal_like.put(auth.getUid(), "like");
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("likedUser").setValue(maptotal_like);
//                    holder.countlike.setText(" Total 👍 :- " + total_like + " Total 😆 :- " + total_laughing + " Total 😡 :- " +
//                            total_angry + " Total 👎 :- " +
//                            total_dislike);
//                    break;
//                case 1:
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("total_angry").setValue(total_angry + 1);
////                    Map<String, String> maptotal_angry = new HashMap<>();
////                    maptotal_angry.put(auth.getUid(), "angry");
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("likedUser").setValue(maptotal_angry);
//                    holder.countlike.setText(" Total 👍 :- " + total_like + " Total 😆 :- " + total_laughing + " Total 😡 :- " +
//                            total_angry + " Total 👎 :- " +
//                            total_dislike);
//                    break;
//                case 2:
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("total_laughing").setValue(total_laughing + 1);
////                    Map<String, String> maptotal_laughing = new HashMap<>();
////                    maptotal_laughing.put(auth.getUid(), "laughing");
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("likedUser").setValue(maptotal_laughing);
//                    holder.countlike.setText(" Total 👍 :- " + total_like + " Total 😆 :- " + total_laughing + " Total 😡 :- " +
//                            total_angry + " Total 👎 :- " +
//                            total_dislike);
//                    break;
//                case 3:
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("total_dislike").setValue(total_dislike + 1);
////                    Map<String, String> maptotal_dislike = new HashMap<>();
////                    maptotal_dislike.put(auth.getUid(), "dislike");
////                    database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("likedUser").setValue(maptotal_dislike);
//                    holder.countlike.setText(" Total 👍 :- " + total_like + " Total 😆 :- " + total_laughing + " Total 😡 :- " +
//                            total_angry + " Total 👎 :- " +
//                            total_dislike);
//                    break;
//
//
//                default:
//                    Toast.makeText(context, "Not Found Your Like", Toast.LENGTH_SHORT).show();
//                    break;
//            }

        @SuppressLint("SetTextI18n") ReactionPopup popup = new ReactionPopup(context, config, (positio) -> {
            Map<String, Integer> setReact = new HashMap<>();
            setReact.put("Position", positio);

            database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("totalReact").child(auth.getUid()).setValue(setReact);
            return true; // true is closing popup, false is requesting a new selection
        });
        if (model.getUserID() != null) {
            if (model.getVideoID() != null) {

                database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("totalReact").child(auth.getUid()).child("Position").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            String react = String.valueOf(task.getResult().getValue());
                            if (react.equals("1") || react.equals("2") || react.equals("3") || react.equals("0")) {
                                int reactPos = Integer.parseInt(react);
                                switch (reactPos) {
                                    case 0:
                                        holder.imageView7.setImageResource(R.drawable.like);
                                        break;
                                    case 1:
                                        holder.imageView7.setImageResource(R.drawable.laughing);
                                        break;
                                    case 2:
                                        holder.imageView7.setImageResource(R.drawable.angry);

                                        break;
                                    case 3:
                                        holder.imageView7.setImageResource(R.drawable.dislike);
                                        break;
                                    default:
                                        holder.imageView7.setImageResource(R.drawable.like);
                                        break;

                                }
                            } else {
                                holder.imageView7.setImageResource(R.drawable.like);
                            }

                        }
                    }
                });
                ArrayList<GetReactModel> rect = new ArrayList<>();
                database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("totalReact").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        rect.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            GetReactModel model1 = snapshot1.getValue(GetReactModel.class);
                            rect.add(model1);
                        }
                         if (rect.isEmpty()) {
                             if (model.getUserID().equals(auth.getUid())) {
                                 holder.imageView6.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (model.getVideoID() != null) {

                                             database.getReference().child("users").child(auth.getUid()).child("storyVideo").child(model.getVideoID()).removeValue();

                                         }
                                     }
                                 });
                             } else {
                                 holder.imageView6.setVisibility(View.GONE);
                             }
//
                        }
                         else {
                             if (model.getUserID().equals(auth.getUid())) {
                                 holder.imageView6.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (model.getVideoID() != null) {

                                             database.getReference().child("users").child(auth.getUid()).child("storyVideo").child(model.getVideoID()).child("totalReact").removeValue();
                                             Toast.makeText(context, "Image React deleted .Try again To delete Your Video", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });
                             } else {
                                 holder.imageView6.setVisibility(View.GONE);
                             }
                             database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("TotalReaction").setValue(rect.size());

//                    Toast.makeText(context, "Find React", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                database.getReference().child("users").child(model.getUserID()).child("storyVideo").child(model.getVideoID()).child("TotalReaction").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            String TotalReaction = String.valueOf(task.getResult().getValue());
                            if (TotalReaction.equals("null")){

                            }else {
                                holder.countlike.setText(TotalReaction);
                            }

                        }
                    }
                });


            } else {
                Toast.makeText(context, "Video ID not Found !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "User Id not Found", Toast.LENGTH_SHORT).show();
        }


         holder.imageView7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popup.onTouch(view, motionEvent);
                return true;
            }
        });


//    }
//}


        SimpleExoPlayer exoPlayer;

        try {
            String link = model.getVidoeUrl();
            Uri videouri = Uri.parse(model.getVidoeUrl());

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            holder.videoView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);

            if (exoPlayer.isLoading()) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }


        } catch (Exception e) {
            Toast.makeText(context, "error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        SimpleExoPlayerView videoView;
        ImageView imageView, imageView7, imageView6;
        TextView countlike;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView2);
            imageView6 = itemView.findViewById(R.id.imageView9);
            imageView7 = itemView.findViewById(R.id.imageView8);
            countlike = itemView.findViewById(R.id.textView10);

        }
    }
}
