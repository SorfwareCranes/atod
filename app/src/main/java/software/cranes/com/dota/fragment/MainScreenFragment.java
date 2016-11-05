package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import software.cranes.com.dota.R;
import software.cranes.com.dota.interfa.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends BaseFragment {


    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        FragmentTabHost fragmentTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(getContext(), getChildFragmentManager(), R.id.realtabcontent);
        Bundle bundleLive = new Bundle();
        bundleLive.putString(Constant.DATA, Constant.TAB_LIVE);
        Bundle bundleRecent = new Bundle();
        bundleRecent.putString(Constant.DATA, Constant.TAB_RECENTS);
        Bundle bundleVideo = new Bundle();
        bundleVideo.putString(Constant.DATA, Constant.TAB_VIDEO);
        Bundle bundleRelax = new Bundle();
        bundleRelax.putString(Constant.DATA, Constant.TAB_RELAX);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_LIVE).setIndicator(getView(Constant.TAB_LIVE)), TabFragment.class, bundleLive);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RECENTS).setIndicator(getView(Constant.TAB_RECENTS)), TabFragment.class, bundleRecent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_VIDEO).setIndicator(getView(Constant.TAB_VIDEO)), TabFragment.class, bundleVideo);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RELAX).setIndicator(getView(Constant.TAB_RELAX)), TabFragment.class, bundleRelax);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                FragmentManager fragmentManager = getChildFragmentManager();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("MainScreen Farther", getFragmentManager().toString());
        Log.e("MainScreen Child", getChildFragmentManager().toString());
    }

    private View getView(String str) {
        TextView view = (TextView) getLayoutInflater(null).inflate(R.layout.tab_view_layout, null, false);
        view.setText(str);
//        if (str.equals(Constant.TAB_UPCOMING)) {
//            view.setVisibility(View.GONE);
//        }
        return view;
    }
}
