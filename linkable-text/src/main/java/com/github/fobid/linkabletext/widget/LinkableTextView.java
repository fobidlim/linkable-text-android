package com.github.fobid.linkabletext.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.TextView;

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
        int EMAIL = 0x3;
        int PHONE = 0x4;
        int URL = 0x5;
        int DOMAIN_NAME = 0x6;
        int IP_ADDRESS = 0x7;
    }

    private static final Pattern MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_-]+)");
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_-]+)");
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public LinkableTextView(Context context) {
        super(context);
    }

    public LinkableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addLinks(final OnLinkableClickListener actionHandler) {
        addLinks(new LinkableCallback() {
            @Override
            public void onMatch(@com.github.fobid.linkabletext.annotation.Link int type, String value) {
                switch (type) {
                    case Link.HASH_TAG: {
                        actionHandler.onHashtagClick(value);
                        break;
                    }
                    case Link.MENTION: {
                        actionHandler.onMentionClick(value);
                        break;
                    }
                    case Link.EMAIL: {
                        actionHandler.onEmailClick(value);
                        break;
                    }
                    case Link.URL: {
                        actionHandler.onUrlClick(value);
                        break;
                    }
                    case Link.PHONE: {
                        actionHandler.onPhoneClick(value);
                        break;
                    }
                    case Link.DOMAIN_NAME: {
                        actionHandler.onDomainNameClick(value);
                        break;
                    }
                    case Link.IP_ADDRESS: {
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

        Linkify.addLinks(this, HASHTAG_PATTERN, LinkableMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        Linkify.addLinks(this, MENTION_PATTERN, LinkableMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter);

        Linkify.addLinks(this, Patterns.PHONE, null, null, filter);

        Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);

        Linkify.addLinks(this, Patterns.DOMAIN_NAME, null, null, filter);

        Linkify.addLinks(this, IP_ADDRESS_PATTERN, LinkableMovementMethod.SOCIAL_UI_IP_ADDRESS_SCHEME, null, filter);

        MovementMethod movementMethod = null;
        if (callback != null) {
            movementMethod = new LinkableMovementMethod(callback);
        }
        setMovementMethod(movementMethod);
    }
}
