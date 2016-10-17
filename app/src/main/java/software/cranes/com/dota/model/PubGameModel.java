package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 17/10/2016.
 */

public class PubGameModel {
    private String playerName;
    private String heroName;
    private String title;
    private String full;
    private String highlight;
    private int time;

    public PubGameModel() {
    }

    public PubGameModel(String playerName, String heroName, String title, String full, String highlight, int time) {
        this.playerName = playerName;
        this.heroName = heroName;
        this.title = title;
        this.full = full;
        this.highlight = highlight;
        this.time = time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
