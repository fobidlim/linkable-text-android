package com.github.fobid.linkabletext.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.fobid.linkabletext.sample.R;
import com.github.fobid.linkabletext.util.OnLinkableClickListener;
import com.github.fobid.linkabletext.widget.LinkableTextView;

import java.util.ArrayList;

/**
 * Created by partner on 2016-11-17.
 */

public class ListViewActivity extends AppCompatActivity {

    private ArrayList<String> mLinkList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_list_view);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinkList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mLinkList.add("#" + (i + 1) + " http://blog.fobid.me is my #blog.");
            mLinkList.add("fobid.me is the domain.");
            mLinkList.add("You can also connect to my #blog on just blog.fobid.me");
            mLinkList.add("You can contact me on #instagram @fobidlim.");
            mLinkList.add("sungtae.lim@partner.so is my email and 010-4421-0228 is my phone number.");
            mLinkList.add("Your IP address is 127.0.0.1");
            mLinkList.add("Thank you.");
        }

        ListView listView = (ListView) findViewById(android.R.id.list);
        LinkableListAdapter adapter = new LinkableListAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class LinkableListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLinkList.size();
        }

        @Override
        public String getItem(int position) {
            return mLinkList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {

                convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.i_list, parent, false);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(getItem(position));
            holder.textView.addLinks(new OnLinkableClickListener() {
                @Override
                public void onHashtagClick(String hashtag) {
                    Toast.makeText(ListViewActivity.this, "clicked hashtag is " + hashtag, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMentionClick(String mention) {
                    Toast.makeText(ListViewActivity.this, "clicked mention is " + mention, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmailAddressClick(String email) {
                    Toast.makeText(ListViewActivity.this, "clicked email is " + email, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onWebUrlClick(String url) {
                    Toast.makeText(ListViewActivity.this, "clicked url is " + url, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPhoneClick(String phone) {
                    Toast.makeText(ListViewActivity.this, "clicked phone is " + phone, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDomainNameClick(String domainName) {
                    Toast.makeText(ListViewActivity.this, "clicked domain name is " + domainName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onIpAddressClick(String ipAddress) {
                    Toast.makeText(ListViewActivity.this, "clicked ip address is " + ipAddress, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        private class ViewHolder {

            private LinkableTextView textView;

            private ViewHolder(View v) {
                textView = (LinkableTextView) v.findViewById(android.R.id.text1);
            }
        }
    }
}
