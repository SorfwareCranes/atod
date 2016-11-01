package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        fragmentTabHost.setup(getContext(), getFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_LIVE).setIndicator(getView(Constant.TAB_LIVE)), LiveMatchFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RECENTS).setIndicator(getView(Constant.TAB_RECENTS)), RecentMatchFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_VIDEO).setIndicator(getView(Constant.TAB_VIDEO)), SyntheticVideoFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec(Constant.TAB_RELAX).setIndicator(getView(Constant.TAB_RELAX)), RelaxVideoFragment.class, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private View getView(String str) {
        TextView view = (TextView) getLayoutInflater(null).inflate(R.layout.tab_view_layout, null, false);
        view.setText(str);
        return view;
    }
}
