package software.cranes.com.dota.screen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import software.cranes.com.dota.dialog.CircleDialog;
import software.cranes.com.dota.dialog.WarningDialog;
import software.cranes.com.dota.fragment.BaseFragment;
import software.cranes.com.dota.interfa.Constant;


/**
 * Created by GiangNT - PC on 10/09/2016.
 */

public class BaseActivity extends AppCompatActivity {
    private WarningDialog warningDialog;
    private CircleDialog circleDialog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    protected String name, email, statusLogin, uid;
    protected Uri photoUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new CustomAuthStateListener();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (statusLogin == Constant.anonymous) {
            mFirebaseAuth.getCurrentUser().delete();
        }
        if (mFirebaseAuth != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private class CustomAuthStateListener implements FirebaseAuth.AuthStateListener {

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {
                if (!mFirebaseUser.isAnonymous()) {
                    // is sign in
                    for (UserInfo userInfo : mFirebaseUser.getProviderData()) {
                        if (userInfo != null && userInfo.getProviderId() != null) {
                            email = userInfo.getEmail();
                            uid = userInfo.getUid();
                            if (userInfo.getDisplayName() == null) {
                                name = email.substring(0, email.length()-9);
                            } else {
                                name = userInfo.getDisplayName();
                            }
                            photoUri = userInfo.getPhotoUrl();
                            statusLogin = userInfo.getProviderId();
                        }
                    }
                } else {
                    // is Anonymous
                    name = null;
                    email = null;
                    uid = mFirebaseUser.getUid();
                    photoUri = null;
                    statusLogin = Constant.anonymous;
                }
            } else {
                // is log out
                name = null;
                email = null;
                uid = null;
                photoUri = null;
            }
        }
    }

    // show CircleDialog
    protected void showCircleDialog() {
        circleDialog = CircleDialog.getInstance();
        if (circleDialog.getDialog() == null || (circleDialog.getDialog() != null && !circleDialog.getDialog().isShowing())) {
            circleDialog.show(getSupportFragmentManager(), "base activity");
        }
    }

    protected void hideCircleDialog() {
        if (circleDialog != null && circleDialog.getDialog() != null && circleDialog.getDialog().isShowing()) {
            circleDialog.dismiss();
        }
        circleDialog = null;
    }

    // show WarningDialog
    protected void showWarningDialog(String message) {
        warningDialog = WarningDialog.getInstance(message);
        if (warningDialog.getDialog() == null || (warningDialog.getDialog() != null && !warningDialog.getDialog().isShowing())) {
            warningDialog.show(getSupportFragmentManager(), null);
        }
    }

    protected void hideWarningDialog() {
        if (warningDialog != null && warningDialog.getDialog() != null && warningDialog.getDialog().isShowing()) {
            warningDialog.dismiss();
        }
        warningDialog = null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatusLogin() {
        return statusLogin;
    }

    public String getUid() {
        return uid;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void replaceFragment(BaseFragment baseFragment, int container, boolean isAddBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, baseFragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatusLogin(String statusLogin) {
        this.statusLogin = statusLogin;
    }
}
