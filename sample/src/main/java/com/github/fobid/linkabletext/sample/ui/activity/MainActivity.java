package com.github.fobid.linkabletext.sample.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.fobid.linkabletext.sample.R;
import com.github.fobid.linkabletext.util.OnLinkableClickListener;
import com.github.fobid.linkabletext.widget.LinkableTextView;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements OnLinkableClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.a_main);

        LinkableTextView textView = (LinkableTextView) findViewById(android.R.id.text1);
        textView.setHighlightColor(Color.TRANSPARENT);

        String text = "http://blog.fobid.me is my #blog.\n" +
                "fobid.me is the domain.\n" +
                "You can also connect to my #blog on just blog.fobid.me\n" +
                "You can contact me on #instagram @fobidlim.\n" +
                "fobidbumz@gmail.com is my email and 010-0000-0000 is my phone number.\n" +
                "Your IP address is 127.0.0.1\n" +
                "Thank you.";
        textView.setText(text);
        textView.addLinks(this);
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
        Toast.makeText(this, "clicked hashtag is " + hashtag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMentionClick(String mention) {
        Toast.makeText(this, "clicked mention is " + mention, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailAddressClick(String email) {
        Toast.makeText(this, "clicked email is " + email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWebUrlClick(String url) {
        Toast.makeText(this, "clicked url is " + url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhoneClick(String phone) {
        Toast.makeText(this, "clicked phone is " + phone, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDomainNameClick(String domainName) {
        Toast.makeText(this, "clicked domain name is " + domainName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIpAddressClick(String ipAddress) {
        Toast.makeText(this, "clicked ip address is " + ipAddress, Toast.LENGTH_SHORT).show();
    }
}
