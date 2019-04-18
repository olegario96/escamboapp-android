package com.example.olegario.escamboapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.firebase.FirebaseAuthHandler;

public class HomeWithoutAuthentication extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_CODE_CREATE_ACCOUNT = 1;
    private final int REQUEST_CODE_LOGIN = 2;
    private final int REQUEST_CODE_HOME_WITH_LOGIN = 3;
    private FirebaseAuthHandler authHandler = FirebaseAuthHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (authHandler.isUserAuthenticated()) {
            this.startHomeActivityWithAuthentication();
        } else {
            this.setupView();
        }
    }

    protected  void setupView() {
        setContentView(R.layout.activity_home_without_authentication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleIntent(getIntent());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_without_authentication);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigationDrawerOpen, R.string.navigationDrawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navViewWithouAuthentication);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createAccountMenuButton) {
            Intent intent = new Intent(getApplicationContext(), CreateUserBasicInfoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT);
        } else if (id == R.id.loginMenuButton) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_without_authentication);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent intent;
            switch (requestCode) {
                case REQUEST_CODE_CREATE_ACCOUNT:
                    this.startHomeActivityWithAuthentication();
                    break;
                case REQUEST_CODE_LOGIN:
                    this.startHomeActivityWithAuthentication();
                    break;
                case REQUEST_CODE_HOME_WITH_LOGIN:
                    finish();
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            setupView();
            return;
        }
    }

    private void startHomeActivityWithAuthentication() {
        Intent intent = new Intent(getApplicationContext(), HomeWithAuthentication.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, REQUEST_CODE_HOME_WITH_LOGIN);
    }
}
