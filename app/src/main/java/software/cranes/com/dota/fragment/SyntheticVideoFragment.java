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
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.HashMap;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.common.SendRequest;

import software.cranes.com.dota.dialog.FilterDialog;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.VideoModel;
import software.cranes.com.dota.model.ViewHolderVideoModel;
import software.cranes.com.dota.screen.VideoYoutubeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SyntheticVideoFragment extends BaseVideoFragment implements View.OnClickListener, FilterDialog.HandleFilter {
    protected LinearLayoutManager mLinearLayoutManager;
    protected Query query;
    protected String videoId;
    protected String time;
    protected String url;

    private String path;
    private RecyclerView rcvSynthetic;
    private FirebaseRecyclerAdapter<VideoModel, ViewHolderVideoModel> adatper;
    private Button btnFull;
    private Button btnPlayer;
    private Button btnHeroes;
    private ImageButton btnFilter;
    private FilterDialog dialog;
    private Map<String, String> filterMap;
    private String type = "1";
    private String player = Constant.PLAYER;
    private String hero = Constant.HEROES;

    public SyntheticVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterMap = new HashMap<>();
        filterMap.put(Constant.VIDEO, "1");
        filterMap.put(Constant.PLAYER, Constant.NO_IMAGE);
        filterMap.put(Constant.HEROES, Constant.NO_IMAGE);
        path = "vih";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_synthetic_video, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rcvSynthetic.setLayoutManager(mLinearLayoutManager);
        query = mFirebaseDatabase.getReference(path).orderByChild("time").limitToLast(150);
        setupRecyclerView(query, rcvSynthetic);

    }

    private void setupRecyclerView(Query qwert, RecyclerView rcv) {
        adatper = new FirebaseRecyclerAdapter<VideoModel, ViewHolderVideoModel>(VideoModel.class, R.layout.video_row_layout, ViewHolderVideoModel.class, qwert) {
            @Override
            protected void populateViewHolder(final ViewHolderVideoModel viewHolder, VideoModel model, int position) {
                videoId = model.getLv();
                time = CommonUtils.convertIntDateToString(model.getTime());
                // set time
                if (time != null) {
                    viewHolder.tvVideoTime.setText(time);
                }
                // set title for video
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
        rcv.setAdapter(adatper);
    }

    private void findViews(View view) {
        rcvSynthetic = (RecyclerView) view.findViewById(R.id.rcvSynthetic);
        btnFull = (Button) view.findViewById(R.id.btnFull);
        btnPlayer = (Button) view.findViewById(R.id.btnPlayer);
        btnHeroes = (Button) view.findViewById(R.id.btnHeroes);
        btnFilter = (ImageButton) view.findViewById(R.id.btnFilter);
        btnFull.setOnClickListener(this);
        btnPlayer.setOnClickListener(this);
        btnHeroes.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        if (filterMap.get(Constant.VIDEO).equals("1")) {
            btnFull.setText("Full");
        } else {
            btnFull.setText("HighLight");
        }
        if (filterMap.get(Constant.PLAYER).equals(Constant.NO_IMAGE)) {
            btnPlayer.setVisibility(View.INVISIBLE);
        }
        if (filterMap.get(Constant.HEROES).equals(Constant.NO_IMAGE)) {
            btnHeroes.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnFull:
                if (type.equals("1")) {
                    type = "2";
                } else {
                    type = "1";
                }
                filterMap.put(Constant.VIDEO, type);
                break;
            case R.id.btnPlayer:
                if (!player.equals(Constant.NO_IMAGE)) {
                    player = Constant.NO_IMAGE;
                    filterMap.put(Constant.PLAYER, player);
                }
                break;
            case R.id.btnHeroes:
                if (!hero.equals(Constant.NO_IMAGE)) {
                    hero = Constant.NO_IMAGE;
                    filterMap.put(Constant.HEROES, hero);
                }
                break;
            case R.id.btnFilter:
                dialog = new FilterDialog(filterMap, this);
                dialog.show(getFragmentManager(), null);
                break;
        }
        if (id != R.id.btnFilter) {
            path = createPathForllowFilter(filterMap);
            query = mFirebaseDatabase.getReference(path).orderByChild("time").limitToLast(150);
            rcvSynthetic.removeAllViews();
            setupRecyclerView(query, rcvSynthetic);
        }
    }

    @Override
    public void handle(Map<String, String> result) {
        // set up path
        path = createPathForllowFilter(result);
        query = mFirebaseDatabase.getReference(path).orderByChild("time").limitToLast(150);
        rcvSynthetic.removeAllViews();
        setupRecyclerView(query, rcvSynthetic);
    }

    private String createPathForllowFilter(Map<String, String> filterMap) {
        type = filterMap.get(Constant.VIDEO);
        player = filterMap.get(Constant.PLAYER);
        hero = filterMap.get(Constant.HEROES);
        if (type.equals("1")) {
            btnFull.setText("Full");
        } else {
            btnFull.setText("HighLight");
        }
        btnPlayer.setText(player);
        btnHeroes.setText(hero);
        if (player.equals(Constant.NO_IMAGE) && hero.equals(Constant.NO_IMAGE)) {
            btnPlayer.setVisibility(View.INVISIBLE);
            btnHeroes.setVisibility(View.INVISIBLE);
            if (type.equals("1")) {
                return path = "vih";
            } else {
                return path = "vif";
            }
        } else if ((!player.equals(Constant.NO_IMAGE)) && (!hero.equals(Constant.NO_IMAGE))) {
            btnPlayer.setVisibility(View.VISIBLE);
            btnHeroes.setVisibility(View.VISIBLE);
            if (type.equals("1")) {
                return path = "viphh/" + CommonUtils.escapeKey(player) + "/" + hero;
            } else {
                return path = "viphf/" + CommonUtils.escapeKey(player) + "/" + hero;
            }
        } else if (!player.equals(Constant.NO_IMAGE)) {
            btnPlayer.setVisibility(View.VISIBLE);
            btnHeroes.setVisibility(View.INVISIBLE);
            if (type.equals("1")) {
                return path = "viph/" + CommonUtils.escapeKey(player);
            } else {
                return path = "vipf/" + CommonUtils.escapeKey(player);
            }
        } else {
            btnPlayer.setVisibility(View.INVISIBLE);
            btnHeroes.setVisibility(View.VISIBLE);
            if (type.equals("1")) {
                return path = "vihh/" + hero;
            } else {
                return path = "vihf/" + hero;
            }
        }
    }
}