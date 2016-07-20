package googleplay.xiaokai.com.managelife;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class traff2_station extends AppCompatActivity {
private SearchBox searchBox;
    private List<String> list;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traff2_station);
        iniView();
        iniData();
    }

    private void iniData() {
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

        for(int i=0;i<list.size();i++){
            SearchResult option = new SearchResult(list.get(i), ContextCompat.getDrawable(this, R.drawable.flag));
            searchBox.addSearchable(option);
        }

    }

    private void iniView() {
        recyclerView = (RecyclerView) findViewById(R.id.station_recycler);
        searchBox = (SearchBox) findViewById(R.id.searchbox);
        searchBox.setLogoText("请输入地名");
        searchBox.setMenuListener(new SearchBox.MenuListener(){

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(traff2_station.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
        searchBox.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
                //onSearch();

            }

            @Override
            public void onSearchTermChanged(String s) {
                //React to the search term changing
                //Called after it has updated results
            }


            @Override
            public void onSearch(String searchTerm) {
               // Toast.makeText(traff2_station.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
                getDataFromNet(searchTerm);
            }

            @Override
            public void onResultClick(SearchResult result){
                //React to a result being clicked
                onSearch(result.title);
            }


            @Override
            public void onSearchCleared() {

            }

        });
    }
    public void getDataFromNet(String station){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://op.juhe.cn/").build();
        Service service = retrofit.create(Service.class);
        Call<f2_bean> call = service.getWeather(station, "8c439feb9374ac6b52c129683afeafb2");
        call.enqueue(new Callback<f2_bean>() {
            @Override
            public void onResponse(Call<f2_bean> call, Response<f2_bean> response) {
                f2_bean bean = response.body();
                String str = bean.getReason();
                if(str.equals("查询成功")){
                station_Adapter adapter = new station_Adapter(bean,traff2_station.this);
                recyclerView.setAdapter(adapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(traff2_station.this, LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(layoutManager);
                }else{
                    Toast.makeText(traff2_station.this, "找不到哦!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<f2_bean> call, Throwable t) {
                Toast.makeText(traff2_station.this, "找不到呀!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface Service{
        @GET("http://op.juhe.cn/onebox/bus/query")
        Call<f2_bean> getWeather(@Query("station") String station, @Query("key") String key);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
