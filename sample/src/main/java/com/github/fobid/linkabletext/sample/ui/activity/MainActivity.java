package com.github.fobid.linkabletext.sample.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.github.fobid.linkabletext.sample.R;
import com.github.fobid.linkabletext.view.OnLinkClickListener;
import com.github.fobid.linkabletext.widget.LinkableTextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements OnLinkClickListener {

    private InterstitialAd interstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate an InterstitialAd object
        interstitialAd = new InterstitialAd(this, "101094783737704_101116820402167");
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }
        });

        // Load the interstitial ad
        interstitialAd.loadAd();

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.a_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3893540164578089/6619601459");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("B05EA171BA1EACADD3DFF94B87E35314")  // An example device ID
                .build();
        mAdView.loadAd(adRequest);

        LinkableTextView textView = (LinkableTextView) findViewById(android.R.id.text1);
        textView.setHighlightColor(Color.TRANSPARENT);

        String text = "https://github.com/fobid/linkable-text-android is #github #repository of linkable-text.\n" +
                "blog.fobid.me is my #blog.\n" +
                "You can contact me via #instagram @fobidlim or email.\n" +
                "Here is my email address. fobidbumz@gmail.com\n" +
                "And 010-0000-0000 is my phone number.\n" +
                "Thank you.";
        textView.setText(text);
        textView.setOnLinkClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.a_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_listview:
                startActivity(new Intent(this, ListViewActivity.class));
                return true;
            case R.id.menu_recyclerview:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onHashtagClick(String hashtag) {
        Toast.makeText(this, "Clicked hashtag is " + hashtag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMentionClick(String mention) {
        Toast.makeText(this, "Clicked mention is " + mention, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailAddressClick(String email) {
        Toast.makeText(this, "Clicked email is " + email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWebUrlClick(String url) {
        Toast.makeText(this, "Clicked url is " + url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhoneClick(String phone) {
        Toast.makeText(this, "Clicked phone is " + phone, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
