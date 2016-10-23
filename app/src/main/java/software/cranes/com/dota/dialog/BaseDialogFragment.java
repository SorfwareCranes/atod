package software.cranes.com.dota.dialog;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import software.cranes.com.dota.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDialogFragment extends DialogFragment {
    private CircleDialog circleDialog;

    public BaseDialogFragment() {
        // Required empty public constructor
    }

    // show CircleDialog
    protected void showCircleDialog() {
        circleDialog = CircleDialog.getInstance();
        if (circleDialog.getDialog() == null || (circleDialog.getDialog() != null && !circleDialog.getDialog().isShowing())) {
            circleDialog.show(getFragmentManager(), "base dialog");
            CircleDialog.z = 1;
        }
    }
    protected void hideCircleDialog() {
        circleDialog = CircleDialog.getInstance();
        if (circleDialog.getDialog() != null && circleDialog.getDialog().isShowing()) {
            circleDialog.dismiss();
            CircleDialog.z = 0;
        }
        circleDialog = null;
    }

    // use when have a many request get data.
    public synchronized void showCircleDialogOnly() {
        if (CircleDialog.z >= 1) {
            CircleDialog.z ++;
            return;
        }
        circleDialog = CircleDialog.getInstance();
        if (circleDialog.getDialog() == null || (!circleDialog.getDialog().isShowing())) {
            circleDialog.show(getFragmentManager(), null);
            CircleDialog.z = 1;
        }
    }

    public void hideCircleDialogOnly() {
        if (CircleDialog.z > 1) {
            CircleDialog.z--;
        } else if (CircleDialog.z == 1) {
            circleDialog = CircleDialog.getInstance();
            if (circleDialog.getDialog() != null && circleDialog.getDialog().isShowing()) {
                circleDialog.dismiss();
                circleDialog = null;
            }
            CircleDialog.z = 0;
        }
    }

}
