package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class LiveChanelModel {
    private String videoId;
    private String language;

    public LiveChanelModel() {
    }

    public LiveChanelModel(String videoId, String language) {
        this.videoId = videoId;
        this.language = language;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
