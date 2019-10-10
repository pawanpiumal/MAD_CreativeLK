package com.example.creativelk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativelk.Database.DBHandler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

public class AddPhotograph extends AppCompatActivity {

    EditText EditTextPhotoName;
    Spinner EditTextArtistName;
    Spinner EditTextPhotoCategory;
    ImageView imageView;
    Button addPhoto;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photograph);

         EditTextPhotoName = findViewById(R.id.etphotoname);
         EditTextArtistName = findViewById(R.id.etArtistName);
         EditTextPhotoCategory = findViewById(R.id.etPhotoCategory);
         imageView = findViewById(R.id.imageView);
         addPhoto = findViewById(R.id.button);

         String[] categories = {"Landscape","Wildlife","Portrait","Wedding","Fashion","Black and White"};

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categories);
        EditTextPhotoCategory.setAdapter(categoryAdapter);

        DBHandler dbHandler = new DBHandler(this);
        List list = dbHandler.loadArtist();

        ArrayAdapter<String> ArtistAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        EditTextArtistName.setAdapter(ArtistAdapter);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = EditTextPhotoName.getText().toString().trim();
                String ArtistName= EditTextArtistName.getSelectedItem().toString().trim();
                String Category = EditTextPhotoCategory.getSelectedItem().toString().trim();


                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                byte[] image = outputStream.toByteArray();

                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(ArtistName)||TextUtils.isEmpty(Category)|| image == null){
                    Toast.makeText(AddPhotograph.this,"Fill all the Information",Toast.LENGTH_SHORT).show();
                }else {
                    DBHandler dbHandler1 = new DBHandler(AddPhotograph.this);
                    long status = dbHandler1.addPhotos(name, ArtistName, Category, image);
                    if(status < 0 ){
                        Toast.makeText(AddPhotograph.this,"Not Added",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddPhotograph.this,"Added",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
