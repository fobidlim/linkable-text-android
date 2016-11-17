package com.github.fobid.linkabletext.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.TextView;

import com.github.fobid.linkabletext.R;
import com.github.fobid.linkabletext.annotation.LinkType;
import com.github.fobid.linkabletext.util.LinkableCallback;
import com.github.fobid.linkabletext.util.LinkableMovementMethod;
import com.github.fobid.linkabletext.util.OnLinkableClickListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by partner on 2016-11-17.
 */

public class LinkableTextView extends TextView {

    public interface Link {
        int HASH_TAG = 0x1;
        int MENTION = 0x2;
        int EMAIL_ADDRESS = 0x3;
        int PHONE = 0x4;
        int WEB_URL = 0x5;
        int DOMAIN_NAME = 0x6;
        int IP_ADDRESS = 0x7;
    }

    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static Pattern MENTION_PATTERN;
    private static Pattern HASHTAG_PATTERN;

    private boolean enabledHashtag = true;
    private boolean enabledMention = true;
    private boolean enabledEmailAddress = true;
    private boolean enabledPhone = true;
    private boolean enabledWebUrl = true;
    private boolean enabledDomainName = true;
    private boolean enabledIpAddress = true;

    public LinkableTextView(Context context) {
        super(context);
    }

    public LinkableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        String mentionPattern = null;
        String hashtagPattern = null;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinkableTextView);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.LinkableTextView_pattern_mention)
                mentionPattern = a.getString(attr);
            else if (attr == R.styleable.LinkableTextView_pattern_hashtag)
                hashtagPattern = a.getString(attr);
            else if (attr == R.styleable.LinkableTextView_enabledHashtag)
                enabledHashtag = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledMention)
                enabledMention = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledEmail_address)
                enabledEmailAddress = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledPhone)
                enabledPhone = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledWeb_url)
                enabledWebUrl = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledDomain_name)
                enabledDomainName = a.getBoolean(attr, true);
            else if (attr == R.styleable.LinkableTextView_enabledIp_address)
                enabledIpAddress = a.getBoolean(attr, true);
        }
        setMentionPattern(mentionPattern);
        setHashtagPattern(hashtagPattern);
        setEnabledHashtag(enabledHashtag);
        setEnabledMention(enabledMention);
        setEnabledEmailAddress(enabledEmailAddress);
        setEnabledPhone(enabledPhone);
        setEnabledWebUrl(enabledWebUrl);
        setEnabledDomainName(enabledDomainName);
        setEnabledIpAddress(enabledIpAddress);

        a.recycle();
    }

    public void setMentionPattern(String mentionPattern) {
        if (!TextUtils.isEmpty(mentionPattern)) {
            MENTION_PATTERN = Pattern.compile(mentionPattern);
        } else {
            MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_-]+)");
        }
    }

    public Pattern getMentionPattern() {
        return MENTION_PATTERN;
    }

    public void setHashtagPattern(String hashTagPattern) {
        if (!TextUtils.isEmpty(hashTagPattern)) {
            HASHTAG_PATTERN = Pattern.compile(hashTagPattern);
        } else {
            HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_-]+)");
        }
    }

    public Pattern getHashtagPattern() {
        return HASHTAG_PATTERN;
    }

    public boolean isEnabledDomainName() {
        return enabledDomainName;
    }

    public void setEnabledDomainName(boolean enabledDomainName) {
        this.enabledDomainName = enabledDomainName;
    }

    public boolean isEnabledEmailAddress() {
        return enabledEmailAddress;
    }

    public void setEnabledEmailAddress(boolean enabledEmailAddress) {
        this.enabledEmailAddress = enabledEmailAddress;
    }

    public boolean isEnabledHashtag() {
        return enabledHashtag;
    }

    public void setEnabledHashtag(boolean enabledHashtag) {
        this.enabledHashtag = enabledHashtag;
    }

    public boolean isEnabledIpAddress() {
        return enabledIpAddress;
    }

    public void setEnabledIpAddress(boolean enabledIpAddress) {
        this.enabledIpAddress = enabledIpAddress;
    }

    public boolean isEnabledMention() {
        return enabledMention;
    }

    public void setEnabledMention(boolean enabledMention) {
        this.enabledMention = enabledMention;
    }

    public boolean isEnabledPhone() {
        return enabledPhone;
    }

    public void setEnabledPhone(boolean enabledPhone) {
        this.enabledPhone = enabledPhone;
    }

    public boolean isEnabledWebUrl() {
        return enabledWebUrl;
    }

    public void setEnabledWebUrl(boolean enabledWebUrl) {
        this.enabledWebUrl = enabledWebUrl;
    }

    public void addLinks(final OnLinkableClickListener actionHandler) {
        addLinks(new LinkableCallback() {
            @Override
            public void onMatch(@LinkType int type, String value) {
                switch (type) {
                    case Link.HASH_TAG: {
                        if (enabledHashtag)
                            actionHandler.onHashtagClick(value);
                        break;
                    }
                    case Link.MENTION: {
                        if (enabledMention)
                            actionHandler.onMentionClick(value);
                        break;
                    }
                    case Link.EMAIL_ADDRESS: {
                        if (enabledEmailAddress)
                            actionHandler.onEmailAddressClick(value);
                        break;
                    }
                    case Link.WEB_URL: {
                        if (enabledWebUrl)
                            actionHandler.onWebUrlClick(value);
                        break;
                    }
                    case Link.PHONE: {
                        if (enabledPhone)
                            actionHandler.onPhoneClick(value);
                        break;
                    }
                    case Link.DOMAIN_NAME: {
                        if (enabledDomainName)
                            actionHandler.onDomainNameClick(value);
                        break;
                    }
                    case Link.IP_ADDRESS: {
                        if (enabledIpAddress)
                            actionHandler.onIpAddressClick(value);
                        break;
                    }
                }
            }
        });
    }

    public void addLinks(LinkableCallback callback) {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            public final String transformUrl(final Matcher match, String url) {
                return match.group();
            }
        };

        if (enabledHashtag)
            Linkify.addLinks(this, HASHTAG_PATTERN, LinkableMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        if (enabledMention)
            Linkify.addLinks(this, MENTION_PATTERN, LinkableMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        if (enabledEmailAddress)
            Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter);

        if (enabledPhone)
            Linkify.addLinks(this, Patterns.PHONE, null, null, filter);

        if (enabledWebUrl)
            Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);

        if (enabledDomainName)
            Linkify.addLinks(this, Patterns.DOMAIN_NAME, null, null, filter);

        if (enabledIpAddress)
            Linkify.addLinks(this, IP_ADDRESS_PATTERN, LinkableMovementMethod.SOCIAL_UI_IP_ADDRESS_SCHEME, null, filter);

        MovementMethod movementMethod = null;
        if (callback != null) {
            movementMethod = new LinkableMovementMethod(callback);
        }
        setMovementMethod(movementMethod);
    }
}
