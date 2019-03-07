package com.example.carlos.memorable_places;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> lugares = new ArrayList<>();
    static ArrayList<LatLng> locaciones =new ArrayList<>();
    static    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView lista =findViewById(R.id.lugares);


        SharedPreferences shareded = this.getSharedPreferences("com.example.carlos.memorable_places",Context.MODE_PRIVATE);

        ArrayList<String> latitudes =new ArrayList<>();
        ArrayList<String> longitudes =new ArrayList<>();

        lugares.clear();
        latitudes.clear();
        longitudes.clear();
        locaciones.clear();

        try {
            lugares=(ArrayList<String>) ObjectSerializer.deserialize(shareded.getString("lugares",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes=(ArrayList<String>) ObjectSerializer.deserialize(shareded.getString("latitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes=(ArrayList<String>) ObjectSerializer.deserialize(shareded.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lugares.size() > 0 && latitudes.size() >0 && longitudes.size() >0){
            if (lugares.size() == latitudes.size() && lugares.size() == longitudes.size()){
                for (int i=0;i<latitudes.size();i++){
                    locaciones.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                }
            }
        }
        else {
            lugares.add("Añade tu lugar favorito");
            locaciones.add(new LatLng(0,0));
        }



        try {

            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lugares);

            lista.setAdapter(arrayAdapter);
        }
        catch (Exception e){
            Toast.makeText(this, "Aqui esta el error", Toast.LENGTH_SHORT).show();
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent miIntento = new Intent(getApplicationContext(), MapsActivity.class);
                    miIntento.putExtra("lugar",position);
                    startActivity(miIntento);

            }
        });

    }
}
