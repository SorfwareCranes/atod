package software.cranes.com.dota.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.common.ImageRequestCustom;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.GameModel;
import software.cranes.com.dota.model.MatchModel;
import software.cranes.com.dota.screen.VideoYoutubeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTabFragment extends BaseFragment {
    protected int sizeImage;
    protected Bitmap bitmapRes;
    protected int sizeImageHeroes;

    public BaseTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sizeImageHeroes = getResources().getDimensionPixelSize(R.dimen.heroes_detail);
        sizeImage = getResources().getDimensionPixelSize(R.dimen.logo_team_detail);
        bitmapRes = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no_image_team);
    }

    @Override
    public boolean onBackPress() {
        return true;
    }

    protected View createViewForGame(int number, final GameModel game, MatchModel model) {
        String url;
        View resultView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_game_layout, null, false);
        TextView tvGameResult = (TextView) resultView.findViewById(R.id.tvGameResult);
        ImageView imgHeroA1 = (ImageView) resultView.findViewById(R.id.imgHeroA1);
        TextView tvPlayerA1 = (TextView) resultView.findViewById(R.id.tvPlayerA1);
        ImageView imgHeroB1 = (ImageView) resultView.findViewById(R.id.imgHeroB1);
        TextView tvPlayerB1 = (TextView) resultView.findViewById(R.id.tvPlayerB1);
        ImageView imgHeroA2 = (ImageView) resultView.findViewById(R.id.imgHeroA2);
        TextView tvPlayerA2 = (TextView) resultView.findViewById(R.id.tvPlayerA2);
        ImageView imgHeroB2 = (ImageView) resultView.findViewById(R.id.imgHeroB2);
        TextView tvPlayerB2 = (TextView) resultView.findViewById(R.id.tvPlayerB2);
        ImageView imgHeroA3 = (ImageView) resultView.findViewById(R.id.imgHeroA3);
        TextView tvPlayerA3 = (TextView) resultView.findViewById(R.id.tvPlayerA3);
        ImageView imgHeroB3 = (ImageView) resultView.findViewById(R.id.imgHeroB3);
        TextView tvPlayerB3 = (TextView) resultView.findViewById(R.id.tvPlayerB3);
        ImageView imgHeroA4 = (ImageView) resultView.findViewById(R.id.imgHeroA4);
        TextView tvPlayerA4 = (TextView) resultView.findViewById(R.id.tvPlayerA4);
        ImageView imgHeroB4 = (ImageView) resultView.findViewById(R.id.imgHeroB4);
        TextView tvPlayerB4 = (TextView) resultView.findViewById(R.id.tvPlayerB4);
        ImageView imgHeroA5 = (ImageView) resultView.findViewById(R.id.imgHeroA5);
        TextView tvPlayerA5 = (TextView) resultView.findViewById(R.id.tvPlayerA5);
        ImageView imgHeroB5 = (ImageView) resultView.findViewById(R.id.imgHeroB5);
        TextView tvPlayerB5 = (TextView) resultView.findViewById(R.id.tvPlayerB5);
        Button btnFullGame = (Button) resultView.findViewById(R.id.btnFullGame);
        Button btnHighGame = (Button) resultView.findViewById(R.id.btnHighGame);
        if (game.getRs() == Constant.A_WIN) {
            tvGameResult.setText("Game " + number + " : " + model.getTa().getNa() + " WIN");
        } else if (game.getRs() == Constant.B_WIN) {
            tvGameResult.setText("Game " + number + " : " + model.getTb().getNa() + " WIN");
        } else {
            tvGameResult.setVisibility(View.GONE);
        }
        if (game.getTmA() != null) {
            int i = 1;
            for (String name : game.getTmA().keySet()) {
                switch (i) {
                    case 1:
                        tvPlayerA1.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmA().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroA1, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroA1);
                        break;
                    case 2:
                        tvPlayerA2.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmA().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroA2, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroA2);
                        break;
                    case 3:
                        tvPlayerA3.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmA().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroA3, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroA3);
                        break;
                    case 4:
                        tvPlayerA4.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmA().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroA4, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroA4);
                        break;
                    case 5:
                        tvPlayerA5.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmA().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroA5, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroA5);
                        break;
                }
                i++;
            }
        }
        if (game.getTmB() != null) {
            int i = 1;
            for (String name : game.getTmB().keySet()) {
                switch (i) {
                    case 1:
                        tvPlayerB1.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmB().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroB1, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroB1);
                        break;
                    case 2:
                        tvPlayerB2.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmB().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroB2, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroB2);
                        break;
                    case 3:
                        tvPlayerB3.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmB().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroB3, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroB3);
                        break;
                    case 4:
                        tvPlayerB4.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmB().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroB4, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroB4);
                        break;
                    case 5:
                        tvPlayerB5.setText(CommonUtils.unescapeKey(name));
                        url = new StringBuilder("http://cdn.dota2.com/apps/dota2/images/heroes/").append(game.getTmB().get(name)).append("_hphover.png").toString();
                        new ImageRequestCustom(mContext, imgHeroB5, url, sizeImageHeroes, (int) sizeImageHeroes / 127 * 71, R.drawable.no_image_team, bitmapRes).execute(imgHeroB5);
                        break;
                }
                i++;
            }
        }
        if ((game.getLf() == null || game.getLf().equals(Constant.NO_IMAGE)) && (game.getLh() == null || game.getLh().equals(Constant.NO_IMAGE))) {
            resultView.findViewById(R.id.llGameVideo).setVisibility(View.GONE);
        } else {
            if (game.getLf() == null || game.getLf().equals(Constant.NO_IMAGE)) {
                btnFullGame.setVisibility(View.INVISIBLE);
            }
            if (game.getLh() == null || game.getLh().equals(Constant.NO_IMAGE)) {
                btnHighGame.setVisibility(View.INVISIBLE);
            }
            btnFullGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), VideoYoutubeActivity.class);
                    intent.putExtra(Constant.DATA, game.getLf());
                    startActivity(intent);
                }
            });
            btnHighGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), VideoYoutubeActivity.class);
                    intent.putExtra(Constant.DATA, game.getLh());
                    startActivity(intent);
                }
            });
        }

        return resultView;
    }
}
