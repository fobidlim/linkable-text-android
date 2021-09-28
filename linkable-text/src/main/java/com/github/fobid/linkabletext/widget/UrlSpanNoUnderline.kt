package com.github.fobid.linkabletext.widget

import android.text.TextPaint
import android.text.style.URLSpan

internal class URLSpanNoUnderline(
    url: String
) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}