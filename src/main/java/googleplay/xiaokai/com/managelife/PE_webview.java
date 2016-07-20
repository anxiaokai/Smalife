package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/5/26.
 */
public class PE_webview extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Intent intent;
    private String weburpe;
    private WebView webView;
    private Toolbar toolbar;
    private ActionBar supportActionBar;
    private WebSettings settings;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout liner;
    private ShareActionProvider actionProvider;
    private Intent inten;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pe_webview);
        intent = getIntent();
        webView = (WebView) findViewById(R.id.pe_webview);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.peswiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        liner = (LinearLayout) findViewById(R.id.linepe);
        setSupportActionBar(toolbar);
        weburpe = intent.getStringExtra("weburlpe");


        webView.setWebViewClient(new WebViewClient());
        //如果有网，就再加载一次
        if(IsNetUtils.isNetworkConnected(this)) {
            liner.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            //打开的网址
            webView.loadUrl(weburpe);
        }else{
            webView.setVisibility(View.GONE);
            //弹出无网页面
            Toast.makeText(this, "没有网呦!", Toast.LENGTH_SHORT).show();
            liner.setVisibility(View.VISIBLE);
        }
        settings = webView.getSettings();
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

        supportActionBar = getSupportActionBar();

        supportActionBar.setTitle("体育");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    //注入代码，去掉标题栏
    private void injectJS(WebView webview) {
        webview.loadUrl("javascript:(function() " +
                "{ " +
                "document.getElementsByClassName('m-detail-top-station')[0].style.display='none'; " +
                "document.getElementsByClassName('m-top-nav-station')[0].style.display='none'; " +
                "document.getElementsByClassName('m-row m-footer-row')[0].style.display='none'; " +
                "document.getElementsByClassName('m-top-bar')[0].style.display='none'; " +
                "})()");
    }

    @Override
    public void onRefresh() {
        //如果有网，就再加载一次
        if(IsNetUtils.isNetworkConnected(this)) {
            liner.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            //打开的网址
            webView.loadUrl(weburpe);
        }else{
            webView.setVisibility(View.GONE);
            //弹出无网页面
            Toast.makeText(this, "没有网,啥也刷不出来!", Toast.LENGTH_SHORT).show();
            liner.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pemain,menu);
        MenuItem item = menu.findItem(R.id.pe_menu_item);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        actionProvider.setShareIntent(getDefaultIntent());
        item.setIcon(R.drawable.share);
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        inten = new Intent(Intent.ACTION_SEND);
        inten.setType("text/plain");
        inten.putExtra(Intent.EXTRA_SUBJECT,"小生活分享");
        inten.putExtra(Intent.EXTRA_TEXT,weburpe+"来自小生活的体育新闻!");
        return inten;
    }
}
