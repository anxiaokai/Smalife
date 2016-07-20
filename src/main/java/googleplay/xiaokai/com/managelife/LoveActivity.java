package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hanks.htextview.HTextView;

import java.util.Timer;
import java.util.TimerTask;

public class LoveActivity extends AppCompatActivity {
    HTextView hTextView;
    Timer timer;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        intent = new Intent(this,LoveDetail.class);
        TimerTask task = new TimerTask(){
            public void run(){
                // 在此处添加执行的代码
            startActivity(intent);
            }
        };

        timer = new Timer();
        timer.schedule(task, 10000);//开启定时器，delay 1s后执行task
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
