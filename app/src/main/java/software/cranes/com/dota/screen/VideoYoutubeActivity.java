package software.cranes.com.dota.screen;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import software.cranes.com.dota.R;
import software.cranes.com.dota.common.CommonUtils;
import software.cranes.com.dota.dialog.WarningDialog;
import software.cranes.com.dota.interfa.Constant;

public class VideoYoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerSupportFragment fragment;
    private String video_id;
    private final String LINK = "LINK";
    private final String TIME = "TIME";
    private YouTubePlayer youTubePlayer;
    private int timeContinue;
    private WarningDialog mWarningDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            video_id =getIntent().getStringExtra(Constant.DATA);
            timeContinue = 0;
        } else {
            video_id = savedInstanceState.getString(LINK);
            timeContinue = savedInstanceState.getInt(TIME);
        }
        setContentView(R.layout.activity_video_youtube);
        fragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.youTubePlayerFragment, fragment);
        fragmentTransaction.commit();
        fragment.initialize(getString(R.string.api_key_android), this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LINK, video_id);
        if (youTubePlayer != null) {
            timeContinue = youTubePlayer.getCurrentTimeMillis();
            outState.putInt(TIME, timeContinue);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            this.youTubePlayer = youTubePlayer;
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.loadVideo(video_id, timeContinue);

        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            showWarningDialog(youTubeInitializationResult.toString());
        }
    }

    protected void showWarningDialog(String message) {
        mWarningDialog = WarningDialog.getInstance(message);
        if (mWarningDialog.getDialog() == null || (mWarningDialog.getDialog() != null && !mWarningDialog.getDialog().isShowing())) {
            mWarningDialog.show(getSupportFragmentManager(), null);
        }
    }
    /**
     * Take care of popping the fragment back stack or finishing the activity as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
