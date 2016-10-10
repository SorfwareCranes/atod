package software.cranes.com.dota.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by GiangNT - PC on 23/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankerMMRmodel {
    @JsonProperty(value = "time_posted")
    private int time_posted;
    @JsonProperty(value = "next_scheduled_post_time")
    private int next_scheduled_post_time;
    @JsonProperty(value = "sever_time")
    private int sever_time;
    @JsonProperty(value = "leaderboard")
    private List<MMRmodel> leaderboard;

    public RankerMMRmodel() {
    }

    public int getTime_posted() {
        return time_posted;
    }

    public void setTime_posted(int time_posted) {
        this.time_posted = time_posted;
    }

    public int getNext_scheduled_post_time() {
        return next_scheduled_post_time;
    }

    public void setNext_scheduled_post_time(int next_scheduled_post_time) {
        this.next_scheduled_post_time = next_scheduled_post_time;
    }

    public int getSever_time() {
        return sever_time;
    }

    public void setSever_time(int sever_time) {
        this.sever_time = sever_time;
    }

    public List<MMRmodel> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<MMRmodel> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
