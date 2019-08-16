package com.github.fobid.linkabletext.view

import com.github.fobid.linkabletext.annotation.LinkType

interface LinkableCallback {

    fun onMatch(@LinkType type: Int, value: String)
}