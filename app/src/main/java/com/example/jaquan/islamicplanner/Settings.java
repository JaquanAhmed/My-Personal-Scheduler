package com.example.jaquan.islamicplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends Fragment implements View.OnClickListener{

    View myView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_layout, container, false);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        Spinner dropdown = myView.findViewById(R.id.spinner);
        String[] items = new String[]{"Shia Ithna-Ansari", "University of Islamic Sciences, Karachi",
                "Islamic Society of North America", "Muslim World League", "Umm Al-Qura University, Makkah",
                "Egyptian General Authority of Survey", "Institute of Geophysics, University of Tehran" ,
                "Gulf Region", "Kuwait", "Qatar", "Majlis Ugama Islam Singapura, Singapore", "Union Organization islamic de France",
                "Diyanet İşleri Başkanlığı, Turkey", "Spiritual Administration of Muslims of Russia"};
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        SharedPreferences apiData = getActivity().getPreferences(Context.MODE_PRIVATE);
        dropdown.setSelection(apiData.getInt("calculationMethod", 4));
        Button b = (Button) myView.findViewById(R.id.button_main);
        b.setOnClickListener(this);
        return myView;
    }


    @Override
    public void onClick(View myView) {
        EditText city = (EditText) getActivity().findViewById(R.id.city);
        EditText country = (EditText) getActivity().findViewById(R.id.country);
        String ci = city.getText().toString();
        String co = country.getText().toString();
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        int cm = spinner.getSelectedItemPosition();
        SharedPreferences apiData = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = apiData.edit();
        edit.putString("city", ci);
        edit.putString("county", co);
        edit.putInt("calculationMethod", cm);
        edit.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
