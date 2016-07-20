package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class Splash_Activity extends AppCompatActivity implements Animation.AnimationListener {
    PackageInfo packageInfo;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        View view = findViewById(R.id.splash_layout);
        getVersionName();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(3000);
        view.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(this);
    }


    /*
        得到应用程序的版本名称
         */
    private String getVersionName() {
        String versionName = null;
        PackageManager pm = getPackageManager();
        try {
            packageInfo = pm.getPackageInfo("googleplay.xiaokai.com.managelife", 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(intent==null) {
            intent = new Intent(Splash_Activity.this, MainActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
