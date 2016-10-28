package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 28/10/2016.
 */

public class VideoModel {
    private String lv;
    private long time;

    public VideoModel() {
    }

    public VideoModel(String lv, long time) {
        this.lv = lv;
        this.time = time;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
