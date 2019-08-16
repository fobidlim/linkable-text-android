package com.github.fobid.linkabletext.sample.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.fobid.linkabletext.sample.R;
import com.github.fobid.linkabletext.view.OnLinkClickListener;
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
            mLinkList.add("#" + (i + 1) + " https://github.com/fobidlim/linkable-text-android is #github #repository of linkable-text.");
            mLinkList.add("blog.jameslim.kr is my #blog.");
            mLinkList.add("You can contact me via #instagram @fobidlim or email.");
            mLinkList.add("Here is my email address. fobidlim@gmail.com");
            mLinkList.add("And 010-0000-0000 is my phone number.");
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
            holder.textView.setOnLinkClickListener(new OnLinkClickListener() {
                @Override
                public void onHashtagClick(String hashtag) {
                    Toast.makeText(RecyclerViewActivity.this, "Clicked hashtag is " + hashtag, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMentionClick(String mention) {
                    Toast.makeText(RecyclerViewActivity.this, "Clicked mention is " + mention, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmailAddressClick(String email) {
                    Toast.makeText(RecyclerViewActivity.this, "Clicked email is " + email, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onWebUrlClick(String url) {
                    Toast.makeText(RecyclerViewActivity.this, "Clicked url is " + url, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPhoneClick(String phone) {
                    Toast.makeText(RecyclerViewActivity.this, "Clicked phone is " + phone, Toast.LENGTH_SHORT).show();
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
