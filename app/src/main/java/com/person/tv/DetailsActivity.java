package com.person.tv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.*;

/**
 * @author chenming
 * @CREATE AT 2021/3/1 15:37
 * @Description:
 */
public class DetailsActivity  extends Activity {

    private String url_set = "";

    private OnKeyListener onKeyListener = new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_MENU&& event.getAction() == MotionEvent.ACTION_UP){
                assetsWrite("");
                Intent intent = new Intent();
                intent.putExtra("url_set",url_set);
                intent.setClassName("com.person.tv","com.person.tv.MainActivity");
                startActivity(intent);
                return true;
            }
            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_UP){
                System.exit(0);
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String url = this.getIntent().getExtras().getString("url");
        if(url == null || url.trim().length() == 0){
            setContentView(R.layout.activity_main);
            return;
        }
        Log.i(""+url,url);
        showWebView(url);
        url_set = url;
        assetsWrite(url_set);
    }

    private void showWebView(String url){
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.clearCache(true);
        webView.loadUrl(url);
        WebViewClient webViewClient= new WebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.setOnKeyListener(onKeyListener);
    }



    private void assetsWrite(String url_set) {
        String fileName = "tv_setting.txt";
        try {
            openFileOutput(fileName, Context.MODE_PRIVATE).write(url_set.getBytes());
        } catch (IOException e) {
            Log.e("assetsWrite error",e.getMessage());
        }
    }
}
