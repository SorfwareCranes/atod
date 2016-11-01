package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 01/11/2016.
 */

public class TourModel {
    private int ra;
    private int rb;
    private int st;
    private int sum;
    private long time;
    private String lo;
    private String ro;
    private TeamModel teamA;
    private TeamModel teamB;

    public TourModel() {
    }

    public TourModel(int ra, int rb, int st, int sum, long time, String lo, String ro, TeamModel teamA, TeamModel teamB) {
        this.ra = ra;
        this.rb = rb;
        this.st = st;
        this.sum = sum;
        this.time = time;
        this.lo = lo;
        this.ro = ro;
        this.teamA = teamA;
        this.teamB = teamB;
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

    public TeamModel getTeamA() {
        return teamA;
    }

    public void setTeamA(TeamModel teamA) {
        this.teamA = teamA;
    }

    public TeamModel getTeamB() {
        return teamB;
    }

    public void setTeamB(TeamModel teamB) {
        this.teamB = teamB;
    }
}
