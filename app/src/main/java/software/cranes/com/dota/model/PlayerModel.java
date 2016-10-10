package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 04/10/2016.
 */

public class PlayerModel {
    private String name;
    private String id_photo;

    public PlayerModel() {
    }

    public PlayerModel(String name, String id_photo) {
        this.name = name;
        this.id_photo = id_photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }
}
