package software.cranes.com.dota.fragment;


import com.google.firebase.database.Query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.RelaxModel;
import software.cranes.com.dota.model.VideoModel;
import software.cranes.com.dota.model.ViewHolderRelaxModel;
import software.cranes.com.dota.model.ViewHolderVideoModel;
import software.cranes.com.dota.screen.VideoYoutubeActivity;

import static software.cranes.com.dota.R.id.rcvSynthetic;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelaxVideoFragment extends BaseVideoFragment {
    protected LinearLayoutManager mLinearLayoutManager;
    protected Query query;
    protected String videoId;
    protected String time;
    protected String url;

    private RecyclerView rcvRelax;

    public RelaxVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relax_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvRelax = (RecyclerView) view.findViewById(R.id.rcvRelax);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rcvRelax.setLayoutManager(mLinearLayoutManager);
        query = mFirebaseDatabase.getReference("relax").orderByChild("time").limitToLast(200);
        FirebaseRecyclerAdapter<RelaxModel, ViewHolderRelaxModel> adatper = new FirebaseRecyclerAdapter<RelaxModel, ViewHolderRelaxModel>(RelaxModel.class, R.layout.video_row_layout, ViewHolderRelaxModel.class, query) {
            @Override
            protected void populateViewHolder(final ViewHolderRelaxModel viewHolder, RelaxModel model, int position) {
                videoId = getRef(position).getKey();
                time = CommonUtils.convertintDateTimeToString(model.getTime());
                // set time
                if (time != null) {
                    viewHolder.tvVideoTime.setText(time);
                }
                // set title for video
                if (model.getTitle() != null && !model.getTitle().equals(Constant.NO_IMAGE)) {
                    viewHolder.tvVideoTitle.setText(model.getTitle());
                } else {
                    SendRequest.requestYoutubeTitle(mContext, videoId, new SendRequest.HandleYoutubeVideoId() {
                        @Override
                        public void onSuccess(String title, String url) {
                            viewHolder.tvVideoTitle.setVisibility(View.VISIBLE);
                            viewHolder.tvVideoTitle.setText(title);
                        }

                        @Override
                        public void onFail(String err) {
                            viewHolder.tvVideoTitle.setVisibility(View.GONE);
                        }
                    });
                }
                // load image
                url = new StringBuilder("https://i.ytimg.com/vi/").append(videoId).append("/hqdefault.jpg").toString();
                new ImageRequestCustom(mContext, viewHolder.imgVideo, url, sizeImage, (int) sizeImage / 16 * 9, R.drawable.no_image, bitmapRes).execute(viewHolder.imgVideo);
//                if (!loaderMap.containsKey(videoId)) {
//                    viewHolder.youtubeThumbnailView.setTag(videoId);
//                    viewHolder.youtubeThumbnailView.initialize(getString(R.string.api_key_android), new YouTubeThumbnailView.OnInitializedListener() {
//                        @Override
//                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                            loaderMap.put(videoId, youTubeThumbnailLoader);
//                            viewHolder.youtubeThumbnailView.setImageResource(R.drawable.no_image);
//                            youTubeThumbnailLoader.setVideo(videoId);
//                        }
//
//                        @Override
//                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//                        }
//                    });
//                } else {
//                    YouTubeThumbnailLoader loader = loaderMap.get(videoId);
//                    if (loader == null) {
//
//                    } else {
//                        viewHolder.youtubeThumbnailView.setImageResource(R.drawable.no_image);
//                        loader.setVideo(videoId);
//                    }
//                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), VideoYoutubeActivity.class);
                        intent.putExtra(Constant.DATA, videoId);
                        startActivity(intent);
                    }
                });
            }
        };
        rcvRelax.setAdapter(adatper);
    }

}
