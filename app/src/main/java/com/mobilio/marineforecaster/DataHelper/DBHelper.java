package com.mobilio.marineforecaster.DataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by LENOVO PC on 11-02-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private String db_path;
    private SQLiteDatabase myDataBase;

    public static final String TABLE_NAME = "poi";

    private static String	dbname = "pointofinterest";
    private static final int DATABASE_VERSION = 55;

    //Greek and english

    public static final String TABLE = "poi";


    public static final String COLUMN_STATE = "State";
    public static final String COLUMN_CITY = "City";
    public static final String COLUMN_PHONE1 = "Phone1";
    public static final String COLUMN_PHONE2 = "Phone2";
    public static final String COLUMN_PHONE3 = "Phone3";
    public static final String COLUMN_PHONE4 = "Phone4";
    public static final String COLUMN_LATITUDE = "Lat";
    public static final String COLUMN_LONGITUDE = "Long";
    public static final String COLUMN_CATEGORY = "Category";


    public DBHelper(Context context) throws IOException {
        super(context, dbname, null , DATABASE_VERSION);
        this.context = context;
        db_path = "/data/data/com.mobilio.marineforecaster/databases/";
        importIfNotExist();
    }
    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */

    public boolean checkExist(){

        SQLiteDatabase checkDB = null;

        try {
            String myPath = db_path + dbname;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            e.printStackTrace();
            // database does't exist yet.

        } catch (Exception ep) {
            ep.printStackTrace();
        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    private void opendatabase() {
        //Open the database
        String mypath = db_path + dbname;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);


    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void importIfNotExist() throws IOException {
        boolean dbExist = checkExist();

        if (dbExist) {
            opendatabase();
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {
                copyDatabase();


            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }


    }

    private void copyDatabase() throws IOException {

        InputStream is = context.getAssets().open(dbname);

        OutputStream os = new FileOutputStream(db_path + dbname);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();
        this.close();
    }

    @Override
    public String getDatabaseName() {
        return dbname;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public synchronized void close(){
        if(myDataBase != null){
            myDataBase.close();
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
