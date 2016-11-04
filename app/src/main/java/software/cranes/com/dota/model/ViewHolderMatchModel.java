package software.cranes.com.dota.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import software.cranes.com.dota.R;

/**
 * Created by GiangNT - PC on 02/11/2016.
 */

public class ViewHolderMatchModel extends RecyclerView.ViewHolder {
    public TextView tvTour;
    public TextView tvTime;
    public ImageView imgTeamA;
    public TextView tvNameTeamA;
    public TextView tvResult;
    public TextView tvBo;
    public ImageView imgTeamB;
    public TextView tvNameTeamB;
    public TextView tvRound;

    public ViewHolderMatchModel(View itemView) {
        super(itemView);
        tvTour = (TextView) itemView.findViewById(R.id.tvTour);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        imgTeamA = (ImageView) itemView.findViewById(R.id.imgTeamA);
        tvNameTeamA = (TextView) itemView.findViewById(R.id.tvNameTeamA);
        tvResult = (TextView) itemView.findViewById(R.id.tvResult);
        tvBo = (TextView) itemView.findViewById(R.id.tvBo);
        imgTeamB = (ImageView) itemView.findViewById(R.id.imgTeamB);
        tvNameTeamB = (TextView) itemView.findViewById(R.id.tvNameTeamB);
        tvRound = (TextView) itemView.findViewById(R.id.tvRound);
    }
}

