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
    private MatchTeamModel teamA;
    private MatchTeamModel teamB;
    private List<LiveChanelModel> liveList;
    private List<String> games;

    public MatchModel() {
    }

    public MatchModel(String tour, String round, String matchId, int bo, int status, long time, MatchTeamModel teamA, MatchTeamModel teamB, List<LiveChanelModel> liveList, List<String> games) {
        this.tour = tour;
        this.round = round;
        this.matchId = matchId;
        this.bo = bo;
        this.status = status;
        this.time = time;
        this.teamA = teamA;
        this.teamB = teamB;
        this.liveList = liveList;
        this.games = games;
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

    public MatchTeamModel getTeamA() {
        return teamA;
    }

    public void setTeamA(MatchTeamModel teamA) {
        this.teamA = teamA;
    }

    public MatchTeamModel getTeamB() {
        return teamB;
    }

    public void setTeamB(MatchTeamModel teamB) {
        this.teamB = teamB;
    }

    public List<LiveChanelModel> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<LiveChanelModel> liveList) {
        this.liveList = liveList;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }
}
