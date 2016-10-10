package software.cranes.com.dota.model;

import java.util.Map;

/**
 * Created by GiangNT - PC on 02/10/2016.
 */

public class GosuGamerTeamRankModel {
    private int data_id;
    private int ranking;
    private String country;
    private String teamName;
    private int typeLocal;
    private int local_ranking;
    private String id_photo;
    private String earnedMoney;
    private Map<String, String> teamPlayer;

    public GosuGamerTeamRankModel() {
    }

    public GosuGamerTeamRankModel(int data_id, int ranking, String country, String teamName) {
        this.data_id = data_id;
        this.ranking = ranking;
        this.country = country;
        this.teamName = teamName;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getLocal_ranking() {
        return local_ranking;
    }

    public void setLocal_ranking(int local_ranking) {
        this.local_ranking = local_ranking;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTypeLocal() {
        return typeLocal;
    }

    public void setTypeLocal(int typeLocal) {
        this.typeLocal = typeLocal;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public String getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(String earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public Map<String, String> getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(Map<String, String> teamPlayer) {
        this.teamPlayer = teamPlayer;
    }
}
