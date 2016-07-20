package googleplay.xiaokai.com.managelife;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 孙晓凯 on 2016/4/21.
 */
public class ListAdapter extends BaseAdapter {
    private List<ItemBean> list;
    private Context context;
    private ViewHolder viewHolder;
    public ListAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.list_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            viewHolder.text = (TextView) convertView.findViewById(R.id.list_item_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(list.get(position).getImagesrc());
        viewHolder.text.setText(list.get(position).getTextsrc());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView text;
    }
}
