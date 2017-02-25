package com.example.ibrahim.unibook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBookList;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mBookList =(RecyclerView)findViewById(R.id.book_list);
        mBookList.setHasFixedSize(true);
        mBookList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Book,BookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(Book.class,R.layout.book_row,BookViewHolder.class,mDatabase ) {
            @Override
            protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {
              final String post_key=getRef(position).getKey();
               viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent singleBookIntent= new Intent(MainActivity.this,BookSingleActivity.class);
                        singleBookIntent.putExtra("book_id",post_key );
                        startActivity(singleBookIntent);

                    }
                });
            }

        };
        mBookList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public BookViewHolder(View itemView) {
            super(itemView);
            mView  =itemView;
        }
        public void setTitle(String title){
            TextView book_title=(TextView)mView.findViewById(R.id.book_title);
            book_title.setText(title);
        }

        public void setDesc(String desc){
            TextView book_desc=(TextView)mView.findViewById(R.id.desc_title);
            book_desc.setText(desc);
        }
        public void setPrice(String price){
            TextView book_price=(TextView)mView.findViewById(R.id.price_title);
            book_price.setText(price);
        }

        public void setImage(Context ctx, String image){
            ImageView book_image=(ImageView) mView.findViewById(R.id.book_image);
            Picasso.with(ctx).load(image).into(book_image);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.action_add){
        startActivity(new Intent(MainActivity.this, SellerInfo.class));
    }
        return super.onOptionsItemSelected(item);
    }
}
