package com.example.jaquan.islamicplanner;

import android.content.Context;
import android.database.Cursor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import java.util.Calendar;
import static android.support.constraint.Constraints.TAG;
//split date into day, month and year.
//go through json for each than if and then break. if fails toast.

public class PrayerTimes {

    private Context context;
    private String city;
    private String country;
    private String method;
    private String year;
    private String month;
    private String day;

    public PrayerTimes(Context context, String city, String country, String method) {
        this.context = context;
        this.city = city;
        this.country = country;
        this.method = method;
        //String currentDateString = DateFormat.getDateInstance().format(new Date());
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat y = new SimpleDateFormat("YYYY");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        this.year = y.format(date);
        this.month = m.format(date);
        this.day = d.format(date);
    }

    public PrayerTimes(Context context, String city, String country, String method, String year, String month, String day) {
        this.context = context;
        this.city = city;
        this.country = country;
        this.method = method;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getMethod() {
        return method;
    }

    public ArrayList<SpannableString> getTimes() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();

        String url = "http://api.aladhan.com/v1/calendarByCity?city="+this.city+"&country="+this.country+"&method="+this.method+"&month="+this.month+"&year="+this.year;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        final String myResponse = response.body().string();
        JSONObject json = new JSONObject(myResponse);
        if (json != null){
            JSONArray data = json.getJSONArray("data");
            ArrayList<String> Times = new ArrayList<String>();
            for (int i = 0; i<data.length(); i++) {
                JSONObject d = data.getJSONObject(i);
                if (d.getJSONObject("date").getJSONObject("gregorian").getString("date").equals(this.day+"-"+this.month+"-"+this.year)){
                    JSONObject timings = d.getJSONObject("timings");
                    String fajr = "FAJR\n" + timings.getString("Fajr");
                    Times.add(fajr);
                    String sunrise = "Sunrise\n" + timings.getString("Sunrise");
                    Times.add(sunrise);
                    String zuhr = "ZUHR\n" + timings.getString("Dhuhr");
                    Times.add(zuhr);
                    String asr = "ASR\n" + timings.getString("Asr");
                    Times.add(asr);
                    String maghrib = "MAGHRIB\n" + timings.getString("Maghrib");
                    Times.add(maghrib);
                    String isha = "ISHA\n" + timings.getString("Isha");
                    Times.add(isha);
                    break;
                }
            }
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat d = new SimpleDateFormat("dd-MM-YYYY");
            SimpleDateFormat dotw = new  SimpleDateFormat("EEEE");
            Database database = new Database(context);
            Cursor Data = database.getData (d.format(date));
            while(Data.moveToNext()){
                String t = Data.getString(1) + "\n" + Data.getString(3);
                Times.add(t);
            }
            Collections.sort(Times, new Comparator<String>(){
                @Override
                public int compare(String s1, String s2){
                    return s1.split("\n")[1].compareTo(s2.split("\n")[1]);
                }
            });
            ArrayList<SpannableString> sTimes = new ArrayList<SpannableString>();
            for (String s : Times){
                SpannableString ss = new SpannableString(s);
                ss.setSpan(new RelativeSizeSpan(0.8f), s.indexOf("\n")+1, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sTimes.add(ss);
            }
            return sTimes;
        }
        else {
            ArrayList<SpannableString> Times = new ArrayList<SpannableString>();
            for (int i = 0; i < Times.size(); i++){
                String e = "-1";
                SpannableString sE = new SpannableString(e);
                Times.add(sE);
            }
            return Times;
        }
    }
}
