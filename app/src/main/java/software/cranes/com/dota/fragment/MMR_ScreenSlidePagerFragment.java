package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.PagerMMR_Adapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MMR_ScreenSlidePagerFragment extends BaseFragment {
    private ViewPager pagerMMR;

    public MMR_ScreenSlidePagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mmr_screen_slide_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerMMR = (ViewPager) view.findViewById(R.id.pagerMMR);
        PagerMMR_Adapter adapter = new PagerMMR_Adapter(getChildFragmentManager(), 4);
        pagerMMR.setAdapter(adapter);
    }
}
