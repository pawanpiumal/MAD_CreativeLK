package com.example.creativelk.Database;

import android.provider.BaseColumns;

public class ArtistMaster {

    private ArtistMaster(){}

    protected static class PhotographDetails implements BaseColumns{
        protected static final String TABLE_NAME = "photographdetails";
        protected static final String COLUMN_NAME_PHOTOGRAPHNAME = "photographname";
        protected static final String COLUMN_NAME_ARTISTID = "artistid";
        protected static final String COLUMN_NAME_PHOTOCATEGORY = "photocaterogy";
        protected static final String COLUMN_NAME_IMAGE = "image";

    }

    protected static class ArtistDetails implements BaseColumns{
        protected static final String TABLE_NAME = "artistdetails";
        protected static final String COLUMN_NAME_ARTISTNAME = "artistname";

    }

}
