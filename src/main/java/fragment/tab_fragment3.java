package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import googleplay.xiaokai.com.managelife.PE_webview;
import googleplay.xiaokai.com.managelife.R;
import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/4/23.
 */
public class tab_fragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view3;
    private WebView webVie;
    private WebSettings settings;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout liner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view3 = inflater.inflate(R.layout.fragmet3_layout_tab,container,false);
        return view3;
    }

//    private void initView() {
//
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webVie = (WebView) view3.findViewById(R.id.frag3_webview);
        swipeRefreshLayout = (SwipeRefreshLayout) view3.findViewById(R.id.peswiperefresh);
        liner = (LinearLayout) view3.findViewById(R.id.peliner);
        swipeRefreshLayout.setOnRefreshListener(this);


        if(IsNetUtils.isNetworkConnected(getActivity())) {
            liner.setVisibility(View.GONE);
            webVie.setVisibility(View.VISIBLE);
            //打开的网址
            webVie.loadUrl("https://m.hupu.com");
        }else{
            //弹出无网页面
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "没有网~欧欧欧~~~", Toast.LENGTH_SHORT).show();
            webVie.setVisibility(View.GONE);
            liner.setVisibility(View.VISIBLE);
        }
        settings = webVie.getSettings();
        settings.setJavaScriptEnabled(true);
        webVie.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(getActivity(), PE_webview.class);
                intent.putExtra("weburlpe",url);
                Log.i("flag",url);
                if(!"https://m.hupu.com/".equals(url)) {
                    startActivity(intent);
                }
                return true;
            }
        });
        webVie.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress>1){
                    injectJS(view);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onRefresh() {
        if(IsNetUtils.isNetworkConnected(getActivity())) {
            liner.setVisibility(View.GONE);
            webVie.setVisibility(View.VISIBLE);
            //打开的网址
            webVie.loadUrl("https://m.hupu.com");
        }else{
            //弹出无网页面
            Toast.makeText(getActivity(), "没有网~欧欧欧~~~", Toast.LENGTH_SHORT).show();
            webVie.setVisibility(View.GONE);
            liner.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

//    class webclien extends WebViewClient{
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Intent intent = new Intent(getActivity(), PE_webview.class);
//            intent.putExtra("weburlpe",url);
//            startActivity(intent);
//            return true;
//        }
//    }

    //注入代码，去掉标题栏
    private void injectJS(WebView webview) {
        webview.loadUrl("javascript:(function() " +
                "{ " +
                "document.getElementsByClassName('m-top-nav-station')[0].style.display='none'; " +
                "document.getElementsByClassName('m-row')[0].style.display='none'; " +
                "document.getElementsByClassName('m-top-nav')[0].style.display='none'; " +
                "document.getElementsByClassName('card-col-4')[6].style.display='none'; " +
                "document.getElementsByClassName('card-col-4')[5].style.display='none'; " +
                "document.getElementsByClassName('card-col-4')[4].style.display='none'; " +
                "var tabContents = document.getElementsByClassName('card-col-2'); " +
        "for (var i = 0; i < tabContents.length; i++) {"+
           " tabContents[i].style.display = 'none';"+
                "}})()");
    }
}
