package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import software.cranes.com.dota.R;
import software.cranes.com.dota.interfa.Constant;

import static com.google.android.gms.internal.zzaqn.bra;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends BaseFragment {
    private BaseFragment fragment;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        if (getArguments() != null && getArguments().getString(Constant.DATA) != null) {
            switch (getArguments().getString(Constant.DATA)) {
                case Constant.TAB_LIVE:
                    fragment = new LiveMatchFragment();
                    break;
                case Constant.TAB_RECENTS:
                    fragment = new RecentMatchFragment();
                    break;
                case Constant.TAB_VIDEO:
                    fragment = new SyntheticVideoFragment();
                    break;
                case Constant.TAB_RELAX:
                    fragment = new RelaxVideoFragment();
                    break;
            }
            replaceFragment(R.id.contentfragment, fragment, false);
        }
        return view;
    }
}
