package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class TeamModel implements Parcelable {
    private String na;
    private String pt;

    public TeamModel(String na, String pt) {
        this.na = na;
        this.pt = pt;
    }

    public TeamModel() {
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.na);
        dest.writeString(this.pt);
    }

    protected TeamModel(Parcel in) {
        this.na = in.readString();
        this.pt = in.readString();
    }

    public static final Parcelable.Creator<TeamModel> CREATOR = new Parcelable.Creator<TeamModel>() {
        @Override
        public TeamModel createFromParcel(Parcel source) {
            return new TeamModel(source);
        }

        @Override
        public TeamModel[] newArray(int size) {
            return new TeamModel[size];
        }
    };
}
