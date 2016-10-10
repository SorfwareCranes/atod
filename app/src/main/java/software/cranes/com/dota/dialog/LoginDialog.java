package software.cranes.com.dota.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import software.cranes.com.dota.R;

import static android.R.id.message;

/**
 * Created by GiangNT - PC on 19/09/2016.
 */

public class LoginDialog extends DialogFragment {
    private static LoginDialog mInstance;

    public LoginDialog() {

    }

    public synchronized static LoginDialog getInstance() {
        if (mInstance == null) {
            mInstance = new LoginDialog();
        }
        return mInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_dialog_layout, null, false);
        setupView(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void setupView(View view) {

    }
}
