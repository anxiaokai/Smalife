package googleplay.xiaokai.com.managelife;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class About extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar supportActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);

        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("关于");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
