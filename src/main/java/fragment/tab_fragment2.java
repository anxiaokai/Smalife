package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import googleplay.xiaokai.com.managelife.Movie_detail;
import googleplay.xiaokai.com.managelife.R;
import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/4/23.
 */
public class tab_fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    //implements SwipeRefreshLayout.OnRefreshListener
    private View vie;
    private WebView webView;
    private SwipeRefreshLayout swiperefreshmovie;
    private LinearLayout liner;
    WebSettings settings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vie = inflater.inflate(R.layout.fragmet2_layout_tab,container,false);
        return vie;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) vie.findViewById(R.id.fragment_layout2);
        liner = (LinearLayout) vie.findViewById(R.id.linermove);
        swiperefreshmovie = (SwipeRefreshLayout) vie.findViewById(R.id.swiperefreshmovie);
        settings = webView.getSettings();

        swiperefreshmovie.setOnRefreshListener(this);

        if(IsNetUtils.isNetworkConnected(getActivity())) {
            liner.setVisibility(View.GONE);
            //打开的网址
            webView.loadUrl("http://m.dianping.com/movie");
        }else{
            //弹出无网页面
            Toast.makeText(getActivity(), "没有网", Toast.LENGTH_SHORT).show();
            webView.setVisibility(View.GONE);
            liner.setVisibility(View.VISIBLE);
        }

        settings.setJavaScriptEnabled(true);


//                //支持缩放
//                 settings.setUseWideViewPort(true);//设定支持viewport
//                 settings.setLoadWithOverviewMode(true);
//                 settings.setBuiltInZoomControls(true);
//                 settings.setSupportZoom(true);//设定支持缩放

        webView.setWebViewClient(new webclient());
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
                        "document.getElementsByClassName('title')[0].style.display='none'; "+
                "})()");
    }

    @Override
    public void onRefresh() {
        //如果有网，就再加载一次
        if(IsNetUtils.isNetworkConnected(getActivity())) {
            liner.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            //打开的网址
            webView.loadUrl("http://m.dianping.com/movie");
        }else{
            webView.setVisibility(View.GONE);
            //弹出无网页面
            Toast.makeText(getActivity(), "没有网呦!", Toast.LENGTH_SHORT).show();
            liner.setVisibility(View.VISIBLE);
        }
        swiperefreshmovie.setRefreshing(false);
    }

    class webclient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(getActivity(), Movie_detail.class);
            intent.putExtra("weburlmove",url);
            startActivity(intent);
            return true;
        }
    }
}
