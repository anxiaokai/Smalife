package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import googleplay.xiaokai.com.managelife.PeopleAdapter;
import googleplay.xiaokai.com.managelife.R;
import googleplay.xiaokai.com.managelife.Traff1_detail;
import googleplay.xiaokai.com.managelife.f2_bean;
import googleplay.xiaokai.com.managelife.traff2_station;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/4/23.
 */
public class traffic_fragment2 extends Fragment implements View.OnClickListener {
    private RecyclerView tr2_recy;
    private View view;
    private List<String> list;
    private PeopleAdapter adapter;
    private PeopleAdapter adapt;
    private android.support.design.widget.FloatingActionButton floatingActionButton;
    FloatingActionMenu actionMenu;
    StringBuilder stringBuilder;
    FileInputStream traff3data;
    BufferedReader bufferedReader;
    List<f2_bean> cachetraff3;
    Activity mActivity;
    Gson gson;
    Gson ggson;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.add("北京");
        list.add("天津");
        list.add("石家庄");
        list.add("太原");
        list.add("呼和浩特");
        list.add("沈阳");
        list.add("长春");
        list.add("哈尔滨");
        list.add("上海");
        list.add("南京");
        list.add("杭州");

        list.add("合肥");
        list.add("福建");
        list.add("南昌");
        list.add("济南");
        list.add("郑州");
        list.add("武汉");
        list.add("长沙");
        list.add("广州");
        list.add("南宁");
        list.add("海口");
        list.add("重庆");
        list.add("成都");
        list.add("贵阳");
        list.add("昆明");
        list.add("拉萨");
        list.add("西安");

        list.add("兰州");
        list.add("西宁");
        list.add("银川");
        list.add("乌鲁木齐");

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.traffic_fragmet2,container,false);
        initView();
        return view;
    }

    private void initData() {

        if(IsNetUtils.isNetworkConnected(getActivity())) {
            new myAsyncTask().execute(list);
        }
    }

    private void initView() {
        ggson = new Gson();
        tr2_recy = (RecyclerView) view.findViewById(R.id.t2_r);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_search);
//        ImageView icon = new ImageView(getActivity()); // Create an icon
//        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//        FloatingActionButton actionButton = new FloatingActionButton.Builder()
//                .setContentView(icon)
//                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
// repeat many times:
        ImageView itemIcon = new ImageView(getActivity());
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.sousuo));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemI = new ImageView(getActivity());
        itemI.setImageDrawable(getResources().getDrawable(R.drawable.qiche));
        SubActionButton button2 = itemBuilder.setContentView(itemI).build();

//        button1.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.listback));
//        button2.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.listback));


        actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(button1)
                .addSubActionView(button2)
//                .addSubActionView(button3)
                // ...
                .attachTo(floatingActionButton)
                .build();

        button1.setTag(1);
        button2.setTag(2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        recycledata();
    }

    private void recycledata() {
        try {

            traff3data = getActivity().openFileInput("traff3");
            StringBuilder sb = new StringBuilder();
            if(null!=traff3data) {
                bufferedReader = new BufferedReader(new InputStreamReader(traff3data));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line);
                }
                cachetraff3 = new ArrayList<>();
                String[] split = sb.toString().split("#");

                for(int i=0;i<split.length;i++){
                    cachetraff3.add(ggson.fromJson(split[i], f2_bean.class));
                }
                adapt = new PeopleAdapter(getActivity(),list,cachetraff3);
                tr2_recy.setAdapter(adapt);

                adapt.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                tr2_recy.setLayoutManager(layoutManager);
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag){
            case 1:
                Intent intent = new Intent(getActivity(), traff2_station.class);
                startActivity(intent);
                break;
            case 2:
                Intent inten = new Intent(getActivity(), Traff1_detail.class);
                startActivity(inten);
                break;
        }

        actionMenu.close(true);
    }

    class myAsyncTask extends AsyncTask<List<String>,Void,List<f2_bean>> {
        private List<f2_bean> listbean;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<f2_bean> doInBackground(List<String>... params) {
            listbean = new ArrayList<>();
            stringBuilder = new StringBuilder();
            gson = new Gson();
            for(int i=0;i<params[0].size();i++) {
                OkHttpClient mOkHttp = new OkHttpClient();
                Request request = new Request.Builder()
                        //http://op.juhe.cn/onebox/bus/query?station=%E5%8C%97%E4%BA%AC&key=8c439feb9374ac6b52c129683afeafb2
                        .url("http://op.juhe.cn/onebox/bus/query?station="+params[0].get(i)+"&key=8c439feb9374ac6b52c129683afeafb2")
                        .build();

                final okhttp3.Call call = mOkHttp.newCall(request);

                String result = null;
                try {
                    result = call.execute().body().string();
                    if(result!=null) {
                        stringBuilder.append(result + "#");
                    }
                    gson = new Gson();
                    f2_bean f2Bean = gson.fromJson(result, f2_bean.class);
                    listbean.add(f2Bean);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            String s = stringBuilder.toString();
            if(!TextUtils.isEmpty(s)){
                save(s);
                //Log.i("flag",s);
            }

            return listbean;
        }

        @Override
        protected void onPostExecute(List<f2_bean> f1_beanList) {
            super.onPostExecute(f1_beanList);

            //F1_Adapter adapter = new F1_Adapter(f1_beanList,getActivity());
            adapter = new PeopleAdapter(getActivity(),list,f1_beanList);

            tr2_recy.setAdapter(adapter);
            //设置listview之间的分割线
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
//            tr2_recy.addItemDecoration(dividerItemDecoration);
            adapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
            tr2_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
//            tr2_recy.setLayoutManager(layoutManager);
        }
    }
    private void save(String str){

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = mActivity.openFileOutput("traff3", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
