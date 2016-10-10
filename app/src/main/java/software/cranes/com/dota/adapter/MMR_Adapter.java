package software.cranes.com.dota.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import software.cranes.com.dota.R;
import software.cranes.com.dota.model.MMRmodel;

/**
 * Created by GiangNT - PC on 10/10/2016.
 */

public class MMR_Adapter extends BaseAdapter {
    private List<MMRmodel> list;

    public MMR_Adapter(List<MMRmodel> list) {
        this.list = list;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mmr_ranker_view_row, null, false);
            holder = new ViewHolder();
            holder.tvRank = (TextView) convertView.findViewById(R.id.tvRank);
            holder.tvPlayer = (TextView) convertView.findViewById(R.id.tvPlayer);
            holder.tvCountry = (TextView) convertView.findViewById(R.id.tvCountry);
            holder.tvMMR = (TextView) convertView.findViewById(R.id.tvMMR);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tvRank.setText(String.valueOf(list.get(position).getRank()));
        if (list.get(position).getTeam_tag() == null) {
            holder.tvPlayer.setText(list.get(position).getName());
        } else {
            String text = "<b>" + list.get(position).getTeam_tag() + "</b>." + list.get(position).getName();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                holder.tvPlayer.setText(Html.fromHtml(text));
            } else {
                holder.tvPlayer.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
            }

        }
        holder.tvCountry.setText(list.get(position).getCountry());
        holder.tvMMR.setText(String.valueOf(list.get(position).getSolo_mmr()));
        return convertView;
    }

    class ViewHolder {
        TextView tvRank, tvPlayer, tvCountry, tvMMR;
    }
}