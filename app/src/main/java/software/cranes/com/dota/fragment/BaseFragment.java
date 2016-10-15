package software.cranes.com.dota.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import software.cranes.com.dota.R;
import software.cranes.com.dota.dialog.CircleDialog;
import software.cranes.com.dota.dialog.ConfirmDialog;
import software.cranes.com.dota.dialog.WarningDialog;
import software.cranes.com.dota.screen.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements ConfirmDialog.HandleConfirmDialog {
    private ConfirmDialog confirmDialog;
    private WarningDialog warningDialog;
    private CircleDialog circleDialog;
    private Context mContext;
    private MainActivity activity;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Called when a fragment is first attached to its context. {@link #onCreate(Bundle)} will be
     * called after this.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Called when a fragment is first attached to its activity. {@link #onCreate(Bundle)} will be
     * called after this.
     *
     * @deprecated See {@link #onAttach(Context)}.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            this.activity = (MainActivity) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // show CircleDialog
    protected void showCircleDialog() {
        circleDialog = CircleDialog.getInstance();
        if (circleDialog.getDialog() == null || (circleDialog.getDialog() != null && !circleDialog.getDialog().isShowing())) {
            circleDialog.show(getFragmentManager(), null);
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

    // show WarningDialog
    protected void showWarningDialog(String message) {
        warningDialog = WarningDialog.getInstance(message);
        if (warningDialog.getDialog() == null || (warningDialog.getDialog() != null && !warningDialog.getDialog().isShowing())) {
            warningDialog.show(getFragmentManager(), null);
        }
    }

    protected void hideWarningDialog() {
        if (warningDialog != null && warningDialog.getDialog() != null && warningDialog.getDialog().isShowing()) {
            warningDialog.dismiss();
        }
        warningDialog = null;
    }

    // show confirmDiaglog
    protected void showConfirmDialog(String message) {
        confirmDialog = ConfirmDialog.getInstance(message, this);
        if (confirmDialog.getDialog() == null || (confirmDialog.getDialog() != null && !confirmDialog.getDialog().isShowing())) {
            confirmDialog.show(getFragmentManager(), null);
        }
    }

    protected void hideConfirmDialog() {
        if (confirmDialog != null && confirmDialog.getDialog() != null && confirmDialog.getDialog().isShowing()) {
            confirmDialog.dismiss();
        }
        confirmDialog = null;
    }

    @Override
    public void onHanleConfirmDialog() {

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
