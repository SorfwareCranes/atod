package software.cranes.com.dota.model;

import com.google.firebase.database.PropertyName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by GiangNT - PC on 22/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnails {
    @PropertyName(value = "maxres")
    private Maxres maxres;

    public Thumbnails(Maxres maxres) {
        this.maxres = maxres;
    }

    public Thumbnails() {
    }

    public Maxres getMaxres() {
        return maxres;
    }

    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }
}
