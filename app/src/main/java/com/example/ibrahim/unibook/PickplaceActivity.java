package com.example.ibrahim.unibook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Billy on 3/1/2017.
 */

public class PickplaceActivity extends AppCompatActivity {

    private TextView getplace;
    int PLACE_PICKER_REQUEST=1;
    private FirebaseDatabase database;
    Firebase reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
       // Firebase reference = new Firebase("https://unibook-d537d.firebaseio.com/places");
        // database = FirebaseDatabase.getInstance();
        Firebase.setAndroidContext(this);
         reference = new Firebase("https://unibook-d537d.firebaseio.com/places");

        getplace = (TextView)findViewById(R.id.textView);
        getplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;

                String messageText = getplace.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("places", messageText);
                    //reference.push().setValue(map);
                }

                try {
                    intent = builder.build(PickplaceActivity.this);
                    //Place place = PlacePicker.getPlace(this, data);
                    //intent = builder.build((Activity) getApplicationContext());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
                catch (GooglePlayServicesNotAvailableException ex){
                    ex.printStackTrace();
                }




            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == PLACE_PICKER_REQUEST){

            if(resultCode == RESULT_OK){

                Place place = PlacePicker.getPlace(data,this);
                String address = String.format("Place: %s",place.getAddress());
                getplace.setText(address);
                reference.push().setValue(address);
//                String url = "https://unibook-d537d.firebaseio.com/places.json";
//
//                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//                    @Override
//                    public void onResponse(String s) {
//                        Firebase reference = new Firebase("https://unibook-d537d.firebaseio.com/places");
//
//
//                        reference.push().setValue(getplace.getText());
//
//                    }
//
//
//                //DatabaseReference myRef = database.getReference("places");
//
//                //myRef.child((String) getplace.getText());
//
//
//            },new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        System.out.println("" + volleyError );
//                        //pd.dismiss();
//                    }
//                });
        }
    }
}
}