package software.cranes.com.dota.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import software.cranes.com.dota.fragment.Rank_TeamFragment;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GosuGamerTeamRankModel;

import static android.R.attr.configChanges;
import static android.R.attr.type;

/**
 * Created by GiangNT - PC on 11/10/2016.
 */

public class PagerRankTeam_Adapter extends FragmentPagerAdapter {
    private int number_pager;
    private ArrayList<GosuGamerTeamRankModel> rankerList;

    public PagerRankTeam_Adapter(FragmentManager fm, ArrayList<GosuGamerTeamRankModel> rankerList, int number_pager) {
        super(fm);
        this.number_pager = number_pager;
        this.rankerList = rankerList;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.DATA, getDataFollowPosition(position, rankerList));
        Rank_TeamFragment mmr_playerFragment = new Rank_TeamFragment();
        mmr_playerFragment.setArguments(bundle);
        return mmr_playerFragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return number_pager;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "World";
            case 1:
                return "Europe";
            case 2:
                return "Americas";
            case 3:
                return "SEA";
            case 4:
                return "China";
            default:
                return "World";
        }
    }

    private ArrayList<GosuGamerTeamRankModel> getDataFollowPosition(int position, ArrayList<GosuGamerTeamRankModel> data) {
        ArrayList<GosuGamerTeamRankModel> list = new ArrayList<>();
        switch (position) {
            case 0:
            default:
                for (GosuGamerTeamRankModel model : data) {
                    if (model == null) {
                        continue;
                    }
                    if (model.getRanking() != 0) {
                        list.add(model);
                    }
                }
                return list;
            case 1:
                for (GosuGamerTeamRankModel model : data) {
                    if (model == null) {
                        continue;
                    }
                    if (model.getTypeLocal() == Constant.EUROPE_RANK) {
                        list.add(model);
                    }
                }
                return list;
            case 2:
                for (GosuGamerTeamRankModel model : data) {
                    if (model == null) {
                        continue;
                    }
                    if (model.getTypeLocal() == Constant.AMERICAS_RANK) {
                        list.add(model);
                    }
                }
                return list;
            case 3:
                for (GosuGamerTeamRankModel model : data) {
                    if (model == null) {
                        continue;
                    }
                    if (model.getTypeLocal() == Constant.SEA_RANK) {
                        list.add(model);
                    }
                }
                return list;
            case 4:
                for (GosuGamerTeamRankModel model : data) {
                    if (model == null) {
                        continue;
                    }
                    if (model.getTypeLocal() == Constant.CHINA_RANK) {
                        list.add(model);
                    }
                }
                return list;
        }
    }
}
