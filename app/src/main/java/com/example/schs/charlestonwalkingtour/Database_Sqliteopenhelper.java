package com.example.schs.charlestonwalkingtour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

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

        String[] names = {"South Carolina Historical Society","First Baptist Church","The Pink House"
                ,"The Branford-Horry House"};
        String[] types = {"museum","church","house","house"};
        Double[] lats = {32.777245,32.773923,32.7775,32.774444};
        Double[] longs = {-79.930942,-79.930063,-79.9289,-79.931389};
        String[] imglinks = {"http://www.nps.gov/nr/travel/charleston/buildings/fir1.jpg",
                "http://www.southcarolinahistoricalsociety.org/wp-content/uploads/2015/03/schs_first_baptist_chruch.jpg",
                "http://www.southcarolinahistoricalsociety.org/wp-content/uploads/2015/04/2-30-1-17-Chalmers-St.jpg",
                "http://www.southcarolinahistoricalsociety.org/wp-content/uploads/2015/04/2-PAM-975.71-1911-Horry-House.jpg"};
        String[] desc = {"My first datapoint","Regarded as the ‘Mother Church of the Southern Baptist " +
                "denomination, Charleston’s First Baptist Church at 61 Church Street has its roots with" +
                " a congregation organized in Kittery, Maine, in 1682.  Persecution by Puritans in New" +
                " England forced them to immigrate to Carolina, where they established themselves as one " +
                "of the five earliest congregations in the colon. Robert Mills designed this church in 1822" +
                " in a bold form of the Greek Revival Style.  He described it in a later book as 'the best" +
                " specimen of correct taste in architecture of the modern buildings of this city.'  To the" +
                " north stands 69 Church, known as the Capers-Motte House, built around 1750.  The Smith family" +
                " lived in this house the most famous of whom being Alice Ravenel Huger Smith, whose" +
                " watercolors are considered to be a high point of the Charleston Renaissance.  A subsequent" +
                " owner removed the piazzas seen in this photo.","Located at 17 Chalmers St., the Pink House is " +
                "a familiar Charleston landmark, long a favorite subject of photographers and artists " +
                "attempting to capture the city's unique charm.  The date of construction for the building" +
                " is usually placed in the second decade of the eighteenth century, making it one of Charleston's" +
                " oldest structures.  A tavern-keeper owned the house by 1750, as this area was known as " +
                "Charleston's bawdy district during the colonial era.","Merchant Benjamin Savage built this" +
                " home between 1747 and 1750. though residents and members have known it for years as the " +
                "Branford-Horry house.  Savage's niece, Elizabeth Savage, who later married William Branford, " +
                "inherited the house. Elias Horry, a grandson. forms the other half of the house's name.  Horry " +
                "was the president of the South Carolina Canal and Railroad Company, which operated the" +
                " longest railroad in the world in the 1830's, from Charleston th Hamburg, SC, 131 miles in" +
                " distance.  He is credited with adding the home's piazzas over Meeting Street, which have" +
                " sheltered a bus stop for generations.  Immediately to the right of this home is the property'" +
                "s old stable, It was adapted into a residence in the early twentieth century and was the home" +
                " of federal judge Waites Waring.  Judge Waring wrote many important judicial opinions in the " +
                "civil rights movement's early days, most significantly a dissent that formed the basis of the Supreme" +
                " Court's later ruling on Brown v. Board of Education"};

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

    public Cursor fetchAllMarkers(SQLiteDatabase db, String where){
        Cursor c = db.query(Database_Contract.TABLE_NAME,
                new String[] {key_id,key_name,
                        key_type,key_lat,key_long,key_imglink,key_desc},
                where,null,null,null,null);
//        if (c != null)
//            c.moveToFirst();

        return c;
    }
    public Cursor fetchImageURL(SQLiteDatabase db, String name){
        Cursor c = db.query(Database_Contract.TABLE_NAME,
                new String[] {key_imglink},
                "name=?", new String[] {name}, null, null, null);
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
