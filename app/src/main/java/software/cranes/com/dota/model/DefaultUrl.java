package software.cranes.com.dota.model;

import com.google.firebase.database.PropertyName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by GiangNT - PC on 22/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultUrl {
    @PropertyName(value = "url")
    private String url;

    public DefaultUrl() {
    }

    public DefaultUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
