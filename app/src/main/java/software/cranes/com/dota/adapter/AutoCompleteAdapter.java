package software.cranes.com.dota.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiangNT - PC on 18/10/2016.
 */

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> listData;
    private List<String> resultList;

    public AutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
    }

    public AutoCompleteAdapter(Context context, int resource, List<String> listData) {
        super(context, resource, listData);
        this.listData = listData;
        resultList = listData;
    }

    @Override
    public int getCount() {
        if (resultList != null) {
            return resultList.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        if (resultList == null) {
            return null;
        }
        return resultList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = filterFollowString(constraint.toString());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    ;

    private ArrayList<String> filterFollowString(String str) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : listData) {
            if (s.toLowerCase().contains(str.toLowerCase())) {
                list.add(s);
            }
        }
        return list;
    }

    public List<String> getListData() {
        return listData;
    }
}
