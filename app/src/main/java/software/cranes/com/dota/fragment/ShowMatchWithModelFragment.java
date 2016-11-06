package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GameModel;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.screen.VideoYoutubeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMatchWithModelFragment extends BaseTabFragment implements View.OnClickListener {
    private MatchModel model;
    private String matchId;
    private BaseFragment fragment;
    private Bundle bundle;
    //
    private TextView tvTour;
    private TextView tvTime;
    private ImageView imgTeamA;
    private TextView tvNameTeamA;
    private TextView tvRound;
    private TextView tvResult;
    private TextView tvBo;
    private ImageView imgTeamB;
    private TextView tvNameTeamB;
    private LinearLayout llDetailGames;
    String url;

    public ShowMatchWithModelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_detail);
        if (getArguments() != null) {
            model = getArguments().getParcelable(Constant.OBJECT);
            matchId = getArguments().getString(Constant.DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_match, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        tvTour.setOnClickListener(this);
        imgTeamA.setOnClickListener(this);
        imgTeamB.setOnClickListener(this);
    }

    private void findViews(View view) {
        tvTour = (TextView) view.findViewById(R.id.tvTour);
        tvTour.setPaintFlags(tvTour.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        imgTeamA = (ImageView) view.findViewById(R.id.imgTeamA);
        tvNameTeamA = (TextView) view.findViewById(R.id.tvNameTeamA);
        tvRound = (TextView) view.findViewById(R.id.tvRound);
        tvResult = (TextView) view.findViewById(R.id.tvResult);
        tvBo = (TextView) view.findViewById(R.id.tvBo);
        imgTeamB = (ImageView) view.findViewById(R.id.imgTeamB);
        tvNameTeamB = (TextView) view.findViewById(R.id.tvNameTeamB);
        llDetailGames = (LinearLayout) view.findViewById(R.id.llDetailGames);
        if (model != null) {
            tvTour.setText(model.getTo() + " " + (model.getLo() != null ? model.getLo() : Constant.NO_IMAGE));
            tvTime.setText(CommonUtils.convertintDateTimeToString(model.getTime()));
            tvNameTeamA.setText(model.getTa().getNa());
            tvNameTeamB.setText(model.getTb().getNa());
            tvRound.setText(model.getRo() != null ? model.getRo() : Constant.NO_IMAGE);
            tvResult.setText(String.valueOf(model.getRa()) + "-" + model.getRb());
            tvBo.setText("BO" + model.getBo());
            if (model.getTa().getPt() != null && !model.getTa().getPt().equals(Constant.NO_IMAGE)) {
                url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTa().getPt()).toString();
                new ImageRequestCustom(getContext(), imgTeamA, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(imgTeamA);
            }
            if (model.getTb().getPt() != null && !model.getTb().getPt().equals(Constant.NO_IMAGE)) {
                url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTb().getPt()).toString();
                new ImageRequestCustom(getContext(), imgTeamB, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(imgTeamB);
            }
            if (model.getSum() > 0) {
                for (int i = 1; i <= model.getSum(); i++) {
                    showCircleDialogOnly();
                    final int finalI = i;
                    mFirebaseDatabase.getReference("pro/games/" + matchId + i).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GameModel game = dataSnapshot.getValue(GameModel.class);
                            if (game != null) {
                                if (llDetailGames.getChildCount() > 0) {
                                    llDetailGames.removeAllViews();
                                }
                                llDetailGames.addView(createViewForGame(finalI, game, model));
                            }
                            hideCircleDialogOnly();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            hideCircleDialogOnly();
                            Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }

    }

    @Override
    public boolean onBackPress() {
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTour:
                bundle = new Bundle();
                bundle.putString(Constant.DATA, model.getTo());
                bundle.putString(Constant.DETAIL_DATA, model.getLo());
                fragment = new TournamentFragment();
                fragment.setArguments(bundle);
                replaceFragment(R.id.contentfragment, fragment, true);
                break;
            case R.id.imgTeamA:
                bundle = new Bundle();
                bundle.putString(Constant.DATA, model.getTa().getNa());
                fragment = new TeamFragment();
                fragment.setArguments(bundle);
                replaceFragment(R.id.contentfragment, fragment, true);
                break;
            case R.id.imgTeamB:
                bundle = new Bundle();
                bundle.putString(Constant.DATA, model.getTb().getNa());
                fragment = new TeamFragment();
                fragment.setArguments(bundle);
                replaceFragment(R.id.contentfragment, fragment, true);
                break;
        }
    }
}