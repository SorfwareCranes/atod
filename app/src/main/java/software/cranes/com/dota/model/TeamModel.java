package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class TeamModel {
    private String name;
    private String photoId;

    public TeamModel(String name, String photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public TeamModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
