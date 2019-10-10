package com.example.creativelk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.creativelk.Database.ArtistMaster;
import com.example.creativelk.Database.DBHandler;

import java.util.List;

public class RemovePhotoOrArtist extends AppCompatActivity {


    AutoCompleteTextView EditTextPhotoName;
    Spinner EditTextArtistName;
    Button btnDeletePhoto;
    Button btnDeleteArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_photo_or_artist);


         EditTextPhotoName = findViewById(R.id.etPhotoName);
         EditTextArtistName = findViewById(R.id.etArtistName);
         btnDeletePhoto = findViewById(R.id.btndeletePhoto);
         btnDeleteArtist = findViewById(R.id.btndeleteArtist);


        DBHandler dbHandler = new DBHandler(this);
        List list = dbHandler.loadArtist();

        ArrayAdapter<String> ArtistAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        EditTextArtistName.setAdapter(ArtistAdapter);


        btnDeleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistname = EditTextArtistName.getSelectedItem().toString().trim();
                if(TextUtils.isEmpty(artistname)){
                    Toast.makeText(RemovePhotoOrArtist.this,"Fill the Artist Name", Toast.LENGTH_SHORT).show();
                }else {
                    DBHandler dbHandler1 = new DBHandler(RemovePhotoOrArtist.this);
                    boolean status = dbHandler1.deleteDetails("artistdetails", "artistname",artistname );
                    if(status){
                        Toast.makeText(RemovePhotoOrArtist.this,"Deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RemovePhotoOrArtist.this,"Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDeletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String photoName = EditTextPhotoName.getText().toString().trim();
                if(TextUtils.isEmpty(photoName)){
                    Toast.makeText(RemovePhotoOrArtist.this,"Fill the Photo Name", Toast.LENGTH_SHORT).show();
                }else {
                    DBHandler dbHandler1 = new DBHandler(RemovePhotoOrArtist.this);
                    boolean status = dbHandler1.deleteDetails("photographdetails", "photographname",photoName );
                    if(status){
                        Toast.makeText(RemovePhotoOrArtist.this,"Deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RemovePhotoOrArtist.this,"Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
