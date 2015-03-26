package com.example.schs.charlestonwalkingtour;

public final class Database_Contract {
    public static final String DB_NAME = "map_markers.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "map_markers";
    public static final String[] COLUMNS =
            {"_id","name","type","lat","long"};
    /*
    _id ~ required by Android
    name ~ name of marker (could be the name of the location)
    type ~ e.g. church
    lat ~ latitude
    long ~ longitude
     */
    private Database_Contract() {} // stops instantiation
}
