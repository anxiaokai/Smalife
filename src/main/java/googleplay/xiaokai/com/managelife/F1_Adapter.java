package googleplay.xiaokai.com.managelife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 孙晓凯 on 2016/5/31.
 */
public class F1_Adapter extends RecyclerView.Adapter<MMViewHolder> {

    private List<f1_bean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public F1_Adapter(List<f1_bean> list,Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.traff1_item,parent,false);
        MMViewHolder viewHolder = new MMViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MMViewHolder holder, int position) {
        f1_bean.ResultBean result = list.get(position).getResult();
        if (null!=list&&result != null&&null!=list.get(position)) {
        holder.trainumber.setText(list.get(position).getResult().getList().getTrain_no());
            holder.stratstation.setText(result.getList().getStart_station());
            holder.endstation.setText(result.getList().getEnd_station());
            holder.runtime.setText(result.getList().getRun_time());
        holder.typeone.setText(list.get(position).getResult().getList().getPrice_list().getItem().get(0).getPrice_type()+":￥");
        holder.typetwo.setText(list.get(position).getResult().getList().getPrice_list().getItem().get(1).getPrice_type()+":￥");
        //Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩")
        holder.bookone.setText(Html.fromHtml("<font color='#ff0000'>"+list.get(position).getResult().getList().getPrice_list().getItem().get(0).getPrice()+"</font>"));
        holder.booktwo.setText(Html.fromHtml("<font color='#ff0000'>"+list.get(position).getResult().getList().getPrice_list().getItem().get(1).getPrice()+"</font>"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MMViewHolder extends RecyclerView.ViewHolder{
    public TextView trainumber;
    public TextView stratstation;
    public TextView endstation;
    public TextView runtime;
    public TextView typeone;
    public TextView typetwo;
    public TextView bookone;
    public TextView booktwo;
    public MMViewHolder(View itemView) {
        super(itemView);
        trainumber = (TextView) itemView.findViewById(R.id.train_no);
        stratstation = (TextView) itemView.findViewById(R.id.start);
        endstation = (TextView) itemView.findViewById(R.id.end);
        runtime = (TextView) itemView.findViewById(R.id.time);
        typeone = (TextView) itemView.findViewById(R.id.type1);
        typetwo = (TextView) itemView.findViewById(R.id.type2);
        bookone = (TextView) itemView.findViewById(R.id.book1);
        booktwo = (TextView) itemView.findViewById(R.id.book2);
    }
}
