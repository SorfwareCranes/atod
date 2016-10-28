package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class LiveChanelModel {
    private String lv;
    private String la;

    public LiveChanelModel() {
    }

    public LiveChanelModel(String lv, String la) {
        this.lv = lv;
        this.la = la;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }
}
