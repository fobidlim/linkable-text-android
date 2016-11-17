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

    private boolean mEnabledHashtag = true;
    private boolean mEnabledMention = true;
    private boolean mEnabledEmailAddress = true;
    private boolean mEnabledPhone = true;
    private boolean mEnabledWebUrl = true;
    private boolean mEnabledDomainName = true;
    private boolean mEnabledIpAddress = true;

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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinkableTextView);

        int n = a.getIndexCount();

        String mentionPattern = null;
        String hashtagPattern = null;

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.LinkableTextView_pattern_mention) {
                mentionPattern = a.getString(attr);
            } else if (attr == R.styleable.LinkableTextView_pattern_hashtag) {
                hashtagPattern = a.getString(attr);
            } else if (attr == R.styleable.LinkableTextView_enabledHashtag) {
                mEnabledHashtag = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledMention) {
                mEnabledMention = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledEmail_address) {
                mEnabledEmailAddress = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledPhone) {
                mEnabledPhone = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledWeb_url) {
                mEnabledWebUrl = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledDomain_name) {
                mEnabledDomainName = a.getBoolean(attr, true);
            } else if (attr == R.styleable.LinkableTextView_enabledIp_address) {
                mEnabledIpAddress = a.getBoolean(attr, true);
            }
        }
        setMentionPattern(mentionPattern);
        setHashtagPattern(hashtagPattern);
        setHashtagEnabled(mEnabledHashtag);
        setMentionEnabled(mEnabledMention);
        setEmailAddressEnabled(mEnabledEmailAddress);
        setPhonEnabled(mEnabledPhone);
        setWebUrlEnabled(mEnabledWebUrl);
        setDomainNameEnabled(mEnabledDomainName);
        setIpAddressEnabled(mEnabledIpAddress);

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

    public void setHashtagEnabled(boolean enable) {
        mEnabledHashtag = enable;
    }

    public boolean isHashtagEnabled() {
        return mEnabledHashtag;
    }

    public void setMentionEnabled(boolean enable) {
        mEnabledMention = enable;
    }

    public boolean isMentionEnabled() {
        return mEnabledMention;
    }

    public void setEmailAddressEnabled(boolean enable) {
        mEnabledEmailAddress = enable;
    }

    public boolean isEmailAddressEnabled() {
        return mEnabledEmailAddress;
    }

    public void setPhonEnabled(boolean enable) {
        mEnabledPhone = enable;
    }

    public boolean isPhoneEnabled() {
        return mEnabledPhone;
    }

    public void setWebUrlEnabled(boolean enable) {
        mEnabledWebUrl = enable;
    }

    public boolean isWebUrlEnabled() {
        return mEnabledWebUrl;
    }

    public void setDomainNameEnabled(boolean enable) {
        mEnabledDomainName = enable;
    }

    public boolean isDomainNameEnabled() {
        return mEnabledDomainName;
    }

    public void setIpAddressEnabled(boolean enable) {
        mEnabledIpAddress = enable;
    }

    public boolean isIpAddressEnabled() {
        return mEnabledIpAddress;
    }

    public void addLinks(final OnLinkableClickListener actionHandler) {
        addLinks(new LinkableCallback() {
            @Override
            public void onMatch(@LinkType int type, String value) {
                switch (type) {
                    case Link.HASH_TAG: {
                        if (mEnabledHashtag)
                            actionHandler.onHashtagClick(value);
                        break;
                    }
                    case Link.MENTION: {
                        if (mEnabledMention)
                            actionHandler.onMentionClick(value);
                        break;
                    }
                    case Link.EMAIL_ADDRESS: {
                        if (mEnabledEmailAddress)
                            actionHandler.onEmailAddressClick(value);
                        break;
                    }
                    case Link.WEB_URL: {
                        if (mEnabledWebUrl)
                            actionHandler.onWebUrlClick(value);
                        break;
                    }
                    case Link.PHONE: {
                        if (mEnabledPhone)
                            actionHandler.onPhoneClick(value);
                        break;
                    }
                    case Link.DOMAIN_NAME: {
                        if (mEnabledDomainName)
                            actionHandler.onDomainNameClick(value);
                        break;
                    }
                    case Link.IP_ADDRESS: {
                        if (mEnabledIpAddress)
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

        if (mEnabledHashtag)
            Linkify.addLinks(this, HASHTAG_PATTERN, LinkableMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        if (mEnabledMention)
            Linkify.addLinks(this, MENTION_PATTERN, LinkableMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        if (mEnabledEmailAddress)
            Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter);

        if (mEnabledPhone)
            Linkify.addLinks(this, Patterns.PHONE, null, null, filter);

        if (mEnabledWebUrl)
            Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);

        if (mEnabledDomainName)
            Linkify.addLinks(this, Patterns.DOMAIN_NAME, null, null, filter);

        if (mEnabledIpAddress)
            Linkify.addLinks(this, IP_ADDRESS_PATTERN, LinkableMovementMethod.SOCIAL_UI_IP_ADDRESS_SCHEME, null, filter);

        MovementMethod movementMethod = null;
        if (callback != null) {
            movementMethod = new LinkableMovementMethod(callback);
        }
        setMovementMethod(movementMethod);
    }
}
