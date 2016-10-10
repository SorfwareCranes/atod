package software.cranes.com.dota.model;

import java.util.Map;

/**
 * Created by GiangNT - PC on 05/10/2016.
 */

public class JoindotaTeamRankModel {
    private String data_id;
    private int rank;
    private String id_photo;
    private String name;
    private Map<String, String> teamPlayer;

    public JoindotaTeamRankModel() {
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(Map<String, String> teamPlayer) {
        this.teamPlayer = teamPlayer;
    }
}

