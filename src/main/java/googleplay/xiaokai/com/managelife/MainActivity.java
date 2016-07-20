package googleplay.xiaokai.com.managelife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.entertain_fragment;
import fragment.traffic;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ActionBar supportActionBar;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private String[] str;
    private ActionBarDrawerToggle toggle;
    private CircleImageView image;
    private List<ItemBean> list;
    private ListAdapter adapter;
    private Intent intent;
    private Intent inten;
    private Intent inte;
    private Intent imageintent;
    private Intent aboutintent;
    Fragment fragment;
    Fragment traffic;
    TextView usname;
    TextView signcon;
    String usern;
    String qian;
    String ppath;
    int px = (int)(65*16.0f+0.5f);
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "cebd6afd1aadfeb5b679422f0637e997");
        initView();
        setNameAndSign();
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();
        setToolBar();
        list = new ArrayList<>();
        setBeanDate(list);
        adapter = new ListAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        //设置侧滑监听
        setDrawListener();
    }

    private void setNameAndSign() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        if(userInfo!=null) {
            usern = (String) BmobUser.getObjectByKey("username");
//MyUser中的扩展属性
            qian = (String) BmobUser.getObjectByKey("qianming");

            ppath = (String) BmobUser.getObjectByKey("picpath");
            if (usern != null) {
                usname.setText(usern);
            }else{
                usname.setText("");
            }
            if (qian != null) {
                signcon.setText(qian);
            }else{
                signcon.setText("在设置页面设置签名");
            }
            if(ppath!=null){
                image.setImageBitmap(getScaleBitmap(ppath,px,px));
            }else{
                image.setImageResource(R.drawable.touxiang);
            }
        }else{
            usname.setText("");
            signcon.setText("在设置页面设置签名");
            image.setImageResource(R.drawable.touxiang);
        }

    }

    private void setBeanDate(List<ItemBean> list) {
        ItemBean itemplay = new ItemBean();
        itemplay.setImagesrc(R.drawable.yule);
        itemplay.setTextsrc(R.string.play);
        list.add(itemplay);

        ItemBean itemptraffic = new ItemBean();
        itemptraffic.setImagesrc(R.drawable.jiaotong);
        itemptraffic.setTextsrc(R.string.traffic);
        list.add(itemptraffic);

        ItemBean itemptree = new ItemBean();
        itemptree.setImagesrc(R.drawable.shudong);
        itemptree.setTextsrc(R.string.tree);
        list.add(itemptree);

        ItemBean itemset = new ItemBean();
        itemset.setImagesrc(R.drawable.shezhi);
        itemset.setTextsrc(R.string.setting);
        list.add(itemset);

        ItemBean itemexit = new ItemBean();
        itemexit.setImagesrc(R.drawable.tuichu);
        itemexit.setTextsrc(R.string.exit);
        list.add(itemexit);
    }

    private void setDrawListener() {
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setNameAndSign();
            }
        };
        drawerLayout.setDrawerListener(toggle);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
    }

    private void setToolBar() {
        supportActionBar.setTitle(R.string.index_page);
    }

    private void initView() {
//        supportFragmentManager = getSupportFragmentManager();
//        fragmentTransaction = supportFragmentManager.beginTransaction();
        traffic = new traffic();
        fragment = new entertain_fragment();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        listView = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        str = getResources().getStringArray(R.array.planets_array);
        image = (CircleImageView) findViewById(R.id.profile);
        usname = (TextView) findViewById(R.id.draw_text);
        signcon = (TextView) findViewById(R.id.signmain);

        image.setOnClickListener(this);
    }
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onClick(View v) {
       if(imageintent==null){
           imageintent = new Intent(this,ImageDetail.class);
           if(!TextUtils.isEmpty(ppath)) {
               imageintent.putExtra("imagepath", ppath);
           }
       }
        startActivity(imageintent);
    }

    class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {
            switch (position){
                case 0:
                    //把fragment设置到页面
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
                    break;
                case 1:
                    //设置交通fragment到页面
                    if(intent==null){
                        intent = new Intent(MainActivity.this,TrifficActivity.class);
                    }
                    startActivity(intent);
                    break;
                case 2:
                    //树洞
                    if(inte==null){
                        inte = new Intent(MainActivity.this,TreeHold.class);
                    }
                    startActivity(inte);
                    break;
                case 3:
                    //设置页面
                    if(inten==null){
                        inten = new Intent(MainActivity.this,SettingActivity.class);
                    }
                    startActivity(inten);
                    break;
                case 4:
                    if(aboutintent==null) {
                        aboutintent = new Intent(MainActivity.this, About.class);
                    }
                    startActivity(aboutintent);
                    break;
                default:
                    break;
            }

//            Bundle bundle = new Bundle();
//            bundle.putString(entertain_fragment.tex,"我是"+position);
//            fragment.setArguments(bundle);

            //高亮选中item
            listView.setItemChecked(position,true);
            //隐藏侧滑
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    /*
    缩小图片
     */
    private Bitmap getScaleBitmap(String path,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
//
        options.inSampleSize = calculatInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path,options);
    }

    private int calculatInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        Log.i("flag",width+"o"+height);
        int inSampleSize = 1;

        if(width>reqWidth||height>reqHeight){
            int halfWidth = width/2;
            int halfHeight = height/2;

            while ((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>=reqWidth){
                inSampleSize = inSampleSize * 2;
            }
        }
        return inSampleSize;
    }

}
