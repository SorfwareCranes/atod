package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class LiveChanelModel implements Parcelable {
    private String lv;
    private String la;

    public LiveChanelModel() {
    }

    public LiveChanelModel(String lv, String la) {
        this.lv = lv;
        this.la = la;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lv);
        dest.writeString(this.la);
    }

    protected LiveChanelModel(Parcel in) {
        this.lv = in.readString();
        this.la = in.readString();
    }

    public static final Parcelable.Creator<LiveChanelModel> CREATOR = new Parcelable.Creator<LiveChanelModel>() {
        @Override
        public LiveChanelModel createFromParcel(Parcel source) {
            return new LiveChanelModel(source);
        }

        @Override
        public LiveChanelModel[] newArray(int size) {
            return new LiveChanelModel[size];
        }
    };
}
