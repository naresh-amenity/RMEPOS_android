package com.rme.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rme.pos.R;

import java.util.List;

public class ListPopupWindowAdapter extends BaseAdapter {
    private List<ListPopupItem> items;
    Context context;

    public ListPopupWindowAdapter(Context context, List<ListPopupItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListPopupItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt.setText(getItem(position).getTitle());
        holder.img.setImageResource(getItem(position).getImageRes());
        return convertView;
    }

    static class ViewHolder {
        TextView txt;
        ImageView img;

        ViewHolder(View view) {
            txt = view.findViewById(R.id.txt);
            img = view.findViewById(R.id.img);
        }
    }
}