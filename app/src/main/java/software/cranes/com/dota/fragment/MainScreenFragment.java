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
        final FragmentTabHost fragmentTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(getContext(), getFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_UPCOMING).setIndicator(getView(Constant.TAB_UPCOMING)), JoinDotaFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_LIVE).setIndicator(getView(Constant.TAB_LIVE)), OneFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RECENTS).setIndicator(getView(Constant.TAB_RECENTS)), TwoFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_VIDEO).setIndicator(getView(Constant.TAB_VIDEO)), ThreeFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RELAX).setIndicator(getView(Constant.TAB_RELAX)), FourFragment.class, null);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                FragmentManager fragmentManager = getFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count >0) {
                    Log.e("test", tabId  + ":" + count );
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private View getView(String str) {
        TextView view = (TextView) getLayoutInflater(null).inflate(R.layout.tab_view_layout, null, false);
        view.setText(str);
        if (str.equals(Constant.TAB_UPCOMING)) {
            view.setVisibility(View.GONE);
        }
        return view;
    }
}
