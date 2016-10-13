package software.cranes.com.dota.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GiangNT - PC on 02/10/2016.
 */

public class GosuGamerTeamRankModel implements Parcelable{
    private int data_id;
    private int ranking;
    private String country;
    private String teamName;
    private int typeLocal;
    private int local_ranking;
    private String id_photo;
    private String earnedMoney;

    public GosuGamerTeamRankModel() {
    }

    public GosuGamerTeamRankModel(Parcel in) {
        data_id = in.readInt();
        ranking = in.readInt();
        country = in.readString();
        teamName = in.readString();
        typeLocal = in.readInt();
        local_ranking = in.readInt();
        id_photo = in.readString();
        earnedMoney = in.readString();
    }

    public GosuGamerTeamRankModel(int data_id, int ranking, String country, String teamName) {
        this.data_id = data_id;
        this.ranking = ranking;
        this.country = country;
        this.teamName = teamName;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getLocal_ranking() {
        return local_ranking;
    }

    public void setLocal_ranking(int local_ranking) {
        this.local_ranking = local_ranking;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTypeLocal() {
        return typeLocal;
    }

    public void setTypeLocal(int typeLocal) {
        this.typeLocal = typeLocal;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public String getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(String earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled
     * representation. For example, if the object will include a file descriptor in the output of
     * {@link #writeToParcel(Parcel, int)}, the return value of this method must include the {@link
     * #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable
     * object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or {@link
     *              #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data_id);
        dest.writeInt(ranking);
        dest.writeString(country);
        dest.writeString(teamName);
        dest.writeInt(typeLocal);
        dest.writeInt(local_ranking);
        dest.writeString(id_photo);
        dest.writeString(earnedMoney);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GosuGamerTeamRankModel> CREATOR = new Parcelable.Creator<GosuGamerTeamRankModel>() {
        @Override
        public GosuGamerTeamRankModel createFromParcel(Parcel in) {
            return new GosuGamerTeamRankModel(in);
        }

        @Override
        public GosuGamerTeamRankModel[] newArray(int size) {
            return new GosuGamerTeamRankModel[size];
        }
    };
}
