package software.cranes.com.dota.fragment;


import com.google.firebase.database.Query;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.common.SendRequest;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.VideoModel;
import software.cranes.com.dota.model.ViewHolderVideoModel;
import software.cranes.com.dota.screen.VideoYoutubeActivity;

import static software.cranes.com.dota.R.id.rcvSynthetic;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseVideoFragment extends BaseFragment {
    protected int sizeImage;
    protected Bitmap bitmapRes;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sizeImage = getResources().getDimensionPixelSize(R.dimen.youtube_with);
        bitmapRes = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.no_image);
    }

}
