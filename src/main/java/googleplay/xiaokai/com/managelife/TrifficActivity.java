package googleplay.xiaokai.com.managelife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fragment.traffic_fragment1;
import fragment.traffic_fragment2;
import fragment.traffic_fragment3;

/**
 * Created by 孙晓凯 on 2016/4/25.
 */
public class TrifficActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolBar;
    private ActionBar actionBar;
    private ViewPager vp;
    private traffic_fragment1 fra1;
    private traffic_fragment2 fra2;
    private traffic_fragment3 fra3;
    private ArrayList<Fragment> list;
    private FragmentStatePagerAdapter fradpter;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private ImageView tabline;
    private int mScreen1_3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triffic_main_layout);
        toolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolBar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
        initTabLine();
    }
    private void initTabLine() {
        tabline = (ImageView) findViewById(R.id.tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScreen1_3 = metrics.widthPixels/3;
        ViewGroup.LayoutParams layoutParams = tabline.getLayoutParams();
        layoutParams.width = mScreen1_3;
        tabline.setLayoutParams(layoutParams);
    }

    private void init() {
        vp = (ViewPager)findViewById(R.id.viewpager);
        text1 = (TextView) findViewById(R.id.chat);
        text2 = (TextView) findViewById(R.id.reseach);
        text3 = (TextView) findViewById(R.id.contact);
        actionBar.setTitle("交通");
        text1.setText("火车");
        text2.setText("汽车");
        text3.setText("路线");
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text1.setTextColor(getResources().getColor(R.color.listback));
        list = new ArrayList<Fragment>();
        fra1 = new traffic_fragment1();
        fra2 = new traffic_fragment2();
        fra3 = new traffic_fragment3();
        list.add(fra1);
        list.add(fra2);
        list.add(fra3);

        fradpter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        vp.setAdapter(fradpter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabline.getLayoutParams();
                layoutParams.leftMargin = (int) ((position + positionOffset) * mScreen1_3);
                tabline.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                resetcolor();
                switch (position) {
                    case 0:
                        text1.setTextColor(getResources().getColor(R.color.listback));
                        break;
                    case 1:
                        text2.setTextColor(getResources().getColor(R.color.listback));
                        break;
                    case 2:
                        text3.setTextColor(getResources().getColor(R.color.listback));
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

    }
    private void resetcolor() {
        text1.setTextColor(getResources().getColor(R.color.balck));
        text2.setTextColor(getResources().getColor(R.color.balck));
        text3.setTextColor(getResources().getColor(R.color.balck));
    }
    public void tabclic(View v){
        switch (v.getId()){
            case R.id.chat:
                vp.setCurrentItem(0);
                break;
            case R.id.reseach:
                vp.setCurrentItem(1);
                break;
            case R.id.contact:
                vp.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        tabclic(v);
    }
}
