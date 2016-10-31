package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 31/10/2016.
 */

public class RelaxModel {
    private long time;
    private String title;

    public RelaxModel() {
    }

    public RelaxModel(long time, String title) {
        this.time = time;
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
