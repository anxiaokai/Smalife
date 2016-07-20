package googleplay.xiaokai.com.managelife;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙晓凯 on 2016/6/2.
 */
public class PeopleAdapter extends ExpandableRecyclerAdapter<PeopleAdapter.PeopleListItem> {
    public static final int TYPE_PERSON = 1001;
    private List<String> listring;
    private List<f2_bean> lisebean;

    public PeopleAdapter(Context context,List<String> listring,List<f2_bean> lisebean) {
        super(context);
        this.listring = listring;
        this.lisebean = lisebean;
        setItems(getSampleItems());
    }

    public static class PeopleListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        public String first;
        public String second;
        public String three;
        public String four;

        public PeopleListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public PeopleListItem(String first, String last,String three) {
            super(TYPE_PERSON);

            // Text = first + " " + last+" "+three;
            this.first = first;
            second = last;
            this.three = three;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;
        ImageView imageView;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));
            imageView = (ImageView) view.findViewById(R.id.item_arrow);
            name = (TextView) view.findViewById(R.id.item_header_name);
        }

        public void bind(int position) {
            super.bind(position);
            imageView.setImageResource(R.drawable.right);
            name.setText(visibleItems.get(position).Text);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;
        TextView t;
        TextView san;

        public PersonViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            t = (TextView) view.findViewById(R.id.item_two);
            san = (TextView) view.findViewById(R.id.item_three);
        }

        public void bind(int position) {
            name.setText(Html.fromHtml("<font color='#99CC00'>"+visibleItems.get(position).first+"</font>"));
            t.setText(Html.fromHtml("<font color='#CC0033'>"+visibleItems.get(position).second+"</font>"));
            san.setText(Html.fromHtml("<font color='#000000'>"+visibleItems.get(position).three+"</font>"));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.item_person, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

    private List<PeopleListItem> getSampleItems() {
        List<PeopleListItem> items = new ArrayList<>();
        String stationame = null;
        String stationadd = null;
        String stationtel = null;
        for(int i=0;i<lisebean.size();i++){
            if(lisebean.get(i).getReason().equals("查询成功")){
                items.add(new PeopleListItem(listring.get(i)));

                for(int j=0;j<lisebean.get(i).getResult().getList().size();j++){
                    stationame = lisebean.get(i).getResult().getList().get(j).getName();
                    stationadd = lisebean.get(i).getResult().getList().get(j).getAdds();
                    stationtel = lisebean.get(i).getResult().getList().get(j).getTel();
                    items.add(new PeopleListItem(stationame,stationtel,stationadd));
                }


            }
        }
//        items.add(new PeopleListItem("北京"));
//        items.add(new PeopleListItem("北京市朝阳区哈哈哈国际机场","北京市朝阳厉害iolhiohi号","sgeg"));
//        items.add(new PeopleListItem("John","dfds","dsfdf"));
//        items.add(new PeopleListItem("Frank", "Hall"));
//        items.add(new PeopleListItem("Sue", "West"));
//        items.add(new PeopleListItem("Family"));
//        items.add(new PeopleListItem("Drew", "Smith"));
//        items.add(new PeopleListItem("Chris", "Doe"));
//        items.add(new PeopleListItem("Alex", "Hall"));
//        items.add(new PeopleListItem("Associates"));
//        items.add(new PeopleListItem("John", "Jones"));
//        items.add(new PeopleListItem("Ed", "Smith"));
//        items.add(new PeopleListItem("Jane", "Hall"));
//        items.add(new PeopleListItem("Tim", "Lake"));
//        items.add(new PeopleListItem("Colleagues"));
//        items.add(new PeopleListItem("Carol", "Jones"));
//        items.add(new PeopleListItem("Alex", "Smith"));
//        items.add(new PeopleListItem("Kristin", "Hall"));
//        items.add(new PeopleListItem("Pete", "Lake"));

        return items;
    }
}
