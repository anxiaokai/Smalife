package googleplay.xiaokai.com.managelife;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {
    private WebView webview;
    private WebSettings settings;
    private ProgressBar bar;
    private Toolbar toolbar;
    private Intent inten;
    private String url;
    private String content;
    ActionBar supportActionBar;
    ShareActionProvider actionProvider;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        setToolBar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.action_share);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        actionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        inten = new Intent(Intent.ACTION_SEND);
        inten.setType("text/plain");
        inten.putExtra(Intent.EXTRA_SUBJECT,"小生活分享");
        inten.putExtra(Intent.EXTRA_TEXT,url+content);
        return inten;
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("新闻");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
    }

    private void initView() {
        //得到url
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        content = intent.getStringExtra("content");
        //得到webview并进行设置
        webview = (WebView) findViewById(R.id.webview);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);

        //改变进度条颜色
        bar.getProgressDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        //网页适应手机屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);

        //使自己的webkit处理url
        webview.setWebViewClient(new WebViewClient());

        //处理进度条
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        webview.loadUrl(url);
    }
}
