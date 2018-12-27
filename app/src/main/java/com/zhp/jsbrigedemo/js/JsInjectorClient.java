package com.zhp.jsbrigedemo.js;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.Log;
import com.zhp.jsbrigedemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsInjectorClient {

    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String DEFAULT_MIME_TYPE = "text/html";
    private final static String JS_TAG_TEMPLATE = "<script type=\"text/javascript\">%1$s%2$s</script>";

    private final Context context;

    private String jsLibrary;

    public JsInjectorClient(Context context) {
        this.context = context;
    }

    public String assembleJs(Context context, String template) {
        if (TextUtils.isEmpty(jsLibrary)) {
            jsLibrary = loadFile(context, R.raw.tronweb);
        }
        String initJs = loadInitJs(context);
        return String.format(template, jsLibrary, initJs);

    }

    String injectJS(String html) {
        String js = assembleJs(context, JS_TAG_TEMPLATE);
        return injectJS(html, js);
    }

    private String injectJS(String html, String js) {
        if (TextUtils.isEmpty(html)) {
            return html;
        }
        int position = getInjectionPosition(html);
        if (position > 0) {
            String beforeTag = html.substring(0, position);
            String afterTab = html.substring(position);
            return beforeTag + js + afterTab;
        }
        return html;
    }

    private int getInjectionPosition(String body) {
        body = body.toLowerCase();
        int ieDetectTagIndex = body.indexOf("<!--[if");
        int scriptTagIndex = body.indexOf("<script");

        int index;
        if (ieDetectTagIndex < 0) {
            index = scriptTagIndex;
        } else {
            index = Math.min(scriptTagIndex, ieDetectTagIndex);
        }
        if (index < 0) {
            index = body.indexOf("</head");
        }
        return index;
    }

    private String loadInitJs(Context context) {
        return loadFile(context, R.raw.hook);
    }

    private String loadFile(Context context, @RawRes int rawRes) {
        byte[] buffer = new byte[0];
        try {
            InputStream in = context.getResources().openRawResource(rawRes);
            buffer = new byte[in.available()];
            int len = in.read(buffer);
            if (len < 1) {
                throw new IOException("Nothing is read.");
            }
        } catch (Exception ex) {
            Log.d("READ_JS_TAG", "Ex", ex);
        }
        return new String(buffer);
    }

    private String getMimeType(String contentType) {
        Matcher regexResult = Pattern.compile("^.*(?=;)").matcher(contentType);
        if (regexResult.find()) {
            return regexResult.group();
        }
        return DEFAULT_MIME_TYPE;
    }

    private String getCharset(String contentType) {
        Matcher regexResult = Pattern.compile("charset=([a-zA-Z0-9-]+)").matcher(contentType);
        if (regexResult.find()) {
            if (regexResult.groupCount() >= 2) {
                return regexResult.group(1);
            }
        }
        return DEFAULT_CHARSET;
    }

}
