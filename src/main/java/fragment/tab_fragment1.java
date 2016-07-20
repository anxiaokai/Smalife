package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import googleplay.xiaokai.com.managelife.R;
import googleplay.xiaokai.com.managelife.RecyclerAdapter;
import googleplay.xiaokai.com.managelife.WebActivity;
import googleplay.xiaokai.com.managelife.frag1_bean;
import utils.IsNetUtils;

/**
 * Created by 孙晓凯 on 2016/4/23.
 */
public class tab_fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private Activity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<String> list;
    private List<String> newJson;
    private List<String> first_step_list;
    public List<frag1_bean> frag_bean;
    private RecyclerAdapter adapter;
    private SpinKitView progressBar;
    private String str;
    private int flag = 0;
    private Handler handler;
    SharedPreferences first;
    TextView loadtext;
    boolean firststart;
    String url = "http://op.juhe.cn/onebox/news/words?key=16e81ac7ce0f4eddf65061af7af8f37e";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmet_layout_tab,container,false);
        first = getActivity().getSharedPreferences("first", Context.MODE_PRIVATE);
        firststart = first.getBoolean("firststart", false);
        iniView();
        iniDatas();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    private void iniView() {
        loadtext = (TextView) view.findViewById(R.id.loadingtext);
        recyclerView = (RecyclerView) view.findViewById(R.id.fra1_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        progressBar = (SpinKitView) view.findViewById(R.id.spin_kit);

        try {
            FileInputStream strcache = getActivity().openFileInput("strcache");

            if(null!=strcache) {
                ObjectInputStream objectInputStream = new ObjectInputStream(strcache);

                //执行反序列化读取
                frag1_bean[] obj = (frag1_bean[]) objectInputStream.readObject();
                //将数组转换成List
                final List<frag1_bean> li = Arrays.asList(obj);

                //List<frag1_bean> li = (List<frag1_bean>) objectInputStream.readObject();
                adapter = new RecyclerAdapter(li, getActivity());

                if(adapter!=null) {
                    adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra("url", li.get(position).getUrl());
                            intent.putExtra("content", li.get(position).getContent());
                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                }

                recyclerView.setAdapter(adapter);
                objectInputStream.close();
                strcache.close();
            }
        } catch (Exception e) {
            progressBar.setVisibility(View.VISIBLE);
            loadtext.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    /*
    下拉刷新所进行的操作
     */
    private void myDataOperation() {
        //new DownTast().execute(url);
        if(!IsNetUtils.isNetworkConnected(getActivity())){
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "哎呀,不要刷啦!没有网!", Toast.LENGTH_SHORT).show();
        }
        iniDatas();
    }

    private void iniDatas() {

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                first_step_list = parseJson(json);
                new DownTast().execute(first_step_list);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = downloadFromInternet(url);
                    Message message = Message.obtain();
                    message.obj = s;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        myDataOperation();
    }


    class DownTast extends AsyncTask<List<String>,Void,List<frag1_bean>>{
        StringBuilder stringBuilder = new StringBuilder();
        String str = null;
        List<frag1_bean> stringList = null;

        @Override
        protected List<frag1_bean> doInBackground(List<String>... params) {
            try {
                stringList = new ArrayList<>();
                for(int i=0;i<params[0].size();i++){
                    str = downloadFromInternet("http://op.juhe.cn/onebox/news/query?q="+params[0].get(i)+"&dtype=&key=16e81ac7ce0f4eddf65061af7af8f37e")+"?";
                    stringList.add(parseJson2(str));
                }
                save(stringList);//用序列化保存数据
                 return stringList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(final List<frag1_bean> listbean) {
            super.onPostExecute(listbean);
            if(listbean!=null) {
                adapter = new RecyclerAdapter(listbean,activity);
//                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
//                //缓存
                progressBar.setVisibility(View.GONE);
                loadtext.setVisibility(View.GONE);
                //save(listbean);
                //Log.i("filter",str);
                adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("url",listbean.get(position).getUrl());
                        intent.putExtra("content",listbean.get(position).getContent());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }
            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }

        }


    }

    private String downloadFromInternet(String url) throws IOException {
        String readline;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = null;
        URL url1 = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
        urlConnection.setConnectTimeout(3000);
        urlConnection.setRequestMethod("GET");
        if(urlConnection.getResponseCode()==200) {
            inputStream = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();
            while ((readline = bufferedReader.readLine()) != null) {
                stringBuffer.append(readline);
            }
        }
        return stringBuffer.toString();
    }

    private List<String> parseJson(String json){
        list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i=0;i<result.length();i++){
                String str = result.getString(i);
                list.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    private frag1_bean parseJson2(String json){
        frag1_bean bean;
        bean = new frag1_bean();
        String str;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject ob = (JSONObject) jsonArray.get(0);
            str = ob.getString("content");
            bean.setContent(str.replaceAll("<em>|</em>",""));
            bean.setImage(ob.getString("img"));
            bean.setResource(ob.getString("src"));
            bean.setTime(ob.getString("pdate"));
            bean.setTitle(ob.getString("title"));
            bean.setUrl(ob.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }
    /*
    把list对象存入文件
     */
    private void save(List<frag1_bean> list){
        try {
            FileOutputStream strcache = getActivity().openFileOutput("strcache", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(strcache);

            //将List转换成数组
            frag1_bean[] obj = new frag1_bean[list.size()];
            list.toArray(obj);

            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            strcache.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


