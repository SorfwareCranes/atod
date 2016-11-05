package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.model.MatchTeamModel;
import software.cranes.com.dota.model.TourModel;
import software.cranes.com.dota.model.ViewHolderMatchTeamModel;
import software.cranes.com.dota.model.ViewHolderTourModel;

import static software.cranes.com.dota.R.id.rcvTour;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends BaseTabFragment {

    private RecyclerView rcvTeam;
    private String teamName;
    private LinearLayoutManager mLinearLayoutManager;
    private Query query;
    private int sizeImage;
    private Bitmap bitmapRes;
    private String url;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teamName = getArguments().getString(Constant.DATA);
        }
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        rcvTeam = (RecyclerView) view.findViewById(R.id.rcvTeam);
        rcvTeam.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rcvTeam.setLayoutManager(mLinearLayoutManager);
        if (teamName != null) {
            tvTitle.setText(teamName + "'s Match");
            query = mFirebaseDatabase.getReference("pro/team/" + CommonUtils.escapeKey(teamName)).orderByChild("time").limitToLast(200);
            FirebaseRecyclerAdapter<MatchTeamModel, ViewHolderMatchTeamModel> adapter = new FirebaseRecyclerAdapter<MatchTeamModel, ViewHolderMatchTeamModel>(MatchTeamModel.class, R.layout.team_row_layout, ViewHolderMatchTeamModel.class, query) {
                @Override
                protected void populateViewHolder(ViewHolderMatchTeamModel viewHolder, final MatchTeamModel model, int position) {
                    final String matchId = getRef(position).getKey();
                    viewHolder.tvResult.setText(String.valueOf(model.getRa()) + "-" + model.getRb());
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
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.DATA, matchId);
                            BaseFragment fragment = new ShowMatchWithIdFragment();
                            fragment.setArguments(bundle);
                            replaceFragment(R.id.contentfragment, fragment, true);
                        }
                    });
                }
            };
            rcvTeam.setAdapter(adapter);
        }
    }

}
