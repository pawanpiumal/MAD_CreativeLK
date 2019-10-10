package com.example.creativelk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativelk.Database.DBHandler;

public class AddArtist extends AppCompatActivity {
    TextView textViewArtist;
    Button btnAddArtist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);

        textViewArtist = findViewById(R.id.etArtistName);
        btnAddArtist = findViewById(R.id.btnAddArtistadd);
        btnAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(AddArtist.this);
                String artistName = textViewArtist.getText().toString().trim();
                boolean status = dbHandler.addArtist(artistName);
                if(status){
                    Toast.makeText(AddArtist.this, "Artist Added Successfully.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddArtist.this, "Artist Not Added.",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
