package com.example.rentacar;

// Author : Christy Gunawan Simarmata - 10120806 - IF5
// Tanggal : 26 Juli 2022

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Walkthrough extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotLayout;
    Button backBtn, nextBtn, skipBtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walkthrough);

        backBtn = findViewById(R.id.backbtn);
        nextBtn = findViewById(R.id.nextBtn);
        skipBtn = findViewById(R.id.skipButton);

        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getItem(0) > 0) {
                    slideViewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getItem(0) < 3) {
                    slideViewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent i = new Intent(Walkthrough.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Walkthrough.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);

        setupIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);

    }

    @SuppressLint("ResourceAsColor")
    public void setupIndicator(int position) {
        dots = new TextView[4];
        dotLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(R.color.inactive);
            dotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(R.color.active);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setupIndicator(position);

            if (position > 0) {
                backBtn.setVisibility(View.VISIBLE);
            } else {
                backBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }

}