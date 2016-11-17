package com.github.fobid.linkabletext.util;

import com.github.fobid.linkabletext.annotation.Link;

/**
 * Created by partner on 2016-11-17.
 */

public interface LinkableCallback {
    void onMatch(@Link int type, String value);
}
