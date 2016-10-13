package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.MMR_Adapter;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.RankerMMRmodel;

import static android.media.CamcorderProfile.get;

/**
 * A simple {@link Fragment} subclass.
 */
public class MMR_PlayerFragment extends BaseFragment {
    private ListView listView;
    private int type;
    private TextView tvTimeUpdate;

    public MMR_PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(Constant.DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mmr_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.lv);
        tvTimeUpdate = (TextView) view.findViewById(R.id.tvTimeUpdate);
        String local;
        switch (type) {
            case 0:
                local = "europe";
                break;
            case 1:
                local = "americas";
                break;
            case 2:
                local = "se_asia";
                break;
            case 3:
                local = "china";
                break;
            default:
                local = "europe";
                break;
        }
        String url = "http://www.dota2.com/webapi/ILeaderboard/GetDivisionLeaderboard/v0001?division=" + local;
        showCircleDialogOnly();
        SendRequest.requestGet(getContext(), url, Constant.TYPE_RESPONSE_OBJECT, RankerMMRmodel.class, new SendRequest.HandleResponse() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof RankerMMRmodel) {
                    RankerMMRmodel model = (RankerMMRmodel) data;
                    tvTimeUpdate.setText(convertTime(model.getTime_posted()));
                    if (model.getLeaderboard() != null) {
                        MMR_Adapter adapter = new MMR_Adapter(model.getLeaderboard());
                        listView.setAdapter(adapter);
                    }
                    hideCircleDialogOnly();
                }
            }

            @Override
            public void onFail(String err) {
                hideCircleDialogOnly();
                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return "Last Updated :" + (calendar.get(Calendar.DAY_OF_MONTH) + 1) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }
}
