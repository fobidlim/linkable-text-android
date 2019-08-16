package com.github.fobid.linkabletext.view

interface OnLinkClickListener {

    fun onHashtagClick(hashtag: String)

    fun onMentionClick(mention: String)

    fun onEmailAddressClick(email: String)

    fun onWebUrlClick(url: String)

    fun onPhoneClick(phone: String)
}