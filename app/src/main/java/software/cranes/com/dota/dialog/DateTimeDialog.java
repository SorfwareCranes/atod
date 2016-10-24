package software.cranes.com.dota.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;


import java.util.Calendar;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;


/**
 * Created by GiangNT - PC on 17/06/2016.
 */
@SuppressLint("ValidFragment")
public class DateTimeDialog extends DialogFragment implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private TextView btnSetDate, btnSetTime;
    private TextView tvSetDate, tvSetTime;
    private ViewSwitcher viewSwitcher;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView btnPickerCancel;
    private TextView btnPickerOk;
    private HandleClickOnDialog mHandleClickOnDialog;
    private long time_start;
    private String TAG = "DateTimeDialog";

    public DateTimeDialog() {
    }

    public interface HandleClickOnDialog {
        public void handlePickerOk(long time);
    }

    public DateTimeDialog(long time_start, HandleClickOnDialog mHandleClickOnDialog) {
        this.mHandleClickOnDialog = mHandleClickOnDialog;
        this.time_start = time_start;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.w(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.w(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.date_time_dialog_layout, null, false);
        findViews(view);
        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void findViews(View v) {
        btnSetDate = (TextView) v.findViewById(R.id.btnSetDate);
        btnSetTime = (TextView) v.findViewById(R.id.btnSetTime);
        viewSwitcher = (ViewSwitcher) v.findViewById(R.id.viewSwitcher);
        datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        btnPickerCancel = (TextView) v.findViewById(R.id.btnPickerCancel);
        btnPickerOk = (TextView) v.findViewById(R.id.btnPickerOk);
        tvSetDate = (TextView) v.findViewById(R.id.tvSetDate);
        tvSetTime = (TextView) v.findViewById(R.id.tvSetTime);
        btnSetDate.setOnClickListener(this);
        btnSetTime.setOnClickListener(this);
        btnPickerCancel.setOnClickListener(this);
        btnPickerOk.setOnClickListener(this);

        timePicker.setIs24HourView(true);
        if (time_start > 0) {
            int[] arrTime = CommonUtils.convertIntToDateTime(time_start);
            datePicker.init(arrTime[0], arrTime[1], arrTime[2], new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // >= 23
                timePicker.setHour(arrTime[3]);
                timePicker.setMinute(arrTime[4]);
            } else {
                // < 23
                timePicker.setCurrentHour(arrTime[3]);
                timePicker.setCurrentMinute(arrTime[4]);
            }

        }

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
            case R.id.btnSetDate:
                if (viewSwitcher.getDisplayedChild() == 1) {
                    viewSwitcher.showNext();
                    tvSetDate.setVisibility(View.VISIBLE);
                    tvSetTime.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.btnSetTime:
                if (viewSwitcher.getDisplayedChild() == 0) {
                    viewSwitcher.showNext();
                    tvSetDate.setVisibility(View.INVISIBLE);
                    tvSetTime.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btnPickerCancel:
                this.dismiss();
                break;
            case R.id.btnPickerOk:
                executeOkOnDialog();
                break;
        }
    }

    /*
      * if not set time -> day default : current day
      * time start : 00 - 00
      * time end : 23-59
      */
    private void executeOkOnDialog() {
        int hour, minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        time_start = CommonUtils.convertDateTimeToInt(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), hour, minute);
        mHandleClickOnDialog.handlePickerOk(time_start);
        this.dismiss();
    }

    /**
     * Called upon a date change.
     *
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    private String convertToTwoLetter(String time) {
        if (time.length() == 1) {
            time = "0" + time;
        }
        return time;
    }
}


