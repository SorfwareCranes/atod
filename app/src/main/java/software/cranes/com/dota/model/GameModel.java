package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class GameModel {
    private String full;
    private String highlight;
    private GameTeamModel teamA;
    private GameTeamModel teamB;

    public GameModel() {
    }

    public GameModel(String full, String highlight, GameTeamModel teamA, GameTeamModel teamB) {
        this.full = full;
        this.highlight = highlight;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public GameTeamModel getTeamA() {
        return teamA;
    }

    public void setTeamA(GameTeamModel teamA) {
        this.teamA = teamA;
    }

    public GameTeamModel getTeamB() {
        return teamB;
    }

    public void setTeamB(GameTeamModel teamB) {
        this.teamB = teamB;
    }
}
