package com.example.creativelk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnAddArtist;
    Button btnAddPhoto;
    Button btnDelete;
    Button btnViewPhotoList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnAddArtist = findViewById(R.id.btnaddartist);
        Button btnAddPhoto = findViewById(R.id.btnaddphoto);
        Button btnDelete = findViewById(R.id.btndelete);
        Button btnViewPhotoList = findViewById(R.id.btnviewphotolist);

        btnAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddArtist.class);
                startActivity(intent);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhotograph.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RemovePhotoOrArtist.class);
                startActivity(intent);
            }
        });
        btnViewPhotoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewPhotos.class);
                startActivity(intent);
            }
        });




    }
}
