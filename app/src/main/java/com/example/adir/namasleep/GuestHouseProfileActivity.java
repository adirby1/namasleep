package com.example.adir.namasleep;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GuestHouseProfileActivity extends Activity{
    TextView name,include,telephone;
    String GuestHouseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        GuestHouseName=intent.getStringExtra("name");
        name=(TextView)findViewById(R.id.name);
        include=(TextView)findViewById(R.id.include);
        telephone=(TextView)findViewById(R.id.telephone);
        name.setText(GuestHouseName);
        load();
    }
    public void load(){
        ParseQuery<ParseObject> query=ParseQuery.getQuery("GuestHouse");
        try {
            List<ParseObject> items;
            query.whereEqualTo("name",GuestHouseName);
            items=query.find();
            for(ParseObject itemRow:items){
                telephone.setText(itemRow.getString("telephone"));
                include.setText(itemRow.getString("include")+"/n"+itemRow.getString("includ")+"/n"+itemRow.getString("inclu"));


            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
