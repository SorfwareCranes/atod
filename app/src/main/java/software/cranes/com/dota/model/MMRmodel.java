package software.cranes.com.dota.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import static android.R.attr.value;

/**
 * Created by GiangNT - PC on 23/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MMRmodel {
    @JsonProperty(value = "rank")
    private int rank;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "team_id")
    private int team_id;
    @JsonProperty(value = "team_tag")
    private String team_tag;
    @JsonProperty(value = "country")
    private String country;
    @JsonProperty(value = "solo_mmr")
    private int solo_mmr;

    public MMRmodel() {
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_tag() {
        return team_tag;
    }

    public void setTeam_tag(String team_tag) {
        this.team_tag = team_tag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSolo_mmr() {
        return solo_mmr;
    }

    public void setSolo_mmr(int solo_mmr) {
        this.solo_mmr = solo_mmr;
    }
}
