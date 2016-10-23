package software.cranes.com.dota.model;

import com.google.firebase.database.PropertyName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by GiangNT - PC on 22/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnails {
    @PropertyName(value = "default")
    private DefaultUrl defaultUrl;

    public Thumbnails(DefaultUrl defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public Thumbnails() {
    }

    public DefaultUrl getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(DefaultUrl defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
