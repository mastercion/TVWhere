package com.mastercion.tvwhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchResultsAdapter extends ArrayAdapter<String> {

    private int selectedPosition = -1;
    private int focusedPosition = -1;

    public SearchResultsAdapter(Context context, List<String> results) {
        super(context, 0, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false);
        }
        TextView resultText = convertView.findViewById(R.id.result_text);
        resultText.setText(result);

        // Update background based on focus and selection state
        if (position == focusedPosition) {
            convertView.setBackgroundResource(R.drawable.search_result_selected_background);
        } else {
            convertView.setBackgroundResource(R.drawable.search_result_background);
        }

        convertView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });

        convertView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusedPosition = position;
                v.setBackgroundResource(R.drawable.search_result_selected_background);
            } else {
                v.setBackgroundResource(R.drawable.search_result_background);
            }
            notifyDataSetChanged();
        });

        convertView.setFocusable(true);
        convertView.setFocusableInTouchMode(true);

        return convertView;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        this.focusedPosition = position;
        notifyDataSetChanged();
    }
}
