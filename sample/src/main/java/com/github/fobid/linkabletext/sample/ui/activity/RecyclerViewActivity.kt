package com.github.fobid.linkabletext.sample.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.fobid.linkabletext.sample.R
import com.github.fobid.linkabletext.sample.databinding.IListBinding
import com.github.fobid.linkabletext.view.OnLinkClickListener
import kotlinx.android.synthetic.main.a_recycler_view.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var linkList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_recycler_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linkList = ArrayList<String>()
                .apply {
                    for (i in 0..9) {
                        add("#${i + 1} https://github.com/fobidlim/linkable-text-android is #github #repository of linkable-text.")
                        add("blog.jameslim.kr is my #blog.")
                        add("You can contact me via #instagram @fobidlim or email.")
                        add("Here is my email address. fobidlim@gmail.com")
                        add("And 010-0000-0000 is my phone number.")
                        add("Thank you.")
                    }
                }

        recycler_view.adapter = RecyclerAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.i_list, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindData(linkList[position])
        }

        override fun getItemCount(): Int {
            return linkList.size
        }

        internal inner class ViewHolder(
                private val binding: IListBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bindData(text: String) {
                binding.text = text
                binding.textView.setOnLinkClickListener(object : OnLinkClickListener {
                    override fun onHashtagClick(hashtag: String) {
                        Toast.makeText(this@RecyclerViewActivity, "Clicked hashtag is $hashtag", Toast.LENGTH_SHORT).show()
                    }

                    override fun onMentionClick(mention: String) {
                        Toast.makeText(this@RecyclerViewActivity, "Clicked mention is $mention", Toast.LENGTH_SHORT).show()
                    }

                    override fun onEmailAddressClick(email: String) {
                        Toast.makeText(this@RecyclerViewActivity, "Clicked email is $email", Toast.LENGTH_SHORT).show()
                    }

                    override fun onWebUrlClick(url: String) {
                        Toast.makeText(this@RecyclerViewActivity, "Clicked url is $url", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPhoneClick(phone: String) {
                        Toast.makeText(this@RecyclerViewActivity, "Clicked phone is $phone", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}