package com.ear.muestra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ear.muestra.models.ApiData;

import java.util.ArrayList;

/**
 * Created by Esequiel A R on 24/2/2099.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String DATA_TABLE_CREATE = "CREATE TABLE dates( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, " +
            "name TEXT, brewery_type TEXT,street TEXT,address_2 TEXT,address_3 TEXT," +
            "city TEXT,state TEXT,county_province TEXT,postal_code TEXT,country TEXT," +
            "longitude TEXT,latitude TEXT,phone TEXT,website_url TEXT,updated_at TEXT," +
            "created_at TEXT)";

    private static final String DB_NAME = "breweries.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATA_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertar(String id,String name,String brewery_type,String street,String address_2,
                         String address_3,String city,String state,String county_province,
                         String postal_code,String country,String longitude,String latitude,
                         String phone,String website_url,String updated_at,String created_at){

        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("brewery_type", brewery_type);
        cv.put("street", street);
        cv.put("address_2", address_2);
        cv.put("address_3", address_3);
        cv.put("city", city);
        cv.put("state", state);
        cv.put("county_province", county_province);
        cv.put("postal_code", postal_code);
        cv.put("country", country);
        cv.put("longitude", longitude);
        cv.put("latitude", latitude);
        cv.put("phone", phone);
        cv.put("website_url", website_url);
        cv.put("updated_at", updated_at);
        cv.put("created_at", created_at);

        db.insert("dates", null, cv);

    }

    //Borrar todos los datos
    public void borrarTodo(){

        db.delete("dates", null, null);

    }


    public ArrayList<ApiData> getNames(){
        ArrayList<ApiData> lista=new ArrayList<>();

        Cursor c = db.rawQuery("select _id, name from dates ORDER BY id ASC", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {

                int _id=c.getInt(c.getColumnIndex("_id"));
                String name=c.getString(c.getColumnIndex("name"));

                ApiData com =new ApiData(_id, name);

                lista.add(com);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    public ArrayList<ApiData> getComments(){
        ArrayList<ApiData> lista=new ArrayList<>();

        Cursor c = db.rawQuery("select _id, id, name,brewery_type,street,address_2,address_3,city,state" +
                ",county_province,postal_code,country,longitude,latitude" +
                ",phone,website_url,updated_at,created_at from dates ORDER BY name ASC", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {

                int _id=c.getInt(c.getColumnIndex("_id"));
                String id=c.getString(c.getColumnIndex("id"));
                String name=c.getString(c.getColumnIndex("name"));
                String brewery_type=c.getString(c.getColumnIndex("brewery_type"));
                String street=c.getString(c.getColumnIndex("street"));
                String address_2=c.getString(c.getColumnIndex("address_2"));
                String address_3=c.getString(c.getColumnIndex("address_3"));
                String city=c.getString(c.getColumnIndex("city"));
                String state=c.getString(c.getColumnIndex("state"));
                String county_province=c.getString(c.getColumnIndex("county_province"));
                String postal_code=c.getString(c.getColumnIndex("postal_code"));
                String country=c.getString(c.getColumnIndex("country"));
                String longitude=c.getString(c.getColumnIndex("longitude"));
                String latitude=c.getString(c.getColumnIndex("latitude"));
                String phone=c.getString(c.getColumnIndex("phone"));
                String website_url=c.getString(c.getColumnIndex("website_url"));
                String updated_at=c.getString(c.getColumnIndex("updated_at"));
                String created_at=c.getString(c.getColumnIndex("created_at"));

                ApiData com =new ApiData(_id,id, name,brewery_type,street,address_2,address_3,city,state
                        ,county_province,postal_code,country,longitude,latitude
                        ,phone,website_url,updated_at,created_at);

                lista.add(com);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

}
