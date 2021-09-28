package com.github.fobid.linkabletext.sample.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.fobid.linkabletext.sample.R
import com.github.fobid.linkabletext.sample.databinding.ItemListBinding
import com.github.fobid.linkabletext.view.OnLinkClickListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var linkList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linkList = ArrayList<String>()
            .apply {
                for (i in 0..9) {
                    add("#${i + 1} https://github.com/fobidlim/linkable-text-android is #github #repository of linkable-text.")
                    add("blog.fobidlim.com is my #blog.")
                    add("You can contact me via #instagram @fobidlim or email.")
                    add("Here is my email address. fobidlim@gmail.com")
                    add("And 010-0000-0000 is my phone number.")
                    add("Thank you.")
                }
            }

        MobileAds.initialize(applicationContext, "ca-app-pub-3893540164578089/8456261458")

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("B05EA171BA1EACADD3DFF94B87E35314")  // An example device ID
                .build()
        )

        recycler_view.adapter = RecyclerAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_list,
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindData(linkList[position])
        }

        override fun getItemCount(): Int = linkList.size

        inner class ViewHolder(
            private val binding: ItemListBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bindData(text: String) =
                binding.textView.apply {
                    setText(text)
                    setOnLinkClickListener(object : OnLinkClickListener {
                        override fun onHashtagClick(hashtag: String) {
                            Toast.makeText(
                                this@RecyclerViewActivity,
                                "Clicked hashtag is $hashtag",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        override fun onMentionClick(mention: String) {
                            Toast.makeText(
                                this@RecyclerViewActivity,
                                "Clicked mention is $mention",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        override fun onEmailAddressClick(email: String) {
                            Toast.makeText(
                                this@RecyclerViewActivity,
                                "Clicked email is $email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onWebUrlClick(url: String) {
                            Toast.makeText(
                                this@RecyclerViewActivity,
                                "Clicked url is $url",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onPhoneClick(phone: String) {
                            Toast.makeText(
                                this@RecyclerViewActivity,
                                "Clicked phone is $phone",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
        }
    }
}
