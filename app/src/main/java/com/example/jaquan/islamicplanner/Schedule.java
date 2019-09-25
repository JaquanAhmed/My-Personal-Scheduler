package com.example.jaquan.islamicplanner;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import android.os.Bundle;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class Schedule extends Fragment implements View.OnClickListener{

    View myView;

    private ProgressDialog pDialog;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String year;
    private String month;
    private String day;
    private String dotw;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.schedule_layout, container, false);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) myView.findViewById(R.id.rvSchedule);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        TextView Date = (TextView) myView.findViewById(R.id.Date);
        bundle = this.getArguments();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat y = new SimpleDateFormat("YYYY");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        year = y.format(date);
        month = m.format(date);
        day = d.format(date);
        if (bundle != null) {
            year = bundle.getString("year");
            month = bundle.getString("month");
            day = bundle.getString("day");
            dotw = bundle.getString("dotw");
        }
        Date.setText(day+"-"+month+"-"+year);
        Date.setOnClickListener(this);
        new GetPrayerTimes().execute();
        return myView;

    }
    private class GetPrayerTimes extends AsyncTask<ArrayList<SpannableString>, ArrayList<SpannableString>, ArrayList<SpannableString>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected ArrayList<SpannableString> doInBackground(ArrayList<SpannableString>... arg0) {
            SharedPreferences apiData = getActivity().getPreferences(Context.MODE_PRIVATE);
            String city = apiData.getString("city", null);
            String country = apiData.getString("country",null);
            int method = apiData.getInt("calculationMethod",  4);
            String cm = Integer.toString(method);
            ArrayList<SpannableString> pt = new ArrayList<SpannableString>();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat d = new SimpleDateFormat("dd-MM-YYYY");
            if ((day+"-"+month+"-"+year).equals(d.format(date))) {
                PrayerTimes prayerTimes = new PrayerTimes(getActivity(), city, country, cm);
                try {
                    pt = prayerTimes.getTimes();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                PrayerTimes prayerTimes = new PrayerTimes(getActivity(), city, country, cm, year, month, day, dotw);
                try {
                    pt = prayerTimes.getTimes();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return pt;
        }

        @Override
        protected void onPostExecute(ArrayList<SpannableString> result) {
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            adapter = new MyRecyclerViewAdapter(getActivity(), result);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(result);
        }


    }

    @Override
    public void onClick(View myView) {
        Fragment fragment = new Calendar();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
