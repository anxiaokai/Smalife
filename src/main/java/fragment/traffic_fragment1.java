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
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import googleplay.xiaokai.com.managelife.F1_Adapter;
import googleplay.xiaokai.com.managelife.R;
import googleplay.xiaokai.com.managelife.Traff1_detail;
import googleplay.xiaokai.com.managelife.f1_bean;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/4/23.
 */
public class traffic_fragment1 extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton floatbutton;
    private Activity activity;
    private F1_Adapter adapter;
    private F1_Adapter adapt;
    StringBuilder stringBuilder;
    private List<f1_bean> cachebean;
    Gson gson;
    Gson ggson;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.traffic_fragmet1,container,false);
        iniView();
        iniData();
        return view;
    }

    private void iniData() {
        List<String> list = new ArrayList<>();
        list.add("G1");
        list.add("G30");
        list.add("G40");
        list.add("G45");
        list.add("G47");
        list.add("G51");
        list.add("G53");
        list.add("G71");
        list.add("K1010");
        list.add("K1001");
        list.add("K1012");
        list.add("K1015");
        list.add("K1019");
        list.add("K2045");
        list.add("K1023");
        list.add("K1029");
        list.add("K1034");
        list.add("D1");
        list.add("D21");
        list.add("D25");
        list.add("D31");
        list.add("D2312");
        list.add("D2315");
        list.add("D2323");
        list.add("D2334");
        list.add("D2345");
        list.add("D2341");
        list.add("T109");
        list.add("T78");
        list.add("T55");
        list.add("T65");
        list.add("T121");
        list.add("T132");
        list.add("T125");
        list.add("T135");
        list.add("T206");
        list.add("T146");
        list.add("Z202");
        list.add("Z162");
        list.add("Z9");
        list.add("Z88");
        list.add("Z281");
        list.add("Z172");
        list.add("Z157");
        list.add("Z203");
        list.add("Z230");
        list.add("Z108");
        if(IsNetUtils.isNetworkConnected(getActivity())){
        new myAsyncTask().execute(list);
        }else{
            Toast.makeText(getActivity(), "木有,木有,木有网!", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniView() {
        FileInputStream trainfo;
        BufferedReader bufferedReader = null;
        recyclerView = (RecyclerView) view.findViewById(R.id.t1_recycler);
        floatbutton = (FloatingActionButton) view.findViewById(R.id.fab_search);
        ggson = new Gson();
        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Traff1_detail.class);
                startActivity(intent);
            }
        });

        try {

            trainfo = activity.openFileInput("traffcache");
            StringBuilder sb = new StringBuilder();
            if(null!=trainfo) {
                bufferedReader = new BufferedReader(new InputStreamReader(trainfo));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line);
                }
                cachebean = new ArrayList<>();
                String[] split = sb.toString().split("#");

                for(int i=0;i<split.length;i++){
                    cachebean.add(ggson.fromJson(split[i], f1_bean.class));
                }
                adapt = new F1_Adapter(cachebean,activity);
                recyclerView.setAdapter(adapt);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
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

    public class myAsyncTask extends AsyncTask<List<String>,Void,List<f1_bean>> {
        private List<f1_bean> listbean;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<f1_bean> doInBackground(List<String>... params) {
            listbean = new ArrayList<>();
            stringBuilder = new StringBuilder();
            for(int i=0;i<params[0].size();i++) {
//                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//                        .baseUrl("http://op.juhe.cn/").build();
//                Service service = retrofit.create(Service.class);
//                Call<f1_bean> call = service.getWeather(params[0].get(i), "ab756c89628e8bdfa2945af6dbcf6ce1");
//                retrofit2.Response<f1_bean> result = null;
                OkHttpClient mOkHttp = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://op.juhe.cn/onebox/train/query?train="+params[0].get(i)+"&key=ab756c89628e8bdfa2945af6dbcf6ce1")
                        .build();

                final Call call = mOkHttp.newCall(request);

                String result = null;
                try {
                    result = call.execute().body().string();
                    if(result!=null) {
                        stringBuilder.append(result + "#");
                    }
                    gson = new Gson();
                    f1_bean f1Bean = gson.fromJson(result, f1_bean.class);
                    listbean.add(f1Bean);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            String s = stringBuilder.toString();
            if(!TextUtils.isEmpty(s)){
                save(s);
            }
            return listbean;
        }

        @Override
        protected void onPostExecute(List<f1_bean> f1_beanList) {
            super.onPostExecute(f1_beanList);

            F1_Adapter adapter = new F1_Adapter(f1_beanList,activity);
            recyclerView.setAdapter(adapter);

            //设置listview之间的分割线
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
//            recyclerView.addItemDecoration(dividerItemDecoration);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
//    public interface Service{
//        @GET("http://op.juhe.cn/onebox/train/query")
//        Call<f1_bean> getWeather(@Query("train") String dtype, @Query("key") String key);
//    }

    /*
   把list对象存入文件
    */
    private void save(String str){

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = activity.openFileOutput("traffcache", Context.MODE_PRIVATE);
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
