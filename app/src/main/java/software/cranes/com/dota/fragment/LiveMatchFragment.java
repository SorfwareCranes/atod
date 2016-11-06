package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.MatchModel;

import static java.security.AccessController.getContext;
import static software.cranes.com.dota.R.id.tvLive1;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveMatchFragment extends BaseTabFragment {

    private LinearLayout llLive, llUpcoming;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference ref;
    private TextView tvLiveStatus, tvUpcomingStatus;
    String url;
    StringBuilder builder;
    private int sizeImage;

    public LiveMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        liveView = inflater.inflate(R.layout.live_match_layout, null, false);
        return inflater.inflate(R.layout.fragment_live_match, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        llLive = (LinearLayout) view.findViewById(R.id.llLive);
        llUpcoming = (LinearLayout) view.findViewById(R.id.llUpcoming);
        tvLiveStatus = (TextView) view.findViewById(R.id.tvLiveStatus);
        tvUpcomingStatus = (TextView) view.findViewById(R.id.tvUpcomingStatus);
        if (CommonUtils.isNetworkConnect(getContext())) {
            setupView(Constant.LIVE, "pro/live", llLive, true);
            setupView(Constant.UPCOMING, "pro/upcoming", llUpcoming, false);
        }
    }

    private void setupView(final int type, String path, final LinearLayout ll, final boolean showLoading) {
        if (showLoading) {
            showCircleDialogOnly();
        }
        ref = mFirebaseDatabase.getReference(path);
        ref.orderByChild("time").limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (ll.getChildCount() > 0) {
                    ll.removeAllViews();
                }
                GenericTypeIndicator<Map<String, MatchModel>> genericTypeIndicator = new GenericTypeIndicator<Map<String, MatchModel>>() {
                };
                Map<String, MatchModel> map = dataSnapshot.getValue(genericTypeIndicator);
                if (map != null && map.size() > 0) {
                    if (type == Constant.LIVE) {
                        tvLiveStatus.setText("LIVE GAMES");
                    } else {
                        tvUpcomingStatus.setText("UPCOMING GAMES");
                    }
                    for (String id : map.keySet()) {
                        View view = createMatchView(type, id, map.get(id));
                        if (view != null) {
                            ll.addView(view);
                        }
                    }
                } else {
                    if (type == Constant.LIVE) {
                        tvLiveStatus.setText("NO GAMES LIVE");
                    } else {
                        tvUpcomingStatus.setText("NO GAMES UPCOMING");
                    }
                }
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }

    private View createMatchView(int type, final String idMatch, final MatchModel model) {
        if (activity == null) {
            return null;
        }
        View resultView = activity.getLayoutInflater().inflate(R.layout.live_match_layout, null, false);
        TextView tvTour = (TextView) resultView.findViewById(R.id.tvTour);
        TextView tvTime = (TextView) resultView.findViewById(R.id.tvTime);
        TextView tvNameTeamA = (TextView) resultView.findViewById(R.id.tvNameTeamA);
        TextView tvNameTeamB = (TextView) resultView.findViewById(R.id.tvNameTeamB);
        ImageView imgTeamA = (ImageView) resultView.findViewById(R.id.imgTeamA);
        TextView tvResult = (TextView) resultView.findViewById(R.id.tvResult);
        TextView tvBo = (TextView) resultView.findViewById(R.id.tvBo);
        ImageView imgTeamB = (ImageView) resultView.findViewById(R.id.imgTeamB);
        TextView tvLive2 = (TextView) resultView.findViewById(R.id.tvLive2);
        builder = new StringBuilder(model.getTo());
        if (type == Constant.LIVE) {
            if (model.getLo() != null) {
                builder.append(" " + model.getLo());
            }
            if (model.getRo() != null) {
                builder.append(" " + model.getRo());
            }
            tvTour.setText(builder.toString());

            tvTime.setVisibility(View.GONE);
        } else {
            tvTour.setText(builder.toString());
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(CommonUtils.convertintDateTimeToString(model.getTime()));
        }
        tvNameTeamA.setText(model.getTa().getNa());
        tvNameTeamB.setText(model.getTb().getNa());
        tvResult.setText(String.valueOf(model.getRa()) + "-" + model.getRb());
        tvBo.setText("BO" + model.getBo());
        if (model.getTa().getPt() != null && !model.getTa().getPt().equals(Constant.NO_IMAGE)) {
            url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTa().getPt()).toString();
            new ImageRequestCustom(mContext, imgTeamA, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(imgTeamA);
        }
        if (model.getTb().getPt() != null && !model.getTb().getPt().equals(Constant.NO_IMAGE)) {
            url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTb().getPt()).toString();
            new ImageRequestCustom(mContext, imgTeamB, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(imgTeamB);
        }
        if (model.getLl() != null && model.getLl().size() > 0) {
            ((TextView) resultView.findViewById(tvLive1)).setText(model.getLl().get(0).getLa());
            if (model.getLl().size() > 1) {
                tvLive2.setVisibility(View.VISIBLE);
                tvLive2.setText(model.getLl().get(1).getLa());
            } else {
                tvLive2.setVisibility(View.GONE);
            }
        } else {
            (resultView.findViewById(R.id.tvLive1)).setVisibility(View.GONE);
            tvLive2.setVisibility(View.GONE);
        }
        if (model.getBa() != 0 && model.getBb() != 0) {
            ((TextView) resultView.findViewById(R.id.tvBetA)).setText(String.valueOf(model.getBa()));
            ((TextView) resultView.findViewById(R.id.tvBetB)).setText(String.valueOf(model.getBb()));
        } else {
            resultView.findViewById(R.id.llBetTeam).setVisibility(View.GONE);
        }
        if (type == Constant.LIVE) {
            resultView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle argument = new Bundle();
                    argument.putParcelable(Constant.OBJECT, model);
                    argument.putString(Constant.DATA, idMatch);
                    BaseFragment fragment = new ShowMatchWithModelFragment();
                    fragment.setArguments(argument);
                    replaceFragment(R.id.contentfragment, fragment, true);
                }
            });
        }
        return resultView;

    }
}