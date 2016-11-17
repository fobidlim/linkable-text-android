package com.github.fobid.linkabletext.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.fobid.linkabletext.sample.R;
import com.github.fobid.linkabletext.util.OnLinkableClickListener;
import com.github.fobid.linkabletext.widget.LinkableTextView;

import java.util.ArrayList;

/**
 * Created by partner on 2016-11-17.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private ArrayList<String> mLinkList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_recycler_view);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinkList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mLinkList.add("#" + (i + 1) + " http://blog.fobid.me is my #blog.");
            mLinkList.add("fobid.me is the domain.");
            mLinkList.add("You can also connect to my #blog on just blog.fobid.me");
            mLinkList.add("You can contact me on #instagram @fobidlim.");
            mLinkList.add("fobidbumz@gmail.com is my email and 010-0000-0000 is my phone number.");
            mLinkList.add("Your IP address is 127.0.0.1");
            mLinkList.add("Thank you.");
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.i_list, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.textView.setText(mLinkList.get(position));
            holder.textView.addLinks(new OnLinkableClickListener() {
                @Override
                public void onHashtagClick(String hashtag) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked hashtag is " + hashtag, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMentionClick(String mention) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked mention is " + mention, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmailClick(String email) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked email is " + email, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUrlClick(String url) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked url is " + url, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPhoneClick(String phone) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked phone is " + phone, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDomainNameClick(String domainName) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked domain name is " + domainName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onIpAddressClick(String ipAddress) {
                    Toast.makeText(RecyclerViewActivity.this, "clicked ip address is " + ipAddress, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mLinkList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private LinkableTextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = (LinkableTextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }

}
