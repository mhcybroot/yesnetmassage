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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ysenetdigital.yesnetmassage.R;
import com.ysenetdigital.yesnetmassage.models.videoView;

import java.util.ArrayList;

public class videoViewAdapter extends RecyclerView.Adapter<videoViewAdapter.viewholder> {
    Context context;
    ArrayList<videoView> list = new ArrayList<>();

    public videoViewAdapter(Context context, ArrayList<videoView> list) {
        this.context = context;
        this.list = list;
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
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(new int[]{
                        R.drawable.like,
                        R.drawable.laughing,
                        R.drawable.angry,
                        R.drawable.dislike,

                })
                .build();

        @SuppressLint("SetTextI18n") ReactionPopup popup = new ReactionPopup(context, config, (positio) -> {
            int like = 0, laughing = 0, angry = 0, dislike = 0;

            switch (positio) {
                case 0:
                    like++;
                    holder.countlike.setText(" Total 👍 :- " + like + " Total 😆 :- " + laughing + " Total 😡 :- " +
                            angry + " Total 👎 :- " +
                            dislike);
                    break;
                case 1:
                    laughing++;
                    holder.countlike.setText(" Total 👍 :- " + like + " Total 😆 :- " + laughing + " Total 😡 :- " +
                            angry + " Total 👎 :- " +
                            dislike);
                    break;
                case 2:
                    angry++;
                    holder.countlike.setText(" Total 👍 :- " + like + " Total 😆 :- " + laughing + " Total 😡 :- " +
                            angry + " Total 👎 :- " +
                            dislike);
                    break;
                case 3:
                    dislike++;
                    holder.countlike.setText(" Total 👍 :- " + like + " Total 😆 :- " + laughing + " Total 😡 :- " +
                            angry + " Total 👎 :- " +
                            dislike);
                    break;


                default:
                    break;
            }
            return true; // true is closing popup, false is requesting a new selection
        });


        holder.imageView7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popup.onTouch(view, motionEvent);
                return true;
            }
        });
        SimpleExoPlayer exoPlayer;
        if (model.getVideoID() != null) {
            holder.imageView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (model.getVideoID() != null) {

                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("story").child(model.getVideoID()).removeValue();

                    }
                }
            });
        } else {
            holder.imageView6.setVisibility(View.GONE);
        }
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
