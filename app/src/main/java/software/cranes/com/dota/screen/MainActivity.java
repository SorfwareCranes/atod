package software.cranes.com.dota.screen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import software.cranes.com.dota.R;
import software.cranes.com.dota.fragment.AboutUsFragment;
import software.cranes.com.dota.fragment.AddProfessionMatchFragment;
import software.cranes.com.dota.fragment.BaseFragment;
import software.cranes.com.dota.fragment.MMR_ScreenSlidePagerFragment;
import software.cranes.com.dota.fragment.MainScreenFragment;
import software.cranes.com.dota.fragment.RankTeam_ScreenSlidePagerFragment;

import static android.R.attr.fragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupView();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.containerActivity).getChildFragmentManager().findFragmentById(R.id.contentfragment);
            if (fragment != null && fragment.onBackPress()) {
                fragment.getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_login:
                goLogin();
                return true;
            case R.id.action_logout:
                goLogout();
                return true;
            case R.id.action_settings:
                goSetting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupView() {
        BaseFragment fragmentTest = new MainScreenFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerActivity, fragmentTest);
        fragmentTransaction.commit();
    }

    private void goLogin() {

    }

    private void goLogout() {

    }

    private void goSetting() {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_top_mmr:
                replaceFragment(new MMR_ScreenSlidePagerFragment(), R.id.containerActivity, true);
                break;
            case R.id.nav_top_team:
                replaceFragment(new RankTeam_ScreenSlidePagerFragment(), R.id.containerActivity, true);
                break;
            case R.id.nav_legend:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
            case R.id.nav_about:
                replaceFragment(new AboutUsFragment(), R.id.containerActivity, true);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
