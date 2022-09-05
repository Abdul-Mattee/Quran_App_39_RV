package com.example.quran_app_39_rv;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.MyViewHolder> {
    Context c;
    List<String> surah;
    LayoutInflater layoutInflater;
    String translation;
    int surahId;
    public AyatAdapter(Context context, List<String> _surah,int _surahId, String translate){
        c = context;
        surah = _surah;
        layoutInflater = LayoutInflater.from(context);
        translation = translate;
        surahId = _surahId;
    }

    @NonNull
    @Override
    public AyatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.activity_ayat, parent, false);
        return new AyatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AyatAdapter.MyViewHolder holder, int position) {
        DBHelper db = new DBHelper(c);
        holder.ayat.setText(surah.get(position));
        List<String> surahTranslation = null;
        if(!TextUtils.equals(translation, "none") && !TextUtils.isEmpty(translation)){
            surahTranslation = db.getSurahTranslation(surahId, translation);
            holder.ayatTranslation.setText(surahTranslation.get(position));
        }else{
            holder.ayatTranslation.setText("");
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return surah.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ayat;
        TextView ayatTranslation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ayat = itemView.findViewById(R.id.singleAyat);
            ayatTranslation = itemView.findViewById(R.id.ayatTranslation);
        }
    }
}
