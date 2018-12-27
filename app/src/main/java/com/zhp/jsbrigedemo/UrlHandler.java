package com.zhp.jsbrigedemo;

import android.net.Uri;

public interface UrlHandler {

    String getScheme();

    String handle(Uri uri);
}