package com.example.schs.charlestonwalkingtour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Sqliteopenhelper extends SQLiteOpenHelper {
    public static final String key_id = "_id";
    public static final String key_name = "name";
    public static final String key_type = "type";
    public static final String key_lat = "lat";
    public static final String key_long = "long";
    public static final String key_imglink = "imglink";
    public static final String key_desc = "desc";
    public Database_Sqliteopenhelper(Context context){
        super(context, Database_Contract.DB_NAME, null,
                Database_Contract.DB_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        String createSql =
                "CREATE TABLE IF NOT EXISTS " + Database_Contract.TABLE_NAME
                        + "("
                        + "  "+key_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "  "+key_name+" TEXT NOT NULL,"
                        + "  "+key_type+" TEXT NOT NULL,"
                        + "  "+key_lat+" REAL NOT NULL,"
                        + "  "+key_long+" REAL NOT NULL,"
                        + "  "+key_imglink+" TEXT,"
                        + "  "+key_desc+" TEXT"
                        + ")";
        db.execSQL(createSql);
        insertContacts(db);

    }

    private void insertContacts(SQLiteDatabase db){
        // initial inserts for the database
        String tableName = Database_Contract.TABLE_NAME;
        String[] names = {"South Carolina Historical Society"};
        String[] types = {"Museum"};
        Double[] lats = {32.777245};
        Double[] longs = {-79.930942};
        String[] imglinks = {"http://www.nps.gov/nr/travel/charleston/buildings/fir1.jpg"};
        String[] desc = {"My first datapoint"};

        for(int i = 0; i < names.length; i++){
            ContentValues values = new ContentValues();
            values.put(key_name,names[i]);
            values.put(key_type,types[i]);
            values.put(key_lat,lats[i]);
            values.put(key_long,longs[i]);
            values.put(key_imglink,imglinks[i]);
            values.put(key_desc,desc[i]);
            db.insert(tableName, null, values);
        }
    }

    public Cursor fetchAllMarkers(SQLiteDatabase db){
        Cursor c = db.query(Database_Contract.TABLE_NAME,
                new String[] {key_id,key_name,
                        key_type,key_lat,key_long,key_imglink,key_desc},
                null,null,null,null,null);
        if (c != null)
            c.moveToFirst();
        return c;
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
        // This is version 1 so no actions are required.
        // Possible actions include dropping/recreating
        // tables, saving/restoring data in tables, etc.
        db.execSQL("DROP TABLE IF EXISTS " + Database_Contract.TABLE_NAME);
        onCreate(db);
    }
}
