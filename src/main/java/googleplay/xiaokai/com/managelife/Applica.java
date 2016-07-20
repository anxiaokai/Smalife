package googleplay.xiaokai.com.managelife;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 孙晓凯 on 2016/5/5.
 */
public class Applica extends Application {
    public static RequestQueue queue;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        queue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getSharedPreferences("first",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        edit.putBoolean("first",true);
        edit.commit();
    }

    public static RequestQueue getRequestQueue(){
        return queue;
    }
}
