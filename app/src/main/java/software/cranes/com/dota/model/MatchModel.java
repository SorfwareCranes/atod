package software.cranes.com.dota.model;

import java.util.List;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class MatchModel {
    private String tour;
    private String round;
    private String matchId;
    private int bo;
    private int status;
    private long time;
    private int beta, betb, ra, rb;
    private TeamModel teamA;
    private TeamModel teamB;
    private List<LiveChanelModel> liveList;
    private int sum;

    public MatchModel(String tour, String round, String matchId, int bo, int status, long time, int beta, int betb, int ra, int rb, TeamModel teamA, TeamModel teamB, List<LiveChanelModel> liveList, int sum) {
        this.tour = tour;
        this.round = round;
        this.matchId = matchId;
        this.bo = bo;
        this.status = status;
        this.time = time;
        this.beta = beta;
        this.betb = betb;
        this.ra = ra;
        this.rb = rb;
        this.teamA = teamA;
        this.teamB = teamB;
        this.liveList = liveList;
        this.sum = sum;
    }

    public MatchModel() {
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getBo() {
        return bo;
    }

    public void setBo(int bo) {
        this.bo = bo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getBetb() {
        return betb;
    }

    public void setBetb(int betb) {
        this.betb = betb;
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

    public List<LiveChanelModel> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<LiveChanelModel> liveList) {
        this.liveList = liveList;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
