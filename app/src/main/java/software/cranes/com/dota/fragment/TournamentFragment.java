package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.Team_Adapter;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.model.TourModel;
import software.cranes.com.dota.model.ViewHolderTourModel;

import static software.cranes.com.dota.R.id.rcvRecentMatch;
import static software.cranes.com.dota.R.id.tvTitle;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class TournamentFragment extends BaseTabFragment {

    private RecyclerView rcvTour;
    private String tour;
    private String location;
    private LinearLayoutManager mLinearLayoutManager;
    private Query query;
    private String url;

    public TournamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tour = getArguments().getString(Constant.DATA);
            location = getArguments().getString(Constant.DETAIL_DATA);
        }
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tournament, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        rcvTour = (RecyclerView) view.findViewById(R.id.rcvTour);
        rcvTour.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rcvTour.setLayoutManager(mLinearLayoutManager);
        if (tour != null && !tour.equals(Constant.NO_IMAGE)) {
            tvTitle.setText(tour + " " + location);
            query = mFirebaseDatabase.getReference("pro/tour/" + CommonUtils.escapeKey(tour) + "/" + CommonUtils.escapeKey(location)).orderByChild("time");
            FirebaseRecyclerAdapter<TourModel, ViewHolderTourModel> adapter = new FirebaseRecyclerAdapter<TourModel, ViewHolderTourModel>(TourModel.class, R.layout.tour_row_layout, ViewHolderTourModel.class, query) {
                @Override
                protected void populateViewHolder(ViewHolderTourModel viewHolder, final TourModel model, int position) {
                    final String matchId = getRef(position).getKey();
                    viewHolder.tvResult.setText(String.valueOf(model.getRa()) + "-" + model.getRb());
                    viewHolder.tvNameTeamA.setText(model.getTa().getNa());
                    viewHolder.tvNameTeamB.setText(model.getTb().getNa());
                    viewHolder.tvRound.setText(model.getRo());
                    switch (model.getSt()) {
                        case Constant.LIVE:
                            viewHolder.tvStatus.setText("LIVE");
                            break;
                        case Constant.UPCOMING:
                            viewHolder.tvStatus.setText("UPCOMING");
                            break;
                        case Constant.END:
                            viewHolder.tvStatus.setText("END");
                            break;
                    }
                    if (model.getTa().getPt() != null && !model.getTa().getPt().equals(Constant.NO_IMAGE)) {
                        url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTa().getPt()).toString();
                        new ImageRequestCustom(mContext, viewHolder.imgTeamA, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(viewHolder.imgTeamA);
                    }
                    if (model.getTb().getPt() != null && !model.getTb().getPt().equals(Constant.NO_IMAGE)) {
                        url = new StringBuilder("https://cdn0.gamesports.net/edb_team_logos/").append(model.getTb().getPt()).toString();
                        new ImageRequestCustom(mContext, viewHolder.imgTeamB, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(viewHolder.imgTeamB);
                    }
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (model.getSt() != Constant.END) {
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.DATA, matchId);
                            BaseFragment fragment = new ShowMatchWithIdFragment();
                            fragment.setArguments(bundle);
                            replaceFragment(R.id.contentfragment, fragment, true);
                        }
                    });
                }
            };
            rcvTour.setAdapter(adapter);
        }
    }

}
