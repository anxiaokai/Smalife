package googleplay.xiaokai.com.managelife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 孙晓凯 on 2016/5/3.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<frag1_bean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    public RecyclerAdapter(List<frag1_bean> list,Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /*
    监听接口
     */
    public interface  OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    /*
    对外提供方法
     */
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_new_first_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime());
        holder.src.setText(list.get(position).getResource());
        holder.content.setText(list.get(position).getContent());
        Glide.with(context).load(list.get(position).getImage()).into(holder.image);

        if(onItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView content;
    public TextView src;
    public TextView time;
    public TextView url;
    public ImageView image;
    public MyViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        content = (TextView) itemView.findViewById(R.id.content);
        src = (TextView) itemView.findViewById(R.id.src);
        time = (TextView) itemView.findViewById(R.id.time);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
