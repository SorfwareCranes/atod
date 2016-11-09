package software.cranes.com.dota.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import software.cranes.com.dota.R;

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

    public FilterDialog() {
        // Required empty public constructor
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
        rbHighLight = (RadioButton) view.findViewById(R.id.rbHighLight);
        rbFull = (RadioButton) view.findViewById(R.id.rbFull);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvOK = (TextView) view.findViewById(R.id.tvOK);

        rbHighLight.setOnClickListener(this);
        rbFull.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
