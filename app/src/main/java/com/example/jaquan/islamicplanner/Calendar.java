package com.example.jaquan.islamicplanner;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import static android.support.constraint.Constraints.TAG;

public class Calendar extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.calendar_layout, container, false);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        CalendarView calendarView=(CalendarView) myView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String d;
                String m;
                if (dayOfMonth < 10){
                    d = 0 + Integer.toString(dayOfMonth);
                }else{
                    d = Integer.toString(dayOfMonth);
                }
                if (month < 10){
                    m = 0 + Integer.toString(month);
                }else{
                    m = Integer.toString(month);
                }
                //String date = d + "-" + m + "-" + year;
                //Toast.makeText(getActivity().getApplicationContext(),  month,
                        //Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub
                String y = Integer.toString(year);
                Bundle bundle = new Bundle();
                bundle.putString("year", y);
                bundle.putString("month", m);
                bundle.putString("day", d);
                Fragment fragment = new Schedule();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return myView;
    }


}
