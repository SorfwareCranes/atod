package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GiangNT - PC on 01/11/2016.
 */

public class MatchTeamModel implements Parcelable {
    private int ra;
    private int rb;
    private int sum;
    private long time;
    private TeamModel ta;
    private TeamModel tb;

    public MatchTeamModel() {
    }

    public MatchTeamModel(int ra, int rb, int sum, long time, TeamModel ta, TeamModel tb) {
        this.ra = ra;
        this.rb = rb;
        this.sum = sum;
        this.time = time;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ra);
        dest.writeInt(this.rb);
        dest.writeInt(this.sum);
        dest.writeLong(this.time);
        dest.writeParcelable(this.ta, flags);
        dest.writeParcelable(this.tb, flags);
    }

    protected MatchTeamModel(Parcel in) {
        this.ra = in.readInt();
        this.rb = in.readInt();
        this.sum = in.readInt();
        this.time = in.readLong();
        this.ta = in.readParcelable(TeamModel.class.getClassLoader());
        this.tb = in.readParcelable(TeamModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<MatchTeamModel> CREATOR = new Parcelable.Creator<MatchTeamModel>() {
        @Override
        public MatchTeamModel createFromParcel(Parcel source) {
            return new MatchTeamModel(source);
        }

        @Override
        public MatchTeamModel[] newArray(int size) {
            return new MatchTeamModel[size];
        }
    };
}
