package software.cranes.com.dota.dialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.fragment.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class DateDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private DatePicker datePicker;
    private TextView tvCancel;
    private TextView tvSet;
    private long time;
    private HandleSetTime mHandleSetTime;


    public interface HandleSetTime {
        void handleTime(long time);
    }

    public DateDialogFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DateDialogFragment(long time, HandleSetTime mHandleSetTime) {
        this.time = time;
        this.mHandleSetTime = mHandleSetTime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_date_dialog, null, false);
        findViews(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void findViews(View view) {
        datePicker = (DatePicker) view.findViewById(R.id.datePickerPub);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvSet = (TextView) view.findViewById(R.id.tvSet);
        if (time != 0) {
            int[] dayTime = CommonUtils.convertIntToDate(time);
            datePicker.init(dayTime[0], dayTime[1], dayTime[2], new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    DateDialogFragment.this.time = CommonUtils.convertDateToInt(year, monthOfYear, dayOfMonth);
                }
            });
        }
        tvCancel.setOnClickListener(this);
        tvSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                this.dismiss();
                break;
            case R.id.tvSet:
                if (mHandleSetTime != null) {
                    this.time = CommonUtils.convertDateToInt(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    mHandleSetTime.handleTime(time);
                }
                this.dismiss();
                break;
        }
    }

}