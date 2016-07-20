package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import googleplay.xiaokai.com.managelife.R;

/**
 * Created by 孙晓凯 on 2016/4/19.
 */
public class traffic extends Fragment {
    public static String tex = "一";
    private View view;
    private ViewPager vp;
    private traffic_fragment1 fra1;
    private traffic_fragment2 fra2;
    private traffic_fragment3 fra3;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter fradpter;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private ImageView tabline;
    private int mScreen1_3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(view==null) {
           view = inflater.inflate(R.layout.traffic_comp_tab, container, false);
           init();
           initTabLine();
       }
        return view;
    }
    private void initTabLine() {
        tabline = (ImageView) view.findViewById(R.id.tabline);
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScreen1_3 = metrics.widthPixels/3;
        ViewGroup.LayoutParams layoutParams = tabline.getLayoutParams();
        layoutParams.width = mScreen1_3;
        tabline.setLayoutParams(layoutParams);
    }

    private void init() {
        vp = (ViewPager) view.findViewById(R.id.viewpager);
        text1 = (TextView) view.findViewById(R.id.chat);
        text2 = (TextView) view.findViewById(R.id.reseach);
        text3 = (TextView) view.findViewById(R.id.contact);
        list = new ArrayList<Fragment>();
        fra1 = new traffic_fragment1();
        fra2 = new traffic_fragment2();
        fra3 = new traffic_fragment3();
        list.add(fra1);
        list.add(fra2);
        list.add(fra3);

        fradpter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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
                        text1.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        text2.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        text3.setTextColor(Color.BLUE);
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }
    private void resetcolor() {
        text1.setTextColor(Color.BLACK);
        text2.setTextColor(Color.BLACK);
        text3.setTextColor(Color.BLACK);
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

}
