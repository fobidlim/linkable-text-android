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
        if (widget == null || buffer == null || event == null) {
            return super.onTouchEvent(widget, buffer, event)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            var x = event.x.toInt()
            var y = event.y.toInt()

            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop

            x += widget.scrollX
            y += widget.scrollY

            val layout = widget.layout
            val line = layout?.getLineForVertical(y) ?: 0
            val off = layout?.getOffsetForHorizontal(line, x.toFloat()) ?: 0

            val link = buffer.getSpans(off, off, URLSpan::class.java)
            if (link.isNotEmpty()) {
                link.firstOrNull()?.url?.let { url ->
                    handleLink(url)
                }

                // Remove selected background
                Selection.removeSelection(buffer)
                return true
            }
        }

        return super.onTouchEvent(widget, buffer, event)
    }

    private fun handleLink(link: String) {
        when {
            link.startsWith(LINKABLE_HASHTAG_SCHEME) ->
                link.replaceFirst(LINKABLE_HASHTAG_SCHEME, "")
                    .replaceFirst(".*#".toRegex(), "")
                    .let { hashtag ->
                        linkableCallback?.onMatch(LinkableTextView.Link.HASH_TAG, hashtag)
                    }

            link.startsWith(LINKABLE_MENTION_SCHEME) ->
                link.replaceFirst(LINKABLE_MENTION_SCHEME, "")
                    .replaceFirst(".*@".toRegex(), "")
                    .let { mention ->
                        linkableCallback?.onMatch(LinkableTextView.Link.MENTION, mention)
                    }

            link.startsWith(LINKABLE_IP_ADDRESS_SCHEME) ->
                link.replaceFirst(LINKABLE_IP_ADDRESS_SCHEME, "")
                    .replaceFirst(".".toRegex(), "")
                    .let { ip ->
                        linkableCallback?.onMatch(LinkableTextView.Link.IP_ADDRESS, ip)
                    }

            Patterns.EMAIL_ADDRESS.matcher(link).matches() ->
                linkableCallback?.onMatch(LinkableTextView.Link.EMAIL_ADDRESS, link)

            Patterns.IP_ADDRESS.matcher(link).matches()
                    or Patterns.DOMAIN_NAME.matcher(link).matches()
                    or Patterns.WEB_URL.matcher(link).matches() ->
                linkableCallback?.onMatch(LinkableTextView.Link.WEB_URL, link)

            Patterns.PHONE.matcher(link).matches() ->
                linkableCallback?.onMatch(LinkableTextView.Link.PHONE, link)
        }
    }

    companion object {
        private const val LINKABLE_BASE_SCHEME = "https://github.com/fobidlim/linkable-text-android"
        const val LINKABLE_HASHTAG_SCHEME = "$LINKABLE_BASE_SCHEME/hashtag"
        const val LINKABLE_MENTION_SCHEME = "$LINKABLE_BASE_SCHEME/mention"
        const val LINKABLE_IP_ADDRESS_SCHEME = "$LINKABLE_BASE_SCHEME/ip"
    }
}