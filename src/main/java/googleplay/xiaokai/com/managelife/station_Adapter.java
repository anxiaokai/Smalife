package googleplay.xiaokai.com.managelife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 孙晓凯 on 2016/6/7.
 */
public class station_Adapter extends RecyclerView.Adapter<station_ViewHolder> {
    private f2_bean f2be;
    private Context context;
    private View view;
    private LayoutInflater layoutInflater;

    public station_Adapter(f2_bean f2be,Context context) {
        this.f2be = f2be;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public station_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.inflate(R.layout.station_item,parent,false);
        station_ViewHolder station_viewHolder = new station_ViewHolder(view);
        return station_viewHolder;
    }

    @Override
    public void onBindViewHolder(station_ViewHolder holder, int position) {
        holder.add.setText(Html.fromHtml("<font color='#000000'>"+f2be.getResult().getList().get(position).getAdds()+"</font>"));
        holder.name.setText(Html.fromHtml("<font color='#99CC00'>"+f2be.getResult().getList().get(position).getName()+"</font>"));
        holder.tell.setText(Html.fromHtml("<font color='#CC0033'>"+f2be.getResult().getList().get(position).getTel()+"</font>"));
    }

    @Override
    public int getItemCount() {
        return f2be.getResult().getList().size();
    }
}
class station_ViewHolder extends RecyclerView.ViewHolder{
    public TextView name;
    public TextView add;
    public TextView tell;

    public station_ViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.station_name);
        add = (TextView) itemView.findViewById(R.id.station_addr);
        tell = (TextView) itemView.findViewById(R.id.station_number);
    }
}
