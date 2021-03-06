package software.cranes.com.dota.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import software.cranes.com.dota.R;
import software.cranes.com.dota.dialog.LoginDialog;
import software.cranes.com.dota.screen.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfomationUsFragment extends BaseFragment implements View.OnClickListener, LoginDialog.LoginListener {
    TextView tvLogins;

    public InfomationUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infomation_us, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLogins = (TextView) view.findViewById(R.id.tvLogins);
        tvLogins.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogins:
                handleLogin();
                break;
        }
    }

    private void handleLogin() {
        LoginDialog loginDialog = LoginDialog.getInstance(this);
        if (loginDialog.getDialog() == null || !loginDialog.getDialog().isShowing()) {
            loginDialog.show(getFragmentManager(), null);
        }
    }

    @Override
    public void onLoginSuccess() {
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity.isAdmin()) {
                replaceFragment(R.id.aboutFrame, new AdminFragment(), true);
            }
        }
    }
}
