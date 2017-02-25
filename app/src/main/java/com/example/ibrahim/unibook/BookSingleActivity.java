package com.example.ibrahim.unibook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookSingleActivity extends AppCompatActivity {

    private String mPost_key=null;
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabase;
    private ImageView mBookSingleImage;
    private TextView mBookSingleTitle, mBookSingleDesc, mBookSinglePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_single);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key=getIntent().getExtras().getString("book_id");
        mBookSingleTitle = (TextView) findViewById(R.id.titleField);
        mBookSingleDesc = (TextView) findViewById(R.id.descField);
        mBookSinglePrice = (TextView) findViewById(R.id.priceField);
        mBookSingleImage=(ImageView)findViewById(R.id.singleBookImage);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.action_home){
                    startActivity(new Intent(BookSingleActivity.this, MainActivity.class));
                }
                else if(item.getItemId()==R.id.action_chat){
                    startActivity(new Intent(BookSingleActivity.this, ChatActivity.class));
                }
                else if(item.getItemId()==R.id.action_map){
                    startActivity(new Intent(BookSingleActivity.this, MapsActivity.class));
                }
                return false;
            }
        });

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String book_title= (String) dataSnapshot.child("title").getValue();
                String book_desc=(String) dataSnapshot.child("desc").getValue();
                String book_image=(String) dataSnapshot.child("image").getValue();
                String book_price=(String) dataSnapshot.child("price").getValue();

                mBookSingleTitle.setText(book_title);
                mBookSingleDesc.setText(book_desc);
                mBookSinglePrice.setText(book_price);

                Picasso.with(BookSingleActivity.this).load(book_image).into(mBookSingleImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
