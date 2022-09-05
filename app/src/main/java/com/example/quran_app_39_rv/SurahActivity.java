package com.example.quran_app_39_rv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SurahActivity extends AppCompatActivity {
    RecyclerView ayatList;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surah);
        DBHelper db = new DBHelper(SurahActivity.this);
        intent = getIntent();
        String translation = intent.getStringExtra("Translation");
        int surahId = intent.getIntExtra("SurahId",1);
        List<String> surah = db.getSurahById(surahId);
        ayatList = (RecyclerView) findViewById(R.id.surah);
        AyatAdapter ayatAdapter = new AyatAdapter(SurahActivity.this, surah, surahId, translation);
        ayatList.setAdapter(ayatAdapter);
        ayatList.setLayoutManager(new LinearLayoutManager(this));
    }
}
