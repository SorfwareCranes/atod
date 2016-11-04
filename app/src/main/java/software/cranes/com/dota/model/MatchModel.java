package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class MatchModel implements Parcelable {
    private String to;
    private String lo;
    private String ro;
    private String id;
    private int bo;
    private int st;
    private long time;
    private int ba, bb, ra, rb;
    private TeamModel ta;
    private TeamModel tb;
    private List<LiveChanelModel> ll;
    private int sum;

    public MatchModel() {
    }

    public MatchModel(String to, String lo, String ro, String id, int bo, int st, long time, int ba, int bb, int ra, int rb, TeamModel ta, TeamModel tb, List<LiveChanelModel> ll, int sum) {
        this.to = to;
        this.lo = lo;
        this.ro = ro;
        this.id = id;
        this.bo = bo;
        this.st = st;
        this.time = time;
        this.ba = ba;
        this.bb = bb;
        this.ra = ra;
        this.rb = rb;
        this.ta = ta;
        this.tb = tb;
        this.ll = ll;
        this.sum = sum;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRo() {
        return ro;
    }

    public void setRo(String ro) {
        this.ro = ro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBo() {
        return bo;
    }

    public void setBo(int bo) {
        this.bo = bo;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBa() {
        return ba;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public int getBb() {
        return bb;
    }

    public void setBb(int bb) {
        this.bb = bb;
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

    public List<LiveChanelModel> getLl() {
        return ll;
    }

    public void setLl(List<LiveChanelModel> ll) {
        this.ll = ll;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.to);
        dest.writeString(this.lo);
        dest.writeString(this.ro);
        dest.writeString(this.id);
        dest.writeInt(this.bo);
        dest.writeInt(this.st);
        dest.writeLong(this.time);
        dest.writeInt(this.ba);
        dest.writeInt(this.bb);
        dest.writeInt(this.ra);
        dest.writeInt(this.rb);
        dest.writeParcelable(this.ta, flags);
        dest.writeParcelable(this.tb, flags);
        dest.writeList(this.ll);
        dest.writeInt(this.sum);
    }

    protected MatchModel(Parcel in) {
        this.to = in.readString();
        this.lo = in.readString();
        this.ro = in.readString();
        this.id = in.readString();
        this.bo = in.readInt();
        this.st = in.readInt();
        this.time = in.readLong();
        this.ba = in.readInt();
        this.bb = in.readInt();
        this.ra = in.readInt();
        this.rb = in.readInt();
        this.ta = in.readParcelable(TeamModel.class.getClassLoader());
        this.tb = in.readParcelable(TeamModel.class.getClassLoader());
        this.ll = new ArrayList<LiveChanelModel>();
        in.readList(this.ll, LiveChanelModel.class.getClassLoader());
        this.sum = in.readInt();
    }

    public static final Parcelable.Creator<MatchModel> CREATOR = new Parcelable.Creator<MatchModel>() {
        @Override
        public MatchModel createFromParcel(Parcel source) {
            return new MatchModel(source);
        }

        @Override
        public MatchModel[] newArray(int size) {
            return new MatchModel[size];
        }
    };
}
