package software.cranes.com.dota.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import software.cranes.com.dota.R;


/**
 * Created by GiangNT - PC on 05/11/2016.
 */

public class ViewHolderMatchTeamModel extends RecyclerView.ViewHolder {
    public ImageView imgTeamA;
    public TextView tvNameTeamA;
    public TextView tvResult;
    public ImageView imgTeamB;
    public TextView tvNameTeamB;
    public ViewHolderMatchTeamModel(View itemView) {
        super(itemView);
        imgTeamA = (ImageView) itemView.findViewById(R.id.imgTeamA);
        tvNameTeamA = (TextView) itemView.findViewById(R.id.tvNameTeamA);
        tvResult = (TextView) itemView.findViewById(R.id.tvResult);
        imgTeamB = (ImageView) itemView.findViewById(R.id.imgTeamB);
        tvNameTeamB = (TextView) itemView.findViewById(R.id.tvNameTeamB);
    }
}
