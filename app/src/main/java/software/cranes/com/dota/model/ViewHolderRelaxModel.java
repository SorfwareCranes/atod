package software.cranes.com.dota.model;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import software.cranes.com.dota.R;

/**
 * Created by GiangNT - PC on 08/11/2016.
 */

public class ViewHolderRelaxModel extends RecyclerView.ViewHolder {
    public TextView tvVideoTitle;
    public TextView tvVideoTime;
    public ImageView imgVideo;

    public ViewHolderRelaxModel(View itemView) {
        super(itemView);
        tvVideoTitle = (TextView) itemView.findViewById(R.id.tvVideoTitle);
        tvVideoTime = (TextView) itemView.findViewById(R.id.tvVideoTime);
        imgVideo = (ImageView) itemView.findViewById(R.id.imgVideo);
    }
}
