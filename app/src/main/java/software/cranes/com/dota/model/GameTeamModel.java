package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class GameTeamModel {
    private int result;
    private String pl1;
    private String pl2;
    private String pl3;
    private String pl4;
    private String pl5;

    public GameTeamModel() {
    }

    public GameTeamModel(int result, String pl1, String pl2, String pl3, String pl4, String pl5) {
        this.result = result;
        this.pl1 = pl1;
        this.pl2 = pl2;
        this.pl3 = pl3;
        this.pl4 = pl4;
        this.pl5 = pl5;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPl1() {
        return pl1;
    }

    public void setPl1(String pl1) {
        this.pl1 = pl1;
    }

    public String getPl2() {
        return pl2;
    }

    public void setPl2(String pl2) {
        this.pl2 = pl2;
    }

    public String getPl3() {
        return pl3;
    }

    public void setPl3(String pl3) {
        this.pl3 = pl3;
    }

    public String getPl4() {
        return pl4;
    }

    public void setPl4(String pl4) {
        this.pl4 = pl4;
    }

    public String getPl5() {
        return pl5;
    }

    public void setPl5(String pl5) {
        this.pl5 = pl5;
    }
}
