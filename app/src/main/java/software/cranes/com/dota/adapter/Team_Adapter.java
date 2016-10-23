package software.cranes.com.dota.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.common.SingletonRequestQueue;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GosuGamerTeamRankModel;

import static android.R.attr.scaleType;
import static software.cranes.com.dota.R.id.imageView;

/**
 * Created by GiangNT - PC on 12/10/2016.
 */

public class Team_Adapter extends RecyclerView.Adapter<Team_Adapter.ViewHolder> {
    private ArrayList<GosuGamerTeamRankModel> listData;
    private Context context;
    private String baseURL = "http://www.gosugamers.net/uploads/images/teams/";
    private String endURL = ".jpeg";
    private int sizeImage;
    private Bitmap bitmapRes;
    public Team_Adapter(Context context, ArrayList<GosuGamerTeamRankModel> listData) {
        this.context = context;
        this.listData = listData;
        sizeImage = CommonUtils.getPixelFromDips(context, 60);
        bitmapRes = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image_team);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_rank_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GosuGamerTeamRankModel model = listData.get(position);
        holder.tvShowRank.setText(String.valueOf(listData.size() - position));
        holder.tvTeamName.setText(model.getTeamName());
        holder.tvCountry.setText(model.getCountry());
        if (model.getEarnedMoney() == null) {
            holder.llMoney.setVisibility(View.GONE);
        } else {
            holder.tvMoney.setText(model.getEarnedMoney());
        }
        String local;
        switch (model.getTypeLocal()) {
            case Constant.EUROPE_RANK:
                local = Constant.TITLE_EURO_RANK;
                break;
            case Constant.AMERICAS_RANK:
                local = Constant.TITLE_AMERICAS_RANK;
                break;
            case Constant.SEA_RANK:
                local = Constant.TITLE_SEA_RANK;
                break;
            case Constant.CHINA_RANK:
                local = Constant.TITLE_CHINA_RANK;
                break;
            default:
                local = Constant.NO_IMAGE;
                break;
        }
        holder.tvTypeLocal.setText(local);
        holder.tvWorldRank.setText(String.valueOf(model.getRanking()));
        if (!local.equals(Constant.NO_IMAGE)) {
            holder.tvLocalRank.setText(String.valueOf(model.getLocal_ranking()));
        } else {
            holder.tvLocalRank.setText(Constant.NO_IMAGE);
        }
        if (model.getId_photo() != null && !model.getId_photo().equals(Constant.NO_IMAGE)) {
            String url = new StringBuilder(baseURL).append(model.getId_photo()).append(endURL).toString();
            new ImageRequestCustom(context, holder.imgTeam, url, sizeImage, sizeImage, R.drawable.no_image_team, bitmapRes).execute(holder.imgTeam);

        } else {
            holder.imgTeam.setImageResource(R.drawable.no_image_team);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvShowRank;
        public ImageView imgTeam;
        public TextView tvTeamName;
        public TextView tvCountry;
        public TextView tvMoney;
        public TextView tvTypeLocal;
        public TextView tvWorldRank;
        public TextView tvLocalRank;
        public LinearLayout llMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvShowRank = (TextView) itemView.findViewById(R.id.tvShowRank);
            this.imgTeam = (ImageView) itemView.findViewById(R.id.img_team);
            this.tvTeamName = (TextView) itemView.findViewById(R.id.tvTeamName);
            this.tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            this.tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            this.tvTypeLocal = (TextView) itemView.findViewById(R.id.tvTypeLocal);
            this.tvWorldRank = (TextView) itemView.findViewById(R.id.tvWorldRank);
            this.tvLocalRank = (TextView) itemView.findViewById(R.id.tvLocalRank);
            llMoney = (LinearLayout) itemView.findViewById(R.id.llMoney);
        }
    }
}