package com.example.ibrahim.unibook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SellerInfo extends AppCompatActivity {

    private ImageButton mSelectImage;
    private Uri mImageUri =null;
    private Button mSubmitBtn;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private EditText mPostTitle, mPostDescription, mPostPrice;
    private static final int GALLERY_REQUEST=2;
    private static final int CAMERA_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_info);

        mStorage = FirebaseStorage.getInstance().getReference();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");

        mSelectImage =(ImageButton)findViewById(R.id.singleBookImage);

        mPostTitle=(EditText)findViewById(R.id.titleField);

        mPostDescription=(EditText)findViewById(R.id.descField);

        mPostPrice=(EditText)findViewById(R.id.priceField);

        mSubmitBtn=(Button)findViewById(R.id.submitBtn);

        mProgress= new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);


            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startPosting();
            }
        });
    }

    private void startPosting() {
        mProgress.setMessage("Posting to Market");

        final String title_val=mPostTitle.getText().toString().trim();
        final String desc_val=mPostDescription.getText().toString().trim();
        final String price_val=mPostPrice.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val)&& !TextUtils.isEmpty(price_val)&& mImageUri !=null){
            mProgress.show();
            StorageReference filepath= mStorage.child("Blog_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost =mDatabase.push(); //push means unique id
                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("price").setValue(price_val);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgress.dismiss();
                    startActivity(new Intent(SellerInfo.this, MainActivity.class));

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            mImageUri =data.getData();
            mSelectImage.setImageURI(mImageUri);


        }
    }
}
