package com.github.fobid.linkabletext.view;

import com.github.fobid.linkabletext.annotation.LinkType;

/**
 * Created by partner on 2016-11-17.
 */

public interface LinkableCallback {
    void onMatch(@LinkType int type, String value);
}
