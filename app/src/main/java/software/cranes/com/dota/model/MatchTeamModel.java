package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 01/11/2016.
 */

public class MatchTeamModel {
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
}
