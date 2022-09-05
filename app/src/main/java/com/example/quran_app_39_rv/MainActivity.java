package com.example.quran_app_39_rv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahListAdapter.SelectedSurah {

    RecyclerView allSurahsList;
    NavigationView navbar;
    ActionBarDrawerToggle navbarToggler;
    DrawerLayout drawer;
    String translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translate = getIntent().getStringExtra("Translation");
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigationToolbar);
        setSupportActionBar(toolbar);

        navbar = (NavigationView) findViewById(R.id.navHeader);
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        navbarToggler = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(navbarToggler);
        navbarToggler.syncState();
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String translation = "none";
                switch (item.getItemId()){
                    case R.id.engTranslation:
                        Toast.makeText(MainActivity.this, "Surahs will be show with English Translation", Toast.LENGTH_SHORT).show();
                        translation = "Mufti_Taqi_Usmani";
                        break;
                    case R.id.urduTranslation:
                        Toast.makeText(MainActivity.this, "Surahs will be show with Urdu Translation", Toast.LENGTH_SHORT).show();
                        translation = "Fateh_Muhammad_Jalandhri";
                        break;
                    case R.id.noTranslation:
                        Toast.makeText(MainActivity.this, "Surahs will be shown with only Arabic", Toast.LENGTH_SHORT).show();
                        translation = "none";
                        break;
                }
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("Translation", translation);
                startActivity(intent);
                return false;
            }
        });
        allSurahsList = findViewById(R.id.allSurahsList);
        DBHelper db = new DBHelper(MainActivity.this);
        db.CreateDatabase();
        List<String> allSurahs = db.getAllSurahs();
        SurahListAdapter surahAdapter = new SurahListAdapter(getApplicationContext(), allSurahs, this);
        allSurahsList.setAdapter(surahAdapter);
        allSurahsList.setLayoutManager(new LinearLayoutManager(this));
//        allSurahsList.

    }
    @Override
    public void ShowSurah(int position) {
        Intent intent = new Intent(MainActivity.this, SurahActivity.class);
        intent.putExtra("Translation",translate);
        intent.putExtra("SurahId",position+1);
        startActivity(intent);
    }

}