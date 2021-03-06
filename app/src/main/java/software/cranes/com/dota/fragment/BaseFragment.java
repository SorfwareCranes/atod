package software.cranes.com.dota.fragment;


import com.google.firebase.database.FirebaseDatabase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import software.cranes.com.dota.common.CommonUtils;
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
    protected Context mContext;
    protected MainActivity activity;
    protected FirebaseDatabase mFirebaseDatabase;

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
        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
            CircleDialog.z++;
            Log.d("z", " add z: " + CircleDialog.z);
            return;
        } else if (CircleDialog.z == 0) {
            if (circleDialog == null) {
                circleDialog = CircleDialog.getInstance();
            }
            if (circleDialog.getDialog() == null || (!circleDialog.getDialog().isShowing())) {
                CircleDialog.z = 1;
                circleDialog.show(getFragmentManager(), null);
                Log.d("z", "show z: " + CircleDialog.z);

            }
        }
    }

    public synchronized void hideCircleDialogOnly() {
        if (CircleDialog.z > 1) {
            CircleDialog.z--;
            Log.d("z", "tru z: " + CircleDialog.z);
        } else if (CircleDialog.z == 1) {
            if (circleDialog != null) {
                circleDialog.dismiss();
                CircleDialog.z = 0;
                Log.d("z", "hide " + CircleDialog.z);
            }
        }
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
        if (circleDialog != null) {
            circleDialog.dismiss();
            CircleDialog.z = 0;
        }
    }

    public void replaceFragment(int layoutId, BaseFragment fragment, boolean isAddBackTrack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(layoutId, fragment, null);
        if (isAddBackTrack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

//    public void replaceFragment(int layoutId, BaseFragment fragment, String tab, boolean isAddBackTrack) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(layoutId, fragment, tab);
//        if (isAddBackTrack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commit();
//    }

    public ArrayList<String> unEscapeList(List<String> list) {
        ArrayList<String> result = new ArrayList<>();
        if (list != null) {
            for (String str : list) {
                result.add(CommonUtils.unescapeKey(str));
            }
        }
        return result;
    }

    public boolean onBackPress() {
        return false;
    }
}
