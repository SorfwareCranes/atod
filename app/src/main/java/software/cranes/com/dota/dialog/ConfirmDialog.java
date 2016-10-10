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
 * Created by GiangNT - PC on 23/06/2016.
 */
@SuppressLint("ValidFragment")
public class ConfirmDialog extends DialogFragment implements View.OnClickListener {
    private TextView btnConfirmOK, btnConfirmCancel, tvConfirm;
    private String message;
    private HandleConfirmDialog mHandleConfirmDialog;
    private static ConfirmDialog mInstance;

    public interface HandleConfirmDialog {
        void onHanleConfirmDialog();
    }

    public ConfirmDialog() {
    }

    public ConfirmDialog(String message, HandleConfirmDialog mHandleConfirmDialog) {
        this.message = message;
        this.mHandleConfirmDialog = mHandleConfirmDialog;
    }

    public static synchronized ConfirmDialog getInstance(String message, HandleConfirmDialog mHandleConfirmDialog) {
        if (mInstance == null) {
            mInstance = new ConfirmDialog(message, mHandleConfirmDialog);
        }
        return mInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_dialog_layout, null, false);
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        tvConfirm.setText(message);
        btnConfirmOK = (TextView) view.findViewById(R.id.btnConfirmOK);
        btnConfirmCancel = (TextView) view.findViewById(R.id.btnConfirmCancel);
        btnConfirmOK.setOnClickListener(this);
        btnConfirmCancel.setOnClickListener(this);
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
        int id = v.getId();
        switch (id) {
            case R.id.btnConfirmCancel:
                this.dismiss();
                break;
            case R.id.btnConfirmOK:
                mHandleConfirmDialog.onHanleConfirmDialog();
                break;
        }
    }
}
