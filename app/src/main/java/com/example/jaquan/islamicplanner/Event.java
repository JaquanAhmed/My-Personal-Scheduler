package com.example.jaquan.islamicplanner;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class Event extends Fragment implements View.OnClickListener{

    View myView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        myView = inflater.inflate(R.layout.event_layout, container, false);
        TextView t = myView.findViewById(R.id.time);
        TextView d = myView.findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dat = new SimpleDateFormat("dd-MM-YYYY");
        SimpleDateFormat tim = new SimpleDateFormat("hh:mm");
        t.setText(tim.format(date));
        d.setText(dat.format(date));
        Button b = myView.findViewById(R.id.event_button);
        CheckBox c = myView.findViewById(R.id.repeatEveryday);
        CheckBox c2 = myView.findViewById(R.id.repeatYear);
        d.setOnClickListener(this);
        t.setOnClickListener(this);
        c.setOnClickListener(this);
        c2.setOnClickListener(this);
        b.setOnClickListener(this);
        return myView;
    }


    @Override
    public void onClick(View myView) {
        switch (myView.getId()) {
            case R.id.date:
                final TextView d = (TextView)myView.findViewById(R.id.date);
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        int year = datepicker.getYear();
                        int month = datepicker.getMonth();
                        int day = datepicker.getDayOfMonth();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        String strDate = format.format(calendar.getTime());
                        d.setText(strDate);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
                break;
            case R.id.time:
                final TextView t = (TextView)myView.findViewById(R.id.time);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        t.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.repeatEveryday:
                CheckBox checkBox = myView.findViewById(R.id.repeatEveryday);
                if(checkBox.isChecked()){
                    CheckBox m = getActivity().findViewById(R.id.Monday);
                    m.setChecked(true);
                    CheckBox tu = getActivity().findViewById(R.id.Tuesday);
                    tu.setChecked(true);
                    CheckBox w = getActivity().findViewById(R.id.Wednesday);
                    w.setChecked(true);
                    CheckBox th = getActivity().findViewById(R.id.Thursday);
                    th.setChecked(true);
                    CheckBox f = getActivity().findViewById(R.id.Friday);
                    f.setChecked(true);
                    CheckBox s = getActivity().findViewById(R.id.Saturday);
                    s.setChecked(true);
                    CheckBox su = getActivity().findViewById(R.id.Sunday);
                    su.setChecked(true);
                    CheckBox y = getActivity().findViewById(R.id.repeatYear);
                    y.setChecked(false);
                }
                break;
            case R.id.repeatYear:
                CheckBox checkBox2 = myView.findViewById(R.id.repeatYear);
                if(checkBox2.isChecked()){
                    CheckBox m = getActivity().findViewById(R.id.Monday);
                    m.setChecked(false);
                    CheckBox tu = getActivity().findViewById(R.id.Tuesday);
                    tu.setChecked(false);
                    CheckBox w = getActivity().findViewById(R.id.Wednesday);
                    w.setChecked(false);
                    CheckBox th = getActivity().findViewById(R.id.Thursday);
                    th.setChecked(false);
                    CheckBox f = getActivity().findViewById(R.id.Friday);
                    f.setChecked(false);
                    CheckBox s = getActivity().findViewById(R.id.Saturday);
                    s.setChecked(false);
                    CheckBox su = getActivity().findViewById(R.id.Sunday);
                    su.setChecked(false);
                    CheckBox y = getActivity().findViewById(R.id.repeatEveryday);
                    y.setChecked(false);
                }
                break;
            case R.id.event_button:
                EditText title = getActivity().findViewById(R.id.title);
                TextView date = getActivity().findViewById(R.id.date);
                TextView time = getActivity().findViewById(R.id.time);
                CheckBox day = getActivity().findViewById(R.id.repeatEveryday);
                CheckBox monday = getActivity().findViewById(R.id.Monday);
                CheckBox tuesday = getActivity().findViewById(R.id.Tuesday);
                CheckBox wednesday = getActivity().findViewById(R.id.Wednesday);
                CheckBox thursday = getActivity().findViewById(R.id.Thursday);
                CheckBox friday = getActivity().findViewById(R.id.Friday);
                CheckBox saturday = getActivity().findViewById(R.id.Saturday);
                CheckBox sunday = getActivity().findViewById(R.id.Sunday);
                CheckBox year = getActivity().findViewById(R.id.repeatYear);

                if(title.getText().equals(null) || date.getText().equals(null) || time.getText().equals(null)) {
                    Toast.makeText(getActivity().getApplicationContext(),  "All necessary fields not filled",
                    Toast.LENGTH_SHORT).show();//
                }else {
                    int ryear = 0;
                    int rday = 0;
                    int m = 0;
                    int tu = 0;
                    int w = 0;
                    int th = 0;
                    int f = 0;
                    int sa = 0;
                    int su = 0;
                    if(year.isChecked()){
                        ryear = 1;
                    }
                    if(day.isChecked()){
                        rday = 1;
                    }
                    if(monday.isChecked()){
                        m = 1;
                    }
                    if(tuesday.isChecked()){
                        tu = 1;
                    }
                    if(wednesday.isChecked()){
                        w = 1;
                    }
                    if(thursday.isChecked()){
                        th = 1;
                    }
                    if(friday.isChecked()){
                        f = 1;
                    }
                    if(saturday.isChecked()){
                        sa = 1;
                    }
                    if(sunday.isChecked()){
                        su = 1;
                    }
                    Database database = new Database(getActivity());
                    boolean insertData = database.addTableData(title.getText().toString(), date.getText().toString(),
                            time.getText().toString(),ryear,rday,m,tu,w,th,f,sa,su);
                    Fragment fragment = new Schedule();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
        }
    }

}
