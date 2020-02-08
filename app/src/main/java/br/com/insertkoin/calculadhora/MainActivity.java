package br.com.insertkoin.calculadhora;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView logoHome, clockmark;
    private TextView ultimaHora;
    private CardView cardview;
    private static final String SETTINGS = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        clockmark = findViewById(R.id.clockMark);

        ultimaHora = findViewById(R.id.ultimaHoraID);
        ultimaHora.setText(R.string.zerohora);
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("UltimaHora")) {
            ultimaHora.setText(sharedPreferences.getString("UltimaHora", "00:00"));
        }

        cardview = findViewById(R.id.cardViewHome);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(MainActivity.this, TabActivity.class);
                    configIntent.putExtra("key", 3);
                    startActivity(configIntent);
            }
        });

        rotacao();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent configIntent = new Intent(this, ConfigActivity.class);
            startActivity(configIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculadHoraID) {
            Intent configIntent = new Intent(this, TabActivity.class);
            configIntent.putExtra("key", 1);
            startActivity(configIntent);
        } else if (id == R.id.nav_calculaTempoID) {
            Intent configIntent = new Intent(this, TabActivity.class);
            configIntent.putExtra("key", 2);
            startActivity(configIntent);
        } else if (id == R.id.nav_manage) {
            Intent configIntent = new Intent(this, ConfigActivity.class);
            startActivity(configIntent);
        } else if (id == R.id.nav_help) {
            Intent configIntent = new Intent(this, Ajuda.class);
            startActivity(configIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void rotacao() {
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(60000);
        rotate.setRepeatCount(Animation.INFINITE);
        clockmark.startAnimation(rotate);
    }

}
