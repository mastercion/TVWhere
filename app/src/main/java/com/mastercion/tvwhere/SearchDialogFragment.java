package com.mastercion.tvwhere;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchDialogFragment extends DialogFragment {

    private SearchResultsAdapter adapter;
    private List<String> results;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search, container, false);

        EditText searchText = view.findViewById(R.id.search_text);
        ListView searchResults = view.findViewById(R.id.search_results);

        results = new ArrayList<>();
        adapter = new SearchResultsAdapter(getContext(), results);
        searchResults.setAdapter(adapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchResults.setOnItemClickListener((parent, view1, position, id) -> {
            adapter.setSelectedPosition(position);
        });

        return view;
    }

    private void updateResults(String query) {
        results.clear();
        if (!query.isEmpty()) {
            results.add("Dummy Result1");
            results.add("Dummy Result2");
            results.add("Dummy Result3");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public int getTheme() {
        return R.style.TransparentDialog;
    }
}
