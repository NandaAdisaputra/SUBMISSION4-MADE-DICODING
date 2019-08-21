package com.nandaadisaputra.cataloguemovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.nandaadisaputra.cataloguemovie.R;
import com.nandaadisaputra.cataloguemovie.fragment.FavoriteMovie;
import com.nandaadisaputra.cataloguemovie.fragment.FavoriteTv;
import com.nandaadisaputra.cataloguemovie.fragment.MovieFragment;
import com.nandaadisaputra.cataloguemovie.fragment.TvFragment;
import com.nandaadisaputra.cataloguemovie.fragment.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment pageContent = new HomeFragment();
    private String title = "Home";
    private String KEY_FRAGMENT;
    private String KEY_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.main_drawer);

    ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    pageContent = new HomeFragment();
                    title = "Home";
                    break;
                case R.id.menu_movie:
                    pageContent = new MovieFragment();
                    title = "Movie Fragment";
                    break;
                case R.id.menu_tv:
                    pageContent = new TvFragment();
                    title = "Tv Fragment";
                    break;
                case R.id.menu_movieFavorite:
                    pageContent = new FavoriteMovie();
                    title = " Movie Favorite Fragment";
                    break;
                case R.id.menu_tvFavorite:
                    pageContent = new FavoriteTv();
                    title = "Tv Favorite Fragment";
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
            toolbar.setTitle(title);
            drawerLayout.closeDrawers();
            return true;
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
            toolbar.setTitle(title);
        } else {
            pageContent = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            title = savedInstanceState.getString(KEY_TITLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
            toolbar.setTitle(title);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TITLE, title);
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pageContent);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
