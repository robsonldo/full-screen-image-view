package com.github.tntkhang.fullscreenimageview.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class FullScreenImageViewActivity extends AppCompatActivity {

    public static final String URI_LIST_DATA = "URI_LIST_DATA";
    public static final String IMAGE_FULL_SCREEN_CURRENT_POS = "IMAGE_FULL_SCREEN_CURRENT_POS";
    public static final String IMAGE_FULL_SCREEN_SHARE = "IMAGE_FULL_SCREEN_SHARE";
    private int mPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_touch_image_view);

        findViewById(R.id.ic_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> imagePaths = getIntent().getStringArrayListExtra(URI_LIST_DATA);

        ImageView imvShare = findViewById(R.id.ic_share);
        imvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(imagePaths.get(mPosition));
            }
        });

        ViewPager viewPager = findViewById(R.id.view_pager);

        int currentPos = getIntent().getIntExtra(IMAGE_FULL_SCREEN_CURRENT_POS, 0);
        boolean isShare = getIntent().getBooleanExtra(IMAGE_FULL_SCREEN_SHARE, false);

        if (!isShare) imvShare.setVisibility(View.GONE);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager, imagePaths);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPos);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (imagePaths.size() < position + 1) return;
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void shareImage(String pathImage) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, pathImage);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}