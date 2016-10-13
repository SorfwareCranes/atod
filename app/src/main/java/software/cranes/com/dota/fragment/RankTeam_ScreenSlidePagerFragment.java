package software.cranes.com.dota.fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.PagerRankTeam_Adapter;
import software.cranes.com.dota.custom.DepthPageTransformer;
import software.cranes.com.dota.custom.SlidingTabLayout;
import software.cranes.com.dota.model.GosuGamerTeamRankModel;

import static java.security.AccessController.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankTeam_ScreenSlidePagerFragment extends BaseFragment {
    private ViewPager pagerMMR;
    private SlidingTabLayout tabs;

    public RankTeam_ScreenSlidePagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_team__screen_slide_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCircleDialogOnly();
        pagerMMR = (ViewPager) view.findViewById(R.id.pagerMMR);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        pagerMMR.setPageTransformer(true, new DepthPageTransformer());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("gosu/team");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<GosuGamerTeamRankModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<GosuGamerTeamRankModel>>() {};
                ArrayList<GosuGamerTeamRankModel> rankerList = dataSnapshot.getValue(genericTypeIndicator);
                PagerRankTeam_Adapter adapter = new PagerRankTeam_Adapter(getChildFragmentManager(), rankerList, 5);
                pagerMMR.setAdapter(adapter);
                tabs.setDistributeEvenly(true);
                tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                    @Override
                    public int getIndicatorColor(int position) {
                        return ContextCompat.getColor(getContext(), R.color.holo_blue_light);
                    }
                });
                tabs.setViewPager(pagerMMR);
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
