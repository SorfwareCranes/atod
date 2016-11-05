package software.cranes.com.dota.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import software.cranes.com.dota.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTabFragment extends BaseFragment {
    protected int sizeImage;
    protected Bitmap bitmapRes;

    public BaseTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team);
        bitmapRes = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no_image_team);
    }

    @Override
    public boolean onBackPress() {
        return true;
    }
}
