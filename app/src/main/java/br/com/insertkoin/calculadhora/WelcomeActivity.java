package br.com.insertkoin.calculadhora;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private ImageView tutorialGIF01, tutorialGIF02, tutorialGIF03;
    private AnimationDrawable animation1, animation2, animation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5};

        addBottomDots(0);

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSetupScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    launchSetupScreen();
                }
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchSetupScreen() {
        startActivity(new Intent(WelcomeActivity.this, ConfigActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            configAnimation();
            tutorialGIF01 = findViewById(R.id.tutorialGIF01ID);
            tutorialGIF02 = findViewById(R.id.tutorialGIF02ID);
            tutorialGIF03 = findViewById(R.id.tutorialGIF03ID);


            switch (position) {
                case 0:
                    btnNext.setText(getString(R.string.next));
                    btnNext.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    btnNext.setText(getString(R.string.next));
                    btnNext.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setVisibility(View.VISIBLE);
                    tutorialGIF01.setImageDrawable(animation1);
                    animation1.start();
                    break;
                case 2:
                    btnNext.setText(getString(R.string.next));
                    btnNext.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setTextColor(Color.parseColor("#616161"));
                    btnSkip.setVisibility(View.VISIBLE);
                    tutorialGIF02.setImageDrawable(animation2);
                    animation2.start();
                    break;
                case 3:
                    btnNext.setText(getString(R.string.next));
                    btnNext.setTextColor(Color.LTGRAY);
                    btnSkip.setTextColor(Color.LTGRAY);
                    btnSkip.setVisibility(View.VISIBLE);
                    tutorialGIF03.setImageDrawable(animation3);
                    animation3.start();
                    break;
                case 4:
                    btnNext.setText(getString(R.string.start));
                    btnNext.setTextColor(Color.WHITE);
                    btnSkip.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void configAnimation() {
        animation1 = new AnimationDrawable();
        animation1.addFrame(getResources().getDrawable(R.drawable.screen1a), 1200);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2a), 1200);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2b), 1200);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2c), 1200);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2d), 1200);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2e), 800);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2f), 250);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2g), 250);
        animation1.addFrame(getResources().getDrawable(R.drawable.screen2a), 1200);

        animation2 = new AnimationDrawable();
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3a), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3b), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen2c), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen2d), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3c), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3d), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3e), 1200);
        animation2.addFrame(getResources().getDrawable(R.drawable.screen3f), 1200);

        animation3 = new AnimationDrawable();
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5a), 1200);
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5b), 1200);
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5c), 1200);
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5d), 1200);
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5e), 1200);
        animation3.addFrame(getResources().getDrawable(R.drawable.screen5f), 1200);

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}