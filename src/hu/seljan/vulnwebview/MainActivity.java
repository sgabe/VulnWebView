package hu.seljan.vulnwebview;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.webkit.WebView;
import android.widget.EditText;
import android.net.http.SslError;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.SslErrorHandler;
import android.annotation.SuppressLint;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView myWebView = (WebView) findViewById(R.id.webView);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str.length() > 0) {
                    if (str.startsWith("http")) {
                        myWebView.loadUrl(str);
                    } else {
                        // not a good idea!
                        String data = "<script>" + str + "</script>";
                        myWebView.loadData(data, "text/html", null);
                    }
                } else {
                    myWebView.loadUrl("file:///android_asset/vulnwebview.html");
                }
            }
        });

        // also not a good idea!
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // terrible idea!
        myWebView.addJavascriptInterface(new WebAppInterface(this), "android");
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // horrible idea!
                handler.proceed();
            }
        });

        myWebView.loadUrl("file:///android_asset/vulnwebview.html");
    }
}
