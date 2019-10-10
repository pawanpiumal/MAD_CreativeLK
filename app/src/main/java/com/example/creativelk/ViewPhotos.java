package com.example.creativelk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativelk.Database.DBHandler;

import java.util.List;

public class ViewPhotos extends AppCompatActivity {


    Spinner search;
    Button btnSearch;

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);



         search = findViewById(R.id.search);
         btnSearch = findViewById(R.id.button2);
         gridView = findViewById(R.id.gridView);


        DBHandler dbHandler = new DBHandler(this);
        List list = dbHandler.loadArtist();

        ArrayAdapter<String> ArtistAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        search.setAdapter(ArtistAdapter);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = search.getSelectedItem().toString().trim();
                if(TextUtils.isEmpty(artistName)){
                    Toast.makeText(ViewPhotos.this, "Artist Name Is Empty",Toast.LENGTH_SHORT).show();
                }else{
                    DBHandler dbHandler1 = new DBHandler(ViewPhotos.this);
                    ImageAdapter imageAdapter = new ImageAdapter(ViewPhotos.this,R.layout.gridimage,dbHandler1.searchPhotograph(artistName));
                    gridView.setAdapter(imageAdapter);

                }
            }
        });



    }
}
