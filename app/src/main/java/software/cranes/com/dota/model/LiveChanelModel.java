package software.cranes.com.dota.model;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class LiveChanelModel {
    private String videoId;
    private String lang;

    public LiveChanelModel() {
    }

    public LiveChanelModel(String videoId, String language) {
        this.videoId = videoId;
        this.lang = language;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getLanguage() {
        return lang;
    }

    public void setLanguage(String language) {
        this.lang = language;
    }
}
