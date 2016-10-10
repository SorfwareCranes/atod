package software.cranes.com.dota.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import software.cranes.com.dota.R;


/**
 * Created by GiangNT - PC on 14/07/2016.
 */

public class CircleDialog extends DialogFragment {
    private static CircleDialog mInstance;
    public static int z = 0;

    public CircleDialog() {

    }

    public static synchronized CircleDialog getInstance() {
        if (mInstance == null) {
            mInstance = new CircleDialog();
        }
        return mInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.MyTheme);
        mBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.circle_layout, null, false));
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

}
