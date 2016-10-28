package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class TeamModel {
    private String na;
    private String pt;

    public TeamModel(String na, String pt) {
        this.na = na;
        this.pt = pt;
    }

    public TeamModel() {
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }
}
