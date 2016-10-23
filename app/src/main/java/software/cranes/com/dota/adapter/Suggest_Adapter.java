package software.cranes.com.dota.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import software.cranes.com.dota.R;

/**
 * Created by GiangNT - PC on 18/10/2016.
 */

public class Suggest_Adapter extends BaseAdapter {
    private List<String> list;

    public Suggest_Adapter(List<String> list) {
        this.list = list;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.suggest_layout, null, false);
            TextView textView = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder = new ViewHolder(textView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTextView.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(TextView mTextView) {
            this.mTextView = mTextView;
        }

        public TextView mTextView;
    }
}
