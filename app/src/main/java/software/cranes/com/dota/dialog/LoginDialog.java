package software.cranes.com.dota.dialog;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import software.cranes.com.dota.R;
import software.cranes.com.dota.interfa.Constant;
import software.cranes.com.dota.model.SingleStringModel;
import software.cranes.com.dota.model.UserModel;
import software.cranes.com.dota.screen.MainActivity;

/**
 * Created by GiangNT - PC on 19/09/2016.
 */
@SuppressLint("ValidFragment")
public class LoginDialog extends BaseDialogFragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static LoginDialog mInstance;
    private Button btnGoogleLogin;
    private Button btnFacebookLogin;
    private Button btnTwitter;
    private TextView btnBack;
    private final int GOOGLE_SIGNIN_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private LoginListener mListener;

    public interface LoginListener {
        void onLoginSuccess();
    }

    public LoginDialog() {

    }

    public LoginDialog(LoginListener mListener) {
        this.mListener = mListener;
    }

    public synchronized static LoginDialog getInstance(LoginListener mListener) {
        if (mInstance == null) {
            mInstance = new LoginDialog(mListener);
        }
        return mInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.login_dialog_layout, null, false);
        setupView(view);

        mBuilder.setView(view);
        Dialog mDialog = mBuilder.create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    private void setupView(View view) {
        btnGoogleLogin = (Button) view.findViewById(R.id.btnGoogleLogin);
        btnFacebookLogin = (Button) view.findViewById(R.id.btnFacebookLogin);
        btnTwitter = (Button) view.findViewById(R.id.btnTwitter);
        btnBack = (TextView) view.findViewById(R.id.btnBack);

        btnGoogleLogin.setOnClickListener(this);
        btnFacebookLogin.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoogleLogin:
                handleGoogleSignIn();
                break;
            case R.id.btnFacebookLogin:

                break;
            case R.id.btnTwitter:

                break;
            case R.id.btnBack:
                this.dismiss();
                break;
        }
    }

    // SignIn with google
    private void handleGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, GOOGLE_SIGNIN_REQUEST);
    }
    // handle SignIn with Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGNIN_REQUEST) {
            GoogleSignInResult mGoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (mGoogleSignInResult.isSuccess()) {
                GoogleSignInAccount mGoogleSignInAccount = mGoogleSignInResult.getSignInAccount();
                firebaseAuthWithGoogle(mGoogleSignInAccount);
            } else {
                LoginDialog.this.dismiss();
                Toast.makeText(getActivity(), "Google Sign In failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // handle authendicate with google acount
    private void firebaseAuthWithGoogle(GoogleSignInAccount mGoogleSignInAccount) {
        showCircleDialog();
        AuthCredential mAuthCredential = GoogleAuthProvider.getCredential(mGoogleSignInAccount.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(mAuthCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideCircleDialog();
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    writeUserToFireBase(firebaseUser, Constant.google);
                    checkAdmin(firebaseUser.getEmail());
                } else {
                    Toast.makeText(getActivity(), "Authenticate Google Acount failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // write user to data  base
    private void writeUserToFireBase(FirebaseUser mFirebaseUser, String type) {
        String name = mFirebaseUser.getDisplayName();
        final String email = mFirebaseUser.getEmail();
        String userId = mFirebaseUser.getUid();
        if (name == null || name.equals(Constant.NO_IMAGE)) {
            name = email.substring(0, email.length() - 9);
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setName(name);
            ((MainActivity) getActivity()).setStatusLogin(type);
        }
        FirebaseDatabase.getInstance().getReference("users/" + userId).setValue(new UserModel(name, email, type)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // do nothing
                } else {
//                    LoginDialog.this.dismiss();
                    Toast.makeText(getActivity(), "Save User have fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        LoginDialog.this.dismiss();
        Toast.makeText(getActivity(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void checkAdmin(final String email) {
        showCircleDialog();
        FirebaseDatabase.getInstance().getReference("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideCircleDialog();
                if (dataSnapshot != null) {
                    GenericTypeIndicator<ArrayList<SingleStringModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<SingleStringModel>>() {
                    };
                    ArrayList<SingleStringModel> list = dataSnapshot.getValue(genericTypeIndicator);
                    if (list != null && list.size() != 0) {
                        for (SingleStringModel model : list) {
                            if (model.getName().equals(email)) {
                                if (getActivity() instanceof MainActivity) {
                                    ((MainActivity) getActivity()).setAdmin(true);
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
                LoginDialog.this.dismiss();
                if (mListener != null) {
                    mListener.onLoginSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideCircleDialog();
                LoginDialog.this.dismiss();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
