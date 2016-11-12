package software.cranes.com.dota.dialog;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.cranes.com.dota.R;
import software.cranes.com.dota.adapter.AutoCompleteAdapter;
import software.cranes.com.dota.interfa.Constant;

import static android.R.attr.handle;
import static android.R.id.message;
import static software.cranes.com.dota.R.id.btnConfirmCancel;
import static software.cranes.com.dota.R.id.btnConfirmOK;
import static software.cranes.com.dota.R.id.tvConfirm;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterDialog extends BaseDialogFragment implements View.OnClickListener {
    private AutoCompleteTextView actPlayer;
    private AutoCompleteTextView actHeroes;
    private RadioButton rbHighLight;
    private RadioButton rbFull;
    private TextView tvCancel;
    private TextView tvOK;
    private ArrayList<String> listPlayer, listHeroes;
    private Map<String, String> resultMap;
    private String player, hero;
    private HandleFilter mHandleFilter;

    public interface HandleFilter {
        void handle(Map<String, String> result);
    }

    public FilterDialog() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FilterDialog(Map<String, String> resultMap, HandleFilter mHandleFilter) {
        this.resultMap = resultMap;
        this.mHandleFilter = mHandleFilter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_filter_dialog, null, false);
        findViews(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void findViews(View view) {
        actPlayer = (AutoCompleteTextView) view.findViewById(R.id.actPlayer);
        actHeroes = (AutoCompleteTextView) view.findViewById(R.id.actHeroes);
        loadDataAutoCompleteText(actPlayer, "pro/suggest/pl", 1);
        loadDataAutoCompleteText(actHeroes, "heroes_suggest", 2);

        rbHighLight = (RadioButton) view.findViewById(R.id.rbHighLight);
        rbFull = (RadioButton) view.findViewById(R.id.rbFull);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvOK = (TextView) view.findViewById(R.id.tvOK);

        tvCancel.setOnClickListener(this);
        tvOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                this.dismiss();
                break;
            case R.id.tvOK:
                handleOK();
                break;

        }
    }

    private void loadDataAutoCompleteText(final AutoCompleteTextView act, String path, final int type) {
        showCircleDialogOnly();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AutoCompleteAdapter adapter;
                if (type == 1) {
                    listPlayer = (ArrayList<String>) dataSnapshot.getValue();
                    adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listPlayer);
                } else {
                    listHeroes = (ArrayList<String>) dataSnapshot.getValue();
                    adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1, listHeroes);
                }
                act.setAdapter(adapter);
                hideCircleDialogOnly();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialogOnly();
            }
        });
    }

    private void handleOK() {
        if (rbFull.isChecked()) {
            resultMap.put(Constant.VIDEO, "2");
        } else if (rbHighLight.isChecked()) {
            resultMap.put(Constant.VIDEO, "1");
        }
        player = actPlayer.getText().toString().trim();
        hero = actHeroes.getText().toString().trim();
        resultMap.put(Constant.PLAYER, getStringInlist(player, listPlayer));
        resultMap.put(Constant.HEROES, getStringInlist(hero, listHeroes));
        this.dismiss();
        if (mHandleFilter != null) {
            mHandleFilter.handle(resultMap);
        }
    }

    private String getStringInlist(String str, List<String> list) {
        if (list == null || list.size() == 0) {
            return Constant.NO_IMAGE;
        }
        for (String s : list) {
            if (str.equalsIgnoreCase(s)) {
                return s;
            }
        }
        return Constant.NO_IMAGE;
    }
}
