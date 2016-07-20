package googleplay.xiaokai.com.managelife;

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

import utils.IsNetUtils;

public class Traff1_detail extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private WebView webView;
    private WebSettings settings;
    private Toolbar toolbar;
    ActionBar supportActionBar;
    private SwipeRefreshLayout swipe;
    private LinearLayout liner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traff1_detail);
        webView = (WebView) findViewById(R.id.traffi1_webview);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        liner = (LinearLayout) findViewById(R.id.linertrafficdetail);
        swipe.setOnRefreshListener(this);
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        iniToolbar();
        settings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress>1){
                    injectJS(view);
                }
            }
        });
        settings.setJavaScriptEnabled(true);
        if(IsNetUtils.isNetworkConnected(this)){
            liner.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("http://m.tieyou.com/");
        }else{
            liner.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }

    }

    private void iniToolbar() {
        supportActionBar.setTitle("站到站");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if(webView.canGoBack()){
//            webView.goBack();
//        }
//    }
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

    private void injectJS(WebView webview) {
        webview.loadUrl("javascript:(function() " +
                "{ " +
                "document.getElementsByClassName('h48')[0].style.display='none'; " +
//                "document.getElementsByClassName('m-footer')[0].style.display='none';" +
//                "document.getElementsByClassName('m-page')[0].style.display='none';" +
                "})()");
    }

    @Override
    public void onRefresh() {
        if(IsNetUtils.isNetworkConnected(this)){
            liner.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("http://m.tieyou.com/");
        }else{
            liner.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
        swipe.setRefreshing(false);
    }
}
