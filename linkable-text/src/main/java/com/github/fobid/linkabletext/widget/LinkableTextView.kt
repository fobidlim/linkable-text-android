package com.github.fobid.linkabletext.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.method.MovementMethod
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatTextView
import com.github.fobid.linkabletext.R
import com.github.fobid.linkabletext.text.method.LinkableMovementMethod
import com.github.fobid.linkabletext.view.LinkableCallback
import com.github.fobid.linkabletext.view.OnLinkClickListener
import java.util.regex.Pattern

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LinkableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    object Link {
        const val HASH_TAG = 0x1
        const val MENTION = 0x2
        const val EMAIL_ADDRESS = 0x3
        const val PHONE = 0x4
        const val WEB_URL = 0x5
        const val IP_ADDRESS = 0x6
    }

    private var enabledLinks = true
    private var enabledHashtag = true
    private var enabledMention = true
    private var enabledEmailAddress = true
    private var enabledPhone = true
    private var enabledWebUrl = true
    private var enabledIpAddress = true

    private var enabledUnderlines = true
    private var enabledHashtagUnderline = true
    private var enabledMentionUnderline = true
    private var enabledEmailAddressUnderline = true
    private var enabledPhoneUnderline = true
    private var enabledWebUrlUnderline = true
    private var enabledIpAddressUnderline = true

    init {
        var mentionPattern = ""
        var hashtagPattern = ""

        context.obtainStyledAttributes(attrs, R.styleable.LinkableTextView)
            .also {
                for (i in 0 until it.indexCount) {

                    when (val attr = it.getIndex(i)) {
                        R.styleable.LinkableTextView_pattern_mention ->
                            mentionPattern = it.getString(attr) ?: ""
                        R.styleable.LinkableTextView_pattern_hashtag ->
                            hashtagPattern = it.getString(attr) ?: ""
                        R.styleable.LinkableTextView_enabledLinks ->
                            enabledLinks = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledHashtag ->
                            enabledHashtag = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledMention ->
                            enabledMention = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledEmail_address ->
                            enabledEmailAddress = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledPhone ->
                            enabledPhone = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledPhone ->
                            enabledPhone = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledWeb_url ->
                            enabledWebUrl = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledIp_address ->
                            enabledIpAddress = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledUnderlines ->
                            enabledUnderlines = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledHashtagUnderline ->
                            enabledHashtagUnderline = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledMentionUnderline ->
                            enabledMentionUnderline = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledEmail_addressUnderline ->
                            enabledEmailAddressUnderline = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledPhoneUnderline ->
                            enabledPhoneUnderline = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledWeb_urlUnderline ->
                            enabledWebUrlUnderline = it.getBoolean(attr, true)
                        R.styleable.LinkableTextView_enabledIp_addressUnderline ->
                            enabledIpAddressUnderline = it.getBoolean(attr, true)
                    }
                }

                setMentionPattern(mentionPattern)
                setHashtagPattern(hashtagPattern)

                setEnabledLinks(enabledLinks)
                setEnabledHashtag(enabledHashtag)
                setEnabledMention(enabledMention)
                setEnabledEmailAddress(enabledEmailAddress)
                setEnabledPhone(enabledPhone)
                setEnabledWebUrl(enabledWebUrl)
                setEnabledIpAddress(enabledIpAddress)

                setEnabledUnderlines(enabledUnderlines)
                setEnabledHashtagUnderline(enabledHashtagUnderline)
                setEnabledMentionUnderline(enabledMentionUnderline)
                setEnabledEmailAddressUnderline(enabledEmailAddressUnderline)
                setEnabledPhoneUnderline(enabledPhoneUnderline)
                setEnabledWebUrlUnderline(enabledWebUrlUnderline)
                setEnabledIpAddressUnderline(enabledIpAddressUnderline)
            }.apply {
                recycle()
            }
    }

    fun setMentionPattern(mentionPattern: String) {
        MENTION_PATTERN = Pattern.compile(
            when (mentionPattern.isNotEmpty()) {
                true -> mentionPattern
                else -> "@([A-Za-z0-9_-]+)"
            }
        )
    }

    fun setHashtagPattern(hashtagPattern: String) {
        HASHTAG_PATTERN = Pattern.compile(
            when (hashtagPattern.isNotEmpty()) {
                true -> hashtagPattern
                else -> "#(\\w+)"
            }
        )
    }

    fun setEnabledLinks(enabledLinks: Boolean) {
        this.enabledLinks = enabledLinks
    }

    fun setEnabledHashtag(enabledHashtag: Boolean) {
        this.enabledHashtag = enabledHashtag
    }

    fun setEnabledMention(enabledMention: Boolean) {
        this.enabledMention = enabledMention
    }

    fun setEnabledEmailAddress(enabledEmailAddress: Boolean) {
        this.enabledEmailAddress = enabledEmailAddress
    }

    fun setEnabledPhone(enabledPhone: Boolean) {
        this.enabledPhone = enabledPhone
    }

    fun setEnabledWebUrl(enabledWebUrl: Boolean) {
        this.enabledWebUrl = enabledWebUrl
    }

    fun setEnabledIpAddress(enabledIpAddress: Boolean) {
        this.enabledIpAddress = enabledIpAddress
    }

    fun setEnabledUnderlines(enabledUnderlines: Boolean) {
        this.enabledUnderlines = enabledUnderlines
    }

    fun setEnabledHashtagUnderline(enabledHashtagUnderline: Boolean) {
        this.enabledHashtagUnderline = enabledHashtagUnderline
    }

    fun setEnabledMentionUnderline(enabledMentionUnderline: Boolean) {
        this.enabledMentionUnderline = enabledMentionUnderline
    }

    fun setEnabledEmailAddressUnderline(enabledEmailAddressUnderline: Boolean) {
        this.enabledEmailAddressUnderline = enabledEmailAddressUnderline
    }

    fun setEnabledPhoneUnderline(enabledPhoneUnderline: Boolean) {
        this.enabledPhoneUnderline = enabledPhoneUnderline
    }

    fun setEnabledWebUrlUnderline(enabledWebUrlUnderline: Boolean) {
        this.enabledWebUrlUnderline = enabledWebUrlUnderline
    }

    fun setEnabledIpAddressUnderline(enabledIpAddressUnderline: Boolean) {
        this.enabledIpAddressUnderline = enabledIpAddressUnderline
    }

    fun setOnLinkClickListener(listener: OnLinkClickListener?) =
        setOnLinkClickListener(object : LinkableCallback {
            override fun onMatch(type: Int, value: String) {
                listener?.let {
                    if (!enabledLinks) {
                        return
                    }

                    when (type) {
                        Link.HASH_TAG ->
                            if (enabledHashtag) {
                                listener.onHashtagClick(value)
                            }
                        Link.MENTION ->
                            if (enabledMention) {
                                listener.onMentionClick(value)
                            }
                        Link.EMAIL_ADDRESS ->
                            if (enabledEmailAddress) {
                                listener.onEmailAddressClick(value)
                            }
                        Link.IP_ADDRESS ->
                            if (enabledIpAddress) {
                                listener.onWebUrlClick(value)
                            }
                        Link.WEB_URL ->
                            if (enabledWebUrl) {
                                listener.onWebUrlClick(value)
                            }
                        Link.PHONE ->
                            if (enabledPhone) {
                                listener.onPhoneClick(value)
                            }
                    }
                }
            }
        })

    fun setOnLinkClickListener(callback: LinkableCallback?) {
        val filter = Linkify.TransformFilter { match, _ -> match.group() }

        if (enabledLinks) {
            if (enabledUnderlines) {
                if (enabledHashtag) {
                    Linkify.addLinks(
                        this,
                        HASHTAG_PATTERN,
                        LinkableMovementMethod.LINKABLE_HASHTAG_SCHEME,
                        null,
                        filter
                    )

                    if (!enabledHashtagUnderline)
                        stripUnderlines()
                }

                if (enabledMention) {
                    Linkify.addLinks(
                        this,
                        MENTION_PATTERN,
                        LinkableMovementMethod.LINKABLE_MENTION_SCHEME,
                        null,
                        filter
                    )

                    if (!enabledMentionUnderline)
                        stripUnderlines()
                }

                if (enabledEmailAddress) {
                    Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter)

                    if (!enabledEmailAddressUnderline)
                        stripUnderlines()
                }

                if (enabledPhone) {
                    Linkify.addLinks(this, Patterns.PHONE, null, null, filter)

                    if (!enabledPhoneUnderline)
                        stripUnderlines()
                }

                if (enabledWebUrl) {
                    Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter)

                    if (!enabledWebUrlUnderline)
                        stripUnderlines()
                }

                if (enabledIpAddress) {
                    Linkify.addLinks(
                        this,
                        IP_ADDRESS_PATTERN,
                        LinkableMovementMethod.LINKABLE_IP_ADDRESS_SCHEME,
                        null,
                        filter
                    )

                    if (!enabledIpAddressUnderline)
                        stripUnderlines()
                }
            } else {
                if (enabledHashtag)
                    Linkify.addLinks(
                        this,
                        HASHTAG_PATTERN,
                        LinkableMovementMethod.LINKABLE_HASHTAG_SCHEME,
                        null,
                        filter
                    )

                if (enabledMention)
                    Linkify.addLinks(
                        this,
                        MENTION_PATTERN,
                        LinkableMovementMethod.LINKABLE_MENTION_SCHEME,
                        null,
                        filter
                    )

                if (enabledEmailAddress)
                    Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter)

                if (enabledPhone)
                    Linkify.addLinks(this, Patterns.PHONE, null, null, filter)

                if (enabledWebUrl)
                    Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter)

                if (enabledIpAddress)
                    Linkify.addLinks(
                        this,
                        IP_ADDRESS_PATTERN,
                        LinkableMovementMethod.LINKABLE_IP_ADDRESS_SCHEME,
                        null,
                        filter
                    )

                stripUnderlines()
            }
        }

        var movementMethod: MovementMethod? = null
        if (callback != null) {
            movementMethod = LinkableMovementMethod(callback)
        }
        setMovementMethod(movementMethod)
    }

    private fun stripUnderlines() {
        val spannableString = SpannableString(text)

        spannableString.getSpans(0, spannableString.length, URLSpan::class.java).let { spans ->
            for (span in spans) {
                val start = spannableString.getSpanStart(span)
                val end = spannableString.getSpanEnd(span)
                spannableString.removeSpan(span)
                spannableString.setSpan(URLSpanNoUnderline(span.url), start, end, 0)
            }
        }

        text = spannableString
    }

    companion object {
        private val IP_ADDRESS_PATTERN: Pattern =
            Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")
        private lateinit var MENTION_PATTERN: Pattern
        private lateinit var HASHTAG_PATTERN: Pattern
    }
}