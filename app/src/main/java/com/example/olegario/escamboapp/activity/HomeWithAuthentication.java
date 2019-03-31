package com.example.olegario.escamboapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olegario.escamboapp.R;
import com.example.olegario.escamboapp.firebase.FirebaseAuthHandler;
import com.example.olegario.escamboapp.firebase.FirebaseDatabaseHandler;
import com.example.olegario.escamboapp.firebase.FirebaseStorageHandler;
import com.example.olegario.escamboapp.fragment.CreateUserBasicInfoFragment;
import com.example.olegario.escamboapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeWithAuthentication extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;
    private String userId;
    private FirebaseAuthHandler authHandler = FirebaseAuthHandler.getInstance();
    private FirebaseDatabaseHandler databaseHandler = FirebaseDatabaseHandler.getInstance();
    private FirebaseStorageHandler storageHandler = FirebaseStorageHandler.getInstance();

    private ImageView navHeaderUserImage;
    private TextView navHeaderUsername;
    private TextView navHeaderEmail;
    private Menu menu;

    private CreateUserBasicInfoFragment createUserFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_with_authentication);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navViewWithAuthentication);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        this.navHeaderUserImage = headerView.findViewById(R.id.navHeaderUserImageView);
        this.navHeaderUsername = headerView.findViewById(R.id.navHeaderUsernameTextView);
        this.navHeaderEmail = headerView.findViewById(R.id.navHeaderEmailTextView);

        this.setUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                setResult(RESULT_OK);
                finish();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                CreateUserBasicInfoFragment fragment = (CreateUserBasicInfoFragment) fragmentManager
                        .findFragmentByTag("createUserFragment");

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                fragmentManager.popBackStack();
                setSearchBarVisibility(true);
                this.setTitle();
            }
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        if (id == R.id.myProfile) {
            showUserEdition(bundle);
        } else if (id == R.id.myAds) {

        } else if (id == R.id.searchByCategory) {

        } else if (id == R.id.nav_signout) {
            authHandler.logoutUser();
            setResult(RESULT_CANCELED);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUser() {
        final String email = authHandler.getEmailFromCurrentUser();
        if (email == null) finish(); // Something went very wrong
        this.databaseHandler.getUserByEmail(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userId = dataSnapshot.getKey();
                user = dataSnapshot.getValue(User.class);
                setTitle();
                configHeader();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTitle() {
        if (this.user != null) {
            final String title = getString(R.string.welcomeUser) + " " + this.user.getFirstName();
            getSupportActionBar().setTitle(title);
        }
    }

    private void configHeader() {
        storageHandler.getUserImage(userId).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri fileUri = task.getResult();
                    new RetrieveImageTask().execute(fileUri.toString());
                } else {
                    final String errMsg = getString(R.string.cantLoadImage);
                    Toast.makeText(HomeWithAuthentication.this, errMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.navHeaderUsername.setText(this.user.buildFullName());
        this.navHeaderEmail.setText(this.user.getEmail());
    }

    private void setSearchBarVisibility(boolean visible) {
        menu.findItem(R.id.search).setVisible(visible);
    }

    private void addOrReplaceFragment(Fragment fragment, FragmentTransaction transaction) {
        final int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            transaction.add(R.id.fragmentFrameLayout, createUserFragment, "createUserFragment");
        } else {
            transaction.replace(R.id.fragmentFrameLayout, createUserFragment, "createUserFragment");
        }
        transaction.addToBackStack(null);
    }

    private void showUserEdition(Bundle bundle) {
        bundle.putString("titleSupportActionBar", getSupportActionBar().getTitle().toString());
        bundle.putSerializable("user", this.user);
        this.setSearchBarVisibility(false);
        this.createUserFragment = new CreateUserBasicInfoFragment();
        createUserFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        addOrReplaceFragment(createUserFragment, transaction);
        transaction.commit();
    }

    class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp;
            } catch (Exception e) {
                this.exception = e;
                return null;
            } finally {
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            navHeaderUserImage.setImageBitmap(bitmap);
        }
    }
}
