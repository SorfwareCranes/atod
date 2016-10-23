package software.cranes.com.dota.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by GiangNT - PC on 22/10/2016.
 */

public class Items {
    @JsonProperty(value = "items")
    private List<SnitppetModel> list;

    public Items(List<SnitppetModel> list) {
        this.list = list;
    }

    public Items() {
    }

    public List<SnitppetModel> getList() {
        return list;
    }

    public void setList(List<SnitppetModel> list) {
        this.list = list;
    }

    public String getTitle() {
        if (list != null && list.size() > 0) {
            Snippet snippet = list.get(0).getSnippet();
            if (snippet != null) {
                return snippet.getTitle();
            }
        }
        return null;
    }

    public String getUrl() {
        if (list != null && list.size() > 0) {
            Snippet snippet = list.get(0).getSnippet();
            if (snippet != null && snippet.getThumbnails() != null && snippet.getThumbnails().getDefaultUrl() != null) {
                return snippet.getThumbnails().getDefaultUrl().getUrl();
            }
        }
        return null;
    }
}
