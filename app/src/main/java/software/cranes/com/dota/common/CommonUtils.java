package software.cranes.com.dota.common;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * Created by GiangNT - PC on 30/05/2016.
 */
public class CommonUtils {
    /*
        convert dp --> px
     */
    public static int getPixelFromDips(Context ctx, float dp) {
        // Get the screen's density scale
        final float scale = ctx.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    /*
        convert px --> dp
     */
    public static int getDipFromPixel(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        return (int) (px / scale);
    }

    /*
        get height device with unit px
     */
    public static int getHeightScreenDevice(Context pContext) {
        DisplayMetrics mDisplayMetrics = pContext.getResources().getDisplayMetrics();
        return mDisplayMetrics.heightPixels;
    }

    /*
        get width device with unit px
     */
    public static int getWidthScreenDevice(Context pContext) {
        DisplayMetrics mDisplayMetrics = pContext.getResources().getDisplayMetrics();
        return mDisplayMetrics.widthPixels;
    }

    /*
        is Tablet ?
     */
    public static boolean isTablet(Context context) {
        boolean ret = false;
        if (context != null) {
            ret = (context.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        }
        return ret;
    }

    public static boolean isUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
        return url.matches(urlPattern);
    }

    public static boolean isNetworkConnect(Context context) {
        ConnectivityManager connectivityManage = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManage.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *  return [year-month-day-hour-munite]
     */
    public static String[] getCurrentDate() {
        Calendar mCalendar = Calendar.getInstance();
        String YYYY = String.valueOf(mCalendar.get(Calendar.YEAR));
        String MO = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
        String DD = String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH));
        String HH = String.valueOf(mCalendar.get(Calendar.HOUR_OF_DAY));
        String MI = String.valueOf(mCalendar.get(Calendar.MINUTE));
        if (MO.length() == 1) {
            MO = "0" + MO;
        }
        if (DD.length() == 1) {
            DD = "0" + DD;
        }
        if (HH.length() == 1) {
            HH = "0" + HH;
        }
        if (MI.length() == 1) {
            MI = "0" + MI;
        }
        return new String[]{YYYY, MO, DD, HH, MI};
    }

    // return CurrentDate : YYYY-MM-DD
    public static String getCurrentDay() {
        Calendar mCalendar = Calendar.getInstance();
        String MM = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
        if (MM.length() == 1) {
            MM = "0" + MM;
        }
        String DD = String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH));
        if (DD.length() == 1) {
            DD = "0" + DD;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(mCalendar.get(Calendar.YEAR)));
        builder.append("-");
        builder.append(MM);
        builder.append("-");
        builder.append(DD);
        return builder.toString();
    }

    // str = 2016-08-21 13:57
    public static String getYear(String str) {
        return str.substring(0, 4);
    }

    public static Calendar getDateTimeFromString(String start_time) {
        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = mDateFormat.parse(start_time + ":00");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * param timeStart like : YYYY-mm-dd HH:MM
     */
    public static boolean compareTime(String timeStart, String timeEnd) {
        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        Date endDate;
        try {
            startDate = mDateFormat.parse(timeStart + ":00");
            endDate = mDateFormat.parse(timeEnd + ":00");
            if (startDate.compareTo(endDate) == 1) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException ex) {
            return false;
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String[] convertStringToArray(String str) {
        if (str == null) {
            return null;
        }
        return str.split(",");
    }

    public static String convertArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String[] removeElementArray(String[] array, int index) {
        if (array == null || index < 0 || index >= array.length) {
            return array;
        }
        String[] reslut = new String[array.length - 1];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }
            reslut[j++] = array[i];
        }
        return reslut;
    }

    public static String[] addElementToArray(String[] array, String element) {
        if (array == null || element == null || element.trim().isEmpty()) {
            return array;
        }
        String[] result = new String[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            if (element.trim().equals(array[i])) {
                return array;
            }
            result[i] = array[i];
        }
        result[array.length] = element;
        return result;
    }


    public static void setHeight(ExpandableListView exListView) {
        ExpandableListAdapter adapter = exListView.getExpandableListAdapter();
        if (adapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(exListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            view = adapter.getGroupView(i, false, null, exListView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = exListView.getLayoutParams();
        layoutParams.height = totalHeight + exListView.getDividerHeight() * (adapter.getGroupCount() - 1);
        exListView.setLayoutParams(layoutParams);
    }

    public static void setHeight(ExpandableListView exListView, int group) {
        ExpandableListAdapter adapter = exListView.getExpandableListAdapter();
        if (adapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(exListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            view = adapter.getGroupView(i, false, null, exListView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
            if ((exListView.isGroupExpanded(i) && (i != group))
                    || (!exListView.isGroupExpanded(i) && (i == group))) {
                View childView = null;
                for (int j = 0; j < adapter.getChildrenCount(i); j++) {
                    childView = adapter.getChildView(i, j, false, null, exListView);
                    childView.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += childView.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams layoutParams = exListView.getLayoutParams();
        layoutParams.height = totalHeight + exListView.getDividerHeight() * (adapter.getGroupCount() - 1);
        exListView.setLayoutParams(layoutParams);
    }

    public static boolean isYoutube(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Pattern pattern = Pattern.compile("http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(url).matches();
    }

    private static String youTubeLinkWithoutProtocolAndDomain(String url, String youTubeUrlRegEx) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }

    public static String extractVideoIdFromUrl(String url) {
        String vId = null;
        if (url != null) {
            Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*", Pattern.CASE_INSENSITIVE);
//            Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                vId = matcher.group();
            }
        }
        return vId;
    }

    public static void setHeight(RecyclerView recyclerView) {
        FirebaseRecyclerAdapter adapter = (FirebaseRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            view = adapter.onCreateViewHolder(recyclerView, i).itemView;
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        layoutParams.height = totalHeight;
        recyclerView.setLayoutParams(layoutParams);
    }

    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static String changeDateMonth(String str) {
        String[] result = str.split("-");
        return result[1] + "-" + result[0];
    }

    // . $ # [ ] /
    public static String escapeKey(String key) {
//        return key.replace("%", "%25")
        if (key == null) {
            return null;
        }
        return key.replace(".", "%2E")
                .replace("$", "%24")
                .replace("#", "%23")
                .replace("[", "%5B")
                .replace("]", "%5D")
                .replace("/", "%2F");
    }

    public static String unescapeKey(String key) {
//        return key.replace("%25", "%")
        if (key == null) {
            return null;
        }
        return key.replace("%2E", ".")
                .replace("%24", "$")
                .replace("%23", "#")
                .replace("%5B", "[")
                .replace("%5D", "]")
                .replace("%2F", "/");
    }

    public static Bitmap decodeBitmap(Bitmap tmp, int reqWidth, int reqHeight) {
        int rawWidth = tmp.getWidth();
        int rawHeight = tmp.getHeight();
        if (rawWidth <= reqWidth && rawHeight <= reqWidth) {
            return tmp;
        }
        Bitmap result;
        if ((rawWidth / reqWidth) >= (rawHeight / reqHeight)) {
            result = Bitmap.createScaledBitmap(tmp, reqWidth, (int) ((reqWidth / rawWidth) * rawHeight), false);
        } else {
            result = Bitmap.createScaledBitmap(tmp, (int) ((reqHeight / rawHeight) * rawWidth), reqHeight, false);
        }
        tmp.recycle();
        return result;
    }

    public static int[] convertIntToDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)};
    }

    public static int[] convertIntToDateTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)};
    }

    public static long convertDateTimeToInt(int year, int monthOfYear, int dayOfMonth, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, hour, minute);
        return calendar.getTimeInMillis() / 1000;
    }

    public static long convertDateToInt(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        return calendar.getTimeInMillis() / 1000;
    }

    public static String convertIntDateToString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return new StringBuilder().append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))).append(".").append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.MONTH) + 1))).append(".").append(calendar.get(Calendar.YEAR)).toString();
    }

    public static String convertintDateTimeToString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        return new StringBuilder().append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))).append(".").append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.MONTH) + 1))).append(".").append(calendar.get(Calendar.YEAR)).append(", ").append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))).append(":").append(convertToTwoLetter(String.valueOf(calendar.get(Calendar.MINUTE)))).toString();
    }

    private static String convertToTwoLetter(String time) {
        if (time.length() == 1) {
            time = "0" + time;
        }
        return time;
    }
    // abc def -> Abc Def
    // abc.def -> Abc.Def

    private String convertLowerCase(String str) {
        int pos = 0;
        boolean capitalize = true;
        StringBuilder sb = new StringBuilder(str);
        while (pos < sb.length()) {
            if (sb.charAt(pos) == '.' || sb.charAt(pos) == ' ') {
                capitalize = true;
            } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))){
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
            }
            pos++;
        }
        return sb.toString();
    }

}
