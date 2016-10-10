package software.cranes.com.dota.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import software.cranes.com.dota.fragment.MMR_PlayerFragment;
import software.cranes.com.dota.interfa.Constant;

/**
 * Created by GiangNT - PC on 10/10/2016.
 */

public class PagerMMR_Adapter extends FragmentStatePagerAdapter {
    private int number_pager;

    public PagerMMR_Adapter(FragmentManager fm, int number_pager) {
        super(fm);
        this.number_pager = number_pager;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.DATA, position);
        MMR_PlayerFragment mmr_playerFragment = new MMR_PlayerFragment();
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
            case 0 :
                return "Europe";
            case 1:
                return "Americas";
            case 2:
                return "SE Asia";
            case 3:
                return "China";
            default:
                return "Europe";
        }
    }
}
