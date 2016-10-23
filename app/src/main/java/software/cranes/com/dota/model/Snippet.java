package software.cranes.com.dota.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Created by GiangNT - PC on 22/10/2016.
 */
public class Snippet {
    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "thumbnails")
    private Thumbnails thumbnails;

    public Snippet(String title, Thumbnails thumbnails) {
        this.title = title;
        this.thumbnails = thumbnails;
    }

    public Snippet() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
