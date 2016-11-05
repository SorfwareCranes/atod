package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GiangNT - PC on 01/11/2016.
 */

public class TourModel implements Parcelable {
    private int ra;
    private int rb;
    private int st;
    private int sum;
    private long time;
    private String lo;
    private String ro;
    private TeamModel ta;
    private TeamModel tb;

    public TourModel() {
    }

    public TourModel(int ra, int rb, int st, int sum, long time, String lo, String ro, TeamModel ta, TeamModel tb) {
        this.ra = ra;
        this.rb = rb;
        this.st = st;
        this.sum = sum;
        this.time = time;
        this.lo = lo;
        this.ro = ro;
        this.ta = ta;
        this.tb = tb;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    public String getRo() {
        return ro;
    }

    public void setRo(String ro) {
        this.ro = ro;
    }

    public TeamModel getTa() {
        return ta;
    }

    public void setTa(TeamModel ta) {
        this.ta = ta;
    }

    public TeamModel getTb() {
        return tb;
    }

    public void setTb(TeamModel tb) {
        this.tb = tb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ra);
        dest.writeInt(this.rb);
        dest.writeInt(this.st);
        dest.writeInt(this.sum);
        dest.writeLong(this.time);
        dest.writeString(this.lo);
        dest.writeString(this.ro);
        dest.writeParcelable(this.ta, flags);
        dest.writeParcelable(this.tb, flags);
    }

    protected TourModel(Parcel in) {
        this.ra = in.readInt();
        this.rb = in.readInt();
        this.st = in.readInt();
        this.sum = in.readInt();
        this.time = in.readLong();
        this.lo = in.readString();
        this.ro = in.readString();
        this.ta = in.readParcelable(TeamModel.class.getClassLoader());
        this.tb = in.readParcelable(TeamModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<TourModel> CREATOR = new Parcelable.Creator<TourModel>() {
        @Override
        public TourModel createFromParcel(Parcel source) {
            return new TourModel(source);
        }

        @Override
        public TourModel[] newArray(int size) {
            return new TourModel[size];
        }
    };
}
