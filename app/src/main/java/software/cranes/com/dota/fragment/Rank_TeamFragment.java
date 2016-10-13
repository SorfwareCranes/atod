package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.Team_Adapter;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GosuGamerTeamRankModel;

import static android.R.attr.type;
import static android.R.id.list;

public class Rank_TeamFragment extends BaseFragment {

    private ArrayList<GosuGamerTeamRankModel> listData;
    private RecyclerView recyclerView;

    public Rank_TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listData = getArguments().getParcelableArrayList(Constant.DATA);
            if (listData != null) {
                Collections.sort(listData, new GosuGamerTeamRankModelComparator());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_team, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // Set up Layout Manager, reverse layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        Team_Adapter adapter = new Team_Adapter(getContext(), listData);
        recyclerView.setAdapter(adapter);
    }

    private class GosuGamerTeamRankModelComparator implements Comparator<GosuGamerTeamRankModel> {
        @Override
        public int compare(GosuGamerTeamRankModel o1, GosuGamerTeamRankModel o2) {
            return o2.getRanking() - o1.getRanking();
        }
    }
}
