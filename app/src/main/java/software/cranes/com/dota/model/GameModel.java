package software.cranes.com.dota.model;

import java.util.HashMap;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class GameModel {
    private String full;
    private String high;
    private int rs;
    private HashMap<String, String> teamA;
    private HashMap<String, String> teamB;

    public GameModel(String full, String high, int rs, HashMap<String, String> teamA, HashMap<String, String> teamB) {
        this.full = full;
        this.high = high;
        this.rs = rs;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public GameModel() {
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public HashMap<String, String> getTeamA() {
        return teamA;
    }

    public void setTeamA(HashMap<String, String> teamA) {
        this.teamA = teamA;
    }

    public HashMap<String, String> getTeamB() {
        return teamB;
    }

    public void setTeamB(HashMap<String, String> teamB) {
        this.teamB = teamB;
    }
}
