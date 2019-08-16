package com.github.fobid.linkabletext.text.method

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.Patterns
import android.view.MotionEvent
import android.widget.TextView
import com.github.fobid.linkabletext.view.LinkableCallback
import com.github.fobid.linkabletext.widget.LinkableTextView

class LinkableMovementMethod(
        private var linkableCallback: LinkableCallback? = null
) : LinkMovementMethod() {

    init {
        if (linkableCallback == null) {
            linkableCallback = object : LinkableCallback {
                override fun onMatch(type: Int, value: String) {
                }
            }
        }
    }

    override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            var x = event.x.toInt()
            var y = event.y.toInt()

            x -= widget?.totalPaddingLeft ?: 0
            y -= widget?.totalPaddingTop ?: 0

            x += widget?.scrollX ?: 0
            y += widget?.scrollY ?: 0

            val layout = widget?.layout
            val line = layout?.getLineForVertical(y) ?: 0
            val off = layout?.getOffsetForHorizontal(line, x.toFloat()) ?: 0

            val link = buffer?.getSpans(off, off, URLSpan::class.java)
            link?.let {
                if (link.isNotEmpty()) {
                    val url = link[0].url
                    handleLink(url)

                    // Remove selected background
                    Selection.removeSelection(buffer)
                    return true
                }
            }
        }

        return super.onTouchEvent(widget, buffer, event)
    }

    private fun handleLink(link: String) {
        when {
            link.startsWith(LINKABLE_HASHTAG_SCHEME) -> {
                var hashtag = link.replaceFirst(LINKABLE_HASHTAG_SCHEME.toRegex(), "")
                hashtag = hashtag.replaceFirst(".*#".toRegex(), "")
                linkableCallback?.onMatch(LinkableTextView.Link.HASH_TAG, hashtag)
            }
            link.startsWith(LINKABLE_MENTION_SCHEME) -> {
                var mention = link.replaceFirst(LINKABLE_MENTION_SCHEME.toRegex(), "")
                mention = mention.replaceFirst(".*@".toRegex(), "")
                linkableCallback?.onMatch(LinkableTextView.Link.MENTION, mention)
            }
            link.startsWith(LINKABLE_IP_ADDRESS_SCHEME) -> {
                var ip = link.replaceFirst(LINKABLE_IP_ADDRESS_SCHEME.toRegex(), "")
                ip = ip.replaceFirst(".".toRegex(), "")
                linkableCallback?.onMatch(LinkableTextView.Link.IP_ADDRESS, ip)
            }
            Patterns.EMAIL_ADDRESS.matcher(link).matches() -> {
                linkableCallback?.onMatch(LinkableTextView.Link.EMAIL_ADDRESS, link)
            }
            Patterns.IP_ADDRESS.matcher(link).matches()
                    or Patterns.DOMAIN_NAME.matcher(link).matches()
                    or Patterns.WEB_URL.matcher(link).matches() -> {
                linkableCallback?.onMatch(LinkableTextView.Link.WEB_URL, link)
            }
            Patterns.PHONE.matcher(link).matches() -> {
                linkableCallback?.onMatch(LinkableTextView.Link.PHONE, link)
            }
        }
    }

    companion object {
        private val LINKABLE_BASE_SCHEME = "https://github.com/fobidlim/linkable-text-android"
        val LINKABLE_HASHTAG_SCHEME = "$LINKABLE_BASE_SCHEME/hashtag"
        val LINKABLE_MENTION_SCHEME = "$LINKABLE_BASE_SCHEME/mention"
        val LINKABLE_IP_ADDRESS_SCHEME = "$LINKABLE_BASE_SCHEME/ip"
    }
}