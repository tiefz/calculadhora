package br.com.insertkoin.calculadhora;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView logoMain;
    private TextView ultimaHora;
    private TextView horaAjustada;
    private Button calcular;
    private AlertDialog.Builder dialog;
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

        logoMain = findViewById(R.id.logoMainID);
        logoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabActivity.class));
            }
        });

        ultimaHora = findViewById(R.id.ultimaHoraID);
        ultimaHora.setText(R.string.zerohora);
        SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
        if(sharedPreferences.contains("UltimaHora")) {
            ultimaHora.setText(sharedPreferences.getString("UltimaHora", "00:00"));
        }
        horaAjustada = findViewById(R.id.horaAjustadaID);
        horaAjustada.setText("08:48");
        if(sharedPreferences.contains("HoraDeCalculo")) {
            horaAjustada.setText(sharedPreferences.getString("HoraDeCalculo", "08:48"));
        }
        calcular = findViewById(R.id.calcularID);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (!sharedPreferences.getBoolean("primeiroacesso", false)) {
                    dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Configuração de Pausas Extras");
                    dialog.setMessage("Você costuma fazer pausas além do almoço?");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Não faz pausas extras
                            SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("AtivaPausa", false);
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, TabActivity.class));
                        }
                    });
                    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Faz pausas extras
                            SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("AtivaPausa", true);
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, TabActivity.class));
                        }
                    });
                    dialog.create();
                    dialog.show();
                    editor.putBoolean("primeiroacesso", true);
                    editor.commit();
                }else {
                    startActivity(new Intent(MainActivity.this, TabActivity.class));
                }
            }
        });

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
            startActivity(configIntent);
        } else if (id == R.id.nav_calculaTempoID) {
            Intent configIntent = new Intent(this, TabActivity2.class);
            startActivity(configIntent);
        } else if (id == R.id.nav_manage) {
            Intent configIntent = new Intent(this, ConfigActivity.class);
            startActivity(configIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
