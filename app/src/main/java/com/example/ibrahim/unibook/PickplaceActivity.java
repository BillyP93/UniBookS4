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


import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Billy on 3/1/2017.
 */

public class PickplaceActivity extends AppCompatActivity {

    private TextView getplace;
    int PLACE_PICKER_REQUEST=1;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
         database = FirebaseDatabase.getInstance();


        getplace = (TextView)findViewById(R.id.textView);
        getplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;

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
                DatabaseReference myRef = database.getReference("places");
                myRef.child((String) getplace.getText());
            }

        }
    }





}
