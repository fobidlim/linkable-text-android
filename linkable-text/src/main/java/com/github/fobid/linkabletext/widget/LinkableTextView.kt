package com.github.fobid.linkabletext.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.github.fobid.linkabletext.R
import java.util.regex.Pattern

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LinkableTextView(context: Context,
                       attrs: AttributeSet? = null,
                       defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

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
        MENTION_PATTERN = when (mentionPattern.isNotEmpty()) {
            true -> Pattern.compile(mentionPattern)
            else -> Pattern.compile("@([A-Za-z0-9_-]+)")
        }
    }

    fun setHashtagPattern(hashtagPattern: String) {
        HASHTAG_PATTERN = when (hashtagPattern.isNotEmpty()) {
            true -> Pattern.compile(hashtagPattern)
            else -> Pattern.compile("#(\\w+)")
        }
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

    companion object {
        private lateinit var MENTION_PATTERN: Pattern
        private lateinit var HASHTAG_PATTERN: Pattern
    }
}