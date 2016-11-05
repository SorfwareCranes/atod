package software.cranes.com.dota.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import software.cranes.com.dota.R;

import static software.cranes.com.dota.R.id.tvBo;

/**
 * Created by GiangNT - PC on 05/11/2016.
 */

public class ViewHolderTourModel extends RecyclerView.ViewHolder {
    public ImageView imgTeamA;
    public TextView tvNameTeamA;
    public TextView tvStatus;
    public TextView tvRound;
    public TextView tvResult;
    public ImageView imgTeamB;
    public TextView tvNameTeamB;

    public ViewHolderTourModel(View itemView) {
        super(itemView);
        imgTeamA = (ImageView) itemView.findViewById(R.id.imgTeamA);
        tvNameTeamA = (TextView) itemView.findViewById(R.id.tvNameTeamA);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        tvRound = (TextView) itemView.findViewById(R.id.tvRound);
        tvResult = (TextView) itemView.findViewById(R.id.tvResult);
        imgTeamB = (ImageView) itemView.findViewById(R.id.imgTeamB);
        tvNameTeamB = (TextView) itemView.findViewById(R.id.tvNameTeamB);
    }
}
