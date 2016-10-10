package software.cranes.com.dota.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import software.cranes.com.dota.R;


/**
 * Created by GiangNT - PC on 20/06/2016.
 */
@SuppressLint("ValidFragment")
public class WarningDialog extends DialogFragment implements View.OnClickListener {
    private String text;
    private TextView tvWarning;
    private TextView btnWarning;


    private static WarningDialog mInstance;

    public WarningDialog() {

    }

    public static synchronized WarningDialog getInstance(String message) {
        if (mInstance == null) {
            mInstance = new WarningDialog(message);
        }
        return mInstance;
    }

    private WarningDialog(String text) {
        this.text = text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.warning_dialog_layout, null, false);
        tvWarning = (TextView) view.findViewById(R.id.tvWarning);
        btnWarning = (TextView) view.findViewById(R.id.btnWarning);
        tvWarning.setText(text);
        btnWarning.setOnClickListener(this);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
