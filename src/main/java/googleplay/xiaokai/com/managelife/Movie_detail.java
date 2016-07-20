package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import utils.IsNetUtils;

public class Movie_detail extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    WebView webView;
    Intent intent;
    WebSettings settings;
    Toolbar toolbar;
    private String weburmove;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linerar;
    private ActionBar supportActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        intent = getIntent();
        webView = (WebView) findViewById(R.id.movie_detail);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipemovedetail);
        linerar = (LinearLayout) findViewById(R.id.linermovedetail);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("电影");
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(this);

        settings = webView.getSettings();
        weburmove = intent.getStringExtra("weburlmove");
        webView.setWebViewClient(new WebViewClient());
        if(IsNetUtils.isNetworkConnected(this)){
            linerar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(weburmove);
        }else{
            linerar.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }


        settings.setJavaScriptEnabled(true);


        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress>1){
                    injectJS(view);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    //注入代码，去掉标题栏
    private void injectJS(WebView webview) {
        webview.loadUrl("javascript:(function() " +
                "{ " +
                "document.getElementsByClassName('back')[0].style.display='none'; " +
                "document.getElementsByClassName('account')[0].style.display='none'; "+
                "})()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        if(IsNetUtils.isNetworkConnected(this)){
            linerar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(weburmove);
        }else{
            linerar.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Toast.makeText(this, "纵使刷我千百遍，依然带你入初恋!", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
