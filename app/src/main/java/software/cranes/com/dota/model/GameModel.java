package software.cranes.com.dota.model;

import java.util.HashMap;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class GameModel {
    private String lf;
    private String lh;
    private int rs;
    private HashMap<String, String> tmA;
    private HashMap<String, String> tmB;

    public GameModel() {
    }

    public GameModel(String lf, String lh, int rs, HashMap<String, String> tmA, HashMap<String, String> tmB) {
        this.lf = lf;
        this.lh = lh;
        this.rs = rs;
        this.tmA = tmA;
        this.tmB = tmB;
    }

    public HashMap<String, String> getTmA() {
        return tmA;
    }

    public void setTmA(HashMap<String, String> tmA) {
        this.tmA = tmA;
    }

    public HashMap<String, String> getTmB() {
        return tmB;
    }

    public void setTmB(HashMap<String, String> tmB) {
        this.tmB = tmB;
    }

    public String getLf() {
        return lf;
    }

    public void setLf(String lf) {
        this.lf = lf;
    }

    public String getLh() {
        return lh;
    }

    public void setLh(String lh) {
        this.lh = lh;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }


}
