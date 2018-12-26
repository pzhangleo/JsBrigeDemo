package com.zhp.jsbrigedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.webkit.*;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);
        WebSettings localWebSettings = webView.getSettings();
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(false);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setAppCacheEnabled(true);
        localWebSettings.setAppCachePath(new File(getCacheDir(), "webview").getPath());
        localWebSettings.setDatabaseEnabled(true);
        localWebSettings.setDefaultTextEncodingName("utf-8");
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }
        });

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://tron.game.com");
    }
}
