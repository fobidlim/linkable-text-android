package com.github.fobid.linkabletext.annotation;

import com.github.fobid.linkabletext.widget.LinkableTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by partner on 2016-11-17.
 */

@LinkTypeDef({
        LinkableTextView.Link.HASH_TAG,
        LinkableTextView.Link.MENTION,
        LinkableTextView.Link.EMAIL_ADDRESS,
        LinkableTextView.Link.PHONE,
        LinkableTextView.Link.WEB_URL,
        LinkableTextView.Link.IP_ADDRESS
})
@Retention(RetentionPolicy.SOURCE)
public @interface LinkType {
}
