package com.example.creativelk.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.creativelk.AddArtist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Creative.db";

    public DBHandler(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String createPhotographDetails = "CREATE TABLE "+ArtistMaster.PhotographDetails.TABLE_NAME +"( "+
                ArtistMaster.PhotographDetails._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ArtistMaster.PhotographDetails.COLUMN_NAME_PHOTOGRAPHNAME +" TEXT ," +
                ArtistMaster.PhotographDetails.COLUMN_NAME_ARTISTID + " INTEGER, " +
                ArtistMaster.PhotographDetails.COLUMN_NAME_IMAGE + " BLOB, " +
                ArtistMaster.PhotographDetails.COLUMN_NAME_PHOTOCATEGORY + " TEXT, "+
                " FOREIGN KEY("+ ArtistMaster.PhotographDetails.COLUMN_NAME_ARTISTID+") REFERENCES "
                +ArtistMaster.ArtistDetails.TABLE_NAME+"("+ ArtistMaster.ArtistDetails._ID+"))";

        db.execSQL(createPhotographDetails);


        String createArtistDetails = "CREATE TABLE "+ ArtistMaster.ArtistDetails.TABLE_NAME+"("+
                ArtistMaster.ArtistDetails._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME +" TEXT)";

        db.execSQL(createArtistDetails);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ArtistMaster.ArtistDetails.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ArtistMaster.PhotographDetails.TABLE_NAME);

    }


    public long addPhotos(String name, String ArtistName, String Category, byte[] image){

        SQLiteDatabase db1 = getReadableDatabase();
        Cursor artistIDCursor = db1.query(ArtistMaster.ArtistDetails.TABLE_NAME,
                new String[]{ArtistMaster.ArtistDetails._ID},
                ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME +" = ? ",
                new String[]{ArtistName},
                null,
                null,
                null);


        if(artistIDCursor.getCount() < 1){

            return -1;
        }else {
            int artistID = 0;
            while (artistIDCursor.moveToNext()) {
               artistID = artistIDCursor.getInt(artistIDCursor.getColumnIndexOrThrow(ArtistMaster.ArtistDetails._ID));
            }
            ContentValues values = new ContentValues();
            values.put(ArtistMaster.PhotographDetails.COLUMN_NAME_PHOTOGRAPHNAME, name);
            values.put(ArtistMaster.PhotographDetails.COLUMN_NAME_ARTISTID, artistID);
            values.put(ArtistMaster.PhotographDetails.COLUMN_NAME_PHOTOCATEGORY, Category);
            values.put(ArtistMaster.PhotographDetails.COLUMN_NAME_IMAGE, image);
            SQLiteDatabase db = getWritableDatabase();
            long id = db.insert(
                    ArtistMaster.PhotographDetails.TABLE_NAME,null,values);

            return id;
        }

    }

    public boolean addArtist(String name){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME, name);
        long id =  db.insert(ArtistMaster.ArtistDetails.TABLE_NAME, null, values);

        if(id<0){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteDetails(String tableName, String columnName, String name){
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(tableName,columnName + " = ?", new String[]{name});
        if(count < 1){
            return false;
        }else{
            return true;
        }
    }


    public List<String> loadArtist(){
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.query(ArtistMaster.ArtistDetails.TABLE_NAME,
                new String[]{ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME},
                null,
                null,
                null,
                null,
                ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME+ " ASC");
        ArrayList artistNames = new ArrayList();
        while (cursor.moveToNext()){
            String ArtistName = cursor.getString(cursor.getColumnIndexOrThrow(ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME));
            artistNames.add(ArtistName);
        }

        return  artistNames;
    }


    public ArrayList<byte[]> searchPhotograph(String artiseName){

        SQLiteDatabase db = getReadableDatabase();

        Cursor artistIDCursor = db.query(ArtistMaster.ArtistDetails.TABLE_NAME,
                new String[]{ArtistMaster.ArtistDetails._ID},
                ArtistMaster.ArtistDetails.COLUMN_NAME_ARTISTNAME +" = ? ",
                new String[]{artiseName},
                null,
                null,
                null);


        if(artistIDCursor.getCount() < 1){

            return null;
        }else {
            int artistID = 0;
            while (artistIDCursor.moveToNext()) {
                artistID = artistIDCursor.getInt(artistIDCursor.getColumnIndexOrThrow(ArtistMaster.ArtistDetails._ID));
            }

            Cursor cursor = db.query(ArtistMaster.PhotographDetails.TABLE_NAME,
                    new String[]{ArtistMaster.PhotographDetails.COLUMN_NAME_IMAGE},
                    ArtistMaster.PhotographDetails.COLUMN_NAME_ARTISTID + " = ?",
                    new String[]{Integer.toString(artistID)},
                    null,
                    null,
                    null);
            ArrayList list = new ArrayList();
            while (cursor.moveToNext()){
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(ArtistMaster.PhotographDetails.COLUMN_NAME_IMAGE));
                list.add(image);
            }
            return list;

        }

    }



}
