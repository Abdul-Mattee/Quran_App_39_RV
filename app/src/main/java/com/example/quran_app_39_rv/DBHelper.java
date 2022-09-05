package com.example.quran_app_39_rv;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    Context c;
    private SQLiteDatabase myDataBase;
    private final static String DATABASE_NAME = "database.db";
    private final static String DATABASE_PATH = "/data/data/com.example.quran_app_39_rv/databases/";
    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
        c = context;
    }
    public void CreateDatabase(){
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.i("DB Message", "Database exist");
        } else {
            Log.i("DB Message", "Database doesn't exist");
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }
    private void copyDataBase() throws IOException
    {

        InputStream mInput = c.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public void openDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public ArrayList<String> getAllSurahs(){
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("Select SurahNameU from tsurah", null);
        ArrayList<String> allSurahs = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                allSurahs.add(new String(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allSurahs;
    }
    public int getSurahAyatCount(int surahId){
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("Select AyaNo from tayah where SuraID= " + surahId + " order by AyaNo desc limit 1", null);
        int totalAyat = 0;
        if(cursor.moveToFirst()){
            totalAyat = Integer.parseInt(cursor.getString(0));
        }
        return totalAyat;
    }
    public ArrayList<String> getSurahById(int surahId){
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("Select Arabic_Text from tayah where SuraID= "+ surahId, null);
        ArrayList<String> surah = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                surah.add(new String(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return surah;
    }
    public ArrayList<String> getSurahTranslation(int surahId, String translation){
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("Select " + translation + " from tayah where SuraID= "+ surahId, null);
        ArrayList<String> surahTranslation = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                surahTranslation.add(new String(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return surahTranslation;
    }
}
