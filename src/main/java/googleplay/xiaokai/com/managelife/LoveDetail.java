package googleplay.xiaokai.com.managelife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoveDetail extends AppCompatActivity {
private TextView textView;
    private String str = "我爱你";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_detail);
        textView = (TextView) findViewById(R.id.textlove);
        textView.setText(str);

    }
}
