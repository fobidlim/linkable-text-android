package com.github.fobid.linkabletext.annotation

import com.github.fobid.linkabletext.widget.LinkableTextView

@LinkTypeDef(
    LinkableTextView.Link.HASH_TAG,
    LinkableTextView.Link.MENTION,
    LinkableTextView.Link.EMAIL_ADDRESS,
    LinkableTextView.Link.PHONE,
    LinkableTextView.Link.WEB_URL,
    LinkableTextView.Link.IP_ADDRESS
)
@Retention(AnnotationRetention.SOURCE)
annotation class LinkType