package woahme.teamwork.com.woahme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.Models.PlaceResponseModel;
import woahme.teamwork.com.woahme.Storage.PlaceDbHelper;
import woahme.teamwork.com.woahme.Utilities.Notificator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PlacesListFragment.OnPlaceSelectListener{

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, new PlacesListFragment())
                .commit();


        Notificator.Notify(this, "SYFO DIAS", "THE FORCE WILL BE WITH YOU. ALWAYS!");
        PlaceDbHelper helper = new PlaceDbHelper(this);
        helper.onUpgrade(helper.getWritableDatabase(), 1, 2);
        helper.add("asdasd", "Horizontal", "asd.asd.asd", "here got gf", "alucard", "gf land", "1", "1", "1");
        ArrayList<PlaceModel> visited = helper.read();
        for (int i = 0; i < visited.size(); i++) {
            Log.e("Places: ", visited.get(i).toString());
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        fab.setVisibility(View.INVISIBLE);

        if (id == R.id.discover_new_places) {

            fab.setVisibility(View.VISIBLE);
            ReplaceFragment(R.id.main_fragment, new PlacesListFragment());

        } else if (id == R.id.add_new_place) {

            ReplaceFragment(R.id.main_fragment, new AddPlaceFragment());

        } else if (id == R.id.visited_places) {

        } else if (id == R.id.my_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ReplaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    @Override
    public void onPlaceSelected(PlaceModel place) {
        PlaceDetailsFragment placeDetailsFragment = (PlaceDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_details_fragment);

        if (placeDetailsFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            placeDetailsFragment.updateArticleView(place);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            /*Bundle args = new Bundle();
            Log.e("AUTISMO", place.getTitle());
            args.putString("title", "asd");
            args.putString("imageSource", "asdasd");*/

            PlaceDetailsFragment newFragment = new PlaceDetailsFragment();
            newFragment.updateArticleView(place);
            //newFragment.setArguments(args);

            ReplaceFragment(R.id.main_fragment, newFragment);
        }
    }
}
