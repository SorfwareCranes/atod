package software.cranes.com.dota.fragment;


import com.google.firebase.database.Query;

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
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.model.ViewHolderMatchModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentMatchFragment extends BaseTabFragment {

    private RecyclerView rcvRecentMatch;
    private LinearLayoutManager mLinearLayoutManager;
    private Query query;

    public RecentMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_match, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    private void findViews(View view) {
        rcvRecentMatch = (RecyclerView) view.findViewById(R.id.rcvRecentMatch);
        rcvRecentMatch.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rcvRecentMatch.setLayoutManager(mLinearLayoutManager);
        query = mFirebaseDatabase.getReference("pro/match").orderByChild("time").limitToLast(120);
        FirebaseRecyclerAdapter<MatchModel, ViewHolderMatchModel> adapter = new FirebaseRecyclerAdapter<MatchModel, ViewHolderMatchModel>(MatchModel.class, R.layout.match_row_layout, ViewHolderMatchModel.class, query) {
            String matchId, url;
            @Override
            protected void populateViewHolder(ViewHolderMatchModel viewHolder, final MatchModel model, int position) {
                matchId = getRef(position).getKey();
                viewHolder.tvTour.setText(model.getTo() + " " + (model.getLo() != null ? model.getLo() : Constant.NO_IMAGE));
                viewHolder.tvBo.setText("BO" + model.getBo());
                viewHolder.tvRound.setText(model.getRo() != null ? model.getRo() : Constant.NO_IMAGE);
                viewHolder.tvResult.setText(String.valueOf(model.getRa()) + "-" + model.getRb());
                viewHolder.tvTime.setText(CommonUtils.convertintDateTimeToString(model.getTime()));
                viewHolder.tvNameTeamA.setText(model.getTa().getNa());
                viewHolder.tvNameTeamB.setText(model.getTb().getNa());
                if (model.getTa().getPt() != null && !model.getTa().getPt().equals(Constant.NO_IMAGE)) {
                    url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTa().getPt()).toString();
                    new ImageRequestCustom(mContext, viewHolder.imgTeamA, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(viewHolder.imgTeamA);
                }
                if (model.getTb().getPt() != null && !model.getTb().getPt().equals(Constant.NO_IMAGE)) {
                    url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTb().getPt()).toString();
                    new ImageRequestCustom(mContext, viewHolder.imgTeamB, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(viewHolder.imgTeamB);
                }
                if (model.getSum() > 0) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BaseFragment fragment = new ShowMatchWithModelFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constant.OBJECT, model);
                            bundle.putString(Constant.DATA, matchId);
                            fragment.setArguments(bundle);
                            replaceFragment(R.id.contentfragment, fragment, true);
                        }
                    });
                }
            }
        };
        rcvRecentMatch.setAdapter(adapter);
    }

}
