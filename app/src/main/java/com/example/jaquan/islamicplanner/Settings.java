package com.example.jaquan.islamicplanner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Settings extends Fragment{

    View myView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_layout, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.rvAll);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<SpannableString> options = new ArrayList<SpannableString>();
        String o = "Change location and calculation method";
        String t = "Edit saved events";
        SpannableString one = new SpannableString(o);
        SpannableString two = new SpannableString(t);
        one.setSpan(new RelativeSizeSpan(0.65f), o.indexOf('C'),o.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        two.setSpan(new RelativeSizeSpan(0.65f), t.indexOf('E'),t.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        options.add(one);
        options.add(two);
        Log.d(TAG, "onCreateView:" + options.size());
        adapter = new MyRecyclerViewAdapter(getActivity(), options);
        recyclerView.setAdapter(adapter);
        return myView;
    }

}
