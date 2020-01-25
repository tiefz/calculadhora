package br.com.insertkoin.calculadhora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabActivity extends AppCompatActivity {

    private static final String SETTINGS = "Settings";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    int valorRecebido = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null)
            valorRecebido = b.getInt("key");


    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent configIntent = new Intent(this, ConfigActivity.class);
            startActivity(configIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new Fragment();
            Boolean ativaPausa = true;
            SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
            if(sharedPreferences.contains("AtivaPausa")) {
                ativaPausa = sharedPreferences.getBoolean("AtivaPausa", true);
            }

            if(!ativaPausa){

                switch (position) {
                    case 0:
                        fragment = new Entrada();
                        break;
                    case 1:
                        fragment = new AlmocoS();
                        break;
                    case 2:
                        fragment = new AlmocoR();
                        break;
                    case 3:
                        if(valorRecebido > 1) {
                            fragment = new SaidaHT();
                        } else {
                            fragment = new SaidaCalculada();
                        }
                        break;
                }

            }else {

                switch (position) {
                    case 0:
                        fragment = new Entrada();
                        break;
                    case 1:
                        fragment = new AlmocoS();
                        break;
                    case 2:
                        fragment = new AlmocoR();
                        break;
                    case 3:
                        fragment = new PausaExtra();
                        break;
                    case 4:
                        if(valorRecebido > 1) {
                            fragment = new SaidaHT();
                        } else {
                            fragment = new SaidaCalculada();
                        }
                        break;
                }
            }
            return fragment;
        }

        @Override
        public int getCount() {
            Fragment fragment = new Fragment();
            Boolean ativaPausa = true;
            SharedPreferences sharedPreferences = getSharedPreferences(SETTINGS, 0);
            if(sharedPreferences.contains("AtivaPausa")) {
                ativaPausa = sharedPreferences.getBoolean("AtivaPausa", true);
            }
            if(ativaPausa==false){
                return 4;
            }else{
            return 5;
            }
        }
    }
}
