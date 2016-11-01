package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import software.cranes.com.dota.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelaxVideoFragment extends BaseFragment {


    public RelaxVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relax_video, container, false);
    }

}
