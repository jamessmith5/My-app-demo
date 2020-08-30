package com.Nameless.earnmoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.earnmoney.R;


public class AboutActivity extends AppCompatActivity {

    WebView mywebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mywebview = findViewById(R.id.Youtube);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setWebViewClient(new MyWebViewClient());
        mywebview.loadUrl("https://www.youtube.com/channel/UCG_9wGnQDck4rWoVc8mGWRw?view_as=subscriber");
    }
    public class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.youtube.com")) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
           return true;
        }
    }
}
