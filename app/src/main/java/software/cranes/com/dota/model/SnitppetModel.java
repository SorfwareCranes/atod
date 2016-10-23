package software.cranes.com.dota.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by GiangNT - PC on 23/10/2016.
 */

public class SnitppetModel {
    @JsonProperty(value = "snippet")
    private Snippet snippet;

    public SnitppetModel(Snippet snippet) {
        this.snippet = snippet;
    }

    public SnitppetModel() {
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
