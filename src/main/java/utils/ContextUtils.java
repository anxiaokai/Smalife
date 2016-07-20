package utils;

import android.app.Application;

/**
 * Created by 孙晓凯 on 2016/4/20.
 */
public class ContextUtils extends Application{
    /*
    任意地方都可以获取context
     */
    public static ContextUtils instance;
    public static ContextUtils getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
