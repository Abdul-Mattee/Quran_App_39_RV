package com.example.quran_app_39_rv;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SurahListAdapter extends RecyclerView.Adapter<SurahListAdapter.MyViewHolder> {

    Context c;
    List<String> allSurahs;
    LayoutInflater inflater;
    TextView surahName;
    TextView ayatCountView;
    SelectedSurah surah;
    public SurahListAdapter(Context context, List<String> surahList, SelectedSurah _surah){
        c = context;
        surah = _surah;
        allSurahs = surahList;
    }
    @NonNull
    @Override
    public SurahListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.activity_surah_recycler_view, parent, false);
        return new SurahListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahListAdapter.MyViewHolder holder, int position) {
        DBHelper db = new DBHelper(c);
        holder.surahName.setText(allSurahs.get(position));
        int ayatCount = db.getSurahAyatCount(position+1);
        holder.ayatCountView.setText("آيات : " + String.valueOf(ayatCount));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return allSurahs.size();
    }
    public interface SelectedSurah{
        void ShowSurah(int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView surahName;
        TextView ayatCountView;
        public MyViewHolder(@NotNull View itemView){
            super(itemView);
            surahName = itemView.findViewById(R.id.surahNameView);
            ayatCountView = itemView.findViewById(R.id.ayatConuter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    surah.ShowSurah(getAdapterPosition());
                }
            });
        }
    }
}