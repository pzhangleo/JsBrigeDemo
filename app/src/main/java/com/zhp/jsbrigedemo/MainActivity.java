package com.zhp.jsbrigedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Base64;
import android.util.Log;
import android.webkit.*;
import com.zhp.jsbrigedemo.js.JsInjectorClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);

        initWebView(webView);

        webView.loadUrl("https://tron.game.com");
    }

    private void initWebView(WebView webView) {
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

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectScriptFile(view);
            }
        });
    }

    private void injectScriptFile(final WebView view) {
        String js = new JsInjectorClient(getApplicationContext()).assembleJs(view.getContext(), "%1$s%2$s");
        byte[] buffer = js.getBytes();
        final String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);

        view.post(new Runnable() {
            @Override
            public void run() {
                view.evaluateJavascript("javascript:(function() {" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var script = document.createElement('script');" +
                        "script.type = 'text/javascript';" +
                        // Tell the browser to BASE64-decode the string into your script !!!
                        "script.innerHTML = window.atob('" + encoded + "');" +
                        "parent.appendChild(script)" +
                        "})()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d("INJECT", value);
                    }
                });
            }
        });
    }
}
