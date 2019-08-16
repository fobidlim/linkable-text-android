package com.github.fobid.linkabletext.sample.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.github.fobid.linkabletext.sample.R
import com.github.fobid.linkabletext.sample.databinding.AMainBinding
import com.github.fobid.linkabletext.view.OnLinkClickListener
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.a_main.*

class MainActivity : AppCompatActivity(), OnLinkClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        DataBindingUtil.setContentView<AMainBinding>(this, R.layout.a_main)
                .apply {
                    text = "https://github.com/fobidlim/linkable-text-android is #github #repository of linkable-text.\n" +
                            "blog.jameslim.kr is my #blog.\n" +
                            "You can contact me via #instagram @fobidlim or email.\n" +
                            "Here is my email address. fobidlim@gmail.com\n" +
                            "And 010-0000-0000 is my phone number.\n" +
                            "Thank you."
                }

        text_view.apply {
            highlightColor = Color.TRANSPARENT
            setOnLinkClickListener(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.a_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_recyclerview -> {
                startActivity(Intent(this, RecyclerViewActivity::class.java))
                true
            }
            else -> true
        }
    }

    override fun onHashtagClick(hashtag: String) {
        Toast.makeText(this, "Clicked hashtag is $hashtag", Toast.LENGTH_SHORT).show()
    }

    override fun onMentionClick(mention: String) {
        Toast.makeText(this, "Clicked mention is $mention", Toast.LENGTH_SHORT).show()
    }

    override fun onEmailAddressClick(email: String) {
        Toast.makeText(this, "Clicked email is $email", Toast.LENGTH_SHORT).show()
    }

    override fun onWebUrlClick(url: String) {
        Toast.makeText(this, "Clicked url is $url", Toast.LENGTH_SHORT).show()
    }

    override fun onPhoneClick(phone: String) {
        Toast.makeText(this, "Clicked phone is $phone", Toast.LENGTH_SHORT).show()
    }
}
