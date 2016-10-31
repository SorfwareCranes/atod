package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 17/10/2016.
 */

public class PubGameModel {
    private String player;
    private String hero;
    private String title;
    private long time;

    public PubGameModel() {
    }

    public PubGameModel(String playerName, String heroName, String title, long time) {
        this.player = playerName;
        this.hero = heroName;
        this.title = title;
        this.time = time;
    }



    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
