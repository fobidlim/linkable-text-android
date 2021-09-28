package com.github.fobid.linkabletext.sample.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.crashlytics.android.Crashlytics
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.github.fobid.linkabletext.sample.R
import com.github.fobid.linkabletext.sample.databinding.ActivityMainBinding
import com.github.fobid.linkabletext.view.OnLinkClickListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnLinkClickListener {

    private lateinit var interstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate an InterstitialAd object
        interstitialAd = InterstitialAd(this, "101094783737704_101116820402167")
        interstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onLoggingImpression(p0: Ad?) {
            }

            override fun onInterstitialDisplayed(ad: Ad) {
                // Interstitial displayed callback
            }

            override fun onInterstitialDismissed(ad: Ad) {
                // Interstitial dismissed callback
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
            }

            override fun onAdLoaded(ad: Ad) {
                // Show the ad when it's done loading.
                interstitialAd.show()
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }
        })

        // Load the interstitial ad
        interstitialAd.loadAd()

        Fabric.with(this, Crashlytics())
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        MobileAds.initialize(applicationContext, "ca-app-pub-3893540164578089/6619601459")

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("B05EA171BA1EACADD3DFF94B87E35314")  // An example device ID
                .build()
        )

        val text =
            "https://github.com/fobidlim/linkable-text-android is #github #repository of linkable-text.\n" +
                    "blog.fobidlim.com is my #blog.\n" +
                    "You can contact me via #instagram @fobidlim or email.\n" +
                    "Here is my email address. fobidlim@gmail.com\n" +
                    "And 010-0000-0000 is my phone number.\n" +
                    "Thank you."

        text_view.apply {
            setText(text)
            highlightColor = Color.TRANSPARENT
            setOnLinkClickListener(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.a_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_recyclerview -> {
                startActivity(Intent(this, RecyclerViewActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onHashtagClick(hashtag: String) =
        Toast.makeText(this, "Clicked hashtag is $hashtag", Toast.LENGTH_SHORT).show()

    override fun onMentionClick(mention: String) =
        Toast.makeText(this, "Clicked mention is $mention", Toast.LENGTH_SHORT).show()

    override fun onEmailAddressClick(email: String) =
        Toast.makeText(this, "Clicked email is $email", Toast.LENGTH_SHORT).show()

    override fun onWebUrlClick(url: String) =
        Toast.makeText(this, "Clicked url is $url", Toast.LENGTH_SHORT).show()

    override fun onPhoneClick(phone: String) =
        Toast.makeText(this, "Clicked phone is $phone", Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        interstitialAd.destroy()
        super.onDestroy()
    }
}
